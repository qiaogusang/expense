package com.gusangyuan.expense.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gusangyuan.expense.entity.PathBO;
import com.gusangyuan.expense.entity.RecordVO;
import com.gusangyuan.expense.util.ConstUtil;
import com.gusangyuan.expense.util.FileUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件数据的显示处理
 * @author QZ
 */
@Slf4j
@Service
public class ShowHandler {

	/**
	 * 统计
	 * @param path
	 * @param mark : year/month
	 * @return RecordVO
	 * @throws IOException
	 */
	public RecordVO total(Path path, String mark) throws IOException {
		RecordVO recordVo = new RecordVO();
		List<Integer> list = new ArrayList<>();
		List<PathBO> detail = new LinkedList<>();
		
		Stream<Path> paths = FileUtil.fileList(path);
		paths.sorted().forEach(e -> {
			int total = 0;
			try {
				total = ConstUtil.YEAR.equals(mark) ? monthTotal(e) : dayTotal(e);
				list.add(total);
				
				PathBO pathBo = new PathBO();
				pathBo.setTotal(total);
				pathBo.setNum(parseFileName(e));
				pathBo.setPath(URLEncoder.encode(String.valueOf(e), "utf-8"));
				detail.add(pathBo);
			} catch (IOException e1) {
				log.error(e1.getMessage());
			}
		});
		
		recordVo.setTotal(listTotal(list));
		recordVo.setDetail(detail);
		log.info(JSON.toJSONString(recordVo));
		return recordVo;
	}
	
	/**
	 * 日记录
	 * @param path
	 * @return String
	 * @throws IOException
	 */
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
				log.error(e1.getMessage());
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
	
	private String parseFileName(Path path) {
		Path fileName = path;
		
		Iterator<Path> list = path.iterator();
		while(list.hasNext()) {
			fileName = list.next();
		}
		if(!path.toFile().isDirectory()) {
			return StringUtils.replace(fileName.toString(), ".txt", "");
		}
		return fileName.toString();
	}
	
}
