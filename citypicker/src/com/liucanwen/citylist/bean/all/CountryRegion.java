package com.liucanwen.citylist.bean.all;

import java.util.List;

public class CountryRegion {
	private String name;

	private String code;

	private List<Children> children;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setChildren(List<Children> children) {
		this.children = children;
	}

	public List<Children> getChildren() {
		return this.children;
	}

}