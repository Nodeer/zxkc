package com.mrding.zxkc.model;

import java.math.BigDecimal;
import java.sql.*;

/**
 * 货品信息
 * @author mrding
 *
 */
public class ZxkcYwHpxx {
    
    private String ukey;
    private Integer hpbh;
    private String hpmc;
    private String bzgg;
    private String dw;
    private String zxdw;
    private Integer dr;
    private Timestamp ts;
    private String lrr;
    private String xgr;
    private Timestamp xgsj;
    private BigDecimal dwzhl;
    private String dj;
    private String dwmc;
    private BigDecimal zxkc;

    public BigDecimal getZxkc() {
		return zxkc;
	}
	public void setZxkc(BigDecimal zxkc) {
		this.zxkc = zxkc;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public BigDecimal getDwzhl() {
		return dwzhl;
	}
	public void setDwzhl(BigDecimal dwzhl) {
		this.dwzhl = dwzhl;
	}
	public Timestamp getXgsj() {
        return xgsj;
    }
    public String getDj() {
		return dj;
	}
	public void setDj(String dj) {
		this.dj = dj;
	}
	public void setXgsj(Timestamp xgsj) {
        this.xgsj = xgsj;
    }
    public String getUkey() {
        return ukey;
    }
    public void setUkey(String ukey) {
        this.ukey = ukey;
    }
    public String getHpmc() {
        return hpmc;
    }
    public Integer getHpbh() {
        return hpbh;
    }
    public void setHpbh(Integer hpbh) {
        this.hpbh = hpbh;
    }
    public void setHpmc(String hpmc) {
        this.hpmc = hpmc;
    }
    public String getBzgg() {
        return bzgg;
    }
    public void setBzgg(String bzgg) {
        this.bzgg = bzgg;
    }
    public String getDw() {
        return dw;
    }
    public void setDw(String dw) {
        this.dw = dw;
    }
    public String getZxdw() {
        return zxdw;
    }
    public void setZxdw(String zxdw) {
        this.zxdw = zxdw;
    }
    public Integer getDr() {
        return dr;
    }
    public void setDr(Integer dr) {
        this.dr = dr;
    }
    public Timestamp getTs() {
        return ts;
    }
    public void setTs(Timestamp ts) {
        this.ts = ts;
    }
    public String getLrr() {
        return lrr;
    }
    public void setLrr(String lrr) {
        this.lrr = lrr;
    }
    public String getXgr() {
        return xgr;
    }
    public void setXgr(String xgr) {
        this.xgr = xgr;
    }

}
