package com.marthastewart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;

public class MarthastewartClient {
	private static final Pattern ARTICLE_URL_PATTERN = Pattern.compile("http://www.marthastewart.com/(.+)/(.+)");
	
	private static final Pattern CITE_PATTERN = Pattern.compile("(.+),(.*)</cite>");
	private static final Pattern TYPE_PATTERN = Pattern.compile(".*<body.*node-type-(.+?)\\s.*");
	
	private static final String SITEMAP_URL = "http://www.marthastewart.com/sitemap.xml";
	
	private final HttpRequestFactory requestFactory;
	private final Integer connectTimeout;
	private final Integer readTimeout;

	public MarthastewartClient(HttpRequestFactory requestFactory, Integer connectTimeout, Integer readTimeout) {
		this.requestFactory = requestFactory;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}
	
	public List<String> getArticleUrls() throws IOException {
		final List<String> articleUrls = new LinkedList<String>();
		
		final SitemapParser sitemap = new SitemapParser(requestFactory, connectTimeout, readTimeout);
		final List<String> sitemapIndexUrls = sitemap.getLocations(SITEMAP_URL);
		for (String sitemapIndexUrl : sitemapIndexUrls) {
			final List<String> urls = sitemap.getLocations(sitemapIndexUrl);
			for (String url : urls) {
				final Matcher matcher = ARTICLE_URL_PATTERN.matcher(url);
				if (matcher.matches()) {
					articleUrls.add(url);
				}
			}
		}
		
		return articleUrls;
	}
	
	private static Integer getYear(String dateStr) {
		for (int year = 1995; year < 2030; ++year) {
			if (dateStr.contains(Integer.toString(year))) {
				return year;
			}
		}
		return null;
	}
	
	private static final String[] MONTHS = new String[] {
		"January", "February", "March", "April", "May", "June",
		"July", "August", "September", "October", "November", "December"
	};
	private static Integer getMonth(String dateStr) {
		for (int i = 0; i < MONTHS.length; ++i) {
			if (dateStr.contains(MONTHS[i])) {
				return i+1;
			}
		}
		return null;
	}
	
	public ArticleInfo getArticleInfo(String articleUrl) throws IOException {
    	final HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(articleUrl));
        if (connectTimeout != null) {
        	request.setConnectTimeout(connectTimeout);
        }
        if (readTimeout != null) {
        	request.setReadTimeout(readTimeout);
        }

        String type = null;
        String source = null;
        Integer year = null;
        Integer month = null;
        
        final HttpResponse response = request.execute();
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(response.getContent(), "UTF-8"));
            try {
            	String line;
            	while ((line = reader.readLine()) != null) {
            		if (line.length() > 300) {
            			continue;
            		}
            		final Matcher citeMatcher = CITE_PATTERN.matcher(line);
            		if (citeMatcher.matches()) {
            			if (citeMatcher.groupCount() == 2) {
            				source = citeMatcher.group(1).trim();
            				final String dateStr = citeMatcher.group(2).trim();
            				year = getYear(dateStr);
            				month = getMonth(dateStr);
            			}
            		}
            		
            		final Matcher typeMatcher = TYPE_PATTERN.matcher(line);
            		if (typeMatcher.matches()) {
            			if (typeMatcher.groupCount() == 1) {
            				type = typeMatcher.group(1);
            			}
            		}
            	}
            } finally {
            	reader.close();
            }
        } finally {
        	response.ignore();
        }
		
		return new ArticleInfo(articleUrl, type, source, year, month);
	}
}
