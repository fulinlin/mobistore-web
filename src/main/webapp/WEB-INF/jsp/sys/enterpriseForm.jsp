<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
<title>企业管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
			submitHandler : function(form) {
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
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
		<li><a href="${ctx}/sys/enterprise/">企业列表</a></li>
		<li class="active"><a href="${ctx}/sys/enterprise/form?id=${enterprise.id}">企业${not empty enterprise.id?'修改':'添加'}</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="enterprise" action="${ctx}/sys/enterprise/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">名字：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构代码：</label>
			<div class="controls">
				<form:input path="organizationCode" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否管理员：</label>
			<div class="controls">
				<form:checkbox path="isSupplier" htmlEscape="false" maxlength="1"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp; <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>