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
	if (CommonUtils.strIsNotBlank(id)) {
	    if (id.equals("root")) {
		rtnList.add(TreeNode.createNotLeaf("入库录入", "rklr"));
		rtnList.add(TreeNode.createLeaf("出库录入", "cklr", "/cklr_list.shtml"));
		rtnList.add(TreeNode.createNotLeaf("查询统计", "cxtj"));
	    } else if (id.equals("rklr")) {
		rtnList.add(TreeNode.createLeaf("菜品录入", "cplr", "/jhlr/hplr_list.shtml"));
		rtnList.add(TreeNode.createLeaf("货品入库", "hprk", "/jhlr/hprk_list.shtml"));
	    } else if (id.equals("cxtj")) {
		rtnList.add(TreeNode.createLeaf("库存查询", "kccx", "/kccx_list.shtml"));
		rtnList.add(TreeNode.createLeaf("销售统计", "xstj", "/xstj_list.shtml"));
		rtnList.add(TreeNode.createLeaf("库存提醒", "kctx", "/kctx_list.shtml"));
	    }
	} 
        return rtnList;
    }

    @Override
    public IndexVo getModel() {
        return model;
    }

}
