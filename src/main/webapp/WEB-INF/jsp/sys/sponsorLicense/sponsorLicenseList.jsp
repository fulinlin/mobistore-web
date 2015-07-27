<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>赞助车牌管理</title>
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
		<li class="active"><a href="${ctx}/sponsorLicense/">赞助车牌列表</a></li>
		<li><a href="${ctx}/sponsorLicense/form">赞助车牌添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="sponsorLicense" action="${ctx}/sponsorLicense/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.limit}"/>
		<div>
			<label>车牌：</label>
				<form:input path="carNo" htmlEscape="false" maxlength="255" class="input-medium"/>
				 <label class="control-label">分类：</label>
                <form:select path="licenseCategoryId"  htmlEscape="false" maxlength="255" class="input-xlarge ">
	                <form:option value="">请选择</form:option>
	                <form:options items="${licenseCategories}"   itemLabel="name" itemValue="id"></form:options>
                </form:select>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>车牌</th>
				<th>分类</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.items}" var="sponsorLicense">
			<tr>
				<td><a href="${ctx}/sponsorLicense/form?id=${sponsorLicense.id}">
					${sponsorLicense.carNo}
				</a></td>
				<td>${sponsorLicense.licenseCategory.name}</td>
				<td>
    				<a href="${ctx}/sponsorLicense/form?id=${sponsorLicense.id}">修改</a>
					<a href="${ctx}/sponsorLicense/delete?id=${sponsorLicense.id}" onclick="return confirmx('确认要删除该赞助车牌吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>