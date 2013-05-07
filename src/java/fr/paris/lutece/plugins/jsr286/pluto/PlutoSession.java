/*
 * Copyright (c) 2002-2013, Mairie de Paris
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

import fr.paris.lutece.plugins.jsr286.business.portlet.Jsr286Portlet;
import fr.paris.lutece.portal.business.portlet.PortletHome;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.pluto.driver.core.PortletWindowImpl;


/**
 * Unique class present in {@link javax.servlet.http.HttpSession} instance
 * associated to user.
 */
public final class PlutoSession
{
    /**
     * Portlets Window defined for the user (a portlet window
     * contains the definition of the portlet, current state,
     * current mode...)
     */
    private final Map _mapPortletWindow;

    /**
     * Default private constructor, PlutoSession is build
     * by {@link #findSession(HttpServletRequest)} call.
     */
    private PlutoSession(  )
    {
        _mapPortletWindow = new HashMap(  );
    }

    /**
     * Return a portlet window associated to a portlet ID.
     *
     * @param strPortletId The portlet ID (Lutece ID) of the portlet window asked
     * @return The portlet window associated to this servlet
     */
    public synchronized PortletWindowImpl getPortletWindow( String strPortletId, String strPortletName )
    {
        PortletWindowImpl portletWindow = (PortletWindowImpl) _mapPortletWindow.get( strPortletId );

        if ( ( portletWindow == null ) || !(portletWindow.getPortletName().equals(strPortletName)) )
        {
			int nPortletId = Integer.parseInt( strPortletId );
			Jsr286Portlet portlet = (Jsr286Portlet) PortletHome.findByPrimaryKey( nPortletId );
			portletWindow = LuteceToPlutoConnector.initPortletWindow( nPortletId
					, portlet.getJsr286Name()
					, portlet.getContextName() );
			_mapPortletWindow.put( strPortletId, portletWindow );
        }

        

        return portletWindow;
    }

    /**
     * Return the current Pluto session (for current user request)
     *
     * @param request Current user HTTP resquest
     * @return The <code>PlutoSession</code> associated with this session
     */
    public static PlutoSession findSession( final HttpServletRequest request )
    {
        HttpSession session = request.getSession(  );

        synchronized ( session )
        {
            PlutoSession plutoSession = (PlutoSession) session.getAttribute( "fr.paris.lutece.plugins.jsr286" );

            if ( plutoSession == null )
            {
                plutoSession = new PlutoSession(  );
                session.setAttribute( "fr.paris.lutece.plugins.jsr286", plutoSession );
            }

            return plutoSession;
        }
    }
}
