<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/m/head.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		weekBbs.getList(searchForm,1);
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
			<a href="javascript:weekBbs.showHideSearch();">
				<img src="/images/ic_search.png">
			</a>
			<c:if test="${USER_TYPE==1}">
			<a href="javascript:weekBbs.goBbsEdit(searchForm,0,'m')">
				<img src="/images/ic_plus.png">
			</a>
			</c:if>
		</li>
	</ul>
</header>

<article>

	<form method="post" name="searchForm" onsubmit="return weekBbs.onSearch(this,1); return false;">
<%-- 	<input type="hidden" name="bbsType" value="${bbsType}">
	<input type="hidden" name="bbsType2" value=""> --%>
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
	
	</ul>
<div class="paging-block"></div>
<%@ include file="/WEB-INF/views/m/m_footer.jsp"  %>

</article>

</body>
</html>