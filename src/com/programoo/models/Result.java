package com.programoo.models;

import com.programoo.abstracts.BaseObject;

public class Result extends BaseObject{
	String titleNoFormatting;
	String unescapedUrl;
	String url;
	String title;
	Img image;
	public String getTitleNoFormatting()
	{
		return titleNoFormatting;
	}
	public void setTitleNoFormatting(String titleNoFormatting)
	{
		this.titleNoFormatting = titleNoFormatting;
	}
	public String getUnescapedUrl()
	{
		return unescapedUrl;
	}
	public void setUnescapedUrl(String unescapedUrl)
	{
		this.unescapedUrl = unescapedUrl;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public Img getImage()
	{
		return image;
	}
	public void setImage(Img image)
	{
		this.image = image;
	}
}
