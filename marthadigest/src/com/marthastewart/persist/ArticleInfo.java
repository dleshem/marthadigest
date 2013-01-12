package com.marthastewart.persist;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ArticleInfo {
	public ArticleInfo(com.marthastewart.ArticleInfo articleInfo) {
		this.id = articleInfo.id;
		this.url = articleInfo.url;
		this.type = articleInfo.type;
		this.source = articleInfo.source;
		this.year = articleInfo.year;
		this.month = articleInfo.month;
	}
	
	public com.marthastewart.ArticleInfo getArticleInfo() {
		return new com.marthastewart.ArticleInfo(id, url, type, source, year, month);
	}
	
	public static boolean exists(PersistenceManager pm, String id) {
		try {
	    	pm.getObjectById(ArticleInfo.class, id);
	    	return true;
	    } catch (JDOObjectNotFoundException e) {
	    	return false;
	    }
	}
	
	public static List<com.marthastewart.ArticleInfo> queryArticleInfos(PersistenceManager pm, String type, Integer year, Integer month) {
		final Query query = pm.newQuery(ArticleInfo.class);
		query.setFilter("(type == typeParam) && (year == yearParam) && (month == monthParam)");
		query.declareParameters("String typeParam, Integer yearParam, Integer monthParam");
		
		@SuppressWarnings("unchecked")
		final List<ArticleInfo> persistArticleInfos = (List<ArticleInfo>) query.execute(type, year, month);
		
		final List<com.marthastewart.ArticleInfo> articleInfos = new ArrayList<com.marthastewart.ArticleInfo>(persistArticleInfos.size());
		for (ArticleInfo persistArticleInfo : persistArticleInfos) {
			articleInfos.add(persistArticleInfo.getArticleInfo());
		}
		return articleInfos;
	}
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String id;
    
    @Persistent
    private String url;
    
    @Persistent
    private String type;

    @Persistent
    private String source;
    
    @Persistent
    private Integer year;
    
    @Persistent
    private Integer month;
}
