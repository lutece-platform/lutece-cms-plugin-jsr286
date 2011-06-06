/*
 * Copyright (c) 2002-2011, Mairie de Paris
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
package fr.paris.lutece.plugins.jsr286.business.portlet;

import fr.paris.lutece.portal.business.portlet.IPortletInterfaceDAO;
import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * this class provides Data Access methods for HtmlPortlet objects
 */
public final class Jsr286PortletDAO implements IPortletInterfaceDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_portlet , jsr286Name, contextName FROM portlet_jsr286 WHERE id_portlet = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO portlet_jsr286 ( id_portlet, jsr286Name, contextName ) VALUES (?, ?, ? )";
    private static final String SQL_QUERY_DELETE = "DELETE FROM portlet_jsr286 WHERE id_portlet = ?";
    private static final String SQL_QUERY_UPDATE = "UPDATE portlet_jsr286 SET jsr286Name = ?, contextName = ? WHERE id_portlet = ?";

    /** This class implements the Singleton design pattern. */
    private static Jsr286PortletDAO _dao = new Jsr286PortletDAO(  );

    /**
     * Creates a new HtmlPortletDAO object.
     */
    private Jsr286PortletDAO(  )
    {
    }

    /**
     * Returns the unique instance of the singleton.
     *
     * @return the instance
     */
    static Jsr286PortletDAO getInstance(  )
    {
        return _dao;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    //Access methods to data

    /**
     * Insert a new record in the table.
     *
     * @param portlet the object to be inserted
     */
    public void insert( Portlet portlet )
    {
        Jsr286Portlet p = (Jsr286Portlet) portlet;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT );        
        daoUtil.setInt( 1, p.getId(  ) );
        daoUtil.setString( 2, p.getJsr286Name(  ) );
        daoUtil.setString( 3, p.getContextName(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Delete a record from the table
     *
     * @param nPortletId the Jsr 286 Portlet identifier
     */
    public void delete( int nPortletId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE );
        daoUtil.setInt( 1, nPortletId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * loads the data of portlet from the table
     *
     * @param nIdPortlet The Jsr 286 Portlet identifier
     * @return the Html Portlet object
     */
    public Portlet load( int nIdPortlet )
    {
        Jsr286Portlet portlet = new Jsr286Portlet(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT );
        daoUtil.setInt( 1, nIdPortlet );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            portlet.setId( daoUtil.getInt( 1 ) );
            portlet.setJsr286Name( daoUtil.getString( 2 ) );
            portlet.setContextName( daoUtil.getString( 3 ) );
        }

        daoUtil.free(  );

        return portlet;
    }

    /**
     * Update the record in the table
     *
     * @param portlet the instance of Portlet class to be updated
     */
    public void store( Portlet portlet )
    {
        Jsr286Portlet p = (Jsr286Portlet) portlet;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE );
        daoUtil.setString( 1, p.getJsr286Name(  ) );
        daoUtil.setString( 2, p.getContextName(  ) );
        daoUtil.setInt( 3, p.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
