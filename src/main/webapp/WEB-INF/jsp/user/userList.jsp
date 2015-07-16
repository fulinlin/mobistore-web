<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/jsp/include/dialog.jsp" %>
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			// 表格排序
			tableSort({callBack : page});
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/user/list").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/user/list">用户列表</a></li>
		<li><a href="${ctx}/user/edit">用户添加</a></li>
	</ul>
	
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/user/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.limit}"/>
		<%-- <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/> --%>
		<div>
			<label>手机号：</label><form:input path="mobile" htmlEscape="false" maxlength="50" class="input-small"/>
			<label>名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>手机号</th><th>名称</th><th>邮箱</th><th>电话</th><th>类型</th><th>状态</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.items}" var="user">
			<tr>
				<td>${user.mobile}</td>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td>${user.tel}</td>
				<td><c:if test='${user.customerType == "INDIVIDUAL" }'>个人</c:if><c:if test='${user.customerType == "ENTERPRISE" }'>企业</c:if></td>
				<td><c:if test="${user.isDisable}">禁用</c:if><c:if test="${user.isDisable==false}">启用</c:if></td>
				<td>
    				 <a href="${ctx}/user/edit?id=${user.id}">修改</a>
					<%--<a href="${ctx}/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a> --%>
					<a href="${ctx}/user/resetPwd?id=${user.id}" onclick="return confirmx('确认要重置该用户密码吗？', this.href)">重置密码</a>
					<c:if test="${user.isDisable==false}"><a href="${ctx}/user/disable?id=${user.id}" onclick="return confirmx('确认要禁用该用户吗？', this.href)">禁用</a></c:if>
					<c:if test="${user.isDisable}"><a href="${ctx}/user/enable?id=${user.id}" onclick="return confirmx('确认要启用该用户吗？', this.href)">启用</a></c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>