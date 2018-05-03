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
