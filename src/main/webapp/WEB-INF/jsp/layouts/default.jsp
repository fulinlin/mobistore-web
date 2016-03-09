<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
	<head>
		<title><sitemesh:title/></title>
		<%@include file="/WEB-INF/jsp/include/head-default.jsp" %>
		<sitemesh:head/>
	</head>
	<body>
		<sitemesh:body/>
	</body>
</html>
