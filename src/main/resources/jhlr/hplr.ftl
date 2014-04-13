<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<#include "/commons/ext.ftl"/>
<#include "/commons/commonJs.ftl"/>
<script type="text/javascript">
	Ext.onReady(function() {
	var addForm = new Ext.form.FormPanel({
		url: '${ctxPath}/jhlr/hplr_addHp.shtml',
		labelAlign: 'right', labelWidth: 200, height: 200, frame: true, layout: 'form', buttonAlign: 'center', items: [{
			layout: 'column', border: false, style: 'padding-top:5px', items: [
				{
					columnWidth: 0.3, labelWidth: 80, labelAlign: 'right', layout: 'form', border: false, items: [
						{ xtype: 'textfield', name: 'hpmc', id: 'hpmc', fieldLabel: "<font color='red'>*</font>货品名称" }, 
						{ xtype: 'textfield', name: 'bzgg', id: 'bzgg', fieldLabel: "<font color='red'>*</font>包装规格" }
					]
				}, {	
					columnWidth: 0.3, labelWidth: 80, labelAlign: 'right', layout: 'form', border: false, items: [
						unitComboBox,
						{xtype:"numberfield", id:"zxkc", name:"zxkc", fieldLabel:"<font color='red'>*</font>最小库存", anchor:"90%"}
					]
				},{	
					columnWidth: 0.3, labelWidth: 80, labelAlign: 'right', layout: 'form', border: false, items: [
						{ xtype: 'numberfield', id: 'dj', name: 'dj', fieldLabel: "<font color='red'>*</font>单价" }
					]
				}
			],
			buttonAlign: 'center', buttons: [
                { text: '确定',
                	handler: function() {
                		if (!fnCheckBlank()) {
                			return ;
                		}
                		Ext.Msg.confirm("系统提示", "确认保存？", function(opt) {
                            if (opt == "yes") {
                                addForm.getForm().submit({
                                    success: function() {
                                        addWindow.hide();   
                                        hpxxGrid.getStore().load();
                                        fnInitAddWindow();
                                        Ext.Msg.alert("系统提示", "保存成功！");
                                    },
                                    failure: function() {
                                        Ext.Msg.alert("系统提示", "保存失败，请联系系统管理员！");
                                    }
                                });
                            }
                		});
                	}
                },
                {
                	text: '取消',
                	handler: function() {
                		fnInitAddWindow();
                		addWindow.hide();
                	}
                }
			]
		}, new Ext.form.Hidden({id:'ukey', name:'ukey'})]
	});
	
	function fnInitAddWindow() {
        Ext.getCmp("hpmc").setValue("");
        Ext.getCmp("dw").setValue("");
        Ext.getCmp("zxkc").setValue("");
        Ext.getCmp("bzgg").setValue("");
        Ext.getCmp("ukey").setValue("");
        Ext.getCmp("dj").setValue("");
	}
	
	function fnCheckBlank() {
		if (fnIsBlank(Ext.getCmp("hpmc").getValue())) {
			Ext.Msg.alert("系统提示", "货品名称不能为空！");
			return false;
		}
		if (fnIsBlank(Ext.getCmp("bzgg").getValue())) {
			Ext.Msg.alert("系统提示", "包装规格不能为空！");
			return false;
		}
		if (fnIsBlank(Ext.getCmp("dw").getValue())) {
			Ext.Msg.alert("系统提示", "单位不能为空！");
			return false;
		}
		if (fnIsBlank(Ext.getCmp("zxkc").getValue())) {
			Ext.Msg.alert("系统提示", "最小库存不能为空！");
			return false;
		}
		if (fnIsBlank(Ext.getCmp("dj").getValue())) {
			Ext.Msg.alert("系统提示", "单价不能为空！");
			return false;
		}
		return true;
	}

    var addWindow = new Ext.Window({
        width: 950,
        height: 170,
        modal: true,
        closeAction: 'hide',
        layout: 'form',
        items: [addForm]
    });
	
		var gridStore = new Ext.data.JsonStore({
			url: '${ctxPath}/jhlr/hplr_listHpxx.shtml',
			root: 'hpxxList',
			fields: ['ukey', 'hpbh', 'hpmc', 'bzgg', 'dw', 'dwmc', 'zxkc', 'dj', 'ts', ]
		});
		gridStore.load();
		
		var hpxxGrid = new Ext.grid.GridPanel({
			title:"货品信息维护",
			renderTo: 'hpxxList',
			ds: gridStore,
			loadMask: true,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			columns: [
				new Ext.grid.RowNumberer(),
				{dataIndex: 'ukey', hidden: true},
				{header: '货品编号', dataIndex: 'hpbh', hidden:true, width: 100},
				{header: '货品名称', dataIndex: 'hpmc', width: 220},
				{header: '包装规格', dataIndex: 'bzgg', width: 200},
				{header: 'dw', dataIndex: 'dw', hidden:true},
				{header: '单位', dataIndex: 'dwmc', width: 80},
				{header: '最小库存', dataIndex: 'zxkc', width: 90},
				{header: '单价', dataIndex: 'dj', width: 80},
				{header: '录入时间', dataIndex: 'ts', width: 300, renderer: new Ext.util.Format.dateRenderer('Y-m-d H:m:s'), sortable: true},
			],
			viewConfig: {
				forceFit: true
			},
			width: 1000,
			height: 500,
			frame: true,
			tbar: new Ext.Toolbar([
				 '-',
				{
					text: '新增',
					handler: function() {
						addWindow.setTitle("新增货品");
						addWindow.show();
					}
				},
				'-',
				{
					text: '修改',
					handler: function() {
						var hpxxBean = hpxxGrid.getSelectionModel().getSelected();
						if (hpxxBean == null) {
							Ext.Msg.alert("系统提示", "请先选择一条记录！");
							return ;
						}
						Ext.getCmp("hpmc").setValue(hpxxBean.get("hpmc"));
						Ext.getCmp("dw").setValue(hpxxBean.get("dw"));
						Ext.getCmp("zxkc").setValue(hpxxBean.get("zxkc"));
						Ext.getCmp("bzgg").setValue(hpxxBean.get("bzgg"));
						Ext.getCmp("ukey").setValue(hpxxBean.get("ukey"));
						Ext.getCmp("dj").setValue(hpxxBean.get("dj"));
						addWindow.setTitle("修改货品");
						addWindow.show();
					}
				},
				'-',
				{
					text: '删除',
					handler: function() {
                        var hpxxBean = hpxxGrid.getSelectionModel().getSelected();
						if (hpxxBean == null) {
							Ext.Msg.alert("系统提示", "请先选择一条记录！");
							return ;
						}
						Ext.Msg.confirm("系统提示", "确定删除选择的记录？", function(opt) {
							if (opt == "yes") {
								Ext.Ajax.request({
									url: '${ctxPath}/jhlr/hplr_deleteHp.shtml',
									params: {ukey: hpxxBean.get("ukey")},
									success: function(response) {
										var result = Ext.decode(response.responseText); 
										if (result.success) {
											Ext.Msg.alert("系统提示", "删除成功！");
											hpxxGrid.getStore().load();
										} else {
											if (result.hasKc) {
												Ext.Msg.alert("系统提示", "该货品还存在库存，不能删除！");
											} else {
                                                Ext.Msg.alert("系统提示", "删除失败！");
											}
										}
									}
								});		
							}
						})
					}
				},
				'-', '->',
				"货品名称：", 
				{
					xtype:"textfield",
					id: "hpmc_query",
					name:"hpmc_query"
				},
				 '-', {
				 	text: '查询',
				 	handler: function() {
				 		var hpmc = Ext.get("hpmc_query").getValue();
				 		Ext.Ajax.request({
				 			url: '${ctxPath}/jhlr/hplr_queryHpxxByMc.shtml',
				 			params: {hpmc: hpmc},
				 			success: function(response) {
				 				hpxxGrid.getStore().load({params:{hpmc:hpmc}});
				 			}
				 		});
				 	}
				 }, '-',
			])
		});
	});
</script>
</head>
<body>
<div id="hpxxList"></div>
</body>
</html>
