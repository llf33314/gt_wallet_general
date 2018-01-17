<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html class="no-js" style="height: 100%">
<head>
    <title>支付宝支付页面</title>
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
     <link rel="stylesheet" type="text/css" href="css/common.css?<%= System.currentTimeMillis()%>"/>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../../js/sendMessage.js"></script>
</head>
<body style="background-color: #ededed; height: 100%">
<%-- <div class="con-sure">
    <p style="text-align: center; font-weight: bolder; font-size: 1.35em; padding-top: 5%;padding-bottom: 2%">${payOrder.desc } ${data.payinfo}</p>
    <p style="text-align: center; font-weight: bolder; font-size: 1.8em"><b style="font-size: 1.8em">￥</b><span
            style="font-weight: bolder; font-size: 1.8em"><fmt:formatNumber value="${payOrder.amount}"
                                                                            pattern="##.##"
                                                                            minFractionDigits="2"></fmt:formatNumber></span>
    </p>
    <div style="background-color: #fff; color:#787878; padding: 4% 0px; margin: 10% 0px ">
        <p style="display: inline-table; font-size: 1.1em; padding-left: 2%">收款方:${data.payinfo}</p>
        <p style="text-align: right; display: inline-table; float: right; font-size: 1.1em; color: #000;padding-right: 2%">多粉科技有限公司</p>
    </div>
    <a id="pay" class="con-pay"
       style="font-size:1.25em; display:block;  margin:0 auto; width:90%; text-align:center;border-radius:5px;padding:10px 0px; background-color: #46c017; color:#fff;">立即支付</a>
</div> --%>
<!-- <script type="text/javascript">
    $(function () {
        $("#pay").click(function () {
        	AlipayJSBridge.call("tradePay",{
			  	tradeNO: "${data.payinfo}"
			    }, function(result){
			    	alert(result.resultCode);
			    	alert(result.callbackUrl);
			    	alert(result.memo);
			    	alert(result.result);
		    	    if ("9000" == data.resultCode) {//支付成功
				    	if ("${payOrder.sendUrl}" !="") {//支付成功推送消息
	                    	 sendMessage("${homeDomain}","${payOrder.sendUrl}");
	                    }
		        	    if ("${payOrder.returnUrl}" !="") {//支付成功跳转回调地址
	                       	location.href ="${payOrder.returnUrl}";
	                       }else{//不需要回调操作
	                       	//关闭支付宝浏览器
	                    	  AlipayJSBridge.call('closeWebview');
	                       }
				    	
	                 }else{
	                	//关闭支付宝浏览器
                   	 	 AlipayJSBridge.call('closeWebview');
	                 }
			    	 
			   });
        });
    });

</script> -->
<p id="result">result: </p>
<script type="application/javascript">
    // 调试时可以通过在页面定义一个元素，打印信息，使用alert方法不够优雅
    function log(obj) {
        $("#result").append(obj).append(" ").append("<br />");
    }

    $(document).ready(function(){
        // 页面载入完成后即唤起收银台
        // 此处${tradeNO}为模板语言语法，实际调用样例类似为tradePpay("2016072621001004200000000752")
        tradePay("${data.payInfo}"); 

        // 点击payButton按钮后唤起收银台
        $("#payButton").click(function() {
           tradePay("${tradeNO}");
        });

        // 通过jsapi关闭当前窗口，仅供参考，更多jsapi请访问
        // /aod/54/104510
        $("#closeButton").click(function() {
           AlipayJSBridge.call('closeWebview');
        });
     });

    // 由于js的载入是异步的，所以可以通过该方法，当AlipayJSBridgeReady事件发生后，再执行callback方法
    function ready(callback) {
         if (window.AlipayJSBridge) {
             callback && callback();
         } else {
             document.addEventListener('AlipayJSBridgeReady', callback, false);
         }
    }

    function tradePay(tradeNO) {
        ready(function(){
             // 通过传入交易号唤起快捷调用方式(注意tradeNO大小写严格)
             AlipayJSBridge.call("tradePay", {
                  tradeNO: "${data.payInfo}"
             }, function (data) {
                 log(JSON.stringify(data));
                 if ("9000" == data.resultCode) {
                     log("支付成功");
                 }
             });
        });
    }
</script>
</body>
</html>