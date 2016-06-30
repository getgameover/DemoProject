/**
 * 初始化入口函数
 */
$(function(){
	DWZ.init("../plug-in/dwz/dwz.frag.xml", {
		loginUrl:"common/LoginDialog.html", loginTitle:"系统登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage",
		orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		keys: {statusCode:"statusCode", message:"message"}, //【可选】
		ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
		debug:true,	// 调试模式 【true|false】
		callback:function(){
			$("#themeList").theme({themeBase:"../plug-in/dwz/themes"}); // themeBase 相对于index页面的主题base路径
			initEnv();
			//已登录状态
			if($.cookie("logined") && $.cookie("logined")!= "null"){
				lu.refreshLoadMsg();
            }else{
                lu.loadLogin();
            }
			 
           
		}
	});
	$.extend( $.fn.dataTable.defaults, {
	    searching: false,
	    ordering:  false,
	    searchable:  false,
	    language: {
	        "sProcessing": "处理中...",
	        "sLengthMenu": "显示 _MENU_ 项结果",
	        "sZeroRecords": "没有匹配结果",
	        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	        "sInfoPostFix": "",
	        "sSearch": "搜索:",
	        "sUrl": "",
	        "sEmptyTable": "表中数据为空",
	        "sLoadingRecords": "载入中...",
	        "sInfoThousands": ",",
	        "oPaginate": {
	            "sFirst": "首页",
	            "sPrevious": "上页",
	            "sNext": "下页",
	            "sLast": "末页"
	        },
	        "oAria": {
	            "sSortAscending": ": 以升序排列此列",
	            "sSortDescending": ": 以降序排列此列"
	        }
	    }
	} );
	lu.upFileSize=1024*1024*5;
	
});