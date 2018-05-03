/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
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
