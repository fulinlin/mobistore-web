<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>企业登陆账号管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginName").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/loginaccount/form?id=${loginAccount.id}">登陆账号${not empty loginAccount.id?'修改':'添加'}</a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="loginAccount" action="${ctx}/sys/loginaccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="userId"/>
		<form:hidden path="enterpriseId"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="oldLoginName">email:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${loginAccount.email}">
				<form:input path="email" id="loginName" htmlEscape="false" maxlength="50" class="required email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="newPassword">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="8" class="${empty loginAccount.id?'required':''}"/>
				<c:if test="${not empty loginAccount.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="confirmNewPassword">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="8" equalTo="#newPassword"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>