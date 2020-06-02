package com.gusangyuan.expense.util;

public enum ConsumeType {
	
	CLOTHES("衣"),
	FOOD("食"),
	LIVE("住"),
	TRAFFIC("行");
	
	private ConsumeType(String type) {
		this.type = type;
	}
	
	private String type;
	public String getType() {
		return type;
	}
}
