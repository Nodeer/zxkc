package com.mrding.zxkc.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mrding.common.CommonUtils;
import com.mrding.common.dao.DSFactory;
import com.mrding.common.dao.DaoUtils;
import com.mrding.zxkc.model.ZxkcYwHpxx;
import com.mrding.zxkc.vo.ZxkcHplrVo;

public class ZxkcHplrDao {

    public List<ZxkcYwHpxx> listHpxx() {
        List<Object[]> list = DaoUtils.queryBySql("select a.*,b.DWMC from zxkc_yw_hpxx a left join zxkc_dm_dw b on a.DW=b.DWDM and b.DR=0 where a.DR=0", DSFactory.CURRENT);
        return convertList(list);			
    }

    private List<ZxkcYwHpxx> convertList(List<Object[]> list) {
	List<ZxkcYwHpxx> beanList = new ArrayList<ZxkcYwHpxx>();
	if (CommonUtils.listIsNotBlank(list)) {
	    for (Object[] objs : list) {
		beanList.add(convert(objs));
	    }
	}
	return beanList;
    }

    private ZxkcYwHpxx convert(Object[] objs) {
		ZxkcYwHpxx bean = new ZxkcYwHpxx();
		bean.setUkey((String) objs[0]);
		bean.setHpbh((Integer) objs[1]);
		bean.setHpmc((String) objs[2]);
		bean.setBzgg((String) objs[3]);
		bean.setDw((String) objs[4]);
		bean.setZxdw((String) objs[5]);
		bean.setDr((Integer) objs[6]);
		bean.setTs((Timestamp) objs[7]);
		bean.setLrr((String) objs[8]);
		bean.setXgr((String) objs[9]);
		bean.setXgsj((Timestamp) objs[10]);
		bean.setDwzhl((BigDecimal) objs[11]);
		bean.setDj((String) objs[12]);
		try{
			bean.setDwmc((String) objs[13]);
		} catch(Exception e) {
		}
		return bean;
    }

