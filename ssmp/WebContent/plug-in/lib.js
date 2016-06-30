function Base64(){ 
    // private property
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";  
    // public method for encoding
    this.encode = function (input) {  
        var output = "";  
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;  
        var i = 0;  
        input = _utf8_encode(input);  
        while (i < input.length) {  
            chr1 = input.charCodeAt(i++);  
            chr2 = input.charCodeAt(i++);  
            chr3 = input.charCodeAt(i++);  
            enc1 = chr1 >> 2;  
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);  
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);  
            enc4 = chr3 & 63;  
            if (isNaN(chr2)) {  
                enc3 = enc4 = 64;  
            } else if (isNaN(chr3)) {  
                enc4 = 64;  
            }  
            output = output +  
            _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +  
            _keyStr.charAt(enc3) + _keyStr.charAt(enc4);  
        }  
        return output;  
    };
   
    // public method for decoding
    this.decode = function (input) {  
        var output = "";  
        var chr1, chr2, chr3;  
        var enc1, enc2, enc3, enc4;  
        var i = 0;  
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");  
        while (i < input.length) {  
            enc1 = _keyStr.indexOf(input.charAt(i++));  
            enc2 = _keyStr.indexOf(input.charAt(i++));  
            enc3 = _keyStr.indexOf(input.charAt(i++));  
            enc4 = _keyStr.indexOf(input.charAt(i++));  
            chr1 = (enc1 << 2) | (enc2 >> 4);  
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);  
            chr3 = ((enc3 & 3) << 6) | enc4;  
            output = output + String.fromCharCode(chr1);  
            if (enc3 != 64) {  
                output = output + String.fromCharCode(chr2);  
            }  
            if (enc4 != 64) {  
                output = output + String.fromCharCode(chr3);  
            }  
        }  
        output = _utf8_decode(output);  
        return output;  
    };
   
    // private method for UTF-8 encoding
    _utf8_encode = function (string) {  
        string = string.replace(/\r\n/g,"\n");  
        var utftext = "";  
        for (var n = 0; n < string.length; n++) {  
            var c = string.charCodeAt(n);  
            if (c < 128) {  
                utftext += String.fromCharCode(c);  
            } else if((c > 127) && (c < 2048)) {  
                utftext += String.fromCharCode((c >> 6) | 192);  
                utftext += String.fromCharCode((c & 63) | 128);  
            } else {  
                utftext += String.fromCharCode((c >> 12) | 224);  
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);  
                utftext += String.fromCharCode((c & 63) | 128);  
            }  
   
        }  
        return utftext;  
    };
   
    // private method for UTF-8 decoding
    _utf8_decode = function (utftext) {  
        var string = "";  
        var i = 0;  
        var c = c1 = c2 = 0;  
        while ( i < utftext.length ) {  
            c = utftext.charCodeAt(i);  
            if (c < 128) {  
                string += String.fromCharCode(c);  
                i++;  
            } else if((c > 191) && (c < 224)) {  
                c2 = utftext.charCodeAt(i+1);  
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));  
                i += 2;  
            } else {  
                c2 = utftext.charCodeAt(i+1);  
                c3 = utftext.charCodeAt(i+2);  
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));  
                i += 3;  
            }  
        }  
        return string;  
    };
}

