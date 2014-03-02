package com.mrding.zxkc.web.jhlr;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.xml.crypto.URIDereferencer;

import com.mrding.common.web.ActionSupport;
import com.mrding.zxkc.model.ZxkcYwHprk;
import com.mrding.zxkc.server.ZxkcHprkManager;
import com.mrding.zxkc.vo.ZxkcYwHprkVo;

/**
 * 货品入库
 * @author mrding
 *
 */
public class ZxkcHprkAction extends ActionSupport<ZxkcYwHprkVo, ZxkcHprkManager> {
    
    public String list() {
        return "list";
    }
    
    /**
     * 加载货品编号下拉单
     * @return
     */
    public String loadHpbhSelect() {
        jsonMap.put("hpbhSelect", manager.listHpbhSelect());
        jsonMap.put("success", true);
        return SUCCESS;
    }
    
    public String saveHprkList() {
        try {
            String dataJson = URLDecoder.decode(request.getParameter("data"), "utf-8");
            manager.saveHprkByJsonStr(dataJson);
            jsonMap.put("success", true);
        } catch(Exception e) {
            e.printStackTrace();
            jsonMap.put("success", false);
        }
        return SUCCESS;
    }
    
    public String loadCkSelect() {
    	jsonMap.put("ckSelect", manager.listCkSelect());
    	return SUCCESS;
    }
    
    public String funcRkwh() {
    	return "rkwh";
    }
    
    /**
     * 查询货品入库记录
     * @return
     */
    public String loadHprkList() {
    	jsonMap.put("hprkList", manager.listHprk(model));
    	return SUCCESS;
    }
    
    /**
     * 修改货品入库记录
     * @return
     */
    public String modifyHprk() {
    	try {
    		manager.modifyHprk(model);
    		jsonMap.put("success", true);
    	} catch(Exception e) {
    		e.printStackTrace();
    		jsonMap.put("success", false);
    	}
    	return SUCCESS;
    }
    
    /**
     * 删除货品入库记录
     * @return
     */
    public String deleteHprk() {
    	try {
            manager.deleteHprk(model.getUkey());
            jsonMap.put("success", true);
    	} catch(Exception e) {
    		e.printStackTrace();
            jsonMap.put("success", false);
    	}
    	return SUCCESS;
    }
    

}
