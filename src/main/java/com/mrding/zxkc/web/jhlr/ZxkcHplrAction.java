package com.mrding.zxkc.web.jhlr;

import java.util.*;

import javax.servlet.ServletContext;

import com.mrding.common.CommonUtils;
import com.mrding.common.web.ActionSupport;
import com.mrding.zxkc.server.ManagerFactory;
import com.mrding.zxkc.server.ZxkcHplrManager;
import com.mrding.zxkc.vo.ZxkcHplrVo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ZxkcHplrAction extends ActionSupport<ZxkcHplrVo, ZxkcHplrManager>{

    public String list() {
	return "hplr";
    }
    
    /**
     * 查询所有货品信息
     * @return
     */
    public String listHpxx() {
	if (CommonUtils.isNotBlank(model.getHpmc())) {
	    return queryHpxxByMc();
	}
	jsonMap.put("hpxxList", manager.listHpxx());
	return "success";
    }
    
    /**
     * 添加货品
     * @return
     */
    public String addHp() {
	try {
        manager.addHp(model);
        jsonMap.put("success", true);
	} catch(Exception e) {
	    e.printStackTrace();
	    jsonMap.put("success", false);
	}
        return "success";
    }
    
    /**
     * 删除货品
     * @return
     */
    public String deleteHp() {
	try {
		if (manager.hasKc(model.getUkey())) {
			jsonMap.put("success", false);
			jsonMap.put("hasKc", true);
		} else {
            manager.deleteHp(model.getUkey());
            jsonMap.put("success", true);
		}
	} catch(Exception e) {
	    e.printStackTrace();
	    jsonMap.put("success", false);
	}
	return "success";
    }
    
    public String queryHpxxByMc() {
		jsonMap.put("hpxxList", manager.queryHpxxByMc(model.getHpmc()));
		jsonMap.put("success", true);
		return SUCCESS;
    }
    
    /**
     * 查询所有单问
     * @return
     */
    public String loadUnit() {
    	jsonMap.put("unitList", manager.listUnit(model.getHpbh()));
    	return SUCCESS;
    }
    
    /**
     * 根据货品编号获取单位
     * @return
     */
    public String getDwByHpbh() {
    	jsonMap.put("dw", manager.getDwByhpbh(model.getHpbh()));
    	return SUCCESS;
    }
    
    public String loadRy() {
    	jsonMap.put("ryList", manager.listRy());
    	return SUCCESS;
    }

}
