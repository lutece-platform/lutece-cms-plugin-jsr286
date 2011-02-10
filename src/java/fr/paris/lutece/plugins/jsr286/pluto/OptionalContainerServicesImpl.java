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
