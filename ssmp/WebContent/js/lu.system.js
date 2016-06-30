/**
 * 
 */
var Lu=(function($,l,undefined){
	l.sysManageUserLtInit=function(op,panel){
		l.datatables_sys_userlist=$(panel).find("#manage_user_lt").DataTable({
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
			             {data : "username",name : "username"},
			             {data : "name",name : "name"},
			             {data : "phone",name : "phone"},
			             {data : "qq",name : "qq"},
			             {data : "idcard",name : "idcard"},
			             {data : "address",name : "address"},
			             {data : "rightHtml",name : "userRight",
			            	 createdCell:function(td,dataCell,rowData){
			             }},
			             
			             {data : null,width:100,
			            	 createdCell:function(td,dataCell,rowData){
			            		 var bt_tool='<div class="cell_bt_tools">'
			            			+'<button class="edit">修改</button>'
									+'</div>';
			            		$(td).html(bt_tool);
			            		
			            		$(td).find(".edit").click(function(){
			            			$.pdialog.open("system/UpdateUser.html","_blank","修改用户信息",{	
			            				width : 400,
			    						height : 400,
			    						mask : true,
			    						maxable : false,
			    						resizable : false,
			    						loaded : function(op,dialog){//后执行
			    							var upuser_form=$(dialog).find("form#updateuser_form");
			    							var rights=l.system_rights;
			    							var html_rights=lu.formatRightBox(rowData.rightsMsg,rights);
			    							$(upuser_form).find("#rights_msg").html(html_rights);
			    							upuser_form.reloadForm(rowData);
			    							
			    							upuser_form.html5Validate(function() {
			    								var data = $(this).serializeJSON();
			    								lu.post('/lg/system/updateuser.lu', JSON.stringify(data), function(response) {
			    									$.pdialog.closeCurrent();// (op.dialog);
			    									l.datatables_sys_userlist.ajax.reload();
			    								});
			    							});
			    						}
			    					});
			            		});
			            	 }
			             }],
			"dom" : 'l<"datatables_tools">frtip',
			initComplete : function(settings, json) {
				
			},
			"ajax" : function(data, callback, settings) {
				// 智能搜索
				var key = data.search.value;
				if (/^\d{1,10}$/.test(key)) {
					data.columns[0].searchable = false;
					data.columns[0].search.type = 'Integer';
					data.columns[0].search.value = key;
				}
				if (/^(\d|\D){1,10}$/.test(key)) {
					data.columns[1].searchable = false;
					data.columns[1].search.type = 'String';
					data.columns[1].search.value = key;
				}
				if (/^\D{1,10}$/.test(key)) {
					data.columns[2].searchable = false;
					data.columns[2].search.type = 'String';
					data.columns[2].search.value = key;
				}

				lu.post('/lg/system/userlist.lu',JSON.stringify(data),
						function(response) {
							l.system_rights=response.rights;
							callback(response.result);
							
						});
			}
		});
	}
	l.sysSaveUser=function(op,dialog){
		var saveuser_form=$(dialog).find("form#saveuser_form");
		var rights=l.system_rights;
		var html_rights=lu.formatRightBox({},rights);
		$(saveuser_form).find("#rights_msg").html(html_rights);
		
		saveuser_form.html5Validate(function() {
			var data = $(this).serializeJSON();
			lu.post('/lg/system/saveuser.lu', JSON.stringify(data), function(response) {
				$.pdialog.closeCurrent();// (op.dialog);
				l.datatables_sys_userlist.ajax.reload();
			});
		});
	}
})(jQuery, window.lu = window.lu || {});;