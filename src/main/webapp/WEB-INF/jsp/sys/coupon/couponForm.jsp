<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
<title>优惠券管理</title>
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
		
		function repoFormatResult (repo) {
		    var markup = '<div class="clearfix">' ;
		    markup += '<div>' + repo.carNo + '</div>';
		    markup += '</div>';
		    return markup;
		  }

		  function repoFormatSelection (repo) {
		    return repo.carNo;
		  }
		$(".js-data-ajax").select2({
		    placeholder: "查询车牌",
		    minimumInputLength: 1,
		    multiple: true,
		    ajax: {
		    	  url: "${ctx}/sponsorLicense/licenses",
		        dataType: 'json',
		        quietMillis: 250,
		        data: function (term, page) { // page is the one-based page number tracked by Select2
		            return {
		                q: term, //search term
		                page: page // page number
		            };
		        },
		        results: function (data, page) {
		            var more = (page * 30) < data.total_count; // whether or not there are more results available
		            // notice we return the value of more so Select2 knows if more results can be loaded
		            return { results: data.data, more: more };
		        }
		    },
		    formatResult: repoFormatResult, // omitted for brevity, see the source of this page
		    formatSelection: repoFormatSelection, // omitted for brevity, see the source of this page
		    dropdownCssClass: "bigdrop", // apply css that makes the dropdown taller
		    escapeMarkup: function (m) { return m; } // we do not want to escape markup since we are displaying html in results
		});
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/coupon/">优惠券列表</a></li>
		<li class="active"><a href="${ctx}/coupon/form?id=${coupon.id}">优惠券${not empty coupon.id?'修改':'添加'}</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="coupon" action="${ctx}/coupon/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<tags:message content="${message}" />
		<div class="control-group">
			<label class="control-label">绑定车牌号：</label>
			<div class="controls">
				<form:input path="carNos" htmlEscape="false" maxlength="255" class="js-data-ajax input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分类：</label>
			<div class="controls">
				<form:select multiple="true" path="licenseCategories" items="${licenseCategories}" itemLabel="name" itemValue="id" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">优惠金额：</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="note" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">来源：</label>
			<div class="controls">
				<form:input path="origin" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">抵用时长：</label>
			<div class="controls">
				<form:input path="time" htmlEscape="false" maxlength="20" class="input-xlarge  digits" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠券类型：</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="255" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${coupon.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp; <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>