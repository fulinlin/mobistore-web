<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>停车场管理</title>
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
		<li class="active"><a href="${ctx}/parkinglot/">停车场列表</a></li>
		<li><a href="${ctx}/parkinglot/edit">停车场添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="parkingLot" action="${ctx}/parkinglot/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.limit}"/>
		<div>
			<label>城市：</label>
				<form:input path="city" htmlEscape="false" maxlength="255" class="input-medium"/>
			<label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>城市</th>
				<th>名称</th>
				<th>地址</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.items}" var="parkingLot">
			<tr>
				<td><a href="${ctx}/parkingLot/form?id=${parkingLot.id}">
					${parkingLot.city}
				</a></td>
				<td>
					${parkingLot.name}
				</td>
				<td>
					${parkingLot.address}
				</td>
				<td>
    				<a href="${ctx}/parkinglot/edit?id=${parkingLot.id}">修改</a>
					<a href="${ctx}/parkinglot/delete?id=${parkingLot.id}" onclick="return confirmx('确认要删除该停车场吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>