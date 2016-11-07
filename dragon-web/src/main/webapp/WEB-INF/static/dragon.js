var dragon = {
		
}

dragon.getFullUrl=function(url){
	return basePath + url ;
}

dragon.submitForm = function(from , url , success ){
	$(from).form('submit',{
		onSubmit:function(){
			
			var flag = $( this ).form('enableValidation').form('validate') ;
			if(flag){
				dragon.progress(true); 
			}
			return flag ;
		},
		url : dragon.getFullUrl(url) ,
		success : function( data ){
			$.messager.progress("close"); 
			var map = $.parseJSON( data ); 
			if( map.success ){
				success( map.data ) ;
			}else{
				dragon.warn( map.msg );
			}
		},
		onLoadError: function(e){
			dragon.warn( "服务器内部错误");
		}
	})
}

/*页面跳转*/
dragon.go = function(url){
	window.location = dragon.getFullUrl( url );
}

/*加载进度条*/
dragon.progress = function(auto){
	$.messager.progress({
        title:'请稍后',
        msg:'正在努力加载中...'
    });
}

/*消息框*/
dragon.info = function( msg ){
	$.messager.alert("消息框" , msg , 'info' );
}

dragon.warn = function( msg ){
	$.messager.alert("温馨提示" , msg , 'warning' );
}

dragon.show = function(code , msg){
	if(code == 0){
		dragon.info(msg);
	}else{
		dragon.warn(msg);
	}
}

