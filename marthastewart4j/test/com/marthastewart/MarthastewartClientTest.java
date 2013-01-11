package com.marthastewart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

public class MarthastewartClientTest {
	private static final HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();

	@Test
	public void testGetArticleUrls() throws IOException {
		final MarthastewartClient client = new MarthastewartClient(requestFactory, 60000, 60000);
		final List<String> articleUrls = client.getArticleUrls();
		assertTrue(!articleUrls.isEmpty());
	}
	
	@Test
	public void testArticleInfo1() throws IOException {
		final String url = "http://www.marthastewart.com/330153/chicken-fricassee";
		final MarthastewartClient client = new MarthastewartClient(requestFactory, 60000, 60000);
		final ArticleInfo info = client.getArticleInfo(url);
		
		assertEquals(url, info.url);
		assertEquals(ArticleInfo.TYPE_RECIPE, info.type);
		assertEquals(ArticleInfo.SOURCE_MADHUNGRY, info.source);
		assertEquals(Integer.valueOf(2011), info.year);
		assertEquals(Integer.valueOf(1), info.month);
	}
	
	@Test
	public void testArticleInfo2() throws IOException {
		final String url = "http://www.marthastewart.com/925889/chicken-fricassee-fricassee-de-poulet-lancienne";
		final MarthastewartClient client = new MarthastewartClient(requestFactory, 60000, 60000);
		final ArticleInfo info = client.getArticleInfo(url);
		
		assertEquals(url, info.url);
		assertEquals(ArticleInfo.TYPE_RECIPE, info.type);
		assertEquals(ArticleInfo.SOURCE_LIVING, info.source);
		assertEquals(Integer.valueOf(2012), info.year);
		assertEquals(Integer.valueOf(10), info.month);
	}
	
	@Test
	public void testArticleInfo3() throws IOException {
		final String url = "http://www.marthastewart.com/332607/quinoa-muffins";
		final MarthastewartClient client = new MarthastewartClient(requestFactory, 60000, 60000);
		final ArticleInfo info = client.getArticleInfo(url);
		
		assertEquals(url, info.url);
		assertEquals(ArticleInfo.TYPE_RECIPE, info.type);
		assertEquals(ArticleInfo.SOURCE_SHOW, info.source);
		assertEquals(Integer.valueOf(2008), info.year);
		assertEquals(Integer.valueOf(1), info.month);
	}
	
	@Test
	public void testArticleInfo4() throws IOException {
		final String url = "http://www.marthastewart.com/925233/peanut-butter-filled-cupcakes";
		final MarthastewartClient client = new MarthastewartClient(requestFactory, 60000, 60000);
		final ArticleInfo info = client.getArticleInfo(url);
		
		assertEquals(url, info.url);
		assertEquals(ArticleInfo.TYPE_RECIPE, info.type);
		assertEquals(ArticleInfo.SOURCE_EVERYDAYFOOD, info.source);
		assertEquals(Integer.valueOf(2012), info.year);
		assertEquals(Integer.valueOf(9), info.month);
	}
	
	@Test
	public void testArticleInfo5() throws IOException {
		final String url = "http://www.marthastewart.com/274693/christmas-candy-assortment";
		final MarthastewartClient client = new MarthastewartClient(requestFactory, 60000, 60000);
		final ArticleInfo info = client.getArticleInfo(url);
		
		assertEquals(url, info.url);
		assertEquals(ArticleInfo.TYPE_CENTER, info.type);
		assertNull(info.source);
		assertNull(info.year);
		assertNull(info.month);
	}
	
	@Test
	public void testArticleInfo6() throws IOException {
		final String url = "http://www.marthastewart.com/269031/bandanna-party";
		final MarthastewartClient client = new MarthastewartClient(requestFactory, 60000, 60000);
		final ArticleInfo info = client.getArticleInfo(url);
		
		assertEquals(url, info.url);
		assertEquals(ArticleInfo.TYPE_ARTICLE, info.type);
		assertNull(info.source);
		assertNull(info.year);
		assertNull(info.month);
	}
}
