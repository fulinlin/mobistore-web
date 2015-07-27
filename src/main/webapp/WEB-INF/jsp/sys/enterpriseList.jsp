<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>企业管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/enterprise").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/enterprise/">企业列表</a></li>
		<li><a href="${ctx}/sys/enterprise/form">企业添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="enterprise" action="${ctx}/sys/enterprise" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.limit}"/>
		<div>
            <label>名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>
            &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
        </div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>邮箱</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.items}" var="enterprise">
			<tr>
				<td>
				<a href="${ctx}/sys/enterprise/form?id=${enterprise.id}">
					${enterprise.name}
				</a>
				</td>
				<td>
					${enterprise.user.email}
				</td>
				<td>
					<c:if test="${enterprise.user.isDisable}">禁用</c:if><c:if test="${enterprise.user.isDisable==false}">启用</c:if>
				</td>
				<td>
    				<a href="${ctx}/sys/enterprise/form?id=${enterprise.id}">修改</a>
    				<a href="${ctx}/sys/loginaccount/form?userId=${user.id}">登陆账号维护</a>
					<a href="${ctx}/sys/enterprise/delete?id=${enterprise.id}" onclick="return confirmx('确认要删除该企业吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>