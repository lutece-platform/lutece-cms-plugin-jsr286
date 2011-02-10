package com.mycomp.portal.jsr286;

import java.io.Serializable;


import javax.xml.namespace.QName;


public class WrapperEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//public static final String EVENT_NAME = "AppEvent";
	//public static final String EVENT_NS_NAME = "http://www.mycomp.com/jsr286";
	public static final QName QNAME = new QName("http://www.mycomp.com/jsr286", "AppEvent");
	
	private String name;
    private String zipcode;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}			       
}
