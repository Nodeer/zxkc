package com.mrding.zxkc.server;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;

import com.mrding.common.CommonUtils;
import com.mrding.common.Exporter;
import com.mrding.zxkc.dao.ZxkcYxtjDao;
import com.mrding.zxkc.vo.ZxkcYxtjVo;

public class ZxkcYxtjManger {
	
	private ZxkcYxtjDao dao = new ZxkcYxtjDao();

	/**
	 * 查询所有店面
	 * @return
	 */
	public List<Object[]> listDm() {
		return dao.queryDmList();
	}

	/**
	 * 获取营销统计数据
	 * @param model
	 * @return
	 */
	public List<Map<String, Object>> getYxtjList(ZxkcYxtjVo model) {
		model.setDmList(listDm());
		List<Object[]> list = dao.queryYxtjList(model);
		return convertToMapList(list, model.getDmList());
	}

	private List<Map<String, Object>> convertToMapList(List<Object[]> list, List<Object[]> dmList) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (CommonUtils.listIsNotBlank(list)) {
			for (Object[] objs : list) {
				mapList.add(convertToMap(objs, dmList));
			}
		}
		return mapList;
	}

	private Map<String, Object> convertToMap(Object[] objs, List<Object[]> dmList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hpbh", objs[0]);
		map.put("hpmc", objs[1]);
		map.put("bzgg", objs[2]);
		map.put("dj", objs[3]);
		map.put("dwmc", objs[4]);
		if (CommonUtils.listIsNotBlank(dmList)) {
			for (int i = 0, max = dmList.size(); i < max; i++) {
				map.put((String) dmList.get(i)[0], objs[i + 5]);
			}
		}
		return map;
	}

	/**
	 * 导出数据
	 * @param response 
	 * @param zxkcYxtjVo
	 * @throws Exception 
	 */
	public void export(HttpServletResponse response, ZxkcYxtjVo model) throws Exception {
		List<Object[]> dmList = listDm();
		String[] head = createExportHead(dmList);
		new Exporter(head, getYxtjList(model), dmList) {
			@Override
			protected void addRow(WritableSheet sheet, Object row, int i) {
				List<Object[]> dmList_param = (List<Object[]>) param;
				Map<String, Object> map = (Map<String, Object>) row;
				try {
					sheet.addCell(new Label(0, i + 1, (String) map.get("hpmc")));
					sheet.addCell(new Label(1, i + 1, (String) map.get("bzgg")));
					sheet.addCell(new Label(2, i + 1, (String) map.get("dj")));
					sheet.addCell(new Label(3, i + 1, (String) map.get("dwmc")));
					for (int j = 0, max = dmList_param.size(); j < max; j++) {
						sheet.addCell(new Label(j + 4, i + 1, ((BigDecimal) map.get((String) dmList_param.get(i)[0])).toString()));
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}.export(response, "营销统计");;

	}

	/**
	 * 创建excel头
	 * @param dmList
	 * @return
	 */
	private String[] createExportHead(List<Object[]> dmList) {
		List<String> head = new ArrayList<String>();
		head.add("货品名称");
		head.add("包装规格");
		head.add("单价");
		head.add("单位");
		for (int i = 0, max = dmList.size(); i < max; i++) {
			head.add((String) dmList.get(i)[1]);
		}
		return head.toArray(new String[0]);
	}

}
