package com.programoo.utils;

import java.util.List;

import android.content.Context;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.programoo.models.Result;

public class Global {
	private static Global objLogger;
	private AQuery aq;
	private Gson gSon;
	private List<Result> imgModel;
	private MyDatabase mdb;

	public List<Result> getImgModel() {
		return imgModel;
	}

	public void setImgModel(List<Result> imgModel) {
		this.imgModel = imgModel;
	}

	private Global(Context ctx) {
		aq = new AQuery(ctx);
		gSon = new Gson();
		mdb = new MyDatabase(ctx);
	}

	public MyDatabase getMdb() {
		return mdb;
	}

	public void setMdb(MyDatabase mdb) {
		this.mdb = mdb;
	}

	public static Global getInstance(Context ctx) {
		if (objLogger == null) {
			objLogger = new Global(ctx);
		}
		return objLogger;
	}

	public AQuery getAq() {
		return aq;
	}

	public Gson getgSon() {
		return gSon;
	}

	public void setgSon(Gson gSon) {
		this.gSon = gSon;
	}

	public void setAq(AQuery aq) {
		this.aq = aq;
	}

}