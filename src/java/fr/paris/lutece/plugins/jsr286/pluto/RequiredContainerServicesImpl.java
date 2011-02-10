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
