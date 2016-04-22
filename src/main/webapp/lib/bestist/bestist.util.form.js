var isMobile = (
		navigator.userAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null 
		||
		navigator.userAgent.match(/LG|SAMSUNG|Samsung/) != null
);
var isIos = (
		navigator.userAgent.match(/iPhone|iPod/i) != null 
);
var isAndroid = (
		navigator.userAgent.match(/Android/i) != null 
		||
		navigator.userAgent.match(/LG|SAMSUNG|Samsung/) != null
);

function executeJcall(url) {
	var iframe = document.createElement("IFRAME");
	iframe.setAttribute("src", url);
	document.documentElement.appendChild(iframe);
	iframe.parentNode.removeChild(iframe);
	iframe = null;
}

/**
 * 컨트롤러의 체크 유무를 대상 오브젝트들에 동일하게 적용한다. : 전체 선택/취소
 * @param controller
 * @param target : string : 태그의 name 엘리먼트 값
 */
function checkAll(controller, target){
	var chk_arr = document.getElementsByName(target);
	for (var i=0; i<chk_arr.length; i++) {
		alert(chk_arr[i]);
		chk_arr[i].checked = controller.checked;
	}
}

/**
 * checkbox에서 선택된 항목들의 값을 배열로 리턴한다.
 * @param checkbox : 체크박스 name의 스트링값
 * @returns {Array}
 */
function getCheckedListValueByObject(checkbox) {
	var str = new Array();
	var idx = 0;
	for (var i=0; i<checkbox.length; i++) {
		if (checkbox[i].checked) {
			str[idx] = checkbox[i].value;
			idx++;
		}
	}
	return str;
}
/**
 * checkbox에서 선택된 항목들의 값을 배열로 리턴한다.
 * @param checkbox : 체크박스 name의 스트링값
 * @returns {Array}
 */
function getCheckedListValue(checkboxName) {
	var chk_arr = document.getElementsByName(checkboxName);
	var str = new Array();
	var idx = 0;
	for (var i=0; i<chk_arr.length; i++) {
		if (chk_arr[i].checked) {
			str[idx] = chk_arr[i].value;
			idx++;
		}
	}
	return str;
}

function getList(elementName) {
	var chk_arr = document.getElementsByName(elementName);
	var str = new Array();
	var idx = 0;
	if(chk_arr == undefined) {
		return str;
	} else if (chk_arr.length == undefined) {
		str[0] = chk_arr.value;
		return str;
	} else {
		for (var i=0; i<chk_arr.length; i++) {
			str[idx] = chk_arr[i].value;
			idx++;
		}
		
		return str;
	}
}

function getListValue(elementName, d) {
	var chk_arr = document.getElementsByName(elementName);
	var str = new Array();
	var idx = 0;
	var txt = "";
	if(chk_arr == undefined) {
		return txt;
	} else if (chk_arr.length == undefined) {
		txt = chk_arr.value;
		return txt;
	} else {
		for (var i=0; i<chk_arr.length; i++) {
			txt += chk_arr[i].value + d;
		}
		//console.log("txt:",txt);
		//console.log("dv:",d);
		txt = txt.substring(0,txt.length-1);
		return txt;
	}
}

/**
 * String 값이 "" 이라면 true를 반환한다.
 * @param val
 * @returns {Boolean}
 */
function isEmpty(val) {
	if (val == "") {
		return true;
	} else {
		return false;
	}
}

/**
 * 라디오버튼에 선택된 값을 반환한다.<br>
 * getRadioButtonValue(document.testForm.testRadio); 
 * @param radioElement
 * @returns
 */
function getRadioButtonValue( radioElement ) { 
    for (var i =0; i<radioElement.length; i++) { 
        if( radioElement[i].checked == true ) { 
            return radioElement[i].value; 
        } 
    }
}
function getSelectedValue( selectElement ) {
	return selectElement.options[selectElement.selectedIndex].value;
}



/**
 * 숫자만 입력받는다.
 **/
function onlyNum(event) {
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if( ( keyID >=48 && keyID <= 57 ) || ( keyID >=96 && keyID <= 105 ) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) {
        return;
    } else {
        return false;
    }
}

/**
 * 한글,영문,숫자만 입력가능합니다.
 * @param val
 * @returns {Boolean}
 */
function validKorEngNum(val) {
	val = val.replace(/^[0-9a-zA-Zㄱ-힣\s]*$/,"");
	if (val != ""){
		alert("한글,영문,숫자만 입력가능합니다.");
		return false;
	} else {
		return true;
	}
}
/**
 * 영문,숫자만 입력가능합니다.
 * @param val
 * @returns {Boolean}
 */
