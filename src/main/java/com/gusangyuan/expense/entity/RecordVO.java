package com.gusangyuan.expense.entity;

import java.util.List;

import lombok.Data;

@Data
public class RecordVO {

	private int total;
	private String year;
	private String month;
	private List<PathBO> detail;
}
