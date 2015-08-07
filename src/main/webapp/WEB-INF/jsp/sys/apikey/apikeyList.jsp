<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>接入管理</title>
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
		<li class="active"><a href="${ctx}/apikey/">接入key列表</a></li>
		<li><a href="${ctx}/apikey/form">接入key添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="apikey" action="${ctx}/apikey/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.limit}"/>
		<div>
			<label>停车场名称：</label>
			<input type="text" name="name" value="${name}" maxlength="255" class="input-medium"/>
			<label>token：</label>
				<form:input path="token" htmlEscape="false" maxlength="255" class="input-medium"/>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>停车场名称</th>
				<th>token</th>
				<th>回调地址</th>
				<th>回调端口</th>
				<th>根路径</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.items}" var="apikey" varStatus="status">
			<tr class="${apikey.isDisable?'warning':''}">>
				<td>${status.count}</td>
				<td><a href="${ctx}/apikey/form?id=${apikey.id}">${apikey.parkingLot.name}</a></td>
				<td>
					${apikey.token}
				</td>
				<td>
					${apikey.url}
				</td>
				<td>
					${apikey.port}
				</td>
				<td>
					${apikey.rootPath}
				</td>
				<td>
    				<a href="${ctx}/apikey/form?id=${apikey.id}">修改</a>
    				<c:if test="${!apikey.isDisable }">
    					<a href="${ctx}/apikey/disable?id=${apikey.id}" onclick="return confirmx('确认要删除该接入key吗？', this.href)">禁用</a>
    				</c:if>
    				<c:if test="${apikey.isDisable }">
    					<a href="${ctx}/apikey/eisable?id=${apikey.id}" onclick="return confirmx('确认要删除该接入key吗？', this.href)">启用</a>
    				</c:if>
					<a href="${ctx}/apikey/delete?id=${apikey.id}" onclick="return confirmx('确认要删除该接入key吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>