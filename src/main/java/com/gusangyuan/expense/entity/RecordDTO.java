package com.gusangyuan.expense.entity;

import java.util.List;

import lombok.Data;

@Data
public class RecordDTO {

	private String date;
	private String content;
	private List<Record> recordList;
}
