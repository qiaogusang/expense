package com.gusangyuan.expense;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gusangyuan.expense.util.DateUtil;
import com.gusangyuan.expense.util.FileUtil;
import com.gusangyuan.expense.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpenseApplicationTests {

	private String url = "e:\\aaa.txt";
	
	@Test
	public void testPath() {
		Path path = Paths.get("E:\\file");
		log.info("文件地址是否存在：{}", FileUtil.isPathExist(path));
		Iterator<Path> list = path.iterator();
		while(list.hasNext()) {
			log.info("文件路径：{}", list.next());
		}
		log.info("合成文件路径：{}", path.resolve("2020"));
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
		log.info(FileUtil.read(url).toString());
		
	}
	
	@Test
	public void testDateUtil() {
		/*log.info("当前日期：{}", DateUtil.getCurrentDate());
		log.info("当前年：{}", DateUtil.getCurrentYear());
		log.info("当前月：{}", DateUtil.getCurrentMonth());
		log.info("当前天：{}", DateUtil.getCurrentDay());*/
		log.info("当前天：{}", DateUtil.formatDate("2020-219042", "yyyy-MM-dd"));
	}

}
