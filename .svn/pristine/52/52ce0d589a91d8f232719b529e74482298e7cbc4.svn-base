<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>

<script type="text/javascript">
	$(document).ready(function() {
		projectMenu.Fileview(pageForm,1);
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
				 ■ 자료실
			</header>
		
			<div class="contents-block">
			
				<h1><span class="project_name"></span> 자료실</h1>
				
				<div class="contents-main">
					<form method="post" name="pageForm" onsubmit="return bbs.view(this.form,1); return false;">
						<input type="hidden" name="dataSeq" value="${dataSeq}">
						<input type="hidden" name="projectSeq" value="${projectSeq}">
					</form>



					<article>
					
						<dl class="files">
							<dt><div class="filebutton">
						
								</div><!-- --></dt>
							<dd>
								<p class="contents"></p>
								<p class="images">
								</p>
								<p class="info"></p>
								<div class="bbs-menu">
								
								</div>
							</dd>
						</dl>
						
					
					
					</article>
				
				</div>
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>