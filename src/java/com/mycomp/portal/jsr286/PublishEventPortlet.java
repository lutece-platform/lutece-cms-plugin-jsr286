package com.mycomp.portal.jsr286;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;


import com.mycomp.portal.jsr286.WrapperEvent;


public class PublishEventPortlet extends GenericPortlet {

	private static final String VIEW_JSP = "/jsp/publishView.jsp";
	
	@Override
	protected void doView(RenderRequest request, RenderResponse renderResponse)
			throws PortletException, IOException {
		renderResponse.setContentType(request.getResponseContentType());
		getPortletContext().getRequestDispatcher(VIEW_JSP).include(request,renderResponse);     
	}

	@Override
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {
			String nameParam = request.getParameter("name");
			if ( nameParam == null || nameParam.trim().equals("")) {
				nameParam = "Joe Black";
			} 
			String zipParam = request.getParameter("zipcode");
			if ( zipParam == null || zipParam.trim().equals("")) {
				zipParam = "80202";
			} 
			WrapperEvent event = new WrapperEvent();
			event.setName(nameParam);
			event.setZipcode(zipParam);
			response.setEvent(WrapperEvent.QNAME, event);			
	}
}
