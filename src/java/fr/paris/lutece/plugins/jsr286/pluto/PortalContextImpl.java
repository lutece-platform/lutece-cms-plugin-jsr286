package fr.paris.lutece.plugins.jsr286.pluto;

import java.util.Enumeration;
import java.util.Vector;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import javax.servlet.ServletConfig;

public class PortalContextImpl implements PortalContext {
	private Vector<PortletMode> _listModes;
	private Vector<WindowState> _listStates;	
	
	 /**
     * @see org.apache.pluto.services.information.PortalContextProvider#getPortalInfo()
     */
	public String getPortalInfo() 
	{		
		return "Lutece/3.0.0";
	}

	public String getProperty(String arg0) {
		
		return null;
	}

	public Enumeration<String> getPropertyNames() {
		
		return null;
	}
	/**
     * Get an enumeration of all <code>PortletMode</code>s supported by this
     * portal.
     * @return enumeration of all supported portlet modes.
     */
    public Enumeration getSupportedPortletModes() {
        if (_listModes == null) {
        	_listModes = new Vector();
           
        	_listModes.add( new PortletMode("view") );
        	_listModes.add( new PortletMode("edit") );
        	_listModes.add( new PortletMode("help") );
        	_listModes.add( new PortletMode("config") );
        	
            
        }
        return _listModes.elements();
    }

    /**
     * Get an enumeration of all <code>WindowState</code>s supported by this
     * portal.
     * @return an enumeration of all supported window states.
     */
    public Enumeration getSupportedWindowStates() {
        if (_listStates == null) {
        	_listStates = new Vector();       
        	_listStates.add( new WindowState("normal") );
        	_listStates.add( new WindowState("maximized") );
        	_listStates.add( new WindowState("minimized") );
            
        }
        return _listStates.elements();
    }

}
