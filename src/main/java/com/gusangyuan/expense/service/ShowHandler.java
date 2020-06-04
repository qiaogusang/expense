package com.gusangyuan.expense.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gusangyuan.expense.entity.RecordVO;
import com.gusangyuan.expense.util.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShowHandler {

	public RecordVO yearTotal(Path path) throws IOException {
		RecordVO recordVo = new RecordVO();
		List<Integer> list = new ArrayList<>();
		Map<String, Integer> monthMap = new LinkedHashMap<>(12);
		
		Stream<Path> paths = FileUtil.fileList(path);
		paths.sorted().forEach(e -> {
			try {
				int total = monthTotal(e);
				list.add(total);
				monthMap.put(getMonthPath(e), total);
			} catch (IOException e1) {
				log.debug(e1.getMessage());
			}
		});
		
		recordVo.setTotal(listTotal(list));
		recordVo.setDetail(monthMap);
		log.info(JSON.toJSONString(recordVo));
		return recordVo;
	}
	
	public List<Integer> monthDetail(Path path) throws IOException {
		List<Integer> days = new LinkedList<>();
		Stream<Path> paths = FileUtil.fileList(path);
		paths.forEach(e -> {
			try {
				days.add(dayTotal(e));
			} catch (IOException e1) {
				log.debug(e1.getMessage());
			}
		});
		return days;
	}
	
	public String dayShow(Path path) throws IOException  {
		return FileUtil.readByte(path);
	}
	
	private int monthTotal(Path path) throws IOException {
		List<Integer> list = new ArrayList<>();
		Stream<Path> paths = FileUtil.fileList(path);
		paths.forEach(e -> {
			try {
				list.add(dayTotal(e));
			} catch (IOException e1) {
				log.debug(e1.getMessage());
			}
		});
		return listTotal(list);
	}
	
	private int dayTotal(Path path) throws IOException {
		int day = 0;
		List<String> content = FileUtil.read(path);
		for(String str : content) {
			String[] arr = StringUtils.split(str.replace(":", "："), "：");
			day += Integer.parseInt(arr[1]);
		}
		return day;
	}
	
	private int listTotal(List<Integer> list) {
		int total = 0;
		for(Integer i : list) {
			total += i;
		}
		return total;
	}
	
	private String getMonthPath(Path path) {
		Path month = path;
		
		Iterator<Path> list = path.iterator();
		while(list.hasNext()) {
			month = list.next();
		}
		
		return month.toString();
	}
	
}
