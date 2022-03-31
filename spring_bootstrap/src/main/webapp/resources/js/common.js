//팝업창 띄우기
//새로운 Window창을 Open할 경우 사용되는 함수(arg:주소, 창타이틀, 넓이, 길이)
function OpenWindow(UrlStr, WinTitle, WinWidth, WinHeight){
		winleft = (screen.width - WinWidth) / 2;
		wintop = (screen.height - WinHeight) / 2;
		var win = window.open(UrlStr, WinTitle, "scrollbars=yes, width="+ WinWidth + ","
								+ "height="+ WinHeight + ", top="+ wintop + ", left="
								+ winleft + ", resizable=yes, status=yes");
		win.focus();
}

//팝업창 닫기
function CloseWindow(parentURL){
	window.opener.location.reload(true);
	window.close();
}


//사용자 사진 출력
function MemberPictureThumb(targetObj, fileName, contextPath){ //(대상, 이미지파일명)
	
	if(!contextPath) contextPath="";
	
	targetObj.style.backgroundImage="url('"+contextPath+"/member/getPicture.do?picture="+fileName+"')";
	targetObj.style.backgroundPosition="center";
	targetObj.style.backgroundRepeat="no-repeat";
	targetObj.style.backgroundSize="cover";
}


//spring_security redirect loginForm
function AjaxErrorSecurityRedirectHandler(status){
	if(status == "302"){
		alert("세션이 만료되었습니다.\n로그인하세요.");
		location.reload();//로그인화면으로 돌릴려고하는것.
		
	}else if(status == "403"){
		alert("권한이 유효하지 않습니다.");
		history.go(-1); //로그인화면의 권한이없기때문에 뒤로가기.
		
	}else{
		alert("시스템장애로 실행이 불가합니다.");
		history.go(-1);
	}
}