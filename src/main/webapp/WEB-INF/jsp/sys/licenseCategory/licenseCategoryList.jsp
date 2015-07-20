<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
<title>车牌分类管理</title>
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
		<li class="active"><a href="${ctx}/licenseCategory/">车牌分类列表</a></li>
		<li><a href="${ctx}/licenseCategory/form">车牌分类添加</a></li>
	</ul>
	<tags:message content="${message}" />
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.items}" var="licenseCategory">
				<tr>
					<td><a href="${ctx}/licenseCategory/form?id=${licenseCategory.id}"> ${licenseCategory.name} </a></td>
					<td><a href="${ctx}/licenseCategory/form?id=${licenseCategory.id}">修改</a> <a href="${ctx}/licenseCategory/delete?id=${licenseCategory.id}"
						onclick="return confirmx('确认要删除该车牌分类吗？', this.href)">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>