<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>

<script type="text/javascript">
	$(document).ready(function() {
		projectMenu.WeekBbsview(pageForm,1);
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
				 ■ 금주 진행사항
			</header>
				<h1><span class="project_name"></span> 금주 진행사항</h1>
				<div class="contents-main">
					<form method="post" name="pageForm" onsubmit="return projectMenu.WeekBbsview(this,1); return false;">
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
									<button type="button" onclick="weekBbs.bbsCommentedit(this.form);">전송</button>
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