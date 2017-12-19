/**
 * 消息推送js
 */


/**发送消息**/
function sendMessage(domain ,content){
	$.ajax({
		url : domain+"/eat/79B4DE7C/sendMessage.do",
		data : {content : content},
		dataType : "json",
		async : false,
		success : function(data){
			
		}
	});
}




/**发送消息**/
function sendMessage_arrival(domain,content){
	$.ajax({
		url : domain+"/arrival/79B4DE7C/sendMessage.do",
		data : {content : content},
		dataType : "json",
		async : false,
		success : function(data){
			
		}
	});
}

/**推送消息到ERP餐饮分屏广告页面**/
function sendMessage_adv(domain,socketObj,orderNo){
	$.ajax({
		url : domain+"/arrival/79B4DE7C/sendMessage_adv.do",
		data : {socketObj : socketObj,orderNo : orderNo},
		dataType : "json",
		async : false,
		success : function(data){
			
		}
	});
}


function sendMessage_simple(domain,socketObj){
	$.ajax({
		url : domain+"/orderMeal/79B4DE7C/sendMessage.do",
		data : {socketObj : socketObj},
		dataType : "json",
		async : false,
		success : function(data){
			
		}
	});
}
