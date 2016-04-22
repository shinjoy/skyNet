<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
var side = {
    showSubMenu:function(sub) {
    	if (sub==1) {
    		$("#menu-sub-1").css("display","block");
    		$("#menu-sub-1").animate({height: 70}, 200 );
    		if($("#menu1").hasClass("active") == true  || $("#menu-sub-1").animate({height: 70}, 200 ) != true){  
    			$("#menu-sub-1").stop().animate({height: 70}, 200 );    			
    		}
  			if($("#menu2").hasClass("active") == false){
    	   		$("#menu-sub-2").animate({height: 0}, 100, function() { $("#menu-sub-2").css("display","none"); } );
    		}
    		if($("#menu3").hasClass("active") ==false){
    	   		$("#menu-sub-3").animate({height: 0}, 100, function() { $("#menu-sub-3").css("display","none"); } );
    		}
    	} else if (sub==2) {
    		$("#menu-sub-2").css("display","block");
    		$("#menu-sub-2").animate({height: 35}, 200);
    		if($("#menu2").hasClass("active") == true || $("#menu-sub-2").animate({height: 35}, 200 ) != true){  
    			$("#menu-sub-2").stop().animate({height: 35}, 200 );
    		}
    		if ($("#menu1").hasClass("active") == false) {
	    		$("#menu-sub-1").animate({height: 0}, 100, function() { $("#menu-sub-1").css("display","none"); } );
    		}
    		if($("#menu3").hasClass("active") == false){
    	   		$("#menu-sub-3").animate({height: 0}, 100, function() { $("#menu-sub-3").css("display","none"); } );
    		}
    	} else if (sub==3) {
    		$("#menu-sub-3").css("display","block");
    		$("#menu-sub-3").animate({height: 100}, 200 );
    		if ($("#menu1").hasClass("active") == false) {
	    		$("#menu-sub-1").animate({height: 0}, 100, function() { $("#menu-sub-1").css("display","none"); } );
    		}
    		if($("#menu2").hasClass("active") == false){
    	   		$("#menu-sub-2").animate({height: 0}, 100, function() { $("#menu-sub-2").css("display","none"); } );
    		}
    	}
    },
};
</script>


<style>

.admin-menu ul.nav>li>a {
    padding: 11px 15px;
    position: relative;
    font-weight: bold;
    font-size: 14px;
    border-top: 1px solid transparent;
    border-color: rgba(255,255,255,0.05);
    transition: color .3s ease-in-out 0s;
}

.admin-menu li>a>i {
    margin: -12px -15px;
    line-height: 44px;
    width: 44px;
    float: left;
    margin-right: 10px;
    font-size: 14px;
    border-right: 1px solid rgba(255,255,255,0.05);
    text-align: center;
    position: relative;
    overflow: hidden;
}



.menu1 { 
	cursor: pointer; 
	padding: 15px 5px 0px 40px; 
	color: #fff; 
	text-transform: uppercase; 
	font-size: 14px;  
	font-weight:bold; 
	border-top: 1px solid #565c5f; 
	background-repeat: no-repeat;
	background-position: 10px;
}

.menu2 { 
	cursor: pointer; 
	padding: 15px 5px 0px 40px; 
	color: #fff; 
	text-transform: uppercase; 
	font-size: 14px;  
	font-weight:bold; 
	border-top: 1px solid #565c5f; 
	background-repeat: no-repeat;
	background-position: 10px;
}

.menu3 { 
	cursor: pointer; 
	padding: 15px 5px 0px 40px; 
	color: #fff; 
	text-transform: uppercase; 
	font-size: 14px;  
	font-weight:bold; 
	border-top: 1px solid #565c5f; 
	background-repeat: no-repeat;
	background-position: 10px;
}

.logout {

	font-size: 10px; 
	line-height: 1.5;
	display: inline-block;
	position: relative;
	white-space: nowrap;
	overflow: visible;
	text-decoration: none !important;
	margin: 0;
	padding: 3px 7px 3px 5px;
	margin-top : -23px;
	margin-left: 70px;
	border: 1px solid #fff;
	border-radius: 13px;
	background: #535b60;
	color: #fff;
}
.pw {

	font-size: 10px; 
	line-height: 1.5;
	display: inline-block;
	position: relative;
	white-space: nowrap;
	overflow: visible;
	text-decoration: none !important;
	margin: 0;
	padding: 3px 7px 3px 5px;
	margin-top :7px;
	margin-left: -10px;
	border: 1px solid #fff;
	border-radius: 13px;
	background: #535b60;
	color: #fff;
}

.tab1 { 
	padding:5px; 
	cursor:pointer;  
	padding: 5px 5px 5px 40px; 
	background-color: #353c42; 
	font: 12px/1.6 NanumBarunGothic,'맑은 고딕' ; 
	border-bottom: 1px solid #353c42; 
	color: #fff;
}

.logo-img { padding: 10px 10px 20px 35px;}

</style>


		<!-- PC 버전 -->
		<div class="pc-view lnb">
		
			<div class="logo-img">
				
			</div>
		
		
			<div class="login-sec">
				<p style="color:#fff;">${USER_NAME}님 반갑습니다.</p>
				<p><button class="pw " onclick="document.location.href='/admin/user_edit_password.go';">비밀번호변경</button></p>
				<p><button class="logout" onclick="document.location.href='/logout_do.go';">로그아웃</button></p>
			</div>	
			
			
			<nav class="admin-menu">
				<ul class="nav">
				
					<li class="menu1" id="menu1" onclick="side.showSubMenu(1)">회원 관리 
						<a><i class="menuicon"></i></a>
					 </li>
					<li id="menu-sub-1" style="display:none;">
						<ul class="sm">
							<li class="tab1" id="tab1"><a href="/admin/member/member.go" > > 회원목록</a></li>
							<li class="tab1" id="tab2"><a id="menu12" href="/admin/member/analyst.go" > > 분석가 관리</a></li>
						</ul>
					</li>
					<li>
						
						<li class="menu2" id="menu2" onclick="side.showSubMenu(2)">분석 목록</li>					
						<li id="menu-sub-2" style="display:none;">
						<ul class="sm">
							<li class="tab1" id="tab3"><a id="menu13" href="/admin/assay/assay.go"> > 분석목록</a></li>
						</ul>
					</li>
					<li>
						<li class="menu3" id="menu3" onclick="side.showSubMenu(3)">게시판 관리</li>
						<li id="menu-sub-3" style="display:none;">
						<ul class="sm">
							<li class="tab1" id="tab4"><a id="menu41" href="/admin/notice/notice.go"> > 공지사항</a></li>
							<li class="tab1" id="tab5"><a id="menu42" href="/admin/fnq/fnq.go"> > 자주 묻는 질문</a></li>
						</ul>
					</li>	
				</ul>
			</nav>
			
		</div><!-- End of class="lnb" //-->
				
		<!-- 모바일 버전 -->
		<div id="drop-nav1" class="mobile-view drop-nav">
			<div class="wrap">
				<ul>
					<li><a id="menu11" href="/admin/veri_manager.go">인증코드관리</a></li>
					<li><a id="menu12" href="/admin/veri_history.go">인증내역</a></li>
				</ul>
			</div>
		</div>
		