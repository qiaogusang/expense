package com.gusangyuan.expense.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Jdk8文件读写类
 * <br>
 * @author Administrator
 *
 */
public class FileUtil {
	
	private FileUtil() {}
	
	/**
	 * 判断文件是否存在
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static boolean isPathExist(Path path) {
		return path.toFile().exists();
	}
	
	/**
	 * 生成文件路径
	 * @param url
	 * @return Path
	 * @throws IOException 
	 */
	public static Path getPath(String url) throws IOException {
		Path path = Paths.get(url);
		if(!isPathExist(path)) {
			Files.createFile(path);
		}
		return path;
	}
	
	/**
	 * 根据当前时间创建新的文件路径
	 * @param baseUrl 
	 * @param date 日期格式：yyyy-MM-dd
	 * @throws Exception
	 */
	public static Path createFileAppendDate(String baseUrl, String date) throws Exception {
		String[] arr = date.split("-");
		Path path = Paths.get(baseUrl);
		
		path = path.resolve(arr[0]);
		if(!isPathExist(path)) {
			Files.createDirectory(path);
		}
		
		path = path.resolve(arr[1]);
		if(!isPathExist(path)) {
			Files.createDirectory(path);
		}
		
		path = path.resolve(arr[2]+".txt");
		if(!isPathExist(path)) {
			Files.createFile(path);
		}
		
		return path;
	}
	
	/**
	 * 一次性写入
	 * @param url
	 * @param content
	 * @throws Exception
	 */
	public static void write(String url, String content) throws Exception {
		Path path = Paths.get(url);
		Files.write(path, content.getBytes(), StandardOpenOption.CREATE); 
	}
	
	public static void write(Path path, String content) throws Exception {
		Files.write(path, content.getBytes(), StandardOpenOption.CREATE); 
	}
	
	/**
	 * 一次性读取行
	 * @param url
	 * @return List<String>
	 * @throws Exception
	 */
	public static List<String> read(String url) throws Exception {
		Path path = Paths.get(url);
        return Files.readAllLines(path);
	}
	
	/**
	 * 一次性读取字节
	 * @param url
	 * @return String
	 * @throws Exception
	 */
	public static String read(Path path) throws Exception {
        byte[] content = Files.readAllBytes(path);
        return new String(content);
	}
	
	/**
	 * 按行写入
	 * @param url String
	 * @param content String
	 * @throws IOException 
	 */
	public static void writeLine(Path path, String content) throws IOException {
		boolean line0 = false;
		if(!isPathExist(path)) {
			line0 = true;
			Files.createFile(path);
		}
		
		if(Files.size(path) == 0)
			line0 = true;
		
		try(BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
			if(line0) {
				writer.write(content);
			} else {
				writer.newLine();
				writer.write(content);
			}
		}
		
	}
	
	/**
	 * 按行读取
	 * @param url
	 * @return String
	 * @throws Exception
	 */
	public static String readLine(String url) throws Exception {
		List<String> list = new ArrayList<>();
		
		Path path = Paths.get(url);
		try(BufferedReader reader = Files.newBufferedReader(path)) {
			while(reader.ready()) {
				list.add(reader.readLine());
			}
		}
        return StringUtil.listToString(list, "\r\n");
	}
}
