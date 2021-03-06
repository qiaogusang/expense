package com.gusangyuan.expense;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gusangyuan.expense.service.ShowHandler;
import com.gusangyuan.expense.util.DateUtil;
import com.gusangyuan.expense.util.FileUtil;
import com.gusangyuan.expense.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpenseApplicationTests {

	@Autowired
	private ShowHandler handler;
	
	@Test
	public void testPath() {
		Path path = Paths.get("E:\\expense\\files\\2020\\05\\29.txt");
		log.info("文件地址是否存在：{}", path.toFile().isDirectory());
		Iterator<Path> list = path.iterator();
		Path p = null;
		while(list.hasNext()) {
			p = list.next();
//			log.info("文件路径：{}", list.next());
		}
		log.info("文件路径：{}", p.toString());
		String str = StringUtils.replace(p.toString(), ".txt", "");
		log.info("文件路径2：{}", p.toFile().getPath());
		log.info("文件路径3：{}", str);
//		log.info("合成文件路径：{}", path.resolve("2020"));
	}
	
	@Test
	public void testDictionary() throws Exception {
		Path path = FileUtil.createFileAppendDate("E:\\file", "2020-05-28");
		log.info("创建文件目录：{}", path);
	}
	
	@Test
	public void testWrite() throws Exception {
		List<String> content = new ArrayList<>();
		content.add("西游记");
		content.add("红楼梦");
		content.add("水浒传");
		content.add("三国演义");
		
		Path path = FileUtil.createFileAppendDate("E:\\file", "2020-05-28");
		FileUtil.write(path, StringUtil.listToString(content, "\r\n"));
		log.info(FileUtil.read(path.toFile().getPath()).toString());
	}
	
	@Test
	public void testRead() throws Exception {
//		log.info(FileUtil.read(url).toString());
		Path path = Paths.get("E:\\expense\\files\\2020");
		Stream<Path> paths = FileUtil.fileList(path);
		paths.forEach((e) -> {
			log.info("{}", e);
			try {
				Stream<Path> list = FileUtil.fileList(e);
				list.forEach(d -> log.info("{}", d));
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			
		});
	}
	
	@Test
	public void testDateUtil() {
		/*log.info("当前日期：{}", DateUtil.getCurrentDate());
		log.info("当前年：{}", DateUtil.getCurrentYear());
		log.info("当前月：{}", DateUtil.getCurrentMonth());
		log.info("当前天：{}", DateUtil.getCurrentDay());*/
		log.info("当前天：{}", DateUtil.formatDate("2020-219042", "yyyy-MM-dd"));
	}
	
	@Test
	public void testShow() throws IOException {
		Path path = Paths.get("E:\\expense\\files\\2020");
		log.info("{}", JSON.toJSON(handler.total(path, "year")));
	}

}
