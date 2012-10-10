package com.william.excel.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.william.excel.config.Configuration;

/**
 * ��¼��־��
 * @author it2
 *
 */
public class Log {

	private static String  LOGFILE = Configuration.getLOGFILE();//�������ļ��л��������Ϣ
	//д��־
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
			Message.showln("��־�ļ�:"+file.getAbsoluteFile()+"�����ڣ��������ļ��ɹ���");
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
			Message.showln("��־�ļ���¼ʧ�ܣ�"+e.getMessage());
		}
		
		return true;
	}
}
