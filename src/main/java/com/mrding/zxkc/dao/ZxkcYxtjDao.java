package com.mrding.zxkc.dao;

import java.util.List;

import com.mrding.common.CommonUtils;
import com.mrding.common.dao.DSFactory;
import com.mrding.common.dao.DaoUtils;
import com.mrding.zxkc.vo.ZxkcYxtjVo;

public class ZxkcYxtjDao {

	/**
	 * 查询店面
	 * @return
	 */
	public List<Object[]> queryDmList() {
		return DaoUtils.queryBySql("select CKDM,CKMC from zxkc_dm_ck where DR=0 order by CKDM", DSFactory.CURRENT);
	}

	/**
	 * 查询营销统计数据
	 * @param model
	 * @return
	 */
	public List<Object[]> queryYxtjList(ZxkcYxtjVo model) {
		return DaoUtils.queryBySql("select b.HPBH,b.HPMC,b.BZGG,b.DJ,c.DWMC " +
				createYxtjSqlHead(model.getDmList(), model.getDwlx()) +
				" from zxkc_yw_hpck a" +
				" left join zxkc_yw_hpxx b on a.HPBH=b.HPBH and b.DR=0" +
				" left join zxkc_dm_dw c on b.DW=c.DWDM and c.DR=0" +
				" where a.DR=0" +
                (model.getHpbh() != null && !String.valueOf(model.getHpbh()).equals("") ? DaoUtils.sqlEq("a.HPBH", model.getHpbh()) : "") +
                (CommonUtils.isNotBlank(model.getQsrq_str()) ? DaoUtils.sqlGe("a.CKSJ", model.getQsrq_str()) : "") +
                (CommonUtils.isNotBlank(model.getJzrq_str()) ? DaoUtils.sqlLe("a.CKSJ", model.getJzrq_str()) : "") +
				" group by b.HPBH,b.HPMC,b.BZGG,b.DJ,c.DWMC" +
				" order by b.HPBH", 
				DSFactory.CURRENT);
	}

	private String createYxtjSqlHead(List<Object[]> dmList, String dwlx) {
		StringBuilder head = new StringBuilder("");
		if (CommonUtils.listIsNotBlank(dmList)) {
			for (Object[] objs : dmList) {
				head.append(", sum(case when a.CK='" + (String) objs[0] + "' then " + fitHpsl(dwlx) + " else 0 end) as '" + (String) objs[1] + "'");
			}
		}
		return head.toString();
	}

	private String fitHpsl(String dwlx) {
		if (dwlx != null && dwlx.equals("dw")) {
			return " round(a.HPSL / b.DWZHL,2)";
		} else {
			return " a.HPSL";
		}
	}

}
