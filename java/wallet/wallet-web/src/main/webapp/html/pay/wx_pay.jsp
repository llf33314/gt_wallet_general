<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html class="no-js" style="height: 100%">
<head>
    <title>支付页面</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!--[if IE 8]>
    <meta http-equiv="X-UA-Compatible" content="IE=8">
    <![endif]-->
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>

    <base href="<%=basePath%>"/>
    <link rel="stylesheet" type="text/css" href="css/common.css?<%= System.currentTimeMillis()%>"/>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../../js/sendMessage.js"></script>
</head>
<body style="background-color: #ededed; height: 100%">
<script type="text/javascript">

function onBridgeReady(){
	   WeixinJSBridge.invoke(
	       'getBrandWCPayRequest', {
	           "appId":"${data.appId}",     //公众号名称，由商户传入     
	           "timeStamp":"${data.timeStamp}",         //时间戳，自1970年以来的秒数     
	           "nonceStr":"${data.nonceStr}", //随机串     
	           "package":"${data.package}",     
	           "signType":"MD5",         //微信签名方式：     
	           "paySign":"${data.paySign}" //微信签名 
	       },
	       function(res){     
	           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
	        	   
	        	   if ("${payOrder.sendUrl}" !="") {//支付成功推送消息
	        		   sendMessage("${payOrder.busId}","${payOrder.sendUrl}");
	        		   
                     }
	        	   
	           // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
	        	    if ("${payOrder.returnUrl}" !="") {//支付成功跳转回调地址
                        	location.href ="${payOrder.returnUrl}";
                        }else{//不需要回调操作
                        	//关闭微信浏览器
                            WeixinJSBridge.call('closeWindow');
                        }
	           } else if (res.errMsg == "chooseWXPay:cancel") {
                   //关闭微信浏览器
                   WeixinJSBridge.call('closeWindow');
               }   
	       }
	   ); 
	}
	if (typeof WeixinJSBridge == "undefined"){
	   if( document.addEventListener ){
	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	   }else if (document.attachEvent){
	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	   }
	}else{
	   onBridgeReady();
	}

</script>
</body>
</html>