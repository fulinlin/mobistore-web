<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
<title>商圈管理管理</title>
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
		<li class="active"><a href="${ctx}/zone/">商圈列表</a></li>
		<li><a href="${ctx}/zone/form">商圈添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="zone" action="${ctx}/zone/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.limit}" />
		<div>
			<label>名称：</label>
			<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium" />
			<label>区域：</label>
			<tags:treeselect id="area" name="areaId" value="${zone.area.id}" labelName="area.name" labelValue="${zone.area.name}" title="区域" url="/area/treeData" cssClass="input-small" allowClear="true"
				notAllowSelectParent="true" />
			<label>代码：</label>
			<form:input path="code" htmlEscape="false" maxlength="255" class="input-medium" />

			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
		</div>
	</form:form>
	<tags:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>代码</th>
				<th>区域</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.items}" var="zone">
				<tr>
					<td><a href="${ctx}/zone/form?id=${zone.id}">${zone.name}</a></td>
					<td>${zone.code}</td>
					<td>${zone.area.name}</td>
					<td><a href="${ctx}/zone/form?id=${zone.id}">修改</a> <a href="${ctx}/zone/delete?id=${zone.id}" onclick="return confirmx('确认要删除该商圈管理吗？', this.href)">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>