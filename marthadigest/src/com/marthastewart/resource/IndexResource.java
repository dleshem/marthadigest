package com.marthastewart.resource;

import javax.jdo.PersistenceManager;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.marthastewart.persist.PMF;
import com.marthastewart.state.Index;

public class IndexResource extends ServerResource {
	private Integer year;
	private Integer month;
	
    @Override  
    public void doInit() {
    	super.doInit();
    	
        final String yearStr = (String) getRequestAttributes().get("year");
        year = Integer.valueOf(yearStr);

        final String monthStr = (String) getRequestAttributes().get("month");
        month = Integer.valueOf(monthStr);
    }
    
	@Get("html")
	public Object doGet() {
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			return Index.getHtml(pm, year, month);
		} finally {
			pm.close();
		}
	}
}
