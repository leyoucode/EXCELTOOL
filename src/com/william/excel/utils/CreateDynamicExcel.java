package com.william.excel.utils;

import java.io.File;
import java.io.FileWriter;

/**
 * �������ڸ���etc/config.xml��������Ϣ��̬������
 * �������Ĭ������Ϊ"DynamicExcel"
 * @author it2
 *
 */
public class CreateDynamicExcel {
	private String CLASS_NAME="DynamicExcel";//������̬�������,Ĭ��Ϊ��DynamicExcel
	private String CLASS_FILE = CLASS_NAME + ".java";//������̬����ļ�������.javaΪ��չ����
    private String[] properties;//������̬������Լ���
    
    public CreateDynamicExcel(String className , String[] properties){
    	if(className!=null && !"".equals(className.trim())){
    		this.CLASS_NAME = className;
    		this.CLASS_FILE=className+".java";
		}
    	this.properties = properties;
    }
    
    //��̬������
	public void createClass() {
		try {
			new File(CLASS_FILE).delete();
			FileWriter fw = new FileWriter(CLASS_FILE, true);
			// ��
			fw.write("public   class   " + CLASS_NAME + "{");
			for (int i = 0; i < properties.length; i++) {
				String field = properties[i];
				// ����
				fw.write("private String " + field + ";");
				String property = field.replace(field.substring(0, 1), field
						.substring(0, 1).toUpperCase());
				// set����
				fw.write("public void set" + property + "(String " + field
						+ "){");
				fw.write("this." + field + "=" + field + ";");
				fw.write("}");
				// get����
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
//��̬������
	public void compileClass() {
		String filePath = new File(CreateDynamicExcel.class.getClassLoader()
				.getResource("").getFile()).getAbsolutePath();
		String[] source = { "-d", filePath, new String(CLASS_FILE) };
		System.out.println("javac out:"
				+ com.sun.tools.javac.Main.compile(source));
	}
}
