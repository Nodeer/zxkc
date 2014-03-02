package com.mrding.zxkc.server;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import com.mrding.common.CommonUtils;
import com.mrding.common.dao.DSFactory;
import com.mrding.common.dao.DaoUtils;
import com.mrding.zxkc.dao.ZxkcHplrDao;
import com.mrding.zxkc.model.ZxkcYwHpxx;
import com.mrding.zxkc.vo.ZxkcHplrVo;

public class ZxkcHplrManager {
    
    ZxkcHplrDao dao = new ZxkcHplrDao();

    /**
     * 查询所有货品信息
     * @return
     */
    public List<ZxkcYwHpxx> listHpxx() {
	return dao.listHpxx();
    }

    /**
     * 添加货品
     * @param model
     * @throws SQLException 
     */
    public void addHp(ZxkcHplrVo model) throws SQLException {
	dao.save(model);
    }

    /**
     * 删除货品
     * @param ukey
     * @throws SQLException 
     */
    public void deleteHp(String ukey) throws SQLException {
	dao.deleteByPk(ukey);
    }

    /**
     * 根据货品名称模糊查询
     * @param hpmc
     * @return
     */
    public List<ZxkcYwHpxx> queryHpxxByMc(String hpmc) {
	if (CommonUtils.isNotBlank(hpmc)) {
            return dao.queryHpxxByMc(hpmc);
	} else {
	    return dao.listHpxx();
	}
    }

    /**
     * 判断改货品是否还存在库存
     * @param ukey
     * @return
     */
	public boolean hasKc(String ukey) {
		List<Object[]> list = dao.queryHasKc(ukey);
		try {
            return ((BigDecimal) list.get(0)[0]).doubleValue() > 0.0;
		} catch(Exception nullPointException) {
			return false;
		}
	}

	/**
	 * 查询所有单位
	 * @param hpbh 
	 * @return
	 */
	public List<Map<String, Object>> listUnit(Integer hpbh) {
		return dao.queryUnitList(hpbh);
	}

	/**
	 * 根据货品编号获取单位
	 * @param hpbh
	 * @return
	 */
	public String getDwByhpbh(int hpbh) {
		return dao.queryDwByHpbh(hpbh);
	}

	public List<Map<String, Object>> listRy() {
		return dao.queryRyList();
	}

}
