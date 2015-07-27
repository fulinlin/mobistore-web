<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
<title>企业管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
			rules: {
				name: {remote: "${ctx}/sys/enterprise/checkName?eId=${enterprise.id}"},
				user.email: {remote: "sys/loginaccount/checkEmail"}
			},
			messages: {
				name: {remote: "企业名称已存在"}
			},
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
		<li class="active"><a href="${ctx}/sys/enterprise/form?id=${enterprise.id}">企业${not empty enterprise.id?'修改':'添加'}</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="enterprise" action="${ctx}/sys/enterprise/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">名字：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required" />
			</div>
		</div>
		<c:if test="${empty enterprise.id }">
			<div class="control-group">
				<label class="control-label">邮箱：</label>
				<div class="controls">
					<form:input path="user.email" htmlEscape="false" maxlength="255" class="input-xlarge required" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="newPassword">密码:</label>
				<div class="controls">
					<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="6" class="${empty enterprise.user.id?'required':''}"/>
					<c:if test="${not empty enterprise.user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="confirmNewPassword">确认密码:</label>
				<div class="controls">
					<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="6" equalTo="#newPassword"/>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构代码：</label>
			<div class="controls">
				<form:input path="organizationCode" htmlEscape="false" maxlength="255" class="input-xlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
            <label class="control-label">余额：</label>
            <div class="controls">
                <form:input path="balance" htmlEscape="false" maxlength="255" class="input-xlarge digits" /><span class="help-inline">分钟</span>
            </div>
        </div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp; <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>