<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>推送消息管理</title>
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
		<li class="active"><a href="${ctx}/sysMessage/">推送消息列表</a></li>
		<li><a href="${ctx}/sysMessage/form">推送消息添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="sysMessage" action="${ctx}/sysMessage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.limit}"/>
		<div>
			<label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			<label>是否已推送：</label>
				<form:checkbox path="published" htmlEscape="false" maxlength="1" class="input-medium"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>内容</th>
				<th>创建时间</th>
				<th>标题</th>
				<th>是否已推送</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.items}" var="sysMessage">
			<tr>
				<td><a href="${ctx}/sysMessage/form?id=${sysMessage.id}">
					${fns:rabbr(sysMessage.content,50)}
				</a></td>
				<td>
					<fmt:formatDate value="${sysMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:rabbr(sysMessage.title,20)}
				</td>
				<td>
					${sysMessage.published == 'true' ? '是':'否'   }
				</td>
				<td>
    				<a href="${ctx}/sysMessage/form?id=${sysMessage.id}">修改</a>
					<a href="${ctx}/sysMessage/delete?id=${sysMessage.id}" onclick="return confirmx('确认要删除该推送消息吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>