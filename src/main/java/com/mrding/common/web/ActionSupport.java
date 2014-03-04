package com.mrding.common.web;

import java.lang.reflect.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * action支持类
 * @author mrding
 *
 * @param <V>
 * @param <M>
 */
public class ActionSupport<V, M> implements ModelDriven<V>, ServletRequestAware, ServletResponseAware, Action {
    
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Map session = (Map) ActionContext.getContext().get(ActionContext.SESSION);
    
    protected M manager = getManagerInstance();
    protected V model = getVoInstance();
    protected List<Object> jsonList = new ArrayList<Object>();
    protected Map<String, Object> jsonMap = new HashMap<String, Object>();
    
    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    public List<Object> getJsonList() {
	return jsonList;
    }

    @Override
    public V getModel() {
	return model;
    }

    private V getVoInstance() {
	try {
            return ((Class<V>) getTypeArgs()[0]).newInstance();
	} catch(Exception e) {
	    return null;
	}
    }

    private M getManagerInstance() {
	try {
            return ((Class<M>) getTypeArgs()[1]).newInstance();
	} catch(Exception e) {
	    return null;
	}
    }

    private Type[] getTypeArgs() {
	Type sType = getClass().getGenericSuperclass();
        Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
	return generics;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
	this.response = response;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
	this.request = request;
    }

    @Override
    public String execute() throws Exception {
	return SUCCESS;
    }
    
    

}
