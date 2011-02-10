package org.apache.pluto.examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Example Hello World portlet to demonstrate deployment using
 * Maven 2.
 * 
 * @author <a href="mailto:cdoremus@apache.org">Craig Doremus</a>
 */
public class HelloWorldPortlet extends GenericPortlet {

	public void doView(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {
		//get Message from ResourceBundle HelloWorldPortlet.properties
		ResourceBundle bundle = getPortletConfig().getResourceBundle(Locale.getDefault());
		//String msg = bundle.getString("hello");
		//Required call for use of getWriter() and getPortletOutputStream()
		res.setContentType("text/html;charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.println("<h2>");
		out.println("Hello World!");
		//out.println(msg);
		out.println("</h2>");
	}

	public void processAction(ActionRequest req, ActionResponse res)
		throws IOException, PortletException {
	}
	
}
