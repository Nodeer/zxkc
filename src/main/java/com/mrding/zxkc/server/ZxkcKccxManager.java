package com.mrding.zxkc.server;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.mrding.common.CommonUtils;
import com.mrding.common.Exporter;
import com.mrding.zxkc.dao.ZxkcKccxDao;
import com.mrding.zxkc.vo.ZxkcKccxVo;

public class ZxkcKccxManager {
	
	private ZxkcKccxDao dao = new ZxkcKccxDao();

	/**
	 * 查询库存信息
	 * @param model
	 * @return
	 */
	public List<ZxkcKccxVo> loadKclist(ZxkcKccxVo model) {
		List<Object[]> list = dao.queryKclist(model);
		return convertToVoList(list);
	}

	private List<ZxkcKccxVo> convertToVoList(List<Object[]> list) {
		List<ZxkcKccxVo> rtnList = new ArrayList<ZxkcKccxVo>();
		if (CommonUtils.listIsNotBlank(list)) {
			for (Object[] objs : list) {
				rtnList.add(convertToVo(objs));
			}
		}
		return rtnList;
	}

	private ZxkcKccxVo convertToVo(Object[] objs) {
		ZxkcKccxVo voBean = new ZxkcKccxVo();
		voBean.setHpbh((Integer) objs[0]); 
		voBean.setHpmc((String) objs[1]); 
		voBean.setHpsl((BigDecimal) objs[2]); 
		voBean.setDwmc((String) objs[3]); 
		voBean.setBzgg((String) objs[4]); 
		voBean.setDj((String) objs[5]); 
		return voBean;
	}

	/**
	 * 导出Excel
	 * @param response
	 * @param model
	 * @throws IOException 
	 */
	public void export(HttpServletResponse response, ZxkcKccxVo model) throws Exception {
		String[] head = new String[] {"货品名称", "货品数量", "单位", "包装规格"};
		List dataList = loadKclist(model);
		new Exporter(head, dataList) {
			@Override
			protected void addRow(WritableSheet sheet, Object row, int i) {
				try {
					ZxkcKccxVo bean = (ZxkcKccxVo) row;
					sheet.addCell(new Label(0, i + 1, bean.getHpmc()));
					sheet.addCell(new Label(1, i + 1, String.valueOf(bean.getHpsl())));
					sheet.addCell(new Label(2, i + 1, bean.getDwmc()));
					sheet.addCell(new Label(3, i + 1, bean.getBzgg()));
				} catch(Exception e) {
				}
			}
		}.export(response, "库存查询");;
		
	}

}
