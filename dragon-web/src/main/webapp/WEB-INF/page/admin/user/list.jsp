<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<jsp:include page="../comm/import.jsp" />
		
		<script type="text/javascript">
			function doSearch(){
				$('#tt').datagrid('load',{
					loginName: $('#loginName').val(),
					email: $('#email').val(),
					stime: $('#stime').datebox('getValue'),
					etime: $('#etime').datebox('getValue')
				});
			}
			
			function formatOper(val,row,index){
				
				return '<a href="javascript:void()" onclick="editUser('+row.id+')">修改</a>' ; 
			}
			
		</script>
	</head>
	<body>
	
		<table id="tt" class="easyui-datagrid" style="width:100%;height:500px"
            url="admin/ajax_user_list.html" toolbar="#tb"
            title="系统账号管理" iconCls="icon-save"
            rownumbers="true" idField="id" pagination="true" pageList="[15]" pageSize="15">
	        <thead>
	            <tr>
	                <th field="id" checkbox="true" width="80">row ID</th>
	                <th field="loginName" width="180">登陆账号</th>	
	                <th field="phone" width="200" align="right">电话</th>
	                <th field="email" width="200">邮箱</th>
	                <th field="createDate" width="200" align="center">创建日期</th>
	                <th field="isAdmin" width="200" align="center">是否管理</th>
	                <th field="_operate" width="150" align="center" formatter="formatOper">是否管理</th> 
	            </tr>
	        </thead>
	    </table>
	    
	    <div id="tb" style="padding:3px">
			<span>账号:</span>
			<input id="loginName" class="easyui-textbox">
			<span>邮箱:</span>
			<input id="email" class="easyui-textbox">
			<span>创建日期:</span>
			<input id="stime" class="easyui-datebox">--
			<input id="etime" class="easyui-datebox"  >
			<a href="javascript:void()" class="easyui-linkbutton" plain="true" onclick="doSearch()">查询</a>
		</div>
	</body>
</html>