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
		String sql = "select aa.HPBH,bb.HPMC,sum(aa.sl),dd.DWMC,bb.BZGG,bb.DJ from (" +
				" select HPBH,HPSL as sl,RKSJ as sj,CK from zxkc_yw_hprk where DR=0" +
				" union all" +
				" select HPBH,HPSL * (-1) as sl,CKSJ as sj,CK from zxkc_yw_hpck where DR=0" +
				" ) aa " +
				" left join zxkc_yw_hpxx bb on aa.HPBH=bb.HPBH and bb.DR=0" +
				" left join zxkc_dm_ck cc on aa.CK=cc.CKDM and cc.DR=0" +
				" left join zxkc_dm_dw dd on bb.DW=dd.DWDM and dd.DR=0" +
				" where 1=1" +
                (CommonUtils.isNotBlank(model.getHpbh())  ? DaoUtils.sqlEq("aa.HPBH", model.getHpbh()) : "") +
                (CommonUtils.isNotBlank(model.getCk()) ? DaoUtils.sqlEq("aa.CK", model.getCk()) : "") +
                (CommonUtils.isNotBlank(model.getJzrq_str()) ? DaoUtils.sqlLe("aa.sj", model.getJzrq_str()) : "") +
				" group by  aa.HPBH,bb.HPMC,dd.DWMC,bb.BZGG,bb.DJ" +
                //" having sum(aa.sl) <> 0" +
				" order by aa.HPBH";
		return DaoUtils.queryBySql(sql, DSFactory.CURRENT);
	}

}
