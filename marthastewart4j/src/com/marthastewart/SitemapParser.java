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

public class SitemapParser {
	private static final Pattern LOC_PATTERN = Pattern.compile(".*<loc>(.*)</loc>.*");
	
	private final HttpRequestFactory requestFactory;
	private final Integer connectTimeout;
	private final Integer readTimeout;
	
	public SitemapParser(HttpRequestFactory requestFactory, Integer connectTimeout, Integer readTimeout) {
		this.requestFactory = requestFactory;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}
	
	public List<String> getLocations(String url) throws IOException {
		final List<String> locations = new LinkedList<String>();
		
    	final HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
        if (connectTimeout != null) {
        	request.setConnectTimeout(connectTimeout);
        }
        if (readTimeout != null) {
        	request.setReadTimeout(readTimeout);
        }

        final HttpResponse response = request.execute();
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(response.getContent(), "UTF-8"));
            try {
            	String line;
            	while ((line = reader.readLine()) != null) {
            		final Matcher matcher = LOC_PATTERN.matcher(line);
            		if (matcher.matches()) {
            			if (matcher.groupCount() == 1) {
            				locations.add(matcher.group(1));
            			}
            		}
            	}
            } finally {
            	reader.close();
            }
        } finally {
        	response.ignore();
        }
		
		return locations;
	}

}
