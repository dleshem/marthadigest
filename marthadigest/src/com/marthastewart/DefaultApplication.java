package com.marthastewart;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.marthastewart.resource.DigestCronResource;
import com.marthastewart.resource.DigestTaskResource;
import com.marthastewart.resource.IndexResource;
import com.marthastewart.resource.RootResource;

public class DefaultApplication extends Application {
	public DefaultApplication() {}
	
	@Override
	public Restlet createInboundRoot() {
		final Router router = new Router(getContext());
		
		router.attach("/", RootResource.class);
		router.attach("/{year}/{month}", IndexResource.class);

		router.attach("/admin/cron/digest", DigestCronResource.class);
		router.attach("/admin/task/digest", DigestTaskResource.class);
		
		return router;
	}
}
