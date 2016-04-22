<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>

<script type="text/javascript">
	$(document).ready(function() {
		files.edit(fileEditForm);
		
		var options = {
			//target :		'#user-join-result',
			beforeSubmit :	files.formCheck,
			success :		files.formSuccess
		};
		$('#fileEditForm').ajaxForm(options);
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
			
				<h1><span class="top-center"></span> 자료실</h1>
				
				<div class="contents-main">
					<form method="post" name="fileEditForm" id="fileEditForm" class="bbs-edit-form" enctype="multipart/form-data" action="/proc/file_upload.go">
					<input type="hidden" name="userId" value="${USER_ID}">
					<input type="hidden" name="projectSeq" value="${projectSeq}">
					<input type="hidden" name="dataSeq" value="${dataSeq}">
					<table class="edit">
						<colgroup>
							<col width="100">
							<col width="*">
						</colgroup>
							
							<tr>
								<th>내용</th>
								<td>
									<input type="text" name="contents" style="width:700px;" placeholder="내용을 입력해주세요.">
								</td>
							</tr>
							<tr>
								<th></th>
								<td>
									<ul class="bbs-tools-pc">
										<li>
											<input type="file" name="photo" class="file" style="cursor:pointer;" onchange="document.getElementById('upload-btn').click();">
											<button type="submit" id="upload-btn" class="btn-hide"></button>
										</li>
									</ul>
								</td>
							</tr>
							<tr>
								<th>첨부파일</th>
								<td><ul class="file-add"></ul></td>
							</tr>
							
					</table>
						<button type="button" class="btn-txt" onclick="files.filesSave(this.form,'admin');">등록</button>
					</form>
				
				</div>
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>