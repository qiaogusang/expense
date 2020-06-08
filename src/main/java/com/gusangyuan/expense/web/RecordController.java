package com.gusangyuan.expense.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gusangyuan.expense.entity.RecordDTO;
import com.gusangyuan.expense.entity.RecordVO;
import com.gusangyuan.expense.service.ShowHandler;
import com.gusangyuan.expense.util.ConstUtil;
import com.gusangyuan.expense.util.DateUtil;
import com.gusangyuan.expense.util.FileUtil;
import com.gusangyuan.expense.util.StringUtil;

@Controller
public class RecordController {
	
	@Autowired
	private ShowHandler handler;
	
	@GetMapping("/show")
	public String showCurrent(String year, Model model) throws IOException {
		if(StringUtils.isEmpty(year))
			year = DateUtil.getCurrentYear();
		
		Path path = Paths.get(ConstUtil.BASEURL);
		path = path.resolve(year);
		if(FileUtil.isPathExist(path)) {
			RecordVO vo = handler.total(path, ConstUtil.YEAR);
			model.addAttribute("year", year);
			model.addAttribute("total", vo.getTotal());
			model.addAttribute("months", vo.getDetail());
		}
		return "index";
	}
	
	@GetMapping("/show/month/{num}")
	public String showMonth(@PathVariable String num, String url, Model model) throws IOException {
		Path path = Paths.get(URLDecoder.decode(url, "utf-8"));
		if(FileUtil.isPathExist(path)) {
			RecordVO vo = handler.total(path, ConstUtil.MONTH);
			model.addAttribute("month", num);
			model.addAttribute("total", vo.getTotal());
			model.addAttribute("days", vo.getDetail());
		}
		return "detail";
	}
	
	@ResponseBody
	@GetMapping("/show/day")
	public String showDay(String url) throws IOException {
		String content = "";
		
		Path path = Paths.get(URLDecoder.decode(url, "utf-8"));
		if(FileUtil.isPathExist(path)) {
			content = FileUtil.readByte(path);
		}
		return content;
	}
	
	@ResponseBody
	@PostMapping("/create/v1")
	public String addRecordByVersion1(@RequestBody RecordDTO recordVo) throws IOException {
		Path path = FileUtil.createFileAppendDate(ConstUtil.BASEURL, recordVo.getDate());
		FileUtil.write(path, StringUtil.listToString(recordVo.getRecordList()));
		return "success";
	}
	
	@ResponseBody
	@PostMapping("/create/v2")
	public String addRecordByVersion2(@RequestBody RecordDTO recordVo) throws IOException {
		Path path = FileUtil.createFileAppendDate(ConstUtil.BASEURL, recordVo.getDate());
		FileUtil.writeLine(path, recordVo.getContent());
		return "success";
	}
	
}