function validEngNum(val) {
	val = val.replace(/^[0-9a-zA-Z\s]*$/,"");
	if (val != ""){
		//alert("영문,숫자만 입력가능합니다.");
		return false;
	} else {
		return true;
	}
}
/**
 * 영문만 입력가능합니다.
 * @param val
 * @returns {Boolean}
 */
function validEng(val) {
	val = val.replace(/^[a-zA-Z\s]*$/,"");
	if (val != ""){
		alert("영문만 입력가능합니다.");
		return false;
	} else {
		return true;
	}
}
/**
 * 숫자만 입력가능합니다.
 * @param val
 * @returns {Boolean}
 */
function validNum(val) {
	val = val.replace(/^[0-9\s]*$/,"");
	if (val != ""){
		alert("숫자만 입력가능합니다.");
		return false;
	} else {
		return true;
	}
}
/**
 * 영문,숫자,일부 특수문자(-_.)만 입력가능합니다.
 * @param val
 * @returns {Boolean}
 */
function validEngNumSp(val) {
	val = val.replace(/^[0-9a-zA-Z-_.\s]*$/,"");
	if (val != ""){
		return false;
	} else {
		return true;
	}
}
/** 트림 **/
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/gi, "");
}

function autolink(id) {
    var str = $("#"+id).html();
    var regURL = new RegExp("(http|https|ftp|telnet|news|irc)://([-/.a-zA-Z0-9_~#%$?&=:200-377()]+)","gi");
    var regEmail = new RegExp("([xA1-xFEa-z0-9_-]+@[xA1-xFEa-z0-9-]+\.[a-z0-9-]+)","gi");
    $("#"+id).html(str.replace(regURL,"<a href='$1://$2' target='_blank'>$1://$2</a>").replace(regEmail,"<a href='mailto:$1'>$1</a>"));
}

/**
 * 비밀번호의 안전도를 "STRONG", "MEDIUM", "WEEK", "VERYWEEK"로 반환한다.
 * 최소 6자 기준
 * @param frm
 * @param obj
 * @returns {String}
 */
function passwordSafeCheck(frm, obj) {
	var userPw = obj.value;
	var points = userPw.length;
	var safeRate = "";
	var has_letter = new RegExp("[az]");
	var has_caps = new RegExp("[AZ]");
	var has_numbers = new RegExp("[0-9]");
	var has_symbols = new RegExp("\\W");

	if(has_letter.test(userPw)) { points += 3; }
	if(has_caps.test(userPw)) { points += 3; }
	if(has_numbers.test(userPw)) { points += 3; }
	if(has_symbols.test(userPw)) { points += 3; }
	
	
	if( points >= 16 ) {
		safeRate = "STRONG";
		$("#pwCheckResult").css("color","#48f");
		$("#pwCheckResult").html("안전한 비밀번호입니다.");
		frm.isCorrectPw.value = "1";
	} else if( points >= 12 ) {
		safeRate = "MEDIUM";
		$("#pwCheckResult").css("color","#0da");
		$("#pwCheckResult").html("사용할 수 있는 비밀번호입니다.");
		frm.isCorrectPw.value = "1";
	} else if( points >= 9 ) {
		safeRate = "WEEK";
		$("#pwCheckResult").css("color","#cb0");
		$("#pwCheckResult").html("사용할 수 있지만 취약한 비밀번호입니다.");
		frm.isCorrectPw.value = "1";
	} else {
		safeRate = "VERYWEEK";
		$("#pwCheckResult").css("color","#f84");
		$("#pwCheckResult").html("사용할 수 없는 비밀번호입니다.");
		frm.isCorrectPw.value = "0";
	}
	return safeRate;
}

function optionYear(selectorId) {
	var date = new Date();
	var thisYear = date.getFullYear();
	var i = thisYear;
	var str = "<option value=\"\">생년</option>";
	for (var i=0; i<80; i++) {
		str += "<option value=\""+(thisYear - i)+"\">"+(thisYear - i)+"</option>";
	}
	$("#"+selectorId).html(str);
}
function optionMonth(selectorId) {
	var str = "<option value=\"\">월</option>";
	for (var i=1; i<=12; i++) {
		str += "<option value=\""+i+"\">"+i+"</option>";
	}
	$("#"+selectorId).html(str);
}
function optionDay(selectorId) {
	var str = "<option value=\"\">일</option>";
	for (var i=1; i<=31; i++) {
		str += "<option value=\""+i+"\">"+i+"</option>";
	}
	$("#"+selectorId).html(str);
}
function phoneFormat(num) {
	return num.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
}
