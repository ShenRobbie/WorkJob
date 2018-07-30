package com.example.workjob.until;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class CustomUntil {
	
	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception { 
        File targetFile = new File(filePath);  
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }  
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
	
	public static void deleteLoadFile(String filePath, String fileName){
		File targetFile = new File(filePath+fileName);  
		if(targetFile.exists()){
			targetFile.delete();
		}
        
	}
	
	
	//根据限制文件大小生成每天不同的文件名
	@SuppressWarnings("resource")
	public static String getFileName(String filepath,String currentDate) {
		List<String> list =new ArrayList<>();
		File file = new File(filepath);
		String[] filelist= file.list();
		for (int i = 0; i < filelist.length; i++) {
			File readfile = new File(filepath + "\\" + filelist[i]);
			String name=readfile.getName();
			//System.out.println("name=" + name);
			if(name.indexOf(currentDate)!=-1){
				list.add(name.replace(".log", ""));
			}
		}
		if(list.size() == 0){
			return currentDate+"00";
		}else{
			
			//排序			
			Collections.sort(list, new TestServerComparator());  
			String finalName=list.get(0);
			File finalFile = new File(filepath + "\\" +finalName +".log");
			FileInputStream fis;
			try {
				fis = new FileInputStream(finalFile);
				//System.out.println(fis.available());
				if(fis.available()> (1024)){
					Long total= Long.parseLong(finalName)+1;
					return total.toString();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return finalName;
			}

			return finalName;
		}
		
	}

	
	
	
}
