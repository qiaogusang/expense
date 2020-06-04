package com.gusangyuan.expense.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

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
	public static Path createFileAppendDate(String baseUrl, String date) throws IOException {
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
	 * @throws IOException
	 */
	public static void write(String url, String content) throws IOException {
		Path path = Paths.get(url);
		Files.write(path, content.getBytes(), StandardOpenOption.CREATE); 
	}
	
	public static void write(Path path, String content) throws IOException {
		Files.write(path, content.getBytes(), StandardOpenOption.CREATE); 
	}
	
	/**
	 * 一次性读取行
	 * @param url
	 * @return List<String>
	 * @throws Exception
	 */
	public static List<String> read(String url) throws IOException {
		Path path = Paths.get(url);
        return Files.readAllLines(path);
	}
	
	public static List<String> read(Path path) throws IOException {
        return Files.readAllLines(path);
	}
	
	/**
	 * 一次性读取字节
	 * @param url
	 * @return String
	 * @throws IOException 
	 * @throws Exception
	 */
	public static String readByte(Path path) throws IOException {
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
	public static String readLine(String url) throws IOException {
		List<String> list = new ArrayList<>();
		
		Path path = Paths.get(url);
		try(BufferedReader reader = Files.newBufferedReader(path)) {
			while(reader.ready()) {
				list.add(reader.readLine());
			}
		}
        return StringUtil.listToString(list, "\r\n");
	}
	
	/**
	 * 遍历文件夹
	 * @param Path path
	 * @return List<Path>
	 * @throws IOException
	 */
	public static Stream<Path> fileList(Path path) throws IOException {
		return Files.list(path);
	}
	/**
	 * 遍历文件目录树
	 * @param Path path
	 * @return List<Path>
	 * @throws IOException
	 */
	public static List<Path> walkFileTree(Path path) throws IOException {
        List<Path> result = new LinkedList<>();
        Files.walkFileTree(path, new FindJavaVisitor(result));
        return result;
	}
	
	private static class FindJavaVisitor extends SimpleFileVisitor<Path>{
        private List<Path> result;
        public FindJavaVisitor(List<Path> result){
            this.result = result;
        }
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
        	result.add(file.getFileName());
            return FileVisitResult.CONTINUE;
        }
    }
}
