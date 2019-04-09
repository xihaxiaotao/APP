package cn.appsys.tools;
import java.io.File;
import java.util.List;

import cn.appsys.pojo.AppInfo;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtils { //导出表格工具类
	 /**
     * 对象数据写入到Excel
     */
    public static void writeExcel(String  filePath,String sheetName,String[]title,List<AppInfo> appInfoList) {
        WritableWorkbook book = null;
        try {
	          // 打开文件
	          book = Workbook.createWorkbook(new File(filePath));
	          // 生成名为"学生"的工作表，参数0表示这是第一页
	          WritableSheet sheet = book.createSheet(sheetName, 0);
       
              //添加标题
              for (int i = 0; i < title.length; i++) {
            	  sheet.addCell(new Label(i,0,title[i]));
			  }
               
              //添加数据
              for (int i = 0; i < appInfoList.size(); i++) {
            	  AppInfo  app=appInfoList.get(i);
            	for (int j = 1; j <title.length-1; j++) {
            	 	 sheet.addCell(new Label(0,i+1,app.getId().toString()));
    				 sheet.addCell(new Label(1,i+1,app.getSoftwareName()));
    				 sheet.addCell(new Label(2,i+1,app.getAPKName()));
    				 sheet.addCell(new Label(3,i+1,app.getSoftwareSize().toString()));
    				 sheet.addCell(new Label(4,i+1,app.getFlatformName()));
    				 sheet.addCell(new Label(5,i+1,app.getCategoryLevel1Name()+"<"+app.getCategoryLevel2Name()+"<"+app.getCategoryLevel3Name()));
    				 sheet.addCell(new Label(6,i+1,app.getStatusName()));
    				 sheet.addCell(new Label(7,i+1,app.getDownloads().toString()));
    				 sheet.addCell(new Label(8,i+1,app.getVersionNo()));
				}
            		 
			  }

            // 写入数据并关闭文件
            book.write();
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(book!=null){
                try {
                    book.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } 
            }
        }
    
    }
}
