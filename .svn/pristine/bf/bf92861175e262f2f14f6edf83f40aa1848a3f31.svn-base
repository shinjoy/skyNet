<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>



<script type="text/javascript">

$(document).ready(function() {
	company.getView(dataForm,1);
});

$(function() {
	$.datepicker.setDefaults( $.datepicker.regional[ "ko" ] );
		$( ".datepicker" ).datepicker({
			dateFormat: 'yy-mm-dd',
			showButtonPanel: true
		}
	);
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
				 ■ 업체관리 &gt; 업체 보기
			</header>
		
			<div class="contents-block">
			
				<h1><div class="company-name"></div></h1>
				
				<form method="post" name="dataForm" onsubmit="return company.getView(this,1); return false;">
				<input type="hidden" name="companySeq" value="${companySeq}">
				<input type="hidden" name="colName" value="user_name">
				<input type="hidden" name="sort" value="asc">

					<div class="contents-main">
						
						<div class="contents-top">
							<div class="top-tools">
								<div class="search-tool">		
									<button type="button" class="btn" onclick="company.popUp(${companySeq},'');">담당자등록</button>
								</div>
							</div>
						</div>
						<table class="list">
							<colgroup>
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="*">
								<col width="10%">
							</colgroup>
							<thead>
								<th>아이디 
									<span id="user_id">
										<button type="button" onclick="company.listOrder('user_id','asc');" class="user_id" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th>이름 
									<span id="user_name">
										<button type="button" onclick="company.listOrder('user_name','asc');" class="user_name" style="margin-top:3px;">▼</button>
									</span>
								</th>							
								<th>직책
									<span id="user_group">
										 <button type="button" onclick="company.listOrder('user_group','asc');" class="user_group" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th>직함
									<span id="user_position">
										 <button type="button" onclick="company.listOrder('user_position','asc');" class="user_position" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th>휴대폰
									<span id="user_phone">
									<button type="button" onclick="company.listOrder('user_phone','asc');" class="user_phone" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th>email
									<span id="user_email">
									 <button type="button" onclick="company.listOrder('user_email','asc');" class="user_email" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th>비고</th>
								<th>관리</th>
							</thead>
							<tbody class="contents-list">
							</tbody>
						</table>

					</div>
					<div class="contents-main" style="margin-top:10px;">

						<div class="contents-top">
							<div class="top-tools">
								<div class="search-tool">		
									<button type="button" class="btn" onclick="projectAdmin.projectEditGo(0,${companySeq});" >프로젝트등록</button>
								</div>
							</div>
						</div>

						<table class="list">
							<colgroup>
								<col width="20%">
								<col width="*">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
								
							</colgroup>
							<thead>
								<th class="text-left">업체명 
									<span id="company_name">
										<button type="button" onclick="projectAdmin.listOrder('company_name','asc');" class="company_name" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th class="text-left">프로젝트명 
								<span id="project_name">
									<button type="button" onclick="projectAdmin.listOrder('project_name','asc');" class="project_name" style="margin-top:3px;">▼</button>
								</span>
								</th>
							
								<th>시작일
									<span id="project_start_day">
										 <button type="button" onclick="projectAdmin.listOrder('project_start_day','asc');" class="project_start_day" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th>마감일
									<span id="project_end_day">
										 <button type="button" onclick="projectAdmin.listOrder('project_end_day','asc');" class="project_end_day" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th>상태
									<span id="project_status">
									<button type="button" onclick="projectAdmin.listOrder('project_status','asc');" class="project_status" style="margin-top:3px;">▼</button>
									</span>
								</th>
								<th>등록일
									<span id="project_reg_date">
									 <button type="button" onclick="projectAdmin.listOrder('project_reg_date','asc');" class="project_reg_date" style="margin-top:3px;">▼</button>
									</span>
								</th>
								
							</thead>
							<tbody class="project-list">
							</tbody>
						</table>	

						<div class="paging-block">
						</div>

					</div>
				</form>

			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>