    public void save(ZxkcYwHpxx model) throws SQLException {
	if (CommonUtils.isNotBlank(model.getUkey())) {
	    update(model);
	} else {
            String sql = "insert into zxkc_yw_hpxx values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection conn = DSFactory.CURRENT.getConn();
            PreparedStatement pstmt = null;
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, UUID.randomUUID().toString().replace("-", ""));
                pstmt.setInt(2, createHpbh());
                pstmt.setString(3, model.getHpmc());
                pstmt.setString(4, model.getBzgg());
                pstmt.setString(5, model.getDw());
                pstmt.setString(6, model.getZxdw());
                pstmt.setInt(7, 0);
                pstmt.setTimestamp(8, new Timestamp(new java.util.Date().getTime()));
                pstmt.setString(9, "");
                pstmt.setString(10, "");
                pstmt.setTimestamp(11, new Timestamp(new java.util.Date().getTime()));
                pstmt.setBigDecimal(12, model.getDwzhl());
                pstmt.setString(13, model.getDj());
                pstmt.executeUpdate();
            } finally {
                DaoUtils.close(conn, pstmt, null);
            }
	}
    }

    /**
     * 更新货品信息
     * @param model
     * @throws SQLException 
     */
    private void update(ZxkcYwHpxx model) throws SQLException {
        String sql = "update zxkc_yw_hpxx set HPMC=?,BZGG=?,DW=?,ZXDW=?,XGSJ=?,DWZHL=?,DJ=? where DR=0 and UKEY='" + model.getUkey() + "'";
        Connection conn = DSFactory.CURRENT.getConn();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, model.getHpmc());
            pstmt.setString(2, model.getBzgg());
            pstmt.setString(3, model.getDw());
            pstmt.setString(4, model.getZxdw());
            pstmt.setTimestamp(5, new Timestamp(new java.util.Date().getTime()));
            pstmt.setBigDecimal(6, model.getDwzhl());
            pstmt.setString(7, model.getDj());
            pstmt.executeUpdate();
        } finally {
            DaoUtils.close(conn, pstmt, null);
        }
	
    }

    /**
     * 创建一个新的货品编号
     * @return
     */
    private int createHpbh() {
	List<Object[]> list = DaoUtils.queryBySql("select max( HPBH) from zxkc_yw_hpxx", DSFactory.CURRENT);
	if (CommonUtils.listIsNotBlank(list) && list.get(0)[0] != null) {
	    return ((Integer) list.get(0)[0]) + 1;
	} else {
	    return 1;
	}
    }

    /**
     * 根据主键删除货品
     * @param ukey
     * @throws SQLException 
     */
    public void deleteByPk(String ukey) throws SQLException {
		String sql = "update zxkc_yw_hpxx set DR=1 where UKEY='" + ukey + "'";
		DaoUtils.updateBySql(sql, DSFactory.CURRENT);
    }

    /**
     * 根据货品名称模糊查询
     * @param hpmc
     * @return
     */
    public List<ZxkcYwHpxx> queryHpxxByMc(String hpmc) {
		List<Object[]> list = DaoUtils.queryBySql("select a.*,b.DWMC from zxkc_yw_hpxx a left join zxkc_dm_dw b on a.DW=b.DWDM and b.DR=0 where a.DR=0 and a.HPMC like '%" + hpmc + "%'", DSFactory.CURRENT);
		return convertList(list);
    }

    /**
     * 根据字段值来查询
     * @param string
     * @param object
     * @return
     */
    public List<ZxkcYwHpxx> queryByCol(String colName, Object value) {
		String sql = "select * from zxkc_yw_hpxx where DR=0 and " + colName + " = '" + value + "'";
		return convertList(DaoUtils.queryBySql(sql, DSFactory.CURRENT));
    }

    /**
     * 根据货品编号获取单位转换率
     * @param hpbh
     * @return
     */
	public BigDecimal getDwzhl(Integer hpbh) {
		String sql = "select DWZHL from zxkc_yw_hpxx where DR=0 and HPBH=" + hpbh;
		List<Object[]> list = DaoUtils.queryBySql(sql, DSFactory.CURRENT);
		if (CommonUtils.listIsNotBlank(list) && list.get(0) != null) {
			return (BigDecimal) list.get(0)[0];
		} else {
			return null;
		}
	}

	/**
	 * 查询是否该货品还存在库存
	 * @param ukey
	 * @return
	 */
	public List<Object[]> queryHasKc(String ukey) {
		return DaoUtils.queryBySql("select sum(aa.sl) from (" +
            " select HPSL as sl from zxkc_yw_hpxx a left join zxkc_yw_hprk b on a.HPBH=b.HPBH where a.DR=0 and b.DR=0 and a.UKEY='" + ukey + "'" +
            " union all" +
            " select HPSL * (-1) as sl from zxkc_yw_hpxx a left join zxkc_yw_hpck b on a.HPBH=b.HPBH where a.DR=0 and b.DR=0 and a.UKEY='" + ukey + "'" +
            " ) aa", DSFactory.CURRENT);
	}

	public List<Map<String, Object>> queryUnitList(Integer hpbh) {
		String sql = "";
		if (CommonUtils.isNotBlank(hpbh)) {
			sql = "select a.DWDM,a.DWMC from zxkc_dm_dw a,zxkc_yw_hpxx b where a.DWDM=b.DW and a.DR=0 and b.DR=0 and b.HPBH=" + hpbh;
		} else {
			sql = "SELECT DWDM,DWMC FROM zxkc_dm_dw where DR=0 order by DWMC";
		}
		return CommonUtils.convertSelectData(DaoUtils.queryBySql(sql, DSFactory.CURRENT), "dwdm", "dwmc");
	}

	/**
	 * 根据货品编号获取单位
	 * @param hpbh
	 * @return
	 */
	public String queryDwByHpbh(int hpbh) {
		List<Object[]> list = DaoUtils.queryBySql("select DW from zxkc_yw_hpxx where DR=0 and HPBH=" + hpbh, DSFactory.CURRENT);
		if (CommonUtils.listIsNotBlank(list)) {
			return (String) list.get(0)[0];
		} else {
			return null;
		}
	}

	public List<Map<String, Object>> queryRyList() {
		return CommonUtils.convertSelectData(DaoUtils.queryBySql("select RYDM,RYMC from zxkc_dm_ry where DR=0 order by RYDM", DSFactory.CURRENT),
				"rydm", "rymc");
	}

}
