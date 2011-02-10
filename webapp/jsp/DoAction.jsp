<%@ page import="fr.paris.lutece.portal.web.LocalVariables" 
%><%@ page import="fr.paris.lutece.portal.web.constants.Parameters" 
%><%@ page errorPage="../../ErrorPagePortal.jsp" 
%><jsp:useBean id="jsr286Portlet" scope="session" class="fr.paris.lutece.plugins.jsr286.web.portlet.Jsr286PortletJspBean" 
/><%
	// Attention: no text must out from this page, all definition must be joined

	try 
	{
		LocalVariables.setLocal( config, request, response );

		if ( ! jsr286Portlet.realiseAction( request ) ) 
		{
			if ( request.getParameter(Parameters.PAGE_ID) != null ) 
			{
				response.sendRedirect( "site/Portal.jsp?" + Parameters.PAGE_ID + "=" + request.getParameter( Parameters.PAGE_ID ) );
			} 
			else 
			{
				response.sendRedirect( "site/Portal.jsp" );
			}
		}
	} 
	finally 
	{
		LocalVariables.setLocal( null, null, null );
	}
%>