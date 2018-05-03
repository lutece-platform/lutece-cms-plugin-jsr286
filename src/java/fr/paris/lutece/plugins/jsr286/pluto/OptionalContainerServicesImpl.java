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

import org.apache.pluto.container.CCPPProfileService;
import org.apache.pluto.container.NamespaceMapper;
import org.apache.pluto.container.driver.OptionalContainerServices;
import org.apache.pluto.container.PortletEnvironmentService;
import org.apache.pluto.container.PortletInvokerService;
import org.apache.pluto.container.PortletPreferencesService;
import org.apache.pluto.container.RequestDispatcherService;
import org.apache.pluto.container.UserInfoService;
import org.apache.pluto.driver.container.DummyCCPPProfileServiceImpl;

public class OptionalContainerServicesImpl implements OptionalContainerServices {

	private CCPPProfileService _ccppps;
	private NamespaceMapper _nsm;
	
	public OptionalContainerServicesImpl()
	{
		_ccppps = new DummyCCPPProfileServiceImpl();
		//_nsm = new D();
	}
	public CCPPProfileService getCCPPProfileService() {
		
		return _ccppps;
	}

	public NamespaceMapper getNamespaceMapper() {
		
		return _nsm;
	}

	public PortletEnvironmentService getPortletEnvironmentService() {
		// TODO Auto-generated method stub
		return null;
	}

	public PortletInvokerService getPortletInvokerService() {
		// TODO Auto-generated method stub
		return null;
	}

	public PortletPreferencesService getPortletPreferencesService() {
		// TODO Auto-generated method stub
		return null;
	}

	public UserInfoService getUserInfoService() {
		// TODO Auto-generated method stub
		return null;
	}
	public RequestDispatcherService getRequestDispatcherService() {
		// TODO Auto-generated method stub
		return null;
	}

}
