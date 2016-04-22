<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>



<script type="text/javascript">

$(document).ready(function() {
	todo.getList(dataForm);
});

$(function() {
    $.datepicker.setDefaults( $.datepicker.regional[ "ko" ] );
    $( ".datepicker" ).datepicker(
        {
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
				 ■ Todo 리스트
			</header>
		
			<div class="contents-block">
			
				<h1>Todo 리스트</h1>
				
				<div class="contents-main">
					<form method="post" name="dataForm" onsubmit="return todo.getList(this); return false;">
					
						<div class="contents-top">
							<div class="top-tools">
								<div class="search-tool">		
									<input type="text" name="keyword" value="${keyword}" placeholder="프로젝트 명 검색" class="itext">
									<input type="text" name="startDate" class="itext datepicker" value="${startDate}" placeholder="시작일" style="width:80px;">
									<input type="text" name="endDate" class="itext datepicker" value="${endDate}" placeholder="종료일" style="width:80px;">
						

									<button type="submit" class="btn">검색</button>
									
								
								</div>
								
							</div>
						</div>
					 </form>	
						<h2>일정</h2>
						<table class="list">
							<colgroup>
								<col width="5%">
								<col width="5%">
								<col width="5%">
								<col width="10%">
								<col width="5%">
								<col width="10%">
								<col width="10%">
								<col width="5%">
								<col width="*%">
								<col width="10%">
							
							</colgroup>
							<thead>
								<tr>
									<th>프로젝트명</th>
									<th>모듈</th>
									<th>프로세스</th>
									<th>TODO</th>
									<th>PART</th>
									<th>시작일</th>
									<th>종료일</th>
									<th>상태</th>
									<th>작업 내용</th>
									<th>완료일</th>
								
								</tr>
							</thead>
							<tbody class="now-list">
							</tbody>
						</table>		
						<h2>지난 일정</h2>
						<table class="list">
							<colgroup>
								<col width="5%">
								<col width="5%">
								<col width="5%">
								<col width="10%">
								<col width="5%">
								<col width="10%">
								<col width="10%">
								<col width="5%">
								<col width="*%">
								<col width="10%">
							
							</colgroup>
							<thead>
								<tr>
									<th>프로젝트명</th>
									<th>모듈</th>
									<th>프로세스</th>
									<th>TODO</th>
									<th>PART</th>
									<th>시작일</th>
									<th>종료일</th>
									<th>상태</th>
									<th>작업 내용</th>
									<th>완료일</th>
							
								</tr>
							</thead>
							<tbody class="before-list">
							</tbody>
						</table>	
					
				</div>
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>