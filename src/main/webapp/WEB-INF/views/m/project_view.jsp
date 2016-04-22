<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/m/head.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		project.view(pageForm.projectSeq.value);
	});
</script>

<style>
</style>

</head>
<body>

<form name="pageForm">
	<input type="hidden" name="projectSeq" value="${projectSeq}">
	<input type="hidden" name="loginId" value="${USER_ID}">
	<input type="hidden" name="loginName" value="${USER_NAME}">
	<input type="hidden" name="loginType" value="${USER_TYPE}">
	<input type="hidden" name="loginCompany" value="${COMPANY_SEQ}">
</form>

<header>
	<ul>
		<li class="top-left">
			<a href="/m/project_list.go">
				<img src="/images/ic_top_back_arrow.png">
				<span class="top-menu">메인</span>
			</a>
		</li>
		<li class="top-center">
			
		</li>
		<li class="top-right">
		</li>
	</ul>
</header>

<article>

	<div class="list-title">프로젝트 작업 진행 현황</div>

	<ul class="progress-list">
		<li id="design-part">
			<dl>
				<dt class="icon">
					<img src="/images/ic_design.png">
				</dt>
				<dt class="title">
					<span class="part-name">디자인</span>
				</dt>
				<dd class="progress-cell">
					<div class="progress-bar">
						<div class="progress"></div>
					</div>
				</dd>
				<dd class="arrow">
					<span class="precent">0</span>%
				</dd>
			</dl>
		</li>
		<li id="ios-part">
			<dl>
				<dt class="icon">
					<img src="/images/ic_apple.png">
				</dt>
				<dt class="title">
					<span class="part-name">아이폰</span>
				</dt>
				<dd class="progress-cell">
					<div class="progress-bar">
						<div class="progress"></div>
					</div>
				</dd>
				<dd class="arrow">
					<span class="precent">0</span>%
				</dd>
			</dl>
		</li>
		<li id="android-part">
			<dl>
				<dt class="icon">
					<img src="/images/ic_android.png">
				</dt>
				<dt class="title">
					<span class="part-name">안드로이드</span>
				</dt>
				<dd class="progress-cell">
					<div class="progress-bar">
						<div class="progress"></div>
					</div>
				</dd>
				<dd class="arrow">
					<span class="precent">0</span>%
				</dd>
			</dl>
		</li>
		<li id="web-part">
			<dl>
				<dt class="icon">
					<img src="/images/ic_web.png">
				</dt>
				<dt class="title">
					<span class="part-name">웹</span>
				</dt>
				<dd class="progress-cell">
					<div class="progress-bar">
						<div class="progress"></div>
					</div>
				</dd>
				<dd class="arrow">
					<span class="precent">0</span>%
				</dd>
			</dl>
		</li>
		<li id="server-part">
			<dl>
				<dt class="icon">
					<img src="/images/ic_server.png">
				</dt>
				<dt class="title">
					<span class="part-name">서버</span>
				</dt>
				<dd class="progress-cell">
					<div class="progress-bar">
						<div class="progress"></div>
					</div>
				</dd>
				<dd class="arrow">
					<span class="precent">0</span>%
				</dd>
			</dl>
		</li>
		<li id="pc-part">
			<dl>
				<dt class="icon">
					<img src="/images/ic_pc.png">
				</dt>
				<dt class="title">
					<span class="part-name">PC</span>
				</dt>
				<dd class="progress-cell">
					<div class="progress-bar">
						<div class="progress"></div>
					</div>
				</dd>
				<dd class="arrow">
					<span class="precent">0</span>%
				</dd>
			</dl>
		</li>
	</ul>


	<div class="list-title">요청/버그 리포트, 자료실</div>

	<ul class="board-list">
		<li>
			<dl>
				<dt onclick="document.location.href='/m/bbs_list.go?projectSeq=${projectSeq}&bbsType=1';" class="bullet">
					<img src="/images/ic_question.png">
				</dt>
				<dt onclick="document.location.href='/m/bbs_list.go?projectSeq=${projectSeq}&bbsType=1';" class="title">
					<span class="project-name">요청게시판</span>
				</dt>
				<dd class="info">
					<span class="require-count"></span>
				</dd>
				<dd onclick="document.location.href='/m/bbs_list.go?projectSeq=${projectSeq}&bbsType=1';" class="arrow">
					<img src="/images/ic_list_arrow.png">
				</dd>
			</dl>
		</li>
		<li>
			<dl>
				<dt onclick="document.location.href='/m/bbs_list.go?projectSeq=${projectSeq}&bbsType=2';" class="bullet">
					<img src="/images/ic_error.png">
				</dt>
				<dt onclick="document.location.href='/m/bbs_list.go?projectSeq=${projectSeq}&bbsType=2';" class="title">
					<span class="project-name">버그게시판</span>
				</dt>
				<dd class="info">
					<span class="bug-count"></span>
				</dd>
				<dd onclick="document.location.href='/m/bbs_list.go?projectSeq=${projectSeq}&bbsType=2';" class="arrow">
					<img src="/images/ic_list_arrow.png">
				</dd>
			</dl>
		</li>
		
		<c:if test="${USER_TYPE==1}">
			<li>
				<dl>
					<dt onclick="document.location.href='/m/admin_list.go?projectSeq=${projectSeq}';" class="bullet">
						<img src="/images/ic_error.png">
					</dt>
					<dt onclick="document.location.href='/m/admin_bbs_list.go?projectSeq=${projectSeq}';" class="title">
						<span class="project-name">공지게시판</span>
					</dt>
					<dd class="info">
						<span class="notice-count"></span>
					</dd>
					<dd onclick="document.location.href='/m/admin_list.go?projectSeq=${projectSeq}';" class="arrow">
						<img src="/images/ic_list_arrow.png">
					</dd>
				</dl>
			</li>
		</c:if>
		<li>
			<dl>
				<dt onclick="document.location.href='/m/week_bbs_list.go?projectSeq=${projectSeq}';" class="bullet">
					<img src="/images/ic_error.png">
				</dt>
				<dt onclick="document.location.href='/m/week_bbs_list.go?projectSeq=${projectSeq}';" class="title">
					<span class="project-name">금주 진행사항</span>
				</dt>
				<dd class="info">
					<span class="new-count"></span>
				</dd>
				<dd onclick="document.location.href='/m/week_bbs_list.go?projectSeq=${projectSeq}';" class="arrow">
					<img src="/images/ic_list_arrow.png">
				</dd>
			</dl>
		</li>
		<li>
			<dl>
				<dt onclick="document.location.href='/m/files_list.go?projectSeq=${projectSeq}';" class="bullet">
					<img src="/images/ic_resource.png">
				</dt>
				<dt onclick="document.location.href='/m/files_list.go?projectSeq=${projectSeq}';" class="title">
					<span class="project-name">자료실</span>
				</dt>
				<dd class="info">
				</dd>
				<dd onclick="document.location.href='/m/files_list.go?projectSeq=${projectSeq}';" class="arrow">
					<img src="/images/ic_list_arrow.png">
				</dd>
			</dl>
		</li>
	</ul>



	<div class="list-title">프로젝트 담당자</div>

	<ul class="man-list">
		<!-- 
		<li>
			<dl>
				<dt>
					<img src="/images/ic_worker.png">
					<span class="man-name">이사 | 최승규</span>
					<span class="man-position">(기획/총괄)</span>
				</dt>
				<dd>
					<img src="/images/ic_list_arrow.png">
				</dd>
			</dl>
		</li>
		<li>
			<dl>
				<dt>
					<img src="/images/ic_worker.png">
					<span class="man-name">대리 | 오영민</span>
					<span class="man-position">(디자인)</span>
				</dt>
				<dd>
					<img src="/images/ic_list_arrow.png">
				</dd>
			</dl>
		</li>
		<li>
			<dl>
				<dt>
					<img src="/images/ic_worker.png">
					<span class="man-name">대리 | 임동민</span>
					<span class="man-position">(iOS개발)</span>
				</dt>
				<dd>
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