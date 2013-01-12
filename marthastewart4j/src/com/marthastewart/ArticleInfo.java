package com.marthastewart;

public class ArticleInfo {
	public static final String TYPE_RECIPE = "recipe";
	public static final String TYPE_ARTICLE = "article";
	public static final String TYPE_CENTER = "center";
	public static final String TYPE_VIDEO = "video";
	public static final String TYPE_DINNERTONIGHT = "dinnertonight";
	public static final String TYPE_CONTENTGALLERY = "contentgallery";
	public static final String TYPE_ADVERTISERRECIPE = "advertiser-recipe";
	public static final String TYPE_ADVERTISERCONTENTCOLLECTION = "advertiser-content-collection";
	
	public static final String SOURCE_MADHUNGRY = "Mad Hungry";
	public static final String SOURCE_LIVING = "Martha Stewart Living";
	public static final String SOURCE_SHOW = "The Martha Stewart Show";
	public static final String SOURCE_EVERYDAYFOOD = "Everyday Food";
	public static final String SOURCE_COOKINGSCHOOL = "Martha Stewart's Cooking School";
	
	public final String id;
	public final String url;
	public final String type;
	public final String source;
	public final Integer year;
	public final Integer month;
	
	public ArticleInfo(String id, String url, String type, String source, Integer year, Integer month) {
		this.id = id;
		this.url = url;
		this.type = type;
		this.source = source;
		this.year = year;
		this.month = month;
	}
}
