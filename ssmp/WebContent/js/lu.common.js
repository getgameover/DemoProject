/**
 * 公共部分
 */
var Lu = (function($, l, undefined) {
	l.loadLogin = function(){
		if ($.pdialog && DWZ._set.loginTitle) {
			$.pdialog.open(DWZ._set.loginUrl, "login", DWZ._set.loginTitle, {mask:true,maxable:false,resizable:false,
                width:350,height:200,
                loaded:function(op,dialog){
                	$(dialog).find("#login_form").html5Validate(function(){
                		 //登录表单提交
                        var data = $(this).serializeJSON();
                        lu.post('/login/in.lu', JSON.stringify(data), function(response){
                        	$.cookie("username", response.username);   //设置用户名
                            $.cookie("logined", response.username);
                            $.pdialog.closeCurrent();
                        	lu.refreshLoadMsg();
                        },{},function(){
                    		$(dialog).find("img#login_img_code").attr({src:lu.getimgcodeurl()});
                        });
                	});
                	$(dialog).find("img#login_img_code").attr({src:lu.getimgcodeurl()});
                	$(dialog).find("img#login_img_code").click(function(){
                		$(dialog).find("img#login_img_code").attr({src:lu.getimgcodeurl()});
                	});
                }});
		} else {
			window.location = DWZ._set.loginUrl;
		}
	};
	//修改密码
	l.changepwd =function(){
		var op={rel:'common_up_user_msg',title:'修改用户信息'}
		var dialog=$.pdialog.open("common/ChangePwd.html",op.rel,op.title,{	
			width : 350,
			height : 230,
			mask : true,
			maxable : false,
			rel:op.rel,
			resizable : false,
			loaded : function(op,dialog){//后执行
				$(dialog).find("#changepwd_form").html5Validate(function(){
					var data = $(this).serializeJSON();
					lu.post('/lg/common/changepwd.lu', JSON.stringify(data), function(response){
						$.pdialog.closeCurrent();
						alertMsg.correct('密码修改成功！')
					},{},function(){
		        		$(dialog).find("img#login_img_code").attr({src:lu.getimgcodeurl()});
					});
				});
				$(dialog).find("img#login_img_code").attr({src:lu.getimgcodeurl()});
				$(dialog).find("img#login_img_code").click(function(){
		    		$(dialog).find("img#login_img_code").attr({src:lu.getimgcodeurl()});
		    	});
			}
		});
	};
	
	l.upUserMsg =function(){
		var op={rel:'common_up_user_msg',title:'修改用户信息'}
		var dialog=$.pdialog.open("common/UpUserMsg.html",op.rel,op.title,{	
			width : 400,
			height : 400,
			mask : true,
			maxable : false,
			rel:op.rel,
			resizable : false,
			loaded : function(op,dialog){//后执行
				var data1={};
				lu.post('/lg/common/selectusermsg.lu', JSON.stringify(data1), function(response){
					
					$(dialog).find("#upusermsg_form #headImgUrl").attr({src:response.headImgUrl});
					$(dialog).find("#upusermsg_form").reloadForm(response.result);
					$(dialog).find("#upusermsg_form").html5Validate(function(){
						var data = $(this).serializeJSON();
						lu.post('/lg/common/upusermsg.lu', JSON.stringify(data), function(response){
							$.pdialog.closeCurrent();
							alertMsg.correct('用户信息修改成功！')
						});
					});
					$(dialog).find("#selectHeadImg").click(function(){
						lu.comOpenFilesList({insert:function(op,rowData){
							$(dialog).find('input[name="headImgId"]').val(rowData.id);
							$(dialog).find("#upusermsg_form #headImgUrl").attr({src:rowData.url});
						}});
					});
				},{},function(){
					alertMsg.error('用户信息请求失败！');
					$.pdialog.closeCurrent();
				});
			}
		});
		
	};
	l.timeout = function(){
		alertMsg.confirm("你确认要退出当前登陆用户吗?",{okCall:function(){
			lu.post('/lg/common/timeout.lu',{},function(response){
				
			});
		}});
	}
	//刷新登陆信息
	l.refreshLoadMsg = function(loaded){
		lu.post("/lg/common/loadsysmsg.lu",{},function(response){
			var menus=response.result.menus;
			var username=response.result.username;
			$.cookie("username", username);   //设置用户名
            $.cookie("logined", username);
            $("#page_login_username").html(username);
           
            if(menus){
            	var html='<div class="accordion" fillSpace="sidebar">'
            	html+=formatMenusHtml(menus);
            	html+='</div>';
            	$("#sidebar").find(".accordion").remove().end().append(html).initUI();
            }
            if ($.isFunction(loaded)){
            	loaded(response);
			}
		});
		function formatMenusHtml(menus){
			var html='';
			if(menus&&menus.length>0){
				for(var i in menus){
					var menu=menus[i];
					var m=menu.menu;
					var ms=menu.menus;
					if(m&&m.target=='accordionHeader'){
						html+='<div class="accordionHeader"><h2><span>Folder</span>'+m.name+'</h2></div>';
						html+='<div class="accordionContent"><ul class="tree treeFolder">';
						var html_child=formatMenusHtml(ms);
						if(html_child){
							html+=html_child;
						}
						html+='</ul></div>';
					}else if(m){
						html+='<li';
						if(i+1==menus.length){
							html+=' class="last" '
						}
						html+='><a ';
						if(m.href){
							html+='href="'+m.href+'" ';
						}
						if(m.target){
							html+='target="'+m.target+'" ';
						}
						if(m.rel){
							html+='rel="'+m.rel+'" ';
						}
						if(m.width){
							html+='width="'+m.width+'" ';
						}
						if(m.height){
							html+='height="'+m.height+'" ';
						}
						if(m.loaded){
							html+='loaded="'+m.loaded+'" ';
						}
						if(m.click){
							html+='onClick="'+m.click+'" ';
						}
						html+='>'+m.name+'</a>';
						var html_child=formatMenusHtml(ms);
						if(html_child){
							html+='<ul>';
							html+=html_child;
							html+='</ul>';
						}
						html+='</li>';
					}
				}
			}
			return html;
		}
	};
	l.comOpenFilesList=function(_op,dialog){
		var op={rel:'common-mymsg-managefiles',title:'文件存档管理',insert:function(rowData){/**选择要插入对象,执行方法**/}}
		op=$.extend(op,_op);
		if(!dialog){
			dialog=$.pdialog.open("common/MamageFilesList.html",op.rel,op.title,{	
				width : 1100,
				height : 600,
				mask : true,
				insert:op.insert,
				maxable : false,
				rel:op.rel,
				resizable : false,
				loaded : function(op,dialog){//后执行
					loadFileList(op,dialog);
				}
			});
		}else{
			loadFileList(op,dialog);
		}
		function loadFileList(op,dialog){
			l.datatables_com_filelist=$(dialog).find("#manage_file_lt").DataTable({
				"processing" : true, // 打开正在处理指示
				"serverSide" : true, // 服务器端处理/分页
				searching: true,
			    ordering:  true,
				"columnsDefs" : [{"targets" : "_all","searchable" : false}],
				"columns" : [
				             {data : "addTime",name : "addTime",
				            	 createdCell:function(td,dataCell,rowData){
				            		 $(td).html(l.dateFormat(dataCell,'yyyy-MM-dd hh:mm'));
				            	 }},
				             {data : "url",name : "url",createdCell:function(td,dataCell,rowData){
				            	 $(td).html(lu.formatImgUrl(dataCell,rowData));
				             }},
				             {data : "name",name : "name"},
				             {data : "ext",name : "ext"},
				             {data : "imgType",name : "imgType",createdCell:function(td,dataCell,rowData){
				            	 $(td).html(lu.formatFileType(dataCell));
				             }},
				             {data : "status",name : "status",createdCell:function(td,dataCell){
				            	 $(td).html(lu.formatStatus(dataCell));
				             }},
				             
				             {data : "size",name : "size",createdCell:function(td,dataCell){
				            	 $(td).html(lu.formatFileSize(dataCell));
				             }},
				             
				             {data : null,
				            	 createdCell:function(td,dataCell,rowData){
				            		 var bt_tool='<div class="cell_bt_tools">'
				            			+'<button class="edit">修改</button>'
				            			+'<button class="dete">删除</button>'
				            			+'<button class="insert">插入</button>'
										+'</div>';
				            		$(td).html(bt_tool);
				            		
				            		$(td).find(".edit").click(function(){
				            			$.pdialog.open("common/UpdateFileMsgDialog.html","_blank","修改文件信息",{	
				            				width : 400,
				    						height : 200,
				    						mask : true,
				    						maxable : false,
				    						resizable : false,
				    						loaded : function(op,dialog){//后执行
				    							var upfilemsg_form=$(dialog).find("form#updatefilemsg_form");
				    							upfilemsg_form.reloadForm(rowData);
				    							
				    							upfilemsg_form.html5Validate(function() {
				    								var data = $(this).serializeJSON();
				    								data.id=rowData.id;
				    								lu.post('/lg/common/upfilemsg.lu', JSON.stringify(data), function(response) {
				    									$.pdialog.closeCurrent();// (op.dialog);
				    									l.datatables_com_filelist.ajax.reload();
				    								});
				    							});
				    						}
				    					});
				            		});
				            		$(td).find('.dete').click(function(){
				            			alertMsg.confirm('你确定要删除['+rowData.name+']该文件吗?删除后不可恢复',{okCall:function(){
				            				var data={id:rowData.id};
				            				lu.post('/lg/common/delefilemsg.lu', JSON.stringify(data), function(response) {
		    									l.datatables_com_filelist.ajax.reload();
		    								});
				            			}});
				            		});
				            		$(td).find('.insert').click(function(){
				            			$.pdialog.close(op.rel);
				            			if(typeof op.insert == 'string' ){
				            				 op.insert = lu[op.insert];
				            			}
						                if($.isFunction(op.insert)) op.insert(op,rowData);
				            		});
				            		
				            	 }
				             }],
				"dom" : 'l<"datatables_tools">frtip',
				initComplete : function(settings, json) {
					
				},
				"ajax" : function(data, callback, settings) {
					// 智能搜索
					var key = data.search.value;
					if (/^(\d|\D){1,10}$/.test(key)) {
						data.columns[2].searchable = false;
						data.columns[2].search.type = 'String';
						data.columns[2].search.value = key;
					}
					if (/^\D{1,10}$/.test(key)) {
						data.columns[3].searchable = false;
						data.columns[3].search.type = 'String';
						data.columns[3].search.value = key;
					}
					
					lu.post('/lg/common/filelist.lu',JSON.stringify(data),
							function(response) {
								l.common_fileTypes=response.fileTypes;
								callback(response.result);
								
							});
				}
			});
		}
	}
	l.comUpFilesDialog=function(op,dialog){
		if(typeof FileReader == undefined){  
			alertMsg.error('你的浏览器不支持FileReader接口！请升级到最新的浏览器');
			return;
		}
		var upfile_form=$(dialog).find("#upfile_form");
		if(l.common_fileTypes){
			var html_filetype='';
			$.each(l.common_fileTypes,function(i,filetype){
				var value=filetype.typeValue;
				var name=filetype.typeName;
				html_filetype+='<option value="'+value+'">'+name+'</option>';
			});
			$(upfile_form).find('select[name="fileType"]').html(html_filetype);
		}
		
		var file=$(upfile_form).find('input#upfile_form_file');
		file.change(function(){
			if(this.files){
				var file_src=this.files[0];
				var name=file_src.name;
				$(upfile_form).find('input[name="name"]').val(name);
			}
		})
		
		upfile_form.html5Validate(function() {
			var file=$(this).find('input#upfile_form_file');
			if(file[0]){
				file=file[0];
			}
			if(!file.files||!file.files[0]){
				alertMsg.error("未读取到上传文件!");
				return;
			}
			var file_src=file.files[0];
			var size=file_src.size;
			var name=file_src.name;
			if(size>lu.upFileSize){
				alertMsg.error('上传文件不能超过:'+(lu.upFileSize/1024)+'KB');
				$(this).find('input[name="name"]').val('');
				$(this).find('input#upfile_form_file').val('');
				return;
			}else{
				$(this).find('input[name="name"]').val(name);
			}
			
			var fr=new FileReader();
			fr.onerror=function(){
				alertMsg.error("文件读取有误");
			}
			fr.onload=function(e){
				var data = $(upfile_form).serializeJSON();
				data.file=fr.result;
				data.fileName=name;
				data.fileSize=size;
				lu.post('/lg/common/upfiles.lu', JSON.stringify(data), function(response) {
					$.pdialog.closeCurrent();// (op.dialog);
					l.datatables_com_filelist.ajax.reload();
				});
			}
			fr.readAsDataURL(file_src,"utf-8");
		});
	};
	
	
})(jQuery, window.lu = window.lu || {});
