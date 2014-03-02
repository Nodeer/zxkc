package com.mrding.zxkc.web;

import java.util.*;

import com.mrding.common.CommonUtils;
import com.mrding.common.model.TreeNode;
import com.mrding.zxkc.vo.IndexVo;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 首页展现的action
 * @author mrding
 *
 */
public class IndexAction implements ModelDriven<IndexVo> {
    	
    private IndexVo model = new IndexVo();
	
    private List<Object> jsonList = new ArrayList<Object>();
    private Map<String, Object> jsonMap = new HashMap<String, Object>();
    
    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    public List<Object> getJsonList() {
        return jsonList;
    }

    public String execute() {
        return "index";
    }

    public String head() {
        return "head";
    }

    public String menu() {
        
        return "menu";
    }

    public String home() {
        return "home";
    }

    public String loadMenu() {
	jsonList.addAll(loadNodesById(model.getNode()));
	return "success";
    }

    /**
     * 根据节点id，获取子节点
     * @param node
     * @return
     */
    private List<TreeNode> loadNodesById(String id) {
	List<TreeNode> rtnList = new ArrayList<TreeNode>();
	if (CommonUtils.isNotBlank(id)) {
	    if (id.equals("root")) {
		rtnList.add(TreeNode.createNotLeaf("入库", "rklr"));
		rtnList.add(TreeNode.createNotLeaf("出库", "ck"));
		//rtnList.add(TreeNode.createLeaf("出库", "cklr", "/ck/cklr_list.shtml"));
		rtnList.add(TreeNode.createNotLeaf("查询统计", "cxtj"));
	    } else if (id.equals("rklr")) {
		rtnList.add(TreeNode.createLeaf("货品信息录入", "cplr", "/jhlr/hplr_list.shtml"));
		rtnList.add(TreeNode.createLeaf("货品入库", "hprk", "/jhlr/hprk_list.shtml"));
		rtnList.add(TreeNode.createLeaf("货品入库维护", "rkwh", "/jhlr/hprk_funcRkwh.shtml"));
	    } else if (id.equals("ck")) {
		rtnList.add(TreeNode.createLeaf("货品出库", "cklr", "/ck/cklr_list.shtml"));
		rtnList.add(TreeNode.createLeaf("货品出库维护", "ckwh", "/ck/cklr_funcCkwh.shtml"));
	    } else if (id.equals("cxtj")) {
		rtnList.add(TreeNode.createLeaf("库存查询", "kccx", "/cxtj/kccx_list.shtml"));
		rtnList.add(TreeNode.createLeaf("营销统计", "xstj", "/cxtj/yxtj_list.shtml"));
	    }
	} 
        return rtnList;
    }

    @Override
    public IndexVo getModel() {
        return model;
    }

}
