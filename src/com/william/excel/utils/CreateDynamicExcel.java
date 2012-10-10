package com.william.excel.utils;

import java.io.File;
import java.io.FileWriter;

/**
 * 该类用于根据etc/config.xml的配置信息动态创建类
 * 创建类的默认名字为"DynamicExcel"
 * @author it2
 *
 */
public class CreateDynamicExcel {
	private String CLASS_NAME="DynamicExcel";//创建动态类的类名,默认为：DynamicExcel
	private String CLASS_FILE = CLASS_NAME + ".java";//创建动态类的文件名（以.java为拓展名）
    private String[] properties;//创建动态类的属性集合
    
    public CreateDynamicExcel(String className , String[] properties){
    	if(className!=null && !"".equals(className.trim())){
    		this.CLASS_NAME = className;
    		this.CLASS_FILE=className+".java";
		}
    	this.properties = properties;
    }
    
    //动态创建类
	public void createClass() {
		try {
			new File(CLASS_FILE).delete();
			FileWriter fw = new FileWriter(CLASS_FILE, true);
			// 类
			fw.write("public   class   " + CLASS_NAME + "{");
			for (int i = 0; i < properties.length; i++) {
				String field = properties[i];
				// 属性
				fw.write("private String " + field + ";");
				String property = field.replace(field.substring(0, 1), field
						.substring(0, 1).toUpperCase());
				// set方法
				fw.write("public void set" + property + "(String " + field
						+ "){");
				fw.write("this." + field + "=" + field + ";");
				fw.write("}");
				// get方法
				fw.write("public String get" + property + "(){");
				fw.write("return " + field + ";");
				fw.write("}");

			}

			fw.write("}");
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//动态编译类
	public void compileClass() {
		String filePath = new File(CreateDynamicExcel.class.getClassLoader()
				.getResource("").getFile()).getAbsolutePath();
		String[] source = { "-d", filePath, new String(CLASS_FILE) };
		System.out.println("javac out:"
				+ com.sun.tools.javac.Main.compile(source));
	}
}
