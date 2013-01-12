package com.marthastewart.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.taskqueue.TaskOptions;
import com.marthastewart.ArticleInfo;
import com.marthastewart.MarthastewartClient;
import com.marthastewart.environment.Http;
import com.marthastewart.gae.QueueUtils;

public class Digest {
	private Digest() {}
	
	private static final MarthastewartClient client =
			new MarthastewartClient(Http.getRequestFactory(), Http.CONNECT_TIMEOUT, Http.READ_TIMEOUT);
	
	public static void queryNewArticlesDeferred() {
	    QueueUtils.addTasks("digest", Collections.singletonList(createDeferredQueryTask()));
	}
	
	public static void queryNewArticles(PersistenceManager pm) throws IOException {
		final List<String> articleUrls = client.getArticleUrls();
		
		final List<TaskOptions> tasks = new ArrayList<TaskOptions>(articleUrls.size());
		for (String articleUrl : articleUrls) {
			tasks.add(createDeferredDigestTask(articleUrl));
		}
		QueueUtils.addTasks("digest", tasks);
	}
	
	public static void queryArticleInfo(PersistenceManager pm, String articleUrl) {
		final String id = client.getId(articleUrl);
		if ((id == null) || (com.marthastewart.persist.ArticleInfo.exists(pm, id))) {
			return;
		}
		
		try {
			final ArticleInfo articleInfo = client.getArticleInfo(articleUrl);
			pm.makePersistent(new com.marthastewart.persist.ArticleInfo(articleInfo));
		} catch (IOException e) {}
	}
	
	private static TaskOptions createDeferredQueryTask() {
		return TaskOptions.Builder
				.withUrl("/admin/task/digest")
	    		.method(TaskOptions.Method.GET);
	}
	
	private static TaskOptions createDeferredDigestTask(String articleUrl) {
		return TaskOptions.Builder
				.withUrl("/admin/task/digest")
	    		.param("url", articleUrl)
	    		.method(TaskOptions.Method.GET);
	}
}
