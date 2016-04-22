<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>

<script type="text/javascript">
	$(document).ready(function() {
		projectMenu.view(pageForm,1);
	//	bbs.contextMenu();
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
				 ■ 버그/요청 게시판
			</header>
				<h1><span class="project_name"></span> 버그/요청 게시판</h1>
				<div class="contents-main">
					<form method="post" name="pageForm" onsubmit="return projectMenu.view(this,1); return false;">
						<input type="hidden" name="bbsSeq" value="${bbsSeq}">
						<input type="hidden" name="projectSeq" value="${projectSeq}">
						<input type="hidden" name="bbsCommentSeq" value="${bbsCommentSeq}">
					</form>

					<article>
						
						<div class="bbs-box">
							<dl class="bbs">
								
							</dl>
						</div>
					
						<ul class="comment-list">
							
						</ul>
					
						<div class="comment-edit">
							<form method="post" name="bbsEditForm">
							<input type="hidden" name="projectSeq" value="${projectSeq}">
							<input type="hidden" name="bbsSeq" value="${bbsSeq}">
							<input type="hidden" name="bbsCommentSeq" value="${bbsCommentSeq}">
								<c:choose>
									<c:when test="${USER_TYPE==1}">
										<ul class="table" style="margin-bottom:5px;">
											<li>
												<select name="commentStatus" style="width:55px;">
													<option value="0" class="status0">대기</option>
													<option value="1" class="status1">접수</option>
													<option value="2" class="status2">진행</option>
													<option value="3" class="status3">보류</option>
													<option value="4" class="status4">완료</option>
												</select>
											</li>
											<li>
												<select name="todoPart" style="width:60px;">
													<option value="AND">AND</option>
													<option value="IOS">IOS</option>
													<option value="DESIGN">DESIGN</option>
													<option value="WEB">WEB</option>
													<option value="SERVER">SERVER</option>
												</select>
											</li>
												<c:if test="${list.size()>0}">
											<li>
													<select name="workAdmin" style="width:70px;">
													  <c:forEach items="${list}" var="it">
														<option value="${it.userId}">${it.userName}</option>
													  </c:forEach>
													</select>
											</li>
												</c:if>
											<li>
												<input type="text" name="dateSet" class="itext datepicker" value="" style="width:65px;">
											</li>
											<li>
												<button type="button" class="btn" onclick="bbs.todoEdit(this.form);">추가</button>
											</li>
										</ul>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="commentStatus"  value="0">
										<input type="hidden" name="dateSet" value="">
									</c:otherwise>
								</c:choose>
								<ul class="comment-form">
									<li class="camera">
										<img src="/images/ic_camera_01.png">
									</li>
								
									<li class="input">
										<div class="text-box">
											<textarea name="bbsContents" placeholder="댓글을 입력해주세요."></textarea>
										</div>
									</li>
									<li class="tool">
									<button type="button" onclick="bbs.bbsCommentedit(this.form);">전송</button>
									</li>
								</ul>
							</form>
						</div>
					
					
					</article>
				</div>
			
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>