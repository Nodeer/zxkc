<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录</title>
<#include "/commons/ext.ftl"/>
<script type="text/javascript">

	function fnLogin() {
		Ext.Ajax.request({
			form: 'loginForm',
			success: fnCheckLogin
		});
	}
	
	function fnEnterLogin(event) {
		if (event.keyCode == 13) {
			fnLogin();
		}
	}
	
	function fnCheckLogin(response, options) {
		var data = Ext.decode(response.responseText);
		if (data.success) {
			window.location.href = "index.shtml";
        } else {
        	Ext.Msg.alert("系统提示", data.loginErrorMsg);
        	return ;
        }
	}
	
</script>
</head>
<body>
<div id="content" style="text-align:center">
	<div id="loginDiv" style="text-align:center; position:relative; top:200px">	
		<form action="/zxkc/login.shtml" id="loginForm">
			<table style="border:none; width:100%">
				<tr>
					<td style="text-align:right; width:45%">用户名：</td>
					<td style="text-align:left; width:55%"><input type="text" style="width:180px" id="username" name="username"/></td>
				</tr>
				<tr>
					<td style="text-align:right; width:45%">密码：</td>
					<td style="text-align:left; width:55%"><input type="password" style="width:180px "id="password" name="password" onKeyPress="fnEnterLogin(event)"/></td>
				</tr>
				<tr>
					<td style="text-align:center" colspan="2"><input type="button" id="loginBtn" onclick="fnLogin()" value="登录"/></td>
				</tr>
			</table>
		</form>
	</div>
</div>
</body>
</html>
