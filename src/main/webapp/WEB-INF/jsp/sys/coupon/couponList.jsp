<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
<title>优惠券管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/coupon/">优惠券列表</a></li>
		<li><a href="${ctx}/coupon/form">优惠券添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="coupon" action="${ctx}/coupon/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.limit}" />
		<div>
			<label>绑定车牌号：</label>
			<form:input path="carNo" htmlEscape="false" maxlength="255" class="input-medium" />
			<label>来源：</label>
			<form:input path="origin" htmlEscape="false" maxlength="255" class="input-medium" />
			<label>是否使用：</label>
			<form:checkbox path="isUsed" htmlEscape="false" maxlength="1" class="input-medium" />
			<br> <br> 
			<label>优惠券类型：</label>
			<form:input path="type" htmlEscape="false" maxlength="255" class="input-medium" />
			<label>开始时间：</label> <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${coupon.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" /> <label>结束时间：</label> <input
				name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" value="<fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" /> <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
		</div>
	</form:form>
	<tags:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>绑定车牌号</th>
				<th>结束时间</th>
				<th>是否使用</th>
				<th>优惠金额</th>
				<th>来源</th>
				<th>开始时间</th>
				<th>抵用时长</th>
				<th>优惠券类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.items}" var="coupon">
				<tr>
					<td><a href="${ctx}/coupon/form?id=${coupon.id}"> ${coupon.carNo} </a></td>
					<td><fmt:formatDate value="${coupon.endDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${coupon.isUsed}</td>
					<td>${coupon.money}</td>
					<td>${coupon.origin}</td>
					<td><fmt:formatDate value="${coupon.startDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${coupon.time}</td>
					<td>${coupon.type}</td>
					<td><a href="${ctx}/coupon/form?id=${coupon.id}">修改</a> <a href="${ctx}/coupon/delete?id=${coupon.id}" onclick="return confirmx('确认要删除该优惠券吗？', this.href)">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>