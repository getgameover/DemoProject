/**
 * 
 */
var Lu = (function($, l, undefined) {
	l.formatRightBox = function(rightsMsg, rights) {
		var html = '<div class="form_rights_msg">';
		$.each(rights,
				function(i, right) {
					var rightName = right.rightName;
					var name = right.name;
					var description = right.description;
					var ischose = false;
					if (rightsMsg) {
						$.each(rightsMsg, function(j, msg) {
							var rightName_Msg = msg.rightName
							if (rightName_Msg == rightName) {
								ischose = true;
							}
						});
					}

					html += '<div class="rights_checkbox" title="'
							+ description + '">';
					html += '<input type="checkbox" ';
					if (ischose) {
						html += 'checked="checked"';
					}
					html += ' name="rights[' + i + ']" value="' + rightName
							+ '">';
					html += '<span>' + name + '</span></div>';
				});
		html += '</div>';
		return html;
	}
	/**
	 * 对日期进行格式化，
	 * 
	 * @param date
	 *            要格式化的日期
	 * @param format
	 *            进行格式化的模式字符串 支持的模式字母有： y:年, M:年中的月份(1-12), d:月份中的天(1-31),
	 *            h:小时(0-23), m:分(0-59), s:秒(0-59), S:毫秒(0-999), q:季度(1-4)
	 * @return String
	 * @author yanis.wang
	 * @see http://yaniswang.com/frontend/2013/02/16/dateformat-performance/
	 */
	l.dateFormat = function(date, format) {
		if (!date)
			return date;
		date = new Date(date);
		var map = {
			"M" : date.getMonth() + 1, // 月份
			"d" : date.getDate(), // 日
			"h" : date.getHours(), // 小时
			"m" : date.getMinutes(), // 分
			"s" : date.getSeconds(), // 秒
			"q" : Math.floor((date.getMonth() + 3) / 3), // 季度
			"S" : date.getMilliseconds()
		// 毫秒
		};
		format = format.replace(/([yMdhmsqS])+/g, function(all, t) {
			var v = map[t];
			if (v !== undefined) {
				if (all.length > 1) {
					v = '0' + v;
					v = v.substr(v.length - 2);
				}
				return v;
			} else if (t === 'y') {
				return (date.getFullYear() + '').substr(4 - all.length);
			}
			return all;
		});
		return format;
	};
	
	
	l.formatStatus=function(status){
    	if(!status&&status!=0){
    		return '<span class="lu_format lu_format_error">ERROR</span>';
    	}
    	switch (status) {
		case 0: return '<span class="lu_format lu_format_status_0">失效</span>';
		case 1: return '<span class="lu_format lu_format_status_1">有效</span>';
		case 2: return '<span class="lu_format lu_format_status_2">其他</span>';
		default:
			return '<span class="lu_format lu_format_error">ERROR</span>';
		}
    };
	l.formatFileType=function(typeValue){
		if(l.common_fileTypes){
			var html_ft='';
			$.each(l.common_fileTypes,function(i,ft){
				var name=ft.typeName;
				if(ft.typeValue==typeValue){
					html_ft= '<span class="lu_format lu_format_'+typeValue+'">'+name+'</span>';
					return false;
				}
			});
			if(html_ft){
				return html_ft;
			}
		}
		return '<span class="lu_format lu_format_error">ERROR</span>';
	};
	l.formatImgUrl=function(typeValue,rowData){
		if(rowData&&rowData.ext){
			var ext=rowData.ext;
			var url=rowData.url;
			var name=rowData.name;
			if(ext=="jpg"||ext=="jpeg"||ext=="png"||ext=="gif"){
				return '<span class="lu_format lu_format_img"><img src="'+url+'" alt="'+name+'" width="80" /></span>';
			}
		}
		return '<span class="lu_format lu_format_img_noshow" title="'+name+'">不能预览</span>';
	};
	
	l.formatFileSize=function(size){
		if(!status&&status!=0){
			return '<span class="lu_format lu_format_error">ERROR</span>';
		}
		if(size<=1024){
			return '<span class="lu_format lu_format_size">'+size+'B</span>';
		}else if(size<=1024*1024){
			return '<span class="lu_format lu_format_size">'+(size/1024).toFixed(2)+'K</span>';
		}else if(size<=1024*1024*1024){
			return '<span class="lu_format lu_format_size">'+(size/1024/1024).toFixed(2)+'M</span>';
		}else{
			return '<span class="lu_format lu_format_size">'+(size/1024/1024/1024).toFixed(2)+'G</span>';
		}
	};
})(jQuery, window.lu = window.lu || {});
