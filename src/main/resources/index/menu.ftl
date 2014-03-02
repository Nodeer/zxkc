<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录</title>
<#include "/commons/ext.ftl"/>
<script type="text/javascript">

Ext.onReady(function() {

	var root = new Ext.tree.AsyncTreeNode({
		id: 'root',
		text: '在线库存'
	});
	
	var loader = new Ext.tree.TreeLoader({dataUrl: '${ctxPath!}/index_loadMenu.shtml?'}); 

	var menuTree = new Ext.tree.TreePanel({
		renderTo: 'menuTreeDiv',
		loader: loader,
		border: false,
		root: root
	});
	
	root.expand();
	
	menuTree.on("click", function(node) {
		if (node.leaf) {
			intoFunc("${ctxPath}" + node.attributes.funcUrl);
		}
	})
	
	function intoFunc(sUrl) {
		window.parent.document.getElementById("func").src = sUrl;
	}
});	

</script>
</head>
<body>
<div style="width:100%; height:100%; overflow:auto">
	<div id="menuTreeDiv" style="width:100%; height:100%"></div>
</div>
</body>
</html>
 
