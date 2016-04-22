<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/m/head.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		files.getList(searchForm,1);
	});
</script>

<style>
</style>

</head>
<body>

<header>
	<ul>
		<li class="top-left">
			<a href="/m/project_view.go?projectSeq=${projectSeq}">
				<img src="/images/ic_top_back_arrow.png">
				<span class="top-menu">메인</span>
			</a>
		</li>
		<li class="top-center">
		</li>
		<li class="top-right">
			<a href="javascript:files.showHideSearch();">
				<img src="/images/ic_search.png">
			</a>
			<a href="javascript:files.goFileEdit(searchForm,0,'m')">
				<img src="/images/ic_plus.png">
			</a>
		</li>
	</ul>
</header>

<article>

	<form method="post" name="searchForm" onsubmit="return files.onSearch(this,1); return false;">
	<input type="hidden" name="dataType" value="${dataType}">
	
	<input type="hidden" name="dataSeq" value="${dataSeq}">
	<input type="hidden" name="sort" value="">
	<input type="hidden" name="colName" value="">
	<input type="hidden" name="projectSeq" value="${projectSeq}">
		<div class="search">
			<dl>
				<dt><div class="search-box"><input type="text" name="keyword" placeholder="내용/작성자 검색"></div></dt>
				<dd><button type="submit">검색</button></dd>
			</dl>
		</div>
	</form>

	<ul class="bbs-list">
		<!-- 
		<li class="active">
			<dl class="bbs">
				<dt><img src="/images/ic_inbox_dw.png"></dt>
				<dd>
					<p class="contents">logo_test.jpg</p>
					<p class="info">김영희 | 16.12.30</p>
				</dd>
			</dl>
		</li>
		<li>
			<dl class="bbs">
				<dt><img src="/images/ic_inbox.png"></dt>
				<dd>
					<p class="contents">[접수]작업이 진행중입니다.</p>
					<p class="info">임동민 | 16.12.30</p>
				</dd>
			</dl>
		</li>
		 -->
	</ul>

<%@ include file="/WEB-INF/views/m/m_footer.jsp"  %>

</article>

</body>
</html>