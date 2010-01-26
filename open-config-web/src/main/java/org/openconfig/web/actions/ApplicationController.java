package org.openconfig.web.actions;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ApplicationController extends ActionSupport {

    private String id;

    public String index(){
        return SUCCESS;
    }

    public String show(){
        System.out.println("id = " + id);
        return SUCCESS;
    }

    public void setId(String id) {
        this.id = id;
    }
}
