package com.mrding.zxkc.server;

import java.math.BigDecimal;
import java.util.*;

import com.mrding.common.CommonUtils;
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

}
