<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>停车记录明细</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/parking/list">停车记录列表</a></li>
		<li class="active"><a href="#">停车记录明细</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="parkingRecord" action="#" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">车牌：</label>
			<div class="controls">
				${parkingRecord.carNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				${parkingRecord.carPicPath}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">驶入时间：</label>
			<div class="controls">
				<fmt:formatDate value="${parkingRecord.driveInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">驶出时间：</label>
			<div class="controls">
				<fmt:formatDate value="${parkingRecord.driveOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入口编号：</label>
			<div class="controls">
				${parkingRecord.entranceNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出口编号：</label>
			<div class="controls">
				${parkingRecord.exportNo}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">停车场：</label>
			<div class="controls">
				${parkingRecord.parkingLot.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名 ：</label>
			<div class="controls">
				${parkingRecord.user.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户手机 ：</label>
			<div class="controls">
				${parkingRecord.user.mobile}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缴费状态 ：</label>
			<div class="controls">
				<c:choose>
				<c:when test=" bill != null && bill.payStatus>0">缴费成功</c:when>
				<c:otherwise>未缴费</c:otherwise>
				</c:choose>
			</div>
		</div>
		<c:if test="${bill!=null}">
			<div class="control-group">
				<label class="control-label">缴费渠道：</label>
				<div class="controls">
					${bill.paytype.textLable}
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">缴费金额：</label>
				<div class="controls">
					${bill.payAmount}
				</div>
			</div>
			<c:if test="${not empty bill.couponId}">
				<div class="control-group">
					<label class="control-label">优惠券信息：</label>
					<div class="controls">
						(${bill.coupon.type.textLable})&nbsp;${(bill.coupon.money!=null and  bill.coupon.money>0)?bill.coupon.money:bill.coupon.time }
					</div>
			</div>
			</c:if>
		</c:if>
	</form:form>
</body>
</html>