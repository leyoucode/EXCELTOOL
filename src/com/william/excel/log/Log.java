package com.william.excel.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.william.excel.config.Configuration;

/**
 * 记录日志类
 * @author it2
 *
 */
public class Log {

	private static String  LOGFILE = Configuration.getLOGFILE();//从配置文件中获得配置信息
	//写日志
	public static boolean writeLog(String msg,boolean show){
		if(show){
			Message.showln(msg);
		}
		File file;
		FileWriter fw;
		BufferedWriter  bw;
		file = new File(LOGFILE);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message.showln("日志文件:"+file.getAbsoluteFile()+"不存在，创建新文件成功！");
		}
		try {
			fw = new FileWriter(file,true);
			bw = new BufferedWriter(fw);
			bw.write("\r\n");
			bw.write(msg, 0, msg.length());
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			Message.showln("日志文件记录失败："+e.getMessage());
		}
		
		return true;
	}
}
