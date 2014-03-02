package com.mrding.zxkc.vo;

import java.math.BigDecimal;

import com.mrding.zxkc.model.ZxkcYwHpck;

public class ZxkcCklrVo extends ZxkcYwHpck{
	
	private String cksjq;
	private String cksjz;
	private String ckmc;
	private String hpmc;
	private String ckyymc;
	private BigDecimal hpsl_zxdw;
	private BigDecimal hpsl_dw;
	private String dwlx;
	private String dwdm;
	private String dw;

	public String getDwdm() {
		return dwdm;
	}
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getDwlx() {
		return dwlx;
	}
	public void setDwlx(String dwlx) {
		this.dwlx = dwlx;
	}
	public String getCkyymc() {
		return ckyymc;
	}
	public BigDecimal getHpsl_zxdw() {
		return hpsl_zxdw;
	}
	public void setHpsl_zxdw(BigDecimal hpsl_zxdw) {
		this.hpsl_zxdw = hpsl_zxdw;
	}
	public BigDecimal getHpsl_dw() {
		return hpsl_dw;
	}
	public void setHpsl_dw(BigDecimal hpsl_dw) {
		this.hpsl_dw = hpsl_dw;
	}
	public void setCkyymc(String ckyymc) {
		this.ckyymc = ckyymc;
	}
	public String getHpmc() {
		return hpmc;
	}
	public void setHpmc(String hpmc) {
		this.hpmc = hpmc;
	}
	public String getCksjq() {
		return cksjq;
	}
	public void setCksjq(String cksjq) {
		this.cksjq = cksjq;
	}
	public String getCksjz() {
		return cksjz;
	}
	public void setCksjz(String cksjz) {
		this.cksjz = cksjz;
	}
	public String getCkmc() {
		return ckmc;
	}
	public void setCkmc(String ckmc) {
		this.ckmc = ckmc;
	}

}
