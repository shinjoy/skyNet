<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/m/head.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		frm = loginForm;
	});
	
	function loginFormCheck(frm) {
		var loginId = frm.loginId.value;
		var loginPw = frm.loginPw.value;

		if (loginId == "") {
			alert("Please enter your ID.");
			return false;
		}
		if (loginPw == "") {
			alert("Please enter a password.");
			return false;
		}

		var param = {
			loginId : loginId,
			loginPw : loginPw
		};
		
		$.ajax({
		    type:"POST",
		    url:"/login_do.go",
		    dataType:"json",
		    data:param,
		    success:function(json) {
		        if (json.result) {
		        	document.location.href = "/skynet/main.go";	
		        } else {
		        	alert(json.message);
		        }
		    },
		    error:function(xhr, status, error) {
		        alert(error);
		    },
		    complete:function(data) {
		    }
		});
		return false;
	}

	
</script>

<style>
</style>

</head>
<body>

<article class="login">

<div class="login-img">
	<p><!-- 노마드소프트 IT인프라 스카이넷 --></p>
	<img src="/images/img_logo_skynet.png">
</div>

<form method="post" name="loginForm" onsubmit="return loginFormCheck(this); return false;">
	<ul class="login-form">
		<li class="login-input"><input type="text" name="loginId" placeholder="아이디를 입력하세요."></li>
		<li class="login-input"><input type="password" name="loginPw" placeholder="비밀번호를 입력하세요."></li>
		<li class="login-button"><button type="submit">로그인</button></li>
		<li class="login-tools">
			<a href="">아이디 찾기</a> |
			<a href="">비밀번호 찾기</a>
		</li>
	</ul>
</form>

</article>

<%@ include file="/WEB-INF/views/m/m_footer.jsp"  %>


</body>
</html>