<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>用户信息</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<form:form id="inputForm" action="#" class="form-horizontal">
		<div class="control-group">
			<label class="control-label" for="name">登陆名:</label>
			<div class="controls">
				${logininfo.loginAccount.email} 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="oldLoginName">手机号:</label>
			<div class="controls">
				${logininfo.user.mobile} 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">名称:</label>
			<div class="controls">
				${logininfo.user.name} 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">企业名称:</label>
			<div class="controls">
				${logininfo.enterprice.name} 
			</div>
		</div>
	</form:form>
</body>
</html>