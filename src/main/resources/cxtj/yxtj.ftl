<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<#include "/commons/ext.ftl"/>
<#include "/commons/commonJs.ftl"/>
<script type="text/javascript">
	
	Ext.onReady(function() {
	
		var queryForm = new Ext.form.FormPanel({
			renderTo:"queryForm", width:1000, autoHeight:true, frame:false, border:false, labelWidth:80, labelAlign:"right", buttonAlign:"center", items:[
				{layout:"column", anchor:"100%,100%", title:"查询条件", xtype:"fieldset", checkboxToggle:true, items:[
					{layout:"form", border:false, columnWidth:.33, items:[
						new Ext.form.ComboBox({store:hpbhSelectStore, triggerAction:"all", name:"hpbh", id:"hpbh", fieldLabel:"货品名称", emptyText:"请选择", 
                            mode:"local", valueField:"hpbh", displayField:"hpmc"})
    				]},
					{layout:"form", border:false, columnWidth:.33, items:[new Ext.form.DateField({id:"qsrq", name:"qsrq_str", fieldLabel:"起始日期", width:200, format:"Y-m-d"})]},
					{layout:"form", border:false, columnWidth:.33, items:[new Ext.form.DateField({id:"jzrq", name:"jzrq_str", fieldLabel:"截止日期", width:200, format:"Y-m-d"})]}
				]} 
			], buttons:[{text:"查询", handler:function() {
				fnQueryKc();
			}}]
		});
		
		//查询营销信息
		function fnQueryKc() {
			kclistGrid.getStore().load({params:{hpbh:Ext.getCmp("hpbh").getValue(), qsrq_str:Ext.getCmp("qsrq").getValue(), jzrq_str:Ext.getCmp("jzrq").getValue()}});
		}
		
		var yxtjGridCm = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{header:"货品名称", dataIndex:"hpmc", width:150},
			{header:"包装规格", dataIndex:"bzgg", width:150},
			{header:"总价格", dataIndex:"dj", width:100},
			{header:"总数量", dataIndex:"zsl", width:100},
			{header:"单位", dataIndex:"dwmc", width:70},
			<#if dmList??>
				<#list dmList as dmObj>
					{header:"${dmObj[1]!}", dataIndex:"${dmObj[0]!}"},
				</#list>
			</#if>
        ]);
		
		var yxtjGridDs = new Ext.data.JsonStore({
			url:"${ctxPath}/cxtj/yxtj_loadYxtj.shtml", root:"yxtjList", 
			fields:["hpmc", "bzgg", "dj", "zsl", "dwmc"<#if dmList??><#list dmList as dmObj>, "${dmObj[0]!}"</#list></#if>]
		});
		
		var kclistGrid = new Ext.grid.GridPanel({
			renderTo:"yxtjGrid", title:"营销信息", width:1000, frame:true, height:350, cm:yxtjGridCm, ds:yxtjGridDs, loadMask:true, viewConfig:{forceFit:true},
			tbar:["-", {
				text:"导出",
				handler:function() {
					if (kclistGrid.getStore().getCount() == 0) {
						Ext.Msg.alert("系统提示", "没有数据可供导出！");
						return ;
					}
					window.location.href = "${ctxPath}/cxtj/yxtj_export.shtml";
				}
			}, "-"]
		});
		
	});
	

</script>
</head>
<body>
<div style="width:1000px;text-align:center"><font size="2">营销统计</font></div>
<div id="queryForm"></div>
<div id="yxtjGrid"></div>
</body>
</html>
 
