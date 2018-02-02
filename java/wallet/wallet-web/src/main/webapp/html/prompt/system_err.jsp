<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta id="meta" name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="HandheldFriendly" content="true">
    <meta name="MobileOptimized" content="320">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <meta name="browsermode" content="application">
    <meta name="x5-page-mode" content="app">
    <meta name="msapplication-tap-highlight" content="no">
    <title>系统异常</title>
    <script type="text/javascript">
        $(function () {
            var height = $(window).height();
            $('.bg').css('height', "900px");
        })
    </script>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        body, html {
            width: 100%;
            height: 100%;
            position: relative;
            overflow: hidden;
        }

        .bg {
            background: #b0e2fb;

        }

      
    </style>
</head>
<body>
<div class="bg">

    ${msg==null?" ☹☹☹系统罢工了,请联系管理员":msg}
</div>
</body>
</html>