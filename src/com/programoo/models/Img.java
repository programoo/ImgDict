package com.programoo.models;

import com.programoo.abstracts.BaseObject;

public class Img extends BaseObject {
	String url;
	String tbUrl;
	String originalContextUrl;
	String publisher;
	String tbWidth;
	String bHeight;
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getTbUrl()
	{
		return tbUrl;
	}
	public void setTbUrl(String tbUrl)
	{
		this.tbUrl = tbUrl;
	}
	public String getOriginalContextUrl()
	{
		return originalContextUrl;
	}
	public void setOriginalContextUrl(String originalContextUrl)
	{
		this.originalContextUrl = originalContextUrl;
	}
	public String getPublisher()
	{
		return publisher;
	}
	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}
	public String getTbWidth()
	{
		return tbWidth;
	}
	public void setTbWidth(String tbWidth)
	{
		this.tbWidth = tbWidth;
	}
	public String getbHeight()
	{
		return bHeight;
	}
	public void setbHeight(String bHeight)
	{
		this.bHeight = bHeight;
	}
}
