/*
 -------------- 函数检索 --------------
校验是否为空                                                              required                                             
校验最小长度                                                              minlength
校验最大长度                                                              maxlength
校验最小值                                                                   min
校验最大值                                                                   max
校验Email                          email
校验URL                            url
校验数字                                                                         number
校验文件的后缀                                                          accept
校验整数                                                                         digits
校验密码是否一样                                                     equalTo
校验整型是否为非负数:               isNotNegativeInteger 
校验字符长度介于x与y之间                                rangelength
校验值介于x与y之间                                               range
校验字符串是否为浮点型:             checkIsDouble 
校验字符串是否为日期型:             checkIsValidDate 
校验两个日期的先后:                 checkDateTo 
校验字符串是否为中文:               checkIsChinese 
效验邮政编码                                                               isPostCode
效验电话号码                                                               isTel
效验手机号码                                                               isMobile
效验是否字母						   isLetters
效验身份证号码					   isIdCard
校验是否是数字					   isNum
校验Email       					   isEmail
校验是否字符串是否有特殊字符                        checkIsSpecialCharacter
 -------------- 函数检索 --------------
*/




jQuery.extend(jQuery.validator.methods, {


	// 校验整型是否为非负数  提示信息：您输入的信息不能为负数。
	isNotNegativeInteger: function(value, element, param) {
	       return this.optional(element) || parseInt(value,10)>0;
		},
    //校验字符串是否为浮点型  提示信息：您输入的信息不是合法的浮点数。
    checkIsDouble:function(value, element, param) {
	       return this.optional(element) || /^(\-?)(\d+)(.{1})(\d+)$/g.test(value);
	},
    //校验字符串是否为日期型 提示信息：请您输入是标准的日期格式：如yyyy-MM-dd
    checkIsValidDate:function(value, element, param){
		var pattern = /^((\d{4})|(\d{2}))-(\d{1,2})-(\d{1,2})$/g;
	    var arrDate = value.split("-");
	    if(parseInt(arrDate[0],10) < 100)
	        arrDate[0] = 2000 + parseInt(arrDate[0],10) + "";
	    var date =  new Date(arrDate[0],(parseInt(arrDate[1],10) -1)+"",arrDate[2]);
		return this.optional(element) || (pattern.test(value)&&date.getYear() == arrDate[0]&& date.getMonth() == (parseInt(arrDate[1],10) -1)+""&& date.getDate() == arrDate[2])
	},
    //校验两个日期的先后 提示信息：起始日期不能晚于结束日期
	checkDateTo:function(value, element, param){
		var arr1 = value.split("-");
	    var arr2 = $(param).val().split("-");
	    var date1 = new Date(arr1[0],parseInt(arr1[1].replace(/^0/,""),10) - 1,arr1[2]);
	    var date2 = new Date(arr2[0],parseInt(arr2[1].replace(/^0/,""),10) - 1,arr2[2]);
	    if(arr1[1].length == 1)
	        arr1[1] = "0" + arr1[1];
	    if(arr1[2].length == 1)
	        arr1[2] = "0" + arr1[2];
	    if(arr2[1].length == 1)
	        arr2[1] = "0" + arr2[1];
	    if(arr2[2].length == 1)
	        arr2[2]="0" + arr2[2];
	    var d1 = arr1[0] + arr1[1] + arr1[2];
	    var d2 = arr2[0] + arr2[1] + arr2[2];
		
		
		return this.optional(element) || parseInt(d1,10)>parseInt(d2,10)
	},
	//校验字符串是否为中文  提示信息：请您输入中文。
	checkIsChinese:function(value, element, param){
		return this.optional(element) || /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0])*$/gi.test(value);
	},
	//校验是否字符串是否有特殊字符  提示信息：请您输入中文,字母,数字。
	checkIsSpecialCharacter:function(value, element, param){
		return this.optional(element) || /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0]|[a-zA-Z0-9])*$/gi.test(value);
	},
	//checkIsEnglish:function(value, element, param){
	//	return this.optional(element) || /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0])*$/gi.test(value);
	//},
	//校验邮政编码 提示信息：请您输入标准的邮编。
	
	isPostCode:function(value, element, param) {
	       return this.optional(element) || /^[0-9]{6}$/.test(value);
	},
	//校验电话号码及传真 提示信息：请您输入正确的电话号码:电话号码格式为国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位）
	isTel:function(value, element, param) {
	       //return this.optional(element) || /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(value);
		 var reg0 = /^[0-9 () -]+$/;
	     return this.optional(element) ||reg0.test(value);
	},
	//校验手机号码    提示信息：请您输入标准的手机号码。
	isMobile:function(value, element, param) {
		 /*var reg0 = /^13\d{5,9}$/;
         var reg1 = /^153\d{4,8}$/;
         var reg2 = /^159\d{4,8}$/;
         return this.optional(element) ||reg0.test(value)||reg1.test(value)||reg2.test(value);*/
         var reg0 = /^\d{11}$/;
	     return this.optional(element) ||reg0.test(value);
	},
	//校验是否字母。
	isLetters:function(value, element, param) {
		 var reg0 = /^[a-zA-Z0-9_&.()@#$%*-, ]+$/;;

	     return this.optional(element) ||reg0.test(value);
	} ,
	//校验身份证号码	提示信息：请您输入正确的身份证号码
	isIdCard:function(value,element,param){
		//var reg0 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
		//var reg1 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
		//var reg0 = /^\d{6}((0[48]|[2468][048]|[13579][26])0229|\d\d(0[13578]|10|12)(3[01]|[12]\d|0[1-9])|(0[469]|11)(30|[12]\d|0[1-9])|(02)(2[0-8]|1\d|0[1-9]))\d{3}$/;
		//var reg1 = /^\d{6}((2000|(19|21)(0[48]|[2468][048]|[13579][26]))0229|(((20|19)\d\d)|2100)(0[13578]|10|12)(3[01]|[12]\d|0[1-9])|(0[469]|11)(30|[12]\d|0[1-9])|(02)(2[0-8]|1\d|0[1-9]))\d{3}[\dX]$/;
		var reg0 =/^\d{15}$|^\d{18}$|^\d{17}[xX]$/;

		return this.optional(element) || reg0.test(value) ;
	},
	//校验是否是数字
	isNum:function(value,element,param){
		var reg0 = /^[0-9]+$/;
		return this.optional(element) || reg0.test(value);
	},
	isFloat:function(value,element,param){
		var reg0 = /^[0-9]+.?[0-9]+$|^[0-9]+$/;
		return this.optional(element) || reg0.test(value);
	},
	//校验Email
	isEmail:function(value,element,param){
		var reg0 = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		return this.optional(element) || reg0.test(value);
	},
	isPhone:function(value,element,param){
		var reg0 = /^[0-9 () -]+$|^\d{11}$/; 
		return this.optional(element) || reg0.test(value);
	}

});