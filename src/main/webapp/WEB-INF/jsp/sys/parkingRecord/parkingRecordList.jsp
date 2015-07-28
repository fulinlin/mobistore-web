<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>停车记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".cardNoImg").click( function() {
				top.$.jBox.open("iframe:" + $(".cardNoImg").attr("src"), $(".cardNoImg").attr("alt"), 800, 350, { buttons: { '关闭': true} });
	        });
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/parking/">停车记录列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="parkingRecord" action="${ctx}/parking/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.limit}"/>
		<div>
			<label>车牌：</label>
				<form:input path="carNo" htmlEscape="false" maxlength="255" class="input-medium"/>
			<label>入场时间：</label>
				<input name="driveInTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${parkingRecord.driveInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			<label>出场时间</label>
				<input name="driveOutTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${parkingRecord.driveOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			<label>手机号：</label>
			<input name="mobile" value="${mobile}" htmlEscape="false" maxlength="255" class="input-medium"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>车牌</th>
				<th>车头照</th>
				<th>入场时间</th>
				<th>出场时间</th>
				<th>进场入口</th>
				<th>出场出口</th>
				<th>用户手机</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.items}" var="parkingRecord">
			<tr>
				<td><a href="${ctx}/parkingRecord/form?id=${parkingRecord.id}">
					${parkingRecord.carNo}
				</a></td>
				<td>
				    <img   alt="${parkingRecord.carNo}" src="${parkingRecord.carPicPath}" height="40"  class="cardNoImg">
				</td>
				<td>
					<fmt:formatDate value="${parkingRecord.driveInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${parkingRecord.driveOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${parkingRecord.entranceNo}
				</td>
				<td>
					${parkingRecord.exportNo}
				</td>
				<td>
					${parkingRecord.user.mobile}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>