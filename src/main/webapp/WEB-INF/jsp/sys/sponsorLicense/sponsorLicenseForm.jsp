<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
<title>赞助车牌管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		jQuery.validator.addMethod("isCarNo", function(value, element) {
			var length = value.length;
			var carNo = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
			return this.optional(element) || (length == 7 && carNo.test(value));
		}, "请正确填写车牌");
		
		$("#inputForm").validate({
			rules : {
				carNo : {
					remote : "${ctx}/sponsorLicense/checkCarNo?id=" + $('#id').val(),
					isCarNo : true
				}
			},
			messages : {
				carNo : {
					remote : "车牌已存在"
				}
			},
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
		<li><a href="${ctx}/sponsorLicense/">赞助车牌列表</a></li>
		<li class="active"><a href="${ctx}/sponsorLicense/form?id=${sponsorLicense.id}">赞助车牌${not empty sponsorLicense.id?'修改':'添加'}</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="sponsorLicense" action="${ctx}/sponsorLicense/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<tags:message content="${message}" />
		<div class="control-group">
			<label class="control-label">车牌：</label>
			<div class="controls">
				<form:input path="carNo" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">分类：</label>
			<div class="controls">
				<form:select path="licenseCategoryId" items="${licenseCategories}" itemLabel="name" itemValue="id" htmlEscape="false" maxlength="255" class="input-xlarge " />
				<a href="${ctx}/licenseCategory/form">添加车牌分类 </a>
			</div>
		</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp; <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>