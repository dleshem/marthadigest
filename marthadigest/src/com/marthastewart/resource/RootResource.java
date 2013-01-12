package com.marthastewart.resource;

import java.util.Calendar;
import java.util.TimeZone;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class RootResource extends ServerResource {
	@Get("html")
	public void doGet() {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jerusalem"));
		getResponse().redirectTemporary("/" + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) - (Calendar.JANUARY-1)));
	}
}
