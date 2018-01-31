/**
 * 消息推送js
 */


/**发送消息**/
function sendMessage(busId,url){
	var sendSocket={busId : busId,sendUrl:url};
	$.ajax({
		url : "/wcommon/sendMessage",
		data : JSON.stringify(sendSocket),
		dataType : "json",
		async : false,
		success : function(data){
			
		}
	});
}

