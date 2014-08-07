package com.programoo.models;

import java.util.List;

import com.programoo.abstracts.BaseObject;

public class ResponseDataOut extends BaseObject{
	List<Result> results;

	public List<Result> getResults()
	{
		return results;
	}

	public void setResults(List<Result> results)
	{
		this.results = results;
	}
}
