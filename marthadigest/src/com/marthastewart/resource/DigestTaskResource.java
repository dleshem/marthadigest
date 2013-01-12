package com.marthastewart.resource;

import java.io.IOException;

import javax.jdo.PersistenceManager;

import org.restlet.resource.Get;
import org.slf4j.LoggerFactory;

import com.marthastewart.persist.PMF;
import com.marthastewart.restlet.ParameterizedServerResource;
import com.marthastewart.state.Digest;

/**
 * Queries marthastewart.com for new articles or for individual article information.
 *
 * This resource is meant to run as an AppEngine queued task. We make sure
 * it can only be accessed by an AppEngine admin (in WEB.XML).
 *   
 * @author DL
 */
public class DigestTaskResource extends ParameterizedServerResource {
	private String url;
	
    @Override
    public void doInit() {
    	super.doInit();
    	
    	url = params.get("url");
    }
	
	@Get
	public void doGet() {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if (url == null) {
				Digest.queryNewArticles(pm);
			} else {
				Digest.queryArticleInfo(pm, url);
			}
		} catch (IOException e) {
			LoggerFactory.getLogger(DigestTaskResource.class).error("Digestion error", e);
		} finally {
			pm.close();
		}
	}
}
