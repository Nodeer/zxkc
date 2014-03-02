package com.mrding.zxkc.server;

import java.math.BigDecimal;
import java.util.*;

import com.mrding.common.CommonUtils;
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

}
