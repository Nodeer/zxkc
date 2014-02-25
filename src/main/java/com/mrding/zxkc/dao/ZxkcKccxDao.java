package com.mrding.zxkc.dao;

import java.util.List;

import com.mrding.common.CommonUtils;
import com.mrding.common.dao.DSFactory;
import com.mrding.common.dao.DaoUtils;
import com.mrding.zxkc.vo.ZxkcKccxVo;

public class ZxkcKccxDao {

	/**
	 * 查询库存信息
	 * @param model
	 * @return
	 */
	public List<Object[]> queryKclist(ZxkcKccxVo model) {
	String sql = " select cc.HPBH,cc.HPMC,cc.BZGG,sum(ifnull(bb.sl,0)) as kc " +
                " from zxkc_yw_hpxx cc left join (" +
                " select aa.* from (" +
                " select HPBH,HPSL as sl,CK,RKSJ as sj from zxkc_yw_hprk where DR=0" +
                " union all " +
                " select HPBH,HPSL * (-1) as sl,CK,CKSJ as sj from zxkc_yw_hpck where DR=0" +
                " ) aa where 1=1" +
                (CommonUtils.strIsNotBlank(model.getCk()) ? DaoUtils.sqlEq("aa.CK", model.getCk()) : "") +
                (CommonUtils.strIsNotBlank(model.getJzrq_str()) ? DaoUtils.sqlLe("aa.sj", model.getJzrq_str()) : "") +
                " ) bb on cc.HPBH=bb.HPBH" +
                " where cc.DR=0" +
                (model.getHpbh() != null  ? DaoUtils.sqlEq("cc.HPBH", model.getHpbh()) : "") +
                " group by cc.HPBH,cc.HPMC,cc.BZGG" +
                " order by cc.HPBH";
		return DaoUtils.queryBySql(sql, DSFactory.CURRENT);
	}

}
