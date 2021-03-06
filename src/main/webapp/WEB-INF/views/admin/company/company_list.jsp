<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>



<script type="text/javascript">

$(document).ready(function() {
	company.getList(dataForm,1);
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
				 ■ 업체 리스트
			</header>
		
			<div class="contents-block">
			
				<h1>업체 리스트</h1>
				
				<div class="contents-main">
					<form method="post" name="dataForm" onsubmit="return company.getList(this,1); return false;">
						
						<input type="hidden" name="colName" value="company_seq">
						<input type="hidden" name="sort" value="desc">
						<div class="contents-top">
							<div class="top-tools">
								<div class="search-tool">		
									<input type="text" name="keyword" value="${keyword}" placeholder="업체 검색" class="itext">
									<button type="submit" class="btn">검색</button>
									<button type="button" class="btn" onclick="company.popUpCompany(0)">업체등록</button>
								</div>
							</div>
						</div>
						<table class="list">
							<colgroup>
								<col width="5%">
								<col width="20%">
								<col width="*">
								<col width="10%">
							</colgroup>
							<thead>
								<th>번호 
									<span id="company_seq">
										<button type="button" onclick="company.listOrderCompany('company_seq','asc');" class="company_seq" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th class="text-left">업체명 
									<span id="company_name">
										<button type="button" onclick="company.listOrderCompany('company_name','asc');" class="company_name" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th class="text-left">비고</th>
								<th>관리</th>
							</thead>
							<tbody class="contents-list">
							</tbody>
						</table>	

						<div class="paging-block">
						</div>

					</form>
				</div>
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>