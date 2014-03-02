<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<#include "/commons/ext.ftl"/>
<#include "/commons/commonJs.ftl"/>
<script type="text/javascript">

	function fnCheckPasswd(oEle, e) {
		if (e.keyCode == "13") {
			Ext.Ajax.request({
				url: "${ctxPath}/jhlr/hplr_checkPasswd.shtml",
				params: {passwd:oEle.value},
				success: function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						window.location.href = "${ctxPath}/jhlr/hplr_funcHplr.shtml";
					} else {
						Ext.Msg.alert("系统提示", "您输入的密码不正确！");
					}
				}
			});
		}
	}

</script>
</head>
<body>
<br>
<div>请输入密码后回车：<input type="password" id="passwd" style="width:200px" onkeypress="fnCheckPasswd(this,event)"/></div>
</body>
</html>
