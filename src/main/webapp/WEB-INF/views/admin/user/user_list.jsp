<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>



<script type="text/javascript">

$(document).ready(function() {
	company.getView(dataForm,1);
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
				 ■ 직원 리스트
			</header>
		
			<div class="contents-block">
			
				<h1><div class="company-name"></div></h1>
				
				<div class="contents-main">
					<form method="post" name="dataForm" onsubmit="return company.getView(this,1); return false;">
						<input type="hidden" name="companySeq" value="1">
						<input type="hidden" name="colName" value="user_level">
						<input type="hidden" name="sort" value="asc">
						<div class="contents-top">
							<div class="top-tools">
								<div class="search-tool">		
									<button type="button" class="btn" onclick="company.popUp(1,'');">직원등록</button>
								</div>
							</div>
						</div>
						<table class="list">
						<colgroup>
							<col width="10%">
							<col width="*">
							<col width="10%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
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