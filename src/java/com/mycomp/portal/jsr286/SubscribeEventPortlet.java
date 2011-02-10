package com.mycomp.portal.jsr286;

import java.io.IOException;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.mycomp.portal.jsr286.WrapperEvent;

public class SubscribeEventPortlet extends GenericPortlet {

	private static final String VIEW_JSP = "/jsp/subscribeView.jsp";
	public static final String NAME_ATTR = "NAME";
	public static final String ZIP_ATTR = "ZIP";
	
	@Override
	protected void doView(RenderRequest request, RenderResponse renderResponse)
			throws PortletException, IOException {
		renderResponse.setContentType(request.getResponseContentType());
		// setting the attributes for JSP
		request.setAttribute(NAME_ATTR, name);
		request.setAttribute(ZIP_ATTR, zipcode);
		getPortletContext().getRequestDispatcher(VIEW_JSP).include(request,renderResponse);          
	}

	@Override
	public void processEvent(EventRequest eventRequest, EventResponse response)
			throws PortletException, IOException {
        Event event = eventRequest.getEvent();
        if(event.getQName().equals(WrapperEvent.QNAME))
        {
            WrapperEvent wrapEvent = (WrapperEvent)event.getValue();
            name = wrapEvent.getName();
            zipcode = wrapEvent.getZipcode();  
        }
	}

	private String name = "Default Name";
	private String zipcode = "Default Zip";
	
	
}
