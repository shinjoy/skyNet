<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/m/head.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		project.getList(1);
	});
</script>

<style>
</style>

</head>
<body>
<%@ include file="/WEB-INF/views/m/userInfo.jsp"  %>
<form name="pageForm">
	<input type="hidden" name="loginId" value="${USER_ID}">
	<input type="hidden" name="loginName" value="${USER_NAME}">
	<input type="hidden" name="loginType" value="${USER_TYPE}">
	<input type="hidden" name="loginCompany" value="${COMPANY_SEQ}">
</form>

<header>
	<ul>
		<li class="top-left">
		</li>
		<li class="top-center">
			<img src="/images/img_logo_skynet_top.png">
		</li>
		<li class="top-right">
		</li>
	</ul>
</header>


<article>

	<div class="list-title">프로젝트 리스트</div>

	<ul class="project-list">
		<!-- 
		<li class="active">
			<dl>
				<dt class="title">
					<span class="project-name">리얼체크업</span>
					<span class="project-part">(android)</span>
				</dt>
				<dd class="date">
					2016.2.1
				</dd>
				<dd class="arrow">
					<img src="/images/ic_list_arrow.png">
				</dd>
			</dl>
		</li>
		<li>
			<dl>
				<dt class="title">
					<span class="project-name">포커스톡</span>
					<span class="project-part">(android)</span>
				</dt>
				<dd class="date">
					2016.2.1
				</dd>
				<dd class="arrow">
					<img src="/images/ic_list_arrow.png">
				</dd>
			</dl>
		</li>
		 -->
	</ul>

<%@ include file="/WEB-INF/views/m/m_footer.jsp"  %>

</article>

</body>
</html>