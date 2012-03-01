/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.jsr286.web.portlet;

import fr.paris.lutece.plugins.jsr286.business.portlet.Jsr286Portlet;
import fr.paris.lutece.plugins.jsr286.business.portlet.Jsr286PortletHome;
import fr.paris.lutece.plugins.jsr286.pluto.LuteceToPlutoConnector;
//import fr.paris.lutece.plugins.jsr286.pluto.core.PortalURL;
import fr.paris.lutece.portal.business.portlet.PortletHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.portlet.PortletJspBean;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.pluto.container.PortletWindow;


/**
 * This class provides the user interface to manage Jsr 286 Portlet features
 */
public class Jsr286PortletJspBean extends PortletJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants

    //  Rights
    public static final String RIGHT_MANAGE_ADMIN_SITE = "CORE_ADMIN_SITE";
    private static final String ADMIN_SITE = "../../site/AdminSite.jsp";
    private static final String MESSAGE_MANDATORY_PORTLET_TITLE = "jsr286.portlet.mandatory.message";
    private static final String PARAM_PAGE_ID = "page_id";
    private static final String PARAM_PORTLET_ID = "portlet_id";
    private static final String PARAM_PORTLET_TYPE_ID = "portlet_type_id";
    private static final String PARAM_JSR286_NAME = "jsr286_name";
    private static final String BOOKMARK_JSR286_TITLE_COMBO = "@jsr286_title_combo@";

    //Markers
    private static final String MARK_PORTLET_LIST = "portlet_list";
    private static final String MARK_PORTLET_ID = "default_portlet_id";

    // Templates
    private static final String TEMPLATE_COMBO_PORTLETS = "admin/plugins/jsr286/combo_portletsJSR.html";
    private static final String COMBO_PORTLETS_LIST = "@combo_portletsJSR@";

    //Constants
    private static final String CONSTANT_SLASH = "/";
    
    /**
     * Creates a new Jsr168PortletJspBean object.
     */
    public Jsr286PortletJspBean(  )
    {
    }

    /**
     * Returns the properties prefix used for jsr168 portlet and defined in lutece.properties file
     *
     * @return the value of the property prefix
     */
    public String getPropertiesPrefix(  )
    {
        return "portlet.jsr286";
    }

    /**
     * Returns the Jsr 168 Portlet form of creation
     *
     * @param request The current Http request
     * @return the html code of the jsr 168 portlet form
     */
    public String getCreate( HttpServletRequest request )
    {       

        String strPageId = request.getParameter( PARAM_PAGE_ID );
        String strPortletTypeId = request.getParameter( PARAM_PORTLET_TYPE_ID );
        HtmlTemplate template = getCreateTemplate( strPageId, strPortletTypeId );

        ReferenceList portletsList = LuteceToPlutoConnector.getPortletTitles(  );

        String strHtmlCombo = getPortletIndexCombo( portletsList, "" );
        template.substitute( BOOKMARK_JSR286_TITLE_COMBO, strHtmlCombo );
       
        return template.getHtml(  );
    }

    /**
     * Returns the Jsr 168 Portlet form for update
     *
     * @param request The current Http request
     * @return the html code of the jsr 168 portlet form
     */
    public String getModify( HttpServletRequest request )
    {
        String strPortletId = request.getParameter( PARAM_PORTLET_ID );
        int nPortletId = Integer.parseInt( strPortletId );
        Jsr286Portlet portlet = (Jsr286Portlet) PortletHome.findByPrimaryKey( nPortletId );
        HtmlTemplate template = getModifyTemplate( portlet );

        ReferenceList portletsList = LuteceToPlutoConnector.getPortletTitles(  );

        String strHtmlCombo = getPortletIndexCombo( portletsList, portlet.getContextName(  )+"/"+portlet.getJsr286Name(  ) );
        template.substitute( BOOKMARK_JSR286_TITLE_COMBO, strHtmlCombo );

        return template.getHtml(  );
    }

    /**
     * Processes the creation form of a new jsr 168 portlet
     *
     * @param request The current Http request
     * @return The jsp URL which displays the view of the created jsr 168 portlet
     */
    public String doCreate( HttpServletRequest request )
    {
    	Jsr286Portlet portlet = new Jsr286Portlet(  );

        // Recovers portlet common attributes
        setPortletCommonData( request, portlet );

        // mandatory field
        String strName = portlet.getName(  );

        if ( ( strName == null ) || "".equals( strName.trim(  ) ) )
        {
        	return AdminMessageService.getMessageUrl(request, MESSAGE_MANDATORY_PORTLET_TITLE, AdminMessage.TYPE_ERROR);            
        }

        // Recovers portlet specific attributes
        String strPageId = request.getParameter( PARAM_PAGE_ID );
        int nPageId = Integer.parseInt( strPageId );
        portlet.setPageId( nPageId );

        // html code cleaning
        String strContent = request.getParameter( PARAM_JSR286_NAME );
        int nSlashIndex = strContent.indexOf(CONSTANT_SLASH);
        String strContextName = strContent.substring(0, nSlashIndex);
        String strJsr286Name= strContent.substring(nSlashIndex + 1);
        portlet.setJsr286Name( strJsr286Name );
        portlet.setContextName(strContextName);
        
        // creates the portlet
        Jsr286PortletHome.getInstance(  ).create( portlet );

        // displays the page with the new portlet
        return ADMIN_SITE + "?" + PARAM_PAGE_ID + "=" + portlet.getPageId(  );
    }

    /**
     * Processes the update form of the jsr 168 portlet whose identifier is in the http request
     *
     * @param request The current Http request
     * @return The jsp URL which displays the view of the updated jsr 168 portlet
     */
    public String doModify( HttpServletRequest request )
    {
        // recovers portlet attributes
        String strPortletId = request.getParameter( PARAM_PORTLET_ID );
        int nPortletId = Integer.parseInt( strPortletId );
        Jsr286Portlet portlet = (Jsr286Portlet) PortletHome.findByPrimaryKey( nPortletId );

        // recovers portlet common attributes
        setPortletCommonData( request, portlet );

        // mandatory field
        String strName = portlet.getName(  );

        if ( ( strName == null ) || "".equals( strName.trim(  ) ) )
        {
        	return AdminMessageService.getMessageUrl(request, MESSAGE_MANDATORY_PORTLET_TITLE, AdminMessage.TYPE_ERROR);            
        }

        // html code cleaning
        String strContent = request.getParameter( PARAM_JSR286_NAME );
        int nSlashIndex = strContent.indexOf(CONSTANT_SLASH);
        String strContextName = strContent.substring(0, nSlashIndex);
        String strJsr286Name = strContent.substring(nSlashIndex + 1);
        portlet.setJsr286Name( strJsr286Name );
        portlet.setContextName(strContextName);

        // updates the portlet
        portlet.update(  );

        // displays the page with the portlet updated
        return ADMIN_SITE + "?" + PARAM_PAGE_ID + "=" + portlet.getPageId(  );
    }

    /**
    * Call {@link LuteceToPlutoConnector} method targeted by the request: <code>action</code>
    * or <code>render</code>
    *
    * @param request The current Http request
    * @return Indicate if this action has generated a fragment,
    *         <code>true</code> no redirect to send,
    *         <code>false</code> for a send redirect required (for <code>action</code> request)
     */
    /*public boolean realiseAction( HttpServletRequest request )
    {
        final String strPortletId = PortalURL.extractPortletId( request );

        final int nPortletId = Integer.parseInt( strPortletId );
        final Jsr286Portlet portlet = (Jsr286Portlet) PortletHome.findByPrimaryKey( nPortletId );

        return LuteceToPlutoConnector.request( nPortletId, portlet.getJsr286Name(  ) );
    }*/

    /**
     * Return the feed listing depending on rights
     * @param listFeeds list of available rss feeds
     * @param strDefaultFeedId The id of the feed used in the context
     * @return The html code of the combo
     */
    String getPortletIndexCombo( ReferenceList listPortlets, String strDefaultPortletId )
    {
        HashMap model = new HashMap(  );
        model.put( MARK_PORTLET_LIST, listPortlets );
        model.put( MARK_PORTLET_ID, strDefaultPortletId );

        HtmlTemplate templateCombo = AppTemplateService.getTemplate( TEMPLATE_COMBO_PORTLETS, getLocale(  ), model );

        return templateCombo.getHtml(  );
    }
    
    public boolean realiseAction( HttpServletRequest request )
    {
    	// recovers portlet attributes
        String strPortletId = request.getParameter( "rp_pid" );
        int nPortletId = Integer.parseInt( strPortletId );
        Jsr286Portlet portlet = (Jsr286Portlet) PortletHome.findByPrimaryKey( nPortletId );
        String portletMode = request.getParameter("pm"); 
        String windowState = request.getParameter("ws"); 
        
        //test if it's a mode or window changement
        if( (portletMode != null) || (windowState != null) )
        {
        	return LuteceToPlutoConnector.request( nPortletId, portlet.getJsr286Name(  ), LuteceToPlutoConnector.TYPE_RENDER );        	
        }
        return LuteceToPlutoConnector.request( nPortletId, portlet.getJsr286Name(  ), LuteceToPlutoConnector.TYPE_ACTION );
    }
    
    public boolean realiseServeResource( HttpServletRequest request )
    {
    	// recovers portlet attributes
        String strPortletId = request.getParameter( "rp_pid" );
        int nPortletId = Integer.parseInt( strPortletId );
        Jsr286Portlet portlet = (Jsr286Portlet) PortletHome.findByPrimaryKey( nPortletId );

        return LuteceToPlutoConnector.request( nPortletId, portlet.getJsr286Name(  ), LuteceToPlutoConnector.TYPE_RESOURCE );
    }
    
    public static String extractPortletId( HttpServletRequest servletRequest )
    {
        return servletRequest.getParameter( "rp_pid" );
    }
}
