

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<jsp:include page="../comm/import.jsp" />
	</head>
	<body>
		
		<table title="权限表" class="easyui-treegrid" style="width:100%;height:400px"
			url="admin/ajax_permission_list.html"
			rownumbers="true" showFooter="true"
			idField="id" treeField="menuname">
			<thead frozen="true">
				<tr>
					<th field="menuname" width="200">权限名</th>
				</tr> 
			</thead>
			<thead>
				<tr>
					<th colspan="4">其他信息</th>
				</tr>
				<tr>
					<th field="id" width="100">id</th>
					<th field="icon" width="120" >图标</th> 
					<th field="createDate" width="150">创建日期</th> 
					<th field="url" width="200">链接</th> 
				</tr>
			</thead> 
		</table>
	
	</body>
	
</html>