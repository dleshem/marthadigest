package com.marthastewart;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

public class SitemapParserTest {
	private static final HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();

	@Test
	public void test() throws IOException {
		final SitemapParser sitemap = new SitemapParser(requestFactory, 60000, 60000);
		final List<String> sitemapIndexUrls = sitemap.getLocations("http://www.marthastewart.com/sitemap.xml");
		assertTrue(!sitemapIndexUrls.isEmpty());
		
		final String sitemapIndexUrl = sitemapIndexUrls.iterator().next();
		final List<String> urls = sitemap.getLocations(sitemapIndexUrl);
		assertTrue(!urls.isEmpty());
	}
}
