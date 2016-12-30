package com.nicksong.s.browser.view;

import java.util.Map;

import com.tencent.smtt.sdk.QbSdk;

import android.content.Context;

public abstract class SecurityJsBridgeBundle {
	
	
	///////////////////////////////////////////////////////////////////////////////
	//add js 
	private final static String DEFAULT_JS_BRIDGE ="JsBridge";
	public static final String METHOD = "method";
	public static final String BLOCK = "block";
	public static final String CALLBACK = "callback";
	
	public static final String PROMPT_START_OFFSET = "local_js_bridge::";
	
	private Context mContext;
	private String mJsBlockName ;
	private String mMethodName;
	

	public abstract void onCallMethod();
	
	public SecurityJsBridgeBundle(String JsBlockName, String methodName) throws Exception{
		if(methodName == null){
			throw new Exception("methodName can not be null!");
		}
		
		if(JsBlockName!=null){
			this.mJsBlockName=JsBlockName;
		}else{
			this.mJsBlockName = DEFAULT_JS_BRIDGE;
		}
		
		
	}
	
	
	public String getMethodName(){
		return this.mMethodName;
	}
	
	public String getJsBlockName(){
		return this.mJsBlockName;
	}
	
	
	private void injectJsMsgPipecode(Map<String,Object> data){
		if(data==null){
			return ;
		}
		String injectCode = "javascript:(function JsAddJavascriptInterface_(){ "+ 
		    "if (typeof(window.jsInterface)!='undefined') {"+      
		        "console.log('window.jsInterface_js_interface_name is exist!!');}   "+ 
		    "else {"+ 
		        data.get(BLOCK)+data.get(METHOD)+
		        "window.jsBridge = {"+           
		            "onButtonClick:function(arg0) {"+    
		                "return prompt('MyApp:'+JSON.stringify({obj:'jsInterface',func:'onButtonClick',args:[arg0]}));"+   
		            "},"+  
		              
		            "onImageClick:function(arg0,arg1,arg2) {"+    
		                "prompt('MyApp:'+JSON.stringify({obj:'jsInterface',func:'onImageClick',args:[arg0,arg1,arg2]}));"+  
		            "},"+   
		        "};"+   
		    "}"+   
		"}"+   
		")()";
	}
	
	
	private static String getStandardMethodSignature(){
		return null;
	}
	
	
}
