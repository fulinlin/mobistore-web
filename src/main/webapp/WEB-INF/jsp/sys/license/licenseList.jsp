<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>车牌管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/license/">车牌列表</a></li>
		<li><a href="${ctx}/license/form">车牌添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="license" action="${ctx}/license/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.limit}"/>
		<div>
			<label>品牌：</label>
				<form:input path="brand" htmlEscape="false" maxlength="255" class="input-medium"/>
			<label>车牌号：</label>
				<form:input path="carNo" htmlEscape="false" maxlength="255" class="input-medium"/>
			<label>车架号：</label>
				<form:input path="frameNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>所属用户</th>
				<th>品牌</th>
				<th>车牌号</th>
				<th>车架号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.items}" var="license">
			<tr>
				<td>${license.user.mobile}</td>
				<td><a href="${ctx}/license/form?id=${license.id}">
					${license.brand}
				</a></td>
				<td>
					${license.carNo}
				</td>
				<td>
					${license.frameNumber}
				</td>
				<td>
    				<a href="${ctx}/license/form?id=${license.id}">修改</a>
					<a href="${ctx}/license/delete?id=${license.id}" onclick="return confirmx('确认要删除该车牌吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>