<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../PortletAdminHeader.jsp" />

<jsp:useBean id="portletjsr286" scope="session" class="fr.paris.lutece.plugins.jsr286.web.portlet.Jsr286PortletJspBean" />

<% portletjsr286.init( request,  portletjsr286.RIGHT_MANAGE_ADMIN_SITE  ); %>
<%= portletjsr286.getCreate( request ) %>

<%@ include file="../../AdminFooter.jsp" %>

