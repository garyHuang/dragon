<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<jsp:include page="../comm/import.jsp" />
		<script type="text/javascript" src="${basePath}static/admin.main.js${time}"></script>
		<link rel="stylesheet" type="text/css" href="${basePath}static/css/admin.main.css${time}">
		<style type="text/css">
			#css3menu li a{	color:#fff; padding-right:20px;}
			#css3menu li a.active{color:yellow;}
		</style>
	</head>
	
	<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
		 <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(static/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">欢迎Admin&nbsp;&nbsp;<a href="#" id="loginOut">安全退出</a></span>
	   	
        <span style="padding-left:10px; font-size: 16px; float:left;"><img src="static/images/blocks.gif" width="20" height="20" align="absmiddle" />DRAGON</span>
		
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">DRAGON</div>
    </div>
    <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
<div id='wnav' class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->
				
			</div>

    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="欢迎使用" style="padding:20px;overflow:hidden;" id="home">
				
			<h1>Welcome to using DRAGON</h1>

			</div>
		</div>
    </div>
    
	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
	</div> 
	</body>
	<script type="text/javascript">
		var _menus = {
			basic : ${data}
		}
 	</script>
</html>