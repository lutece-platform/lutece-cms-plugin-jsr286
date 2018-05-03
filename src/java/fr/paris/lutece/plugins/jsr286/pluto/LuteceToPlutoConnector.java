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

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pluto.container.PortletContainer;
import org.apache.pluto.container.PortletContainerException;
import org.apache.pluto.container.PortletWindow;
import org.apache.pluto.container.driver.DriverPortletContext;
import org.apache.pluto.container.driver.PlutoServices;
import org.apache.pluto.container.driver.PortletContextService;
import org.apache.pluto.container.driver.PortletRegistryService;
import org.apache.pluto.container.driver.RequiredContainerServices;
import org.apache.pluto.container.impl.PortletContainerFactory;
import org.apache.pluto.container.om.portlet.PortletDefinition;
import org.apache.pluto.driver.config.impl.DriverConfigurationImpl;
import org.apache.pluto.driver.container.DefaultOptionalContainerServices;
import org.apache.pluto.driver.container.PortalDriverServicesImpl;
import org.apache.pluto.driver.core.PortalRequestContext;
import org.apache.pluto.driver.core.PortletWindowImpl;

import org.apache.pluto.driver.services.impl.resource.ResourceConfig;
import org.apache.pluto.driver.services.impl.resource.StaticServletContextResourceConfigFactory;
import org.apache.pluto.driver.services.impl.resource.RenderConfigServiceImpl;
import org.apache.pluto.driver.services.impl.resource.SupportedModesServiceImpl;
import org.apache.pluto.driver.services.impl.resource.SupportedWindowStateServiceImpl;
import org.apache.pluto.driver.services.portal.PortletWindowConfig;
import org.apache.pluto.driver.url.PortalURL;
import org.apache.pluto.driver.url.PortalURLParser;
import org.apache.pluto.driver.url.impl.PortalURLParserImpl;
import org.apache.pluto.driver.container.PortletContextManager;
import org.apache.pluto.container.impl.RequestDispatcherServiceImpl;
import org.apache.pluto.container.impl.PortletAppDescriptorServiceImpl;


import fr.paris.lutece.plugins.jsr286.pluto.Buttons;

import fr.paris.lutece.plugins.jsr286.pluto.LuteceHttpServletResponse;
import fr.paris.lutece.portal.web.LocalVariables;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

public class LuteceToPlutoConnector 
{
	public static int TYPE_ACTION = 1;
	public static int TYPE_RESOURCE = 2;
	public static int TYPE_RENDER = 3;
	private static PortletContainer _portletContainer;
	private static ServletConfig _config;
	
