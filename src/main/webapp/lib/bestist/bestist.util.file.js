/**
 * 파일 존재여부
 */
function UrlExist(_url){
	var http = new XMLHttpRequest();
    http.open('HEAD', _url, false);
    http.send();
    return http.status!=404;
}


/**
 * JS 파일을 동적으로 로딩한다.
 */
function loadJavascript(url, callback, charset) {
	var head= document.getElementsByTagName('head')[0];
	var script= document.createElement('script');
	script.type= 'text/javascript';
	if (charset != null) {
		script.charset = "utf-8";
	}
	var loaded = false;
	//IE인 경우, script.onreadystatechange를 이용해서 로딩 완료 여부를 확인한다.
	script.onreadystatechange= function () {
	if (this.readyState == 'loaded' || this.readyState == 'complete') {
		if (loaded) {
			return;
		}
			loaded = true;
			callback();
		}
	}
	//Firefox, Chrome 등, script.onload를 이용해서 로딩 완료 여부를 확인한다.
	script.onload = function () {
		callback();
	}
	script.src = url;
	head.appendChild(script);
}


var pictureSource;
var destinationType;


/**
 * 갤러리를 선택했을 때
 * @param thumbTarget
 */
function takeGallery(onCameraSuccess) {
	if(isAndroid) {
		window.MYAPP.getFile();
	} else {
		navigator.camera.getPicture(onCameraSuccess, onCameraError, {
			destinationType : destinationType.FILE_URI,
			sourceType : pictureSource.PHOTOLIBRARY
		});
	}
}
/**
 * 카메라를 선택했을 때
 * @param thumbTarget
 */
function takeCamera(onCameraSuccess) {
	navigator.camera.getPicture(onCameraSuccess, onCameraError);
}
function onCameraError(e) {
	navigator.notification.alert("이미지를 불러오지 못했습니다.\n"+ e);
}

/**
 * 파일 업로드
 * @param filePath
 * @param fileName
 * @param onUploadSuccess
 */
function uploadFile(filePath, fileName, onUploadSuccess) {
	try {
		var imageURL = "";
		
		var options = new FileUploadOptions();
		
		// 같은 파일을 중복해 올리거나 했을 때 랜덤으로 발생하던 error code 3을 해결하는 코드
		options.headers = {
			Connection: "close"
		}
		options.chunkedMode = false;
		
		options.fileKey = "file";
		options.mimeType = "image/jpeg";
		var params = new Object();
		params.filePath = filePath;
			
		options.fileName = fileName.substr(fileName.lastIndexOf("/")+1);
		params.fileName = fileName.substr(fileName.lastIndexOf("/")+1);
		options.params = params;

		var fileTransfer = new FileTransfer();
		//alert("uploadFile() : \n"+ fileName +"\n"+filePath +"\n"+host+"/file_upload.go");

		fileTransfer.upload(fileName, host+"/file_upload.go?filePath="+filePath, onUploadSuccess, onUploadError, options, false);
		
	} catch (e) {
		alert("파일저장 에러\n"+ e.message);
	}
}
function onUploadError(e) {
	navigator.notification.alert("파일이 저장되지 않았습니다.\nmsg:"+e.message+"\ncode:"+e.code);
}


// 이미지 파일 걸러내기
function isImage(file){
	banArray = new Array(".jpg", ".jpeg", ".png", ".gif");    // 걸러낼 확장자를 등록

	banFile = false;

	while (file.indexOf("\\") != -1)
	file = file.slice(file.indexOf("\\") + 1);

	//파일명에 .이 포함되어있을 경우(fileName123.jpg.asp.dll)를 대비하여 마지막 확장자만 추출
	ban = file.substring(file.lastIndexOf('.'),file.length).toLowerCase();    

	for (var i = 0; i < banArray.length; i++) {
		if (banArray[i] == ban) {
			banFile = true;
			break;
		}
	}
	return banFile;
	/*
	if (banFile == true) {
		alert(ban + " 파일은 첨부할 수 없는 파일입니다.");
		return;
	}
	*/
	

}