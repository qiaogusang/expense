package com.gusangyuan.expense.entity;

import java.util.Map;

import lombok.Data;

@Data
public class RecordVO {

	private int total;
	private Map<String, Integer> detail;
}