	public static void init()
	{
		try {
			_config = LocalVariables.getConfig(  );
			RequiredContainerServicesImpl requiredServices = new RequiredContainerServicesImpl();		
			/*DefaultOptionalContainerServices dopc = new DefaultOptionalContainerServices();	
			dopc.getPortletContextService().register(_config);	*/		
			PortalDriverServicesImpl pdriverservice = new PortalDriverServicesImpl((RequiredContainerServices) requiredServices, null);
			
			if(PlutoServices.getServices() == null)
			{
				PlutoServices test = new PlutoServices(pdriverservice);
			}
			
			_portletContainer = PortletContainerFactory.getInstance().createContainer(
					"portletContainer", pdriverservice);
			
	        PortletContextService contextService = PlutoServices.getServices().getPortletContextService();
	        pdriverservice.setPortletContextService(contextService);
	        
	        try {
				String applicationName = contextService.register(_config);
			} catch (PortletContainerException e2) {				
				e2.printStackTrace();
			}
			_portletContainer.init();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * Return titles list of defined portlets
     *
     * @return titles list of defined portlets
     */
    public static ReferenceList getPortletTitles(  )
    {
    	ReferenceList refList = new ReferenceList(  );
    	HttpServletRequest request = LocalVariables.getRequest(  );
    	//init();
    	PortletContextService contextService = PlutoServices.getServices().getPortletContextService();
    	Iterator<DriverPortletContext> it = contextService.getPortletContexts();
        while(it.hasNext())
        {
        	DriverPortletContext context = it.next();
        	String contextName = context.getApplicationName();
			List<? extends PortletDefinition> portletList = context.getPortletApplicationDefinition().getPortlets();
			Iterator<? extends PortletDefinition> portletIterator = portletList.iterator();
			while(portletIterator.hasNext())
			{
				PortletDefinition pDef = portletIterator.next();				
				ReferenceItem item = new ReferenceItem();
				item.setCode( contextName + "/" + pDef.getPortletName() );
				item.setName( contextName + "/" + pDef.getPortletName() );
				refList.add(item);
			}
			
        }
        return refList;
    }
    public static PortletWindowImpl initPortletWindow( int nPortletID, String strPortletName, String strContextName )
    {
    	init();
    	HttpServletRequest request = LocalVariables.getRequest(  );		
		LuteceHttpServletResponse response = new LuteceHttpServletResponse( LocalVariables.getResponse(  ) );
		 
		PortalRequestContext pReqCtx = new PortalRequestContext(_config.getServletContext(), request, response);
		 
		String strPortletID = String.valueOf(nPortletID);
		PortletWindowConfig portletWindowConfig = new PortletWindowConfig();		 
		portletWindowConfig.setContextPath(
				"/" + strContextName);      
		portletWindowConfig.setMetaInfo(
		 		PortletWindowConfig.parseMetaInfo(strPortletID));
		portletWindowConfig.setPortletName(strPortletName);
		
		PortalURL portalUrl = PortalURLParserImpl.getParser().parse(request);
 
    	PortletWindowImpl portletWindow = new PortletWindowImpl(
         		_portletContainer, portletWindowConfig, portalUrl, nPortletID);
    	
    	return portletWindow;
    }
    
	public static String render( int nPortletID, String strPortletName, PortletWindow portletWindow2 )
    {
		init();				
		
        HttpServletRequest request = LocalVariables.getRequest(  );        
        LuteceHttpServletResponse response = new LuteceHttpServletResponse( LocalVariables.getResponse(  ) );
        
        PortalRequestContext pReqCtx = new PortalRequestContext(_config.getServletContext(), request, response);
		// Retrieve the portlet window 
        
        String strPortletID = String.valueOf(nPortletID);
        PortletWindowConfig portletWindowConfig = new PortletWindowConfig();
        
        portletWindowConfig.setContextPath(
        		request.getContextPath());       
        portletWindowConfig.setMetaInfo(
        		PortletWindowConfig.parseMetaInfo(strPortletID));
        portletWindowConfig.setPortletName(strPortletName);
        
        PortalURL portalUrl = PortalURLParserImpl.getParser().parse(request);        
        
        //try {
        	PlutoServices tempPS = PlutoServices.getServices();        	
        	PortletRegistryService tempPRS = tempPS.getPortletRegistryService();        	
        	
        	PortletDefinition tempPD = null;//tempPRS.getPortlet(portletWindowConfig.getContextPath(), portletWindowConfig.getPortletName());
        	
		/*} catch (PortletContainerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
    	// Retrieve the portlet window
        PlutoSession plutoSession = PlutoSession.findSession( request );
        PortletWindowImpl portletWindow = plutoSession.getPortletWindow( String.valueOf( nPortletID ), strPortletName );
        
       
        /*PortletWindowImpl portletWindow = new PortletWindowImpl(
        		_portletContainer, portletWindowConfig, portalUrl, nPortletID);  */   
        
        PortalURLParser parser = PortalURLParserImpl.getParser();
        
        //PropertyConfigServiceImpl propertyService = new PropertyConfigServiceImpl();
        ResourceConfig resourceConfig =  StaticServletContextResourceConfigFactory.getResourceConfig( );
        RenderConfigServiceImpl renderConfig = new RenderConfigServiceImpl( resourceConfig );
        RequestDispatcherServiceImpl requestDispatcherService = new RequestDispatcherServiceImpl(  );
        PortletAppDescriptorServiceImpl portletAppDescriptorService = new PortletAppDescriptorServiceImpl(  );
        PortletContextManager portletContextManager = new PortletContextManager(requestDispatcherService, portletAppDescriptorService);
        SupportedModesServiceImpl supportedModeService = new SupportedModesServiceImpl(resourceConfig, portletContextManager, portletContextManager);
        SupportedWindowStateServiceImpl supportedWindowService = new SupportedWindowStateServiceImpl(resourceConfig, portletContextManager);
        
        DriverConfigurationImpl driverConfig = new DriverConfigurationImpl(parser
        		, resourceConfig
        		, renderConfig
        		,supportedModeService
        		,supportedWindowService);
        pReqCtx.getServletContext().setAttribute("driverConfig", driverConfig);
     
        
        try {
        	_portletContainer.doLoad( portletWindow, request, response );        	
			_portletContainer.doRender( portletWindow, request, response);				
			
			return response.getBufferString();
		} catch (PortletException e) {
			
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			
			e.printStackTrace();
			return e.getMessage();
		} catch (PortletContainerException e) {
			
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
    }
	
	public static boolean request( int nPortletId, String strPortletName, int type )
	{
	    // Restore main parameters from the ThreadLocal
	    HttpServletRequest request = LocalVariables.getRequest(  );	
	    
	    if(type == TYPE_ACTION)
	    {
	    	return LuteceToPlutoConnector.requestAction( nPortletId, strPortletName );
	    }  	 
	    else if(type == TYPE_RESOURCE)
	    {
	    	return LuteceToPlutoConnector.requestServeResource( nPortletId, strPortletName );
	    }
	    else if(type == TYPE_RENDER)
	    {
	    	return LuteceToPlutoConnector.requestRender( nPortletId, strPortletName );
	    }
	    return false;
	}
	
	 /**
     * Serve Resource
     *
     * @param nPortletID Lutece portlet ID
     * @param strPortletName JSR 168 portlet name (ID)
     * @return <code>true</code> for a normal processing, <code>false</code> for the need to send a redirect to the client.
     */
    private static boolean requestServeResource( int nPortletID, String strPortletName )
    {	
    	init();
    	HttpServletRequest request = LocalVariables.getRequest(  );
    	HttpServletResponse response = LocalVariables.getResponse(  );


    	PortalRequestContext pReqCtx = new PortalRequestContext(_config.getServletContext(), request, response);
    	// Retrieve the portlet window 

    	String strPortletID = String.valueOf(nPortletID);
    	PortletWindowConfig portletWindowConfig = new PortletWindowConfig();

    	portletWindowConfig.setContextPath(
    			request.getContextPath());       
    	portletWindowConfig.setMetaInfo(
    			PortletWindowConfig.parseMetaInfo(strPortletID));
    	portletWindowConfig.setPortletName(strPortletName);         
    	PortalURL portalUrl = PortalURLParserImpl.getParser().parse(request); 

    	PlutoServices tempPS = PlutoServices.getServices();     	
    	PortletRegistryService tempPRS = tempPS.getPortletRegistryService();        	

    	PortletDefinition tempPD = null;//tempPRS.getPortlet(portletWindowConfig.getContextPath(), portletWindowConfig.getPortletName());

		/*} catch (PortletContainerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
     	 PlutoSession plutoSession = PlutoSession.findSession( request );
         PortletWindowImpl portletWindow = plutoSession.getPortletWindow( String.valueOf( nPortletID ), strPortletName );      	
	     

         ServletContext servletContext = _config.getServletContext();
         PortalURLParser parser = PortalURLParserImpl.getParser();
         //PropertyConfigServiceImpl propertyService = new PropertyConfigServiceImpl();
         ResourceConfig resourceConfig =  StaticServletContextResourceConfigFactory.getResourceConfig( );
         RenderConfigServiceImpl renderConfig = new RenderConfigServiceImpl( resourceConfig );
         RequestDispatcherServiceImpl requestDispatcherService = new RequestDispatcherServiceImpl(  );
         PortletAppDescriptorServiceImpl portletAppDescriptorService = new PortletAppDescriptorServiceImpl(  );
         PortletContextManager portletContextManager = new PortletContextManager(requestDispatcherService, portletAppDescriptorService);
         SupportedModesServiceImpl supportedModeService = new SupportedModesServiceImpl(resourceConfig, portletContextManager, portletContextManager);
         SupportedWindowStateServiceImpl supportedWindowService = new SupportedWindowStateServiceImpl(resourceConfig, portletContextManager);
        
	     DriverConfigurationImpl driverConfig = new DriverConfigurationImpl(parser
	     		, resourceConfig
	     		, renderConfig
	     		,supportedModeService
	     		,supportedWindowService);	     
	     pReqCtx.getServletContext().setAttribute("driverConfig", driverConfig);
        try
        {        	
            _portletContainer.doServeResource( portletWindow, request, response );
        }
        catch ( Throwable e )
        {
           e.printStackTrace();
           return false;
        }

        return true;
    }
    /**
     * Realise an action
     *
     * @param nPortletID Lutece portlet ID
     * @param strPortletName JSR 286 portlet name (ID)
     * @return <code>true</code> for a normal processing, <code>false</code> for the need to send a redirect to the client.
     */
    private static boolean requestAction( int nPortletID, String strPortletName )
    {	
    	init();
    	 HttpServletRequest request = LocalVariables.getRequest(  );
         //HttpServletResponse response = LocalVariables.getResponse(  );
         LuteceHttpServletResponse response = new LuteceHttpServletResponse( LocalVariables.getResponse(  ) );
         
         PortalRequestContext pReqCtx = new PortalRequestContext(_config.getServletContext(), request, response);
 		// Retrieve the portlet window 
         
         String strPortletID = String.valueOf(nPortletID);
         PortletWindowConfig portletWindowConfig = new PortletWindowConfig();           
         portletWindowConfig.setContextPath(
         		request.getContextPath());       
         portletWindowConfig.setMetaInfo(
         		PortletWindowConfig.parseMetaInfo(strPortletID));
         portletWindowConfig.setPortletName(strPortletName);         
         PortalURL portalUrl = PortalURLParserImpl.getParser().parse(request);      

      
         PlutoServices tempPS = PlutoServices.getServices();

         PortletRegistryService tempPRS = tempPS.getPortletRegistryService();        	

         PortletDefinition tempPD = null;

         PlutoSession plutoSession = PlutoSession.findSession( request );
         PortletWindowImpl portletWindow = plutoSession.getPortletWindow( String.valueOf( nPortletID ), strPortletName );      	


         ServletContext servletContext = _config.getServletContext();
         PortalURLParser parser = PortalURLParserImpl.getParser();
         //PropertyConfigServiceImpl propertyService = new PropertyConfigServiceImpl();
         ResourceConfig resourceConfig =  StaticServletContextResourceConfigFactory.getResourceConfig( );
         RenderConfigServiceImpl renderConfig = new RenderConfigServiceImpl( resourceConfig );
         RequestDispatcherServiceImpl requestDispatcherService = new RequestDispatcherServiceImpl(  );
         PortletAppDescriptorServiceImpl portletAppDescriptorService = new PortletAppDescriptorServiceImpl(  );
         PortletContextManager portletContextManager = new PortletContextManager(requestDispatcherService, portletAppDescriptorService);
         SupportedModesServiceImpl supportedModeService = new SupportedModesServiceImpl(resourceConfig, portletContextManager, portletContextManager);
         SupportedWindowStateServiceImpl supportedWindowService = new SupportedWindowStateServiceImpl(resourceConfig, portletContextManager);
	     
	     DriverConfigurationImpl driverConfig = new DriverConfigurationImpl(parser
	     		, resourceConfig
	     		, renderConfig
	     		,supportedModeService
	     		,supportedWindowService);	     
	     pReqCtx.getServletContext().setAttribute("driverConfig", driverConfig);
        try
        {
        	_portletContainer.doLoad( portletWindow, request, response );
            _portletContainer.doAction( portletWindow, request, response );
        }
        catch ( Throwable e )
        {
           e.printStackTrace();
           return false;
        }

        return true;
    }
    /**
     * Realise a render action (change mode and state)
     *
     * @param nPortletID Lutece portlet ID
     * @return <code>true</code> for a normal processing, <code>false</code> for the need to send a redirect to the client.
     */
    private static boolean requestRender( int nPortletID, String strPortletName )
    {
    	HttpServletRequest request = LocalVariables.getRequest(  );
    	
    	PlutoSession plutoSession = PlutoSession.findSession( request );
        PortletWindowImpl portletWindow = plutoSession.getPortletWindow( String.valueOf( nPortletID ), strPortletName );
        
        String strMode = request.getParameter("pm");
    	String strWindowState = request.getParameter("ws");
    	portletWindow.setPortletMode(strMode);
    	portletWindow.setWindowState(strWindowState);        
        
       
    	return requestAction(nPortletID, strPortletName);
    }
    
    /**
     * Return list of buttons that must be displayed on the portlet titlebar.
     *
     * @param nPortletID Lutece portlet ID
     * @param strPortletName JSR 286 portlet name (ID)
     * @return Return button defined for this
     */
    public static Buttons getButtons( int nPortletID, String strPortletName )
    {        
        final Buttons buttons = new Buttons(  ); 
        
        HttpServletRequest request = LocalVariables.getRequest(  );        
        LuteceHttpServletResponse response = new LuteceHttpServletResponse( LocalVariables.getResponse(  ) );
        
		// Retrieve the portlet window   
		PlutoSession plutoSession = PlutoSession.findSession( request );
		PortletWindowImpl portletWindow = plutoSession.getPortletWindow( String.valueOf( nPortletID ), strPortletName );
		
        buttons.init( request, response, portletWindow.getPortletDefinition(  ), portletWindow );
        return buttons;
    }
}
