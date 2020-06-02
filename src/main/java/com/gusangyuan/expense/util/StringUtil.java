package com.gusangyuan.expense.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
/**
 * jdk8字符处理类
 * @author Administrator
 *
 */
public class StringUtil {

	private StringUtil() {}
	
	public static String listToString(List<String> list, String split) {
		return list.stream().collect(Collectors.joining(split));
	}
	
	public static <T> String listToString(List<T> list) {
		List<String> strList = new ArrayList<>(list.size()); 
		list.forEach(e -> strList.add(JSON.toJSONString(e)));
		return listToString(strList, "\r\n");
	}
	
}
