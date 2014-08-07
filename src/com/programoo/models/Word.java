package com.programoo.models;

import com.programoo.abstracts.BaseObject;

public class Word extends BaseObject {
	private int id;
	private String esearch;
	private String eentry;
	private String tentry;
	private String ecat;
	private String ethai;
	private String esyn;
	private String eant;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEsearch() {
		return esearch;
	}

	public void setEsearch(String esearch) {
		this.esearch = esearch;
	}

	public String getEentry() {
		return eentry;
	}

	public void setEentry(String eentry) {
		this.eentry = eentry;
	}

	public String getTentry() {
		return tentry;
	}

	public void setTentry(String tentry) {
		this.tentry = tentry;
	}

	public String getEcat() {
		return ecat;
	}

	public void setEcat(String ecat) {
		this.ecat = ecat;
	}

	public String getEthai() {
		return ethai;
	}

	public void setEthai(String ethai) {
		this.ethai = ethai;
	}

	public String getEsyn() {
		return esyn;
	}

	public void setEsyn(String esyn) {
		this.esyn = esyn;
	}

	public String getEant() {
		return eant;
	}

	public void setEant(String eant) {
		this.eant = eant;
	}

}
