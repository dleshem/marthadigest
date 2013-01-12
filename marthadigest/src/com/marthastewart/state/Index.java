package com.marthastewart.state;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.marthastewart.ArticleInfo;
import com.marthastewart.MarthastewartClient;

public class Index {
	private Index() {}
	
	public static String getHtml(PersistenceManager pm, Integer year, Integer month) {
		final List<ArticleInfo> articleInfos = com.marthastewart.persist.ArticleInfo.queryArticleInfos(
				pm, ArticleInfo.TYPE_RECIPE, year, month);
		
		final StringBuilder builder = new StringBuilder();
		
		builder.append("<!DOCTYPE html>\n");
		builder.append("<html>\n");
		builder.append("<head>\n");
		builder.append("<title>\n");
		builder.append("</title>\n");
		builder.append("</head>\n");

		builder.append("<body lang=\"en\">\n");
		
		final MarthastewartClient client = new MarthastewartClient(null, null, null);
		for (ArticleInfo articleInfo : articleInfos) {
			// TODO: escape articleInfo fields
			final String shortName = client.getShortName(articleInfo.url).replaceAll("-", " ");
			builder.append("<a href=\"").append(articleInfo.url).append("\">").append(shortName).append("</a>");
			if (articleInfo.source != null) {
				builder.append(" (").append(articleInfo.source).append(")");
			}
			builder.append("<br />\n");
		}
		
		builder.append("</body>\n");
		builder.append("</html>\n");
		
		return builder.toString();
	}

}
