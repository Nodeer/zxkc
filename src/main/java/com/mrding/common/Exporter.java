package com.mrding.common;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 导出Excel工具类
 * @author mrding
 *
 */
public abstract class Exporter {
	
	private String[] head;
	private List dataList;
	protected Object param;

	public Exporter(String[] head, List dataList) {
		this.head = head;
		this.dataList = dataList;
	}

	public Exporter(String[] head, List dataList, Object param) {
		this.head = head;
		this.dataList = dataList;
		this.param = param;
	}

	/**
	 * 执行导出
	 * @param xlsName 
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void export(HttpServletResponse response, String xlsName) throws Exception {
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-disposition",
				"attachment;filename=" + xlsName + ".xls");
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet sheet = wwb.createSheet("sheet1", 0);
		addHead(sheet);
		addData(sheet);
		wwb.write();
		wwb.close();
	}

	/**
	 * 添加数据
	 * @param sheet
	 */
	private void addData(WritableSheet sheet) {
		if (CommonUtils.listIsNotBlank(dataList))
		for (int i = 0, max = dataList.size(); i < max; i++) {
			Object row = dataList.get(i);
			addRow(sheet, row, i);
		}
	}

	/**
	 * 加入一行数据到sheet
	 * @param sheet
	 * @param row
	 * @param i 
	 */
	protected abstract void addRow(WritableSheet sheet, Object row, int i);

	/**
	 * 添加表头
	 * @param sheet
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void addHead(WritableSheet sheet) throws RowsExceededException, WriteException {
		if (head != null) {
			for (int i = 0, max = head.length; i < max; i++) {
				Label label = new Label(i, 0, head[i]);
				sheet.addCell(label);
			}
		}
	}

}
