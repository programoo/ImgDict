package com.programoo.models;

import com.programoo.abstracts.BaseObject;

public class SearchImageOut extends BaseObject{
	public String getResponseDetails()
	{
		return responseDetails;
	}
	public void setResponseDetails(String responseDetails)
	{
		this.responseDetails = responseDetails;
	}
	public String getResponseStatus()
	{
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus)
	{
		this.responseStatus = responseStatus;
	}
	public ResponseDataOut getResponseData()
	{
		return responseData;
	}
	public void setResponseData(ResponseDataOut responseData)
	{
		this.responseData = responseData;
	}
	String responseDetails;
	String responseStatus;
	ResponseDataOut responseData;


}
