/**
 * 消息推送js
 */


/**发送消息**/
function sendMessage(busId,url){
	var sendSocket={busId :busId,sendUrl:url};
	$.ajax({
		url : "/wcommon/79B4DE7C/sendMessage",
		data :sendSocket,
		dataType : "json",
		type:"POST",
		async : false,
		success : function(data){
			
		}
	});
}

