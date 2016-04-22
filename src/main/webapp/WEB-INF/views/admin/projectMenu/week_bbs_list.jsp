<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>

<script type="text/javascript">
	$(document).ready(function() {
		projectMenu.WeekBbsgetList(searchForm,1);
	});
</script>


<style>
.select-search { border:1px solid #ddd; padding-bottom:4px;}
</style>


</head>

<%@ include file="/WEB-INF/views/admin/admin_header.jsp"  %>

<section class="main-cover main-row">
	<section id="main">
		
		<%@ include file="/WEB-INF/views/admin/admin_menu.jsp"  %>

		<section id="contents">
			<header class="panel">
				 ■ 금주 진행사항
			</header>
		
			<div class="contents-block">
			
				<h1><span class="project_name"></span> 금주 진행사항</h1>
				
				<div class="contents-main">

					<form method="post" name="searchForm" onsubmit="return projectMenu.WeekBbsgetList(this,1); return false;">
					<input type="hidden" name="bbsType" value="0">
					<input type="hidden" name="bbsType2" value="0">
					<input type="hidden" name="sort" value="">
					<input type="hidden" name="colName" value="">
					<input type="hidden" name="projectSeq" value="${projectSeq}">
						<div class="contents-top">
							<div class="top-tools">
								<div class="search-tool">	
									<input type="text" name="keyword" placeholder="내용/작성자 검색">
									<button type="submit">검색</button>
									
								</div>
								<button type="button" class="btn-blue" onclick="weekBbs.goBbsEdit(searchForm,0,'admin')">작성하기</button>
							</div>
						</div>
				
					<table class="list">
							<colgroup>
								<col width="5%">
								
							
								<col width="*">
								<col width="10%">
								<col width="10%">
							
								
							</colgroup>
							<thead>
								<th class="text-left">번호
									<span id="bbs_seq">
										<button type="button" onclick="projectMenu.WeekBbslistOrder('bbs_seq','asc');" class="bbs_seq" style="margin-top:3px;">▼</button>
									</span>
								</th>
							
								<th class="text-left">제목
								
								</th>
								<th class="text-left">작성자
								
								</th>
								<th>등록일
									<span id="bbs_reg_date">
										 <button type="button" onclick="projectMenu.WeekBbslistOrder('bbs_reg_date','asc');" class="bbs_reg_date" style="margin-top:3px;">▼</button>
									</span>
								</th>
								
							</thead>
							<tbody class="contents-list">
							</tbody>
						</table>	
					
					<div class="paging-block"></div>
					
					</form>
				</div>
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>