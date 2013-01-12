package com.marthastewart.restlet;

import java.util.HashMap;
import java.util.Map;

import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.resource.ServerResource;

public class ParameterizedServerResource extends ServerResource {
    protected final Map<String, String> params = new HashMap<String, String>();
    
    @Override  
    public void doInit() {
    	super.doInit();
    	
    	final Form form = getRequest().getResourceRef().getQueryAsForm();
    	for (Parameter parameter : form) {
    		params.put(parameter.getName(), parameter.getValue());
    	}
    }
}
