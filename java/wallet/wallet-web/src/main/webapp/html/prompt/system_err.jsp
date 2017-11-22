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

    ${ex.getMessage()==null?" ☹☹☹系统罢工了,请联系管理员":ex.getMessage()}
</div>
</body>
</html>