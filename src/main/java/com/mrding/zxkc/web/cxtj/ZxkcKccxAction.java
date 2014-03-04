package com.mrding.zxkc.web.cxtj;

import java.io.*;

import com.mrding.common.CommonUtils;
import com.mrding.common.ExportExcel;
import com.mrding.common.web.ActionSupport;
import com.mrding.zxkc.server.ZxkcKccxManager;
import com.mrding.zxkc.vo.ZxkcKccxVo;

public class ZxkcKccxAction extends ActionSupport<ZxkcKccxVo, ZxkcKccxManager> {
    
    public String list() {
        return "kccx";
    }
    
    public String loadKclist() {
    	//将查询条件放入session供导出时使用
    	session.put("preModel", model);
    	jsonMap.put("kclist", manager.loadKclist(model));
    	return SUCCESS;
    }
    
    public String export() {
    	String xlsName = request.getParameter("xlsName");
    	ZxkcKccxVo preModel = (ZxkcKccxVo) session.get("preModel");
    	try {
	    	manager.export(response, preModel);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public String exportExcel() {
		String excelName = request.getParameter("excelName");
    	System.out.println(excelName);
		String excelNameaddXls = null;
		if(CommonUtils.isNotBlank(excelName)){
			excelNameaddXls = excelName+".xls";
		}else{
			excelNameaddXls = "未命名.xls";
		}
		try {
			response.setContentType("octets/stream");
			response.setHeader("Content-Disposition","attachment;filename="+new String(excelNameaddXls.getBytes("utf-8"),"utf-8"));
			String str=request.getParameter("exportContent");
			
			String[][] datas=null;
			String[] row=str.split(";");
			datas=new String[row.length][];
			for(int i=0;i<row.length;i++){
				String[] cell=row[i].split(",");
				datas[i]=new String[cell.length];
				for(int j=0;j<cell.length;j++){
					if(CommonUtils.isNotBlank(cell[j])){
						if(cell[j].equals("undefined")){
							datas[i][j]="";
						}else{
							datas[i][j]=cell[j];
						}
					}else{
						datas[i][j] = "";
					}
					
				}
			}
		
	        ExportExcel<String[][]> ex = new ExportExcel<String[][]>();
	        String[] headers = datas[0];
	        try {
	            OutputStream out = response.getOutputStream();
	            ex.exportExcel(headers, datas, out);
	            out.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	        return null;

    }

}
