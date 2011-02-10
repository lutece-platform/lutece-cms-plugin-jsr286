<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects />
	<p>Here are your details:</p>

<br> Name: <%= renderRequest.getAttribute(com.mycomp.portal.jsr286.SubscribeEventPortlet.NAME_ATTR)%> 
<br> Zipcode: <%= renderRequest.getAttribute(com.mycomp.portal.jsr286.SubscribeEventPortlet.ZIP_ATTR)%> 
