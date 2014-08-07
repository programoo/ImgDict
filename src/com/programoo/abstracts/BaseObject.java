package com.programoo.abstracts;

import com.google.gson.Gson;


public class BaseObject
{
	@Override
	public String toString()
	{
		return new Gson().toJson(this);
	}
}
