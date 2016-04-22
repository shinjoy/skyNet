<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>



<script type="text/javascript">


</script>

<style>
.select-search { border:1px solid #ddd; padding-bottom:4px;}
</style>


</head>


<body>

	<article class="popup">

		<header>
			<h1>업체 담당자 ${user.userId=='' ? '등록' :'수정'}</h1>
		</header>
		
		<div class="contents-main">
			<form method="post" name="dataForm" onsubmit="return company.adminEdit(this); return false;">
			<input type="hidden" name="companySeq" value="${companySeq}">
				<dl class="edit">
					<c:choose>
					<c:when test="${companySeq==1}">
						<dt>레벨</dt>
						<dd>
							<label><input type="radio" name="userLevel" value="1" checked="checked">최고</label>
							<label><input type="radio" name="userLevel" value="2" ${user.userLevel==2 ?  'checked=/"checked/"' :''}>중간</label>
							<label><input type="radio" name="userLevel" value="3" ${user.userLevel==3 ?  'checked=/"checked/"' :''}>일반</label>
						</dd>
						<input type="hidden" name="userType" value="1">
					</c:when>
					<c:otherwise>
						<input type="hidden" name="userLevel" value="3">
						<input type="hidden" name="userType" value="2">
					</c:otherwise>
					</c:choose>
				
					<dt>아이디</dt>
					<dd><input type="text" class="itext" name="userId" value="${user.userId}" style="width:300px;"></dd>

					<dt>이름</dt>
					<dd><input type="text" class="itext" name="userName" value="${user.userName}" style="width:300px;"></dd>

					<dt>직책</dt>
					<dd>
						<c:choose>
						<c:when test="${companySeq==1}">
							<label><input type="radio" name="userGroup" value="MANAGER" checked="checked">MANAGER</label>
							<label><input type="radio" name="userGroup" value="AND" ${user.userGroup=='AND' ?  'checked=/"checked/"' :''}>ANDROID</label>
							<label><input type="radio" name="userGroup" value="IOS" ${user.userGroup=='IOS' ?  'checked=/"checked/"' :''}>IOS</label>
							<label><input type="radio" name="userGroup" value="WEB" ${user.userGroup=='WEB' ?  'checked=/"checked/"' :''} >WEB</label>
							<label><input type="radio" name="userGroup" value="DESIGN" ${user.userGroup=='DESIGN' ?  'checked=/"checked/"' :''}>DESIGN</label>
						</c:when>
						<c:otherwise>
							<input type="text" class="itext" name="userGroup" value="${user.userGroup}">
						</c:otherwise>
						</c:choose>
					</dd>

				<c:choose>
				<c:when test="${user.userId==''}">
					<dt>비밀번호</dt>
					<dd><input type="text" class="itext" name="userPw" value="" style="width:300px;"></dd>
					<input type="hidden" name="type" value="0">
				</c:when>
				<c:otherwise>
					<input type="hidden" name="type" value="1">
					<input type="hidden" name="userPw" value="${user.userPw}">
				</c:otherwise>
				</c:choose>
				
					<dt>직함</dt>
					<dd><input type="text" class="itext" name="userPosition" value="${user.userPosition}" style="width:300px;"></dd>

					<dt>휴대폰</dt>
					<dd><input type="text" class="itext" name="userPhone" value="${user.userPhone}" style="width:300px;"></dd>

					<dt>email</dt>
					<dd><input type="text" class="itext" name="userEmail" value="${user.userEmail}" style="width:300px;"></dd>

					<dt>비고</dt>
					<dd><textarea name="userEct" style="width:300px; height:100px; padding:3px;">${user.userEct}</textarea></dd>
				</dl>
				
				<div class="tool-bar">
					<button type="submit" class="btn btn-blue" style="width:100px;">저장</button>
				</div>
			</form>
		</div>

	</article>

</body>
</html>