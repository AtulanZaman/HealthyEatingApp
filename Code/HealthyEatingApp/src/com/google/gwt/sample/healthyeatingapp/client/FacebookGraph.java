package com.google.gwt.sample.healthyeatingapp.client;
/*
Code taken and modified to this project's use from:
http://www.marcusschiesser.de/2013/01/using-the-facebook-graph-api-from-your-gwt-application/
*/
import java.io.IOException;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.api.gwt.oauth2.client.Callback;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

public class FacebookGraph {
    private String token = null;
 
    private static FacebookGraph staticObject = new FacebookGraph();
 
    private static final String FACEBOOK_AUTH_URL = "https://www.facebook.com/dialog/oauth";
     
    private static final String FACEBOOK_CLIENT_ID = "406630196123484"; 
 
    private FacebookGraph() {
    }
 
    public static FacebookGraph getStaticObject() {
	return staticObject;
    }
    
    public String getToken(){
    	return token;
    }
    
    public void resetToken() {
	token = null;
    }
 
    private void RequestAuthorization(Callback<String, Throwable> callback) {
	final AuthRequest req = new AuthRequest(FACEBOOK_AUTH_URL,
		FACEBOOK_CLIENT_ID).withScopes("email", "user_birthday")
	// Facebook expects a comma-delimited list of scopes
		.withScopeDelimiter(",");
	Auth.get().clearAllTokens();
	Auth.get().login(req, callback);	
    }
 
    public void RequestAuthorizationAndQueryGraph(final String id,
	    final Callback<JSONObject, Throwable> callback) {
	RequestAuthorizationAndQueryGraph(id, RequestBuilder.GET, null, callback);
    }
 
    public void RequestAuthorizationAndQueryGraph(final String id, final Method method,
	    final String params, final Callback<JSONObject, Throwable> callback) {
	if (token == null) {
	    RequestAuthorization(new Callback<String, Throwable>() {
		public void onSuccess(String token) {
		    FacebookGraph.this.token = token;
		    QueryGraph(id, method, params, callback);
		}
 
		public void onFailure(Throwable reason) {
		    callback.onFailure(reason);
		}
	    });
	} else {
	    QueryGraph(id, method, params, callback);
	}
    }
 
    private void QueryGraph(String id, Method method, String params,
	    final Callback<JSONObject, Throwable> callback) {
	final String requestData = "access_token=" + token
		+ (params != null ? "&" + params : "");
	RequestBuilder builder;
	if (method == RequestBuilder.POST) {
	    builder = new RequestBuilder(method, "https://graph.facebook.com/"
		    + id);
	    builder.setHeader("Content-Type",
		    "application/x-www-form-urlencoded");
	} else if (method == RequestBuilder.GET) {
	    builder = new RequestBuilder(method, "https://graph.facebook.com/"
		    + id + "?" + requestData);
	} else {
	    callback.onFailure(new IOException(
		    "doGraph only supports GET and POST requests"));
	    return;
	}
	try {
	    builder.sendRequest(requestData, new RequestCallback() {
		public void onError(Request request, Throwable exception) {
		    callback.onFailure(exception);
		}
 
		public void onResponseReceived(Request request,
			Response response) {
		    if (Response.SC_OK == response.getStatusCode()) {
			callback.onSuccess(JSONParser.parseStrict(
				response.getText()).isObject());
		    } else if (Response.SC_BAD_REQUEST == response
			    .getStatusCode()) {
			callback.onFailure(new IOException("Error: "
				+ response.getText()));
		    } else {
			callback.onFailure(new IOException(
				"Couldn't retrieve JSON ("
					+ response.getStatusText() + ")"));
		    }
		}
 
	    });
	} catch (RequestException e) {
	    callback.onFailure(e);
	}
    }
 
}