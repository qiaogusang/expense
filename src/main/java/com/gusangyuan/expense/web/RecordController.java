package com.gusangyuan.expense.web;

import java.io.File;
import java.nio.file.Path;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gusangyuan.expense.entity.RecordVO;
import com.gusangyuan.expense.util.FileUtil;
import com.gusangyuan.expense.util.StringUtil;

@Controller
public class RecordController {
	private static final String BASEURL = System.getProperty("user.dir") + File.separator + "files";
	
	@GetMapping("/show")
	public String showCurrent(Model model) {
		return "index";
	}
	
	@ResponseBody
	@PostMapping("/create/v1")
	public String addRecordByVersion1(@RequestBody RecordVO recordVo) throws Exception {
		Path path = FileUtil.createFileAppendDate(BASEURL, recordVo.getDate());
		FileUtil.write(path, StringUtil.listToString(recordVo.getRecordList()));
		return "success";
	}
	
	@ResponseBody
	@PostMapping("/create/v2")
	public String addRecordByVersion2(@RequestBody RecordVO recordVo) throws Exception {
		Path path = FileUtil.createFileAppendDate(BASEURL, recordVo.getDate());
		FileUtil.writeLine(path, recordVo.getContent());
		return "success";
	}
	
}