$.fn.extend({
	 reloadForm: function(jsonObj){
       if(!jsonObj) return;
       //表单下的input,select和textarea元素,disable的表单元素也会回填
       var that = this;
       var exist = {};
       this.find('input, select, textarea').each(function(){
           var name = this.getAttribute('name');
           //略过没有name属性的表单元素
           if(!name){
               return true;
           }
           //滤除重名的表单元素,重名的表单元素为数组或(子)对象数组的属性
           if(exist[name]){
               return true;
           }else{
               exist[name] = true;
           }

           //表单元素设置/赋值
           function setElement(val){
               if(this.type == 'checkbox'){
                   this.checked = val;
               }else if(this.type == 'radio'){
                   if(this.value == val){
                       this.checked = true;
                   }
               }else if(this.tagName == 'SELECT'){
                   $(this).children().each(function(){
                       var optVal = this.value || this.textContent;
                       if(optVal == "false") optVal = false;
                       if(optVal == "true") optVal = true;
                       if(optVal == val){
                           this.selected = true;
                       }
                   });
               }else if(this.tagName == 'TEXTAREA'){
                   this.textContent = val;
               }else{
                   if(this.getAttribute('type') == 'date'){
                       //this.value = reis.mSecsToDateTimeStr(val);
                       this.value = reis.dateFormat(val, 'yyyy-MM-dd');
                   }else{
                       this.value = val;
                   }
               }
           };

           /*
            * name有多种形式:name, name[], name[prop][prop][...], name[integer][prop][...]  
            * name[prop][...][][prop],存在深层次对象
            */
           if(/^[^\[\]]+(\[[^\[\]0-9]+[^\[\]]*\])*\[\]$/.test(name)){
               //name[prop][prop]...[], name[prop][prop]...数组
               var props = name.match(/([^\[\]]+)/g);
               var subObj = props.shift();
               var tmp = jsonObj[subObj];
               props.forEach(function(value, index, array){
                   tmp = tmp[value];
               });
               //查找所有同名的元素,将jsonObj对应的数组值依次赋予表单元素
               that.find('[name="' + name + '"]').each(function(idx, el){
                   if(tmp[idx]){
                       setElement.call(this, tmp[idx]);
                   }
               });
           }else if(/^[^\[\]]+(\[[^\[\]0-9]+[^\[\]]*\])*\[\](\[[^\[\]]*\]){1}$/.test(name)){
               //name[prop][prop]...[][prop], name[prop][prop]...对象数组
               var props = name.match(/([^\[\]]+)/g);
               //数组元素对象的属性
               var subObjProp = props.pop();
               var subObj = props.shift();
               var tmp = jsonObj[subObj];
               props.forEach(function(value, index, array){
                   tmp = tmp[value];
               });
               if(tmp){
                   //查找所有同名的元素,将jsonObj对应的数组值依次赋予表单元素
                   that.find('[name="' + name + '"]').each(function(idx, el){
                       if(tmp[idx] && tmp[idx][subObjProp]){
                           setElement.call(this, tmp[idx][subObjProp]);
                       }
                   });
               }
           }else if(/^[^\[\]]+\[[0-9]+\](\[[^\[\]0-9]+[^\[\]]*\])*$/.test(name)){
               //name[interger][prop]... ,类数组对象
           }else if(/^[^\[\]]+(\[[^\[\]0-9]+[^\[\]]*\])+$/.test(name)){
               //name[prop][prop]... ,对象
               var props = name.match(/([^\[\]]+)/g);
               var subObj = props.shift();
               var tmp = jsonObj[subObj];
               props.forEach(function(value, index, array){
                   tmp = tmp[value];
               });
               //查找所有同名的元素,将jsonObj对应的值依次赋予表单元素
               that.find('[name="' + name + '"]').each(function(idx, el){
                   if(tmp){
                       setElement.call(this, tmp);
                   }
               });
           }else if(/^[^\[\]]+$/.test(name)){
               //name
               //查找所有同名的元素,将jsonObj对应的值依次赋予表单元素
               that.find('[name="' + name + '"]').each(function(idx, el){
                   if( typeof (jsonObj[name]) != 'undefined'){
                       setElement.call(this, jsonObj[name]);
                   }
               });
           }else{
               //not support
           }
       });
   }
});

var Lu=(function($, l, undefined){
	l.post = function(url, data, success, options,error) {
		if(url){
			if(url.substring(0,1)=="/"){
				url=".."+url;
			}
		}
		var op = {type : 'POST',url : url,data : data,
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			cache : false,
			processData : false,
			success : function(response) {
				if (response.success) {
					if ($.isFunction(success))
						success(response);
				} else {
					// 超时时自动
					if (response.statusCode == '301') {
						window.localStorage.clear();
						$.cookie("username", null);
						$.cookie("logined", null);
						window.location.reload();
					} else {
						alertMsg.error(response.message);
					}
					if ($.isFunction(error)){
						error(response);
					}
						
				}
			},
			error : function(jqXHR, errStatus, errThrown) {
				try {
					var response = JSON.parse(jqXHR.responseText);
					alertMsg.error(response.message + ';请求数据:' + data);
					if ($.isFunction(error)){
						error(response);
					}
				} catch (e) {
					var errMsg = '请将以下错误信息发送给系统管理员:' + '错误类型:' + errStatus
							+ ';错误描述:' + errThrown + ';状态码:' + jqXHR.status
							+ ';错误返回:' + jqXHR.responseText + ';请求URI:' + url
							+ ';请求数据:' + data;
					alertMsg.error(errMsg);
					if ($.isFunction(error)){
						error();
					}
				}
				
				
			}
		};
		$.extend(op, options);
		$.ajax(op);
	};
	l.getimgcodeurl=function(){
		var src="../pb/img-code.lu?v="+Math.random();
		return src;
	}
})(jQuery, window.lu = window.lu || {});

