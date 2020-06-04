package com.gusangyuan.expense.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gusangyuan.expense.entity.RecordDTO;
import com.gusangyuan.expense.entity.RecordVO;
import com.gusangyuan.expense.service.ShowHandler;
import com.gusangyuan.expense.util.DateUtil;
import com.gusangyuan.expense.util.FileUtil;
import com.gusangyuan.expense.util.StringUtil;

@Controller
public class RecordController {
	private static final String BASEURL = System.getProperty("user.dir") + File.separator + "files";
	
	@Autowired
	private ShowHandler handler;
	
	@GetMapping("/show")
	public String showCurrent(Model model) throws IOException {
		String url = BASEURL + File.separator + DateUtil.getCurrentYear();
		Path path = Paths.get(url);
		if(FileUtil.isPathExist(path)) {
			RecordVO vo = handler.yearTotal(path);
			model.addAttribute("year", vo.getTotal());
			model.addAttribute("months", vo.getDetail());
		}
		return "index";
	}
	
	@ResponseBody
	@PostMapping("/create/v1")
	public String addRecordByVersion1(@RequestBody RecordDTO recordVo) throws IOException {
		Path path = FileUtil.createFileAppendDate(BASEURL, recordVo.getDate());
		FileUtil.write(path, StringUtil.listToString(recordVo.getRecordList()));
		return "success";
	}
	
	@ResponseBody
	@PostMapping("/create/v2")
	public String addRecordByVersion2(@RequestBody RecordDTO recordVo) throws IOException {
		Path path = FileUtil.createFileAppendDate(BASEURL, recordVo.getDate());
		FileUtil.writeLine(path, recordVo.getContent());
		return "success";
	}
	
}
