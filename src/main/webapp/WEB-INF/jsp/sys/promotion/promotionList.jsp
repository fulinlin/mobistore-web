<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>优惠活动管理</title>
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
		<li class="active"><a href="${ctx}/promotion/">优惠活动列表</a></li>
		<li><a href="${ctx}/promotion/form">优惠活动添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="promotion" action="${ctx}/promotion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.limit}"/>
		<div>
			<label>标题：</label>
			<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>类型</th>
				<th>限制</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>图片地址</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.items}" var="promotion">
			<tr>
				<td>
					<a href="${ctx}/promotion/form?id=${promotion.id}">
					${promotion.title}
					</a>
				</td>
				<td>
					${promotion.code.textLable}
				</td>
				<td>
					${promotion.limitType.textLable}
				</td>
				<td>
					<fmt:formatDate value="${promotion.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${promotion.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				<td>
					${promotion.picPath}
				</td>
				<td>
    				<a href="${ctx}/promotion/form?id=${promotion.id}">修改</a>
    				<a href="${ctx}/sys/exchangepan/form?pid=${promotion.id}">兑换信息维护</a>
    				<c:if test='${promotion.limitType!="ALL"}'>
    					<a href="#">活动限制维护</a>
    				</c:if>
					<a href="${ctx}/promotion/delete?id=${promotion.id}" onclick="return confirmx('确认要删除该优惠活动吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>