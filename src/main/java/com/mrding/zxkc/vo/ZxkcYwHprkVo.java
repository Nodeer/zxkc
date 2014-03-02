package com.mrding.zxkc.vo;

import java.math.BigDecimal;

import com.mrding.zxkc.model.ZxkcYwHprk;

public class ZxkcYwHprkVo extends ZxkcYwHprk {
	
	private String rksjq;
	private String rksjz;
	private String ckmc;
	private BigDecimal hpsl;
	private String dwlx;
	private String dwdm;
	private String dwmc;
	private String rkrdm;

	public String getDwdm() {
		return dwdm;
	}
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getRkrdm() {
		return rkrdm;
	}
	public void setRkrdm(String rkrdm) {
		this.rkrdm = rkrdm;
	}
	public String getDwlx() {
		return dwlx;
	}
	public void setDwlx(String dwlx) {
		this.dwlx = dwlx;
	}
	public String getCkmc() {
		return ckmc;
	}
	public BigDecimal getHpsl() {
		return hpsl;
	}
	public void setHpsl(BigDecimal hpsl) {
		this.hpsl = hpsl;
	}
	public void setCkmc(String ckmc) {
		this.ckmc = ckmc;
	}
	public String getRksjq() {
		return rksjq;
	}
	public void setRksjq(String rksjq) {
		this.rksjq = rksjq;
	}
	public String getRksjz() {
		return rksjz;
	}
	public void setRksjz(String rksjz) {
		this.rksjz = rksjz;
	}

}
