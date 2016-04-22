<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>

<script type="text/javascript">
	$(document).ready(function() {
		bbs.bbsedit(bbsEditForm);
		
		var options = {
			//target :		'#user-join-result',
			beforeSubmit :	bbs.formCheck,
			success :		bbs.formSuccess
		};
		$('#bbsEditForm').ajaxForm(options);
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
			<h1><span class="top-center"></span> 버그/요청 게시판</h1>
			<div class="contents-block">
				<div class="contents-main">

					<form method="post" name="bbsEditForm" id="bbsEditForm" class="bbs-edit-form" enctype="multipart/form-data" action="/proc/file_upload.go">
					<input type="hidden" name="userId" value="${USER_ID}">
					<input type="hidden" name="projectSeq" value="${projectSeq}">
					<input type="hidden" name="bbsSeq" value="${bbsSeq}">
					<input type="hidden" name="bbsType2" value="${USER_TYPE==1 ? 1 : 0}">
					<input type="hidden" name="type" value="${projectSeq}">
					<input type="hidden" name="isThumb" value="0">
					<input type="hidden" name="addThumb" value="1">
					<input type="hidden" name="idx" value="0">
					<table class="edit">
						<colgroup>
							<col width="100">
							<col width="*">
						</colgroup>
						<tr>
							<th>게시종류</th>
							<td>
								<input type="radio" name="bbsType" value="1">요청</input>
								<input type="radio" name="bbsType" value="2">버그</input>
							</td>
						</tr>
						<tr>
							<th>제목</th>
							<td>
								<input type="text" name="bbsTitle" style="width:700px;" placeholder="제목을 입력해주세요.">
							</td>
						</tr>
						<tr>
							<th>첨부파일</th>
							<td>
								<ul class="file-add"></ul>
								<ul class="bbs-tools-pc">
									<li>
										<input type="file" name="photo" class="file" style="cursor:pointer;" onchange="document.getElementById('upload-btn').click();">
										<button type="submit" id="upload-btn" class="btn-hide"></button>
									</li>
								</ul>
							</td>
						</tr>
						<!-- <tr>
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
						</tr> -->
						<tr>
							<th style="vertical-align: top;">내용</th>
							<td><textarea name="contents"  style="width:700px; height: 500px;" ></textarea></td>
						</tr>
				 </table>
				 <div class="btn-tools">
						<button type="button" class="btn-txt" onclick="bbs.bbsSave(this.form,'admin');">등록</button>
						<button type="button" class="btn-txt" onclick="link.go('/admin/bbs_view.go?projectSeq=${projectSeq}&bbsSeq=${bbsSeq}&page=1');">뒤로</button>
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