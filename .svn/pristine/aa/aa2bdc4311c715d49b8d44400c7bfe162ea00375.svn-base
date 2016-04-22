<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>



<script type="text/javascript">

$(document).ready(function() {
	aside.setActive(dataForm.projectSeq.value,1);
	projectMenu.getProgress(dataForm);
	
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
				 ■ 프로젝트 진행 사항
			</header>
		
			<div class="contents-block">
			
				<h1><span class="project_name"></span> 진행 사항</h1>
				
				<div class="contents-main">
					<form method="post" name="dataForm" >
						<input type="hidden" name="projectSeq" value="${projectSeq}">
						<input type="hidden" name="commentSeq" value="">
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
							
							
							<div class="progress-comment designComment">
									<input type="text" class="itext" name="designComment" id="designComment"><button type="button" class="btn" onclick="projectMenu.saveComment(this.form);">저장</button>
							</div>
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
							
							<div class="progress-comment iosComment">
									<input type="text" class="itext" name="iosComment" id="iosComment"><button type="button" class="btn" onclick="projectMenu.saveComment(this.form);">저장</button>
							</div>
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
								<div class="progress-comment andComment">
									<input type="text" class="itext" name="andComment" id="andComment"><button type="button" class="btn" onclick="projectMenu.saveComment(this.form);">저장</button>
								</div>
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
								<div class="progress-comment webComment">
									<input type="text" class="itext" name="webComment" id="webComment"><button type="button" class="btn" onclick="projectMenu.saveComment(this.form);">저장</button>
								</div>
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
								<div class="progress-comment serverComment">
									<input type="text" class="itext" name="serverComment" id="serverComment"><button type="button" class="btn" onclick="projectMenu.saveComment(this.form);">저장</button>
								</div>
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
								<div class="progress-comment pcComment">
									<input type="text" class="itext" name="pcComment" id="pcComment"><button type="button" class="btn" onclick="projectMenu.saveComment(this.form);">저장</button>
								</div>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>