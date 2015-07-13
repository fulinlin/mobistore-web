<%@page import="com.wolai.platform.constant.Constant"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path;
    request.setAttribute("basePath", basePath);
%>
<html>
  <head>
    <title>用户登录</title>
  </head>
  <body>
  <script type="text/javascript">
  <%
	HttpSession this_session = request.getSession(false);
	if(this_session!=null && this_session.getAttribute(Constant.SESSION_LOGINFO)!=null){
	%>
		top.window.location.replace("${basePath}/app/index");
	<%
		}else{
	%>
		top.window.location.replace("${basePath}/login/prepareLogin");
	<%
		}
	%>
  </script>
  </body>
</html>
