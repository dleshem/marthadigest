package com.marthastewart.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.marthastewart.state.Digest;

/**
 * Initiates a periodic marthastewart.com query session.
 *
 * This resource is meant to run as an AppEngine scheduled task (cron). We make sure
 * it can only be accessed by an AppEngine admin (in WEB.XML).
 *   
 * @author DL
 */
public class DigestCronResource extends ServerResource {
	@Get()
	public void doGet() {
		Digest.queryNewArticlesDeferred();
	}
}
