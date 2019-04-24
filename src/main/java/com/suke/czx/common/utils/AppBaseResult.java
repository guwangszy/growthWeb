package com.suke.czx.common.utils;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @category app返回类
 * @author czx
 * 2017-04-25
 */
public class AppBaseResult<T> implements Serializable {

	private int errcode = 500;
	private String errmsg = "";
	private String data = "";
	private String version = "1.0";
	private String mobile = "";

	public final static int ERROR = 401;
	public final static int SUCCESS = 0;
	public final static int FAIL = 500;
	public final static int TOKENFAIL = 1000;
	public final static String KEY = "czx12345";


	public static AppBaseResult success(String msg){
		AppBaseResult appBaseResult = new AppBaseResult();
		appBaseResult.setErrcode(SUCCESS);
		appBaseResult.setErrmsg(msg);
		return appBaseResult;
	}

	public static AppBaseResult success(){
		AppBaseResult appBaseResult = new AppBaseResult();
		appBaseResult.setErrcode(SUCCESS);
		appBaseResult.setErrmsg("请求成功");
		return appBaseResult;
	}

	public static AppBaseResult error(String msg){
		AppBaseResult appBaseResult = new AppBaseResult();
		appBaseResult.setErrcode(FAIL);
		appBaseResult.setErrmsg(msg);
		return appBaseResult;
	}

	public static AppBaseResult error(int errcode,String msg){
		AppBaseResult appBaseResult = new AppBaseResult();
		appBaseResult.setErrcode(errcode);
		appBaseResult.setErrmsg(msg);
		return appBaseResult;
	}

	public static AppBaseResult error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}


	public int getErrcode() {
		return errcode;
	}
	public AppBaseResult setErrcode(int status) {
		this.errcode = status;
		return this;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public AppBaseResult setErrmsg(String message) {
		this.errmsg= message;
		return this;
	}
	public String getData() {
		return  this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public HashMap<String,Object> decryptData(String data) {
		String mData = null;
		if(!Tools.isEmpty(data)){
			try {
				mData = CDESCrypt.decryptString(data, KEY);
				//mData=data;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new Gson().fromJson(mData,new TypeToken<HashMap<String,Object>>() {}.getType());
	}

	public String decryptData() {
		String mData = null;
		if(!Tools.isEmpty(this.data)){
			try {
				mData = CDESCrypt.decryptString(this.data, KEY);
				//mData=this.data;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mData;
	}

	public AppBaseResult setData(T t) {
		String mData = new Gson().toJson(t);
		try {
			this.data = mData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	public AppBaseResult setEncryptData(T t) {
		String mData = new Gson().toJson(t);
	/*	try {
			if(!Tools.isEmpty(mData)){
				this.data = CDESCrypt.encryptString(mData, KEY);
				//this.data=mData;
			}else{
				this.data = mData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		this.data = mData;
		return this;
	}

	public String getVersion() {
		return version;
	}
	public AppBaseResult setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getMobile() {
		return mobile;
	}

	public AppBaseResult setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	@Override
	public String toString() {
		return "{" +
				"errcode='" + errcode + '\'' +
				", errmsg='" + errmsg + '\'' +
				", data='" + data + '\'' +
				", version='" + version + '\'' +
				", mobile='" + mobile + '\'' +
				'}';
	}
}
