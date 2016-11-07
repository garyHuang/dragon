<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<jsp:include page="comm/import.jsp" />
		<script type="text/javascript">
			
			function submitForm(){
				dragon.submitForm("#ff" , "admin/ajax_user_login.html" , function(json){
					if(json.code == 0){
						dragon.go("admin/p_main_menu.html"); 
					}else{
						dragon.show( json.code , json.msg );
					}
				})
			}
			
		</script>
	</head>
	<body style="text-align:center;">
			<div class="easyui-window" title="登录"
					minimizable="false" maximizable="false" resizable="false" collapsible="false" 
					data-options="
					modal:false,
					closable:false,
					onResize:function(){
					$(this).dialog('center');
					}"
				style="width:400px;height:221px;padding:10px">
				
				<form id="ff" class="easyui-form" method="post" data-options="novalidate:true">
					<div style="margin-bottom:20px">
						<input class="easyui-textbox" id="uname" name="uname" style="width:100%;height:40px;padding:12px" data-options="required:true,prompt:'请输入用户名',iconCls:'icon-man',iconWidth:38">
					</div>
					
					<div style="margin-bottom:20px">
						<input class="easyui-passwordbox" id="pwd" name="pwd" style="width:100%;height:40px;padding:12px" data-options="required:true,prompt:'请输入密码',iconWidth:38">
					</div>  
					  
					<div data-options="border:false" style="text-align:center;padding:5px 0 0;">
						<a class="easyui-linkbutton" href="javascript:void(0)" onclick="javascript:submitForm()" style="width:80px">登录</a>
					</div>
				</form>
			</div>
	</body>
</html>