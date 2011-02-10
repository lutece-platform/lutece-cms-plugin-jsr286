<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects />
	<p>Please enter your name and zipcode. If you do not eneter avlid values and submit, default values would be used instead.</p>

	<form method='post' action="<portlet:actionURL />" >
   	<br> Name: <input type=text name=name> 
   	<br> Zipcode: <input type=text name=zipcode> <br>
   	<input type=submit> <br>
   	</form>

