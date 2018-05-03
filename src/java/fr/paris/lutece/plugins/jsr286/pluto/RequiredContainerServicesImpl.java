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
package fr.paris.lutece.plugins.jsr286.pluto;

import javax.portlet.PortalContext;

import org.apache.pluto.container.EventCoordinationService;
import org.apache.pluto.container.FilterManagerService;
import org.apache.pluto.container.PortletRequestContextService;
import org.apache.pluto.container.PortletURLListenerService;
import org.apache.pluto.container.driver.RequiredContainerServices;
import org.apache.pluto.container.impl.PortletAppDescriptorServiceImpl;
import org.apache.pluto.container.impl.RequestDispatcherServiceImpl;
import org.apache.pluto.driver.container.PortletContextManager;
import org.apache.pluto.driver.services.container.EventCoordinationServiceImpl;
import org.apache.pluto.driver.services.container.FilterManagerServiceImpl;
import org.apache.pluto.driver.services.container.PortletRequestContextServiceImpl;
import org.apache.pluto.driver.services.container.PortletURLListenerServiceImpl;

public class RequiredContainerServicesImpl implements RequiredContainerServices
{
	private PortalContext _pc;
	private EventCoordinationService _ecs;
	private FilterManagerService _fms;
	private PortletRequestContextService _prcs;
	private PortletURLListenerService _puls;
	
	public RequiredContainerServicesImpl()
	{
		this._pc = new PortalContextImpl();
		this._fms = new FilterManagerServiceImpl();
		RequestDispatcherServiceImpl requestDispatcherService = new RequestDispatcherServiceImpl(  );
        PortletAppDescriptorServiceImpl portletAppDescriptorService = new PortletAppDescriptorServiceImpl(  );
        PortletContextManager portletContextManager = new PortletContextManager(requestDispatcherService, portletAppDescriptorService);
		this._ecs = new EventCoordinationServiceImpl(portletContextManager, portletContextManager);
		this._prcs = new PortletRequestContextServiceImpl();
		this._puls = new PortletURLListenerServiceImpl();
	}
	public EventCoordinationService getEventCoordinationService() 
	{
		return this._ecs;
	}

	public FilterManagerService getFilterManagerService() 
	{
		return this._fms;
	}

	public PortalContext getPortalContext() 
	{
		return this._pc;
	}

	public PortletRequestContextService getPortletRequestContextService() 
	{
		return this._prcs;
	}

	public PortletURLListenerService getPortletURLListenerService() 
	{
		return this._puls;
	}

}
