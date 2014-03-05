<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<#include "/commons/ext.ftl"/>
<#include "/commons/commonJs.ftl"/>
<script type="text/javascript">
	
	Ext.onReady(function() {
		
		var queryForm = new Ext.form.FormPanel({
			renderTo:"queryForm", border:false, frame:false, width:1000, autoHeight:true, labelWidth:80, labelAlign:"right", buttonAlign:"center",
			items:[{
				layout:"column", xtype:"fieldset", title:"查询条件", checkboxToggle:true,
				items:[{
					columnWidth:.33, layout:"form", border:false, items:[
						hpbhSelect, ckSelect
					]
                }, {
                	columnWidth:.33, layout:"form", border:false, items:[
                		new Ext.form.DateField({fieldLabel:"入库时间起",anchor:"90%", format:"Y-m-d", name:"rksjq", id:"rksjq"}), 
                		new Ext.form.DateField({fieldLabel:"入库时间止",anchor:"90%", format:"Y-m-d", name:"rksjz", id:"rksjz"})
            		]
                }, {
                	columnWidth:.33, layout:"form", border:false, items:[
						new Ext.form.ComboBox({id:"ry", hiddenName:"ry", store:ryStore, anchor:"90%", triggerAction:"all", emptyText:"请选择", mode:"local", valueField:"rydm", displayField:"rymc", fieldLabel:"入库人"})
                	] 
                }]
            }], buttons:[{text:"查询", handler:fnQueryHprk}]
    	});
    	
    	function fnQueryHprk() {
            hprkList.getStore().load(
            	{params:{hpbh:hpbhSelect.getValue(), rksjq:Ext.get("rksjq").getValue(), rksjz:Ext.get("rksjz").getValue(), 
            	ck:ckSelect.getValue(), rkr:Ext.getCmp("ry").getValue()}}
            );
        }

		var hprkCm = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{header:"ukey", dataIndex:"ukey", hidden:true},
			{header:"hpbh", dataIndex:"hpbh", hidden:true},
			{header:"货品名称", dataIndex:"hpmc", width:150},
			{header:"货品数量", dataIndex:"hpsl"},
			{header:"dwdm", dataIndex:"dwdm", hidden:true},
			{header:"单位", dataIndex:"dwmc"},
			{header:"ck", dataIndex:"ck", hidden:true},
			{header:"店面", dataIndex:"ckmc"},
			{header:"rkrdm", dataIndex:"rkrdm", hidden:true},
			{header:"入库人", dataIndex:"rkr"},
			{header:"入库时间", dataIndex:"rksj",sortable:true, renderer:new Ext.util.Format.dateRenderer("Y-m-d")},
			{header:"备注", dataIndex:"bz"}
		]);
		
		var hprkStore = new Ext.data.JsonStore({
			url:"${ctxPath}/jhlr/hprk_loadHprkList.shtml",
			root:"hprkList",
			fields:["ukey", "hpbh", "hpmc", "hpsl", "dwdm", "dwmc", "ck", "ckmc", "rkrdm", "rkr", "rksj", "bz"]
		});
		
		function fnChangeHpbh(combo, record, index) {
	    	Ext.Ajax.request({
	    		url:"${ctxPath}/jhlr/hplr_getDwByHpbh.shtml",
	    		params:{hpbh: combo.getValue()},
	    		success:function(response) {
	    			var result = Ext.decode(response.responseText);
	    			Ext.getCmp("modifyDw").setValue(result.dw);
	    		}
	    	});
		}

        var modifyForm = new Ext.form.FormPanel({
        	url:"${ctxPath}/jhlr/hprk_modifyHprk.shtml", height:200, frame:true, border:false, items:[{
        		layout:"column", xtype:"fieldset", labelWidth:80, labelAlign:"right", border:false, buttonAlign:"center", items:[
                    {layout:"form", columnWidth:.33, items:[
                    	new Ext.form.Hidden({id:"modifyUkey", name:"ukey"}),
                    	new Ext.form.ComboBox({store:hpbhSelectStore, anchor:"90%", triggerAction:"all",id:"modifyHpbh", hiddenName:"hpbh", fieldLabel:"<font color='red'>*</font>货品名称", emptyText:"请选择", mode:"local", valueField:"hpbh", displayField:"hpmc",
                    		listeners:{select:fnChangeHpbh}
                    	}),
						new Ext.form.ComboBox({id:"modifyDw", hiddenName:"dw", readOnly:true, store:unitStore, anchor:"90%", triggerAction:"all", emptyText:"请选择", mode:"local", valueField:"dwdm", displayField:"dwmc", fieldLabel:"<font color='red'>*</font>单位"}),
                    	new Ext.form.NumberField({id:"modifyHpsl", name:"hpsl", anchor:"90%", fieldLabel:"<font color='red'>*</font>货品数量"})
                    ]}, {layout:"form", columnWidth:.33, items:[
                    	new Ext.form.ComboBox({id:"modifyCk", hiddenName:"ck", anchor:"90%", store:ckSelectStore, triggerAction:"all",mode:"local", valueField:"ck", displayField:"ckmc", emptyText:"请选择", fieldLabel:"<font color='red'>*</font>店面"}),
                    	new Ext.form.ComboBox({id:"modifyRkr", hiddenName:"rkr", store:ryStore, triggerAction:"all", model:"local", valueField:"rydm", displayField:"rymc", emptyText:"请选择", anchor:"90%", fieldLabel:"<font color='red'>*</font>入库人"}),
                    	new Ext.form.DateField({id:"modifyRksj", name:"rksj", anchor:"90%", fieldLabel:"<font color='red'>*</font>入库时间", format:"Y-m-d"})
                    ]}, {layout:"form", columnWidth:.33, items:[
                    	new Ext.form.TextField({id:"modifyBz", name:"bz", anchor:"90%", fieldLabel:"备注"})
                    ]}
                ], buttons:[{text:"保存", handler:function() {
                    if (!checkModifyFormBlank()) {
                            return ;
                    }
                    Ext.Msg.confirm("系统提示", "确认保存？", function(opt) {
                        if (opt == "yes") {
							modifyForm.getForm().submit({
								success:function() {
									resetModifyForm();
									modifyFormWindow.hide();
									fnQueryHprk();
									Ext.Msg.alert("系统提示", "保存成功！");
								},
								failure:function() {
									Ext.Msg.alert("系统提示", "保存失败，请联系系统管理员！");
								}
							});
                        }
                    });
                }
                }, {text:"取消", handler:function() {
                	resetModifyForm();
                	modifyFormWindow.hide();
                }}]  
        	}]
        });
        
		
		function checkModifyFormBlank() {
            if (Ext.getCmp("modifyHpbh").getValue() == null || Ext.getCmp("modifyHpbh").getValue() == undefined || Ext.getCmp("modifyHpbh").getValue() == "") {
            	Ext.Msg.alert("系统提示", "货品名称不能为空！");
            	return ;
            }
            if (fnIsBlank(Ext.getCmp("modifyDw").getValue())) {
            	Ext.Msg.alert("系统提示", "单位不能为空！");
            	return ;
            }
            if (fnIsBlank(Ext.getCmp("modifyHpsl").getValue().toString())) {
            	Ext.Msg.alert("系统提示", "货品数量不能为空！");
            	return ;
            }
            if (fnIsBlank(Ext.getCmp("modifyCk").getValue())) {
            	Ext.Msg.alert("系统提示", "店面不能为空！");
            	return ;
            }
            if (fnIsBlank(Ext.getCmp("modifyRkr").getValue())) {
            	Ext.Msg.alert("系统提示", "入库人不能为空！");
            	return ;
            }
            if (fnIsBlank(Ext.getCmp("modifyRksj").getValue())) {
            	Ext.Msg.alert("系统提示", "入库时间不能为空！");
            	return ;
            }
            return true;
		}
		
        var modifyFormWindow = new Ext.Window({
        	title:"货品入库修改", width:900, height:200, resizable:false, modal:true, layout:"form", closeAction:"hide", items:[modifyForm]
        });

		var hprkList = new Ext.grid.GridPanel({
            title:"货品入库记录",renderTo:"hprkList", cm:hprkCm, ds:hprkStore, frame:true, height:300, width:1000, loadMask:true,
            sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
            viewConfig:{forceFit:true},
            tbar:["-", {text:"修改", handler:function() {
            	var hprkBean = hprkList.getSelectionModel().getSelected();
            	if (hprkBean == null) {
            		Ext.Msg.alert("系统提示", "请先选择要修改的记录！");
            		return ;
            	}
            	setModifyForm(hprkBean);
            	modifyFormWindow.show();
            }}, "-", {text:"删除", handler:function() {
            	var hprkBean = hprkList.getSelectionModel().getSelected();
            	if (hprkBean == null) {
            		Ext.Msg.alert("系统提示", "请先选择要删除的记录！");
            		return ;
            	}
            	Ext.Msg.confirm("系统提示", "确认删除？", function(opt) {
            		if (opt == "yes") {
                        Ext.Ajax.request({
                            url:"${ctxPath}/jhlr/hprk_deleteHprk.shtml", params:{ukey:hprkBean.get("ukey")}, 
                            success:function(response) {
                                var rtnObj = Ext.decode(response.responseText);
                                if (rtnObj.success) {
                                        Ext.Msg.alert("系统提示", "删除成功！");
                                        fnQueryHprk();
                                } else {
                                        Ext.Msg.alert("系统提示", "删除失败，请与系统管理员联系");
                                }
                            }
                        });
            		}
            	});
            }}, "-"]
        });
        
        function setModifyForm(hprkBean) {
            Ext.getCmp("modifyUkey").setValue(hprkBean.get("ukey"));
            Ext.getCmp("modifyHpbh").setValue(hprkBean.get("hpbh"));
            Ext.getCmp("modifyDw").setValue(hprkBean.get("dwdm"));
            Ext.getCmp("modifyHpsl").setValue(hprkBean.get("hpsl"));
            Ext.getCmp("modifyCk").setValue(hprkBean.get("ck"));
            Ext.getCmp("modifyRkr").setValue(hprkBean.get("rkrdm"));
            Ext.getCmp("modifyRksj").setValue(hprkBean.get("rksj").substring(0, 10));
            Ext.getCmp("modifyBz").setValue(hprkBean.get("bz"));
        }

        function resetModifyForm() {
            Ext.getCmp("modifyUkey").setValue("");
            Ext.getCmp("modifyHpbh").setValue("");
            Ext.getCmp("modifyDw").setValue("");
            Ext.getCmp("modifyHpsl").setValue("");
            Ext.getCmp("modifyCk").setValue("");
            Ext.getCmp("modifyRkr").setValue("");
            Ext.getCmp("modifyRksj").setValue("");
            Ext.getCmp("modifyBz").setValue("");
        }
        
	});

</script>
</head>
<body>
<div style="width:1000px;text-align:center"><font size="2">货品入库维护</font></div>
<div id="queryForm"></div>
<div id="hprkList"></div>
</body>
</html>
