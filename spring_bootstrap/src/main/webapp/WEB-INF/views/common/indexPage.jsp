<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>


<head></head>

<title></title>

<body>

	<div class="content-wrapper" style="background-color:#fff;">
	 	<iframe name="ifr" src="" frameborder="0" style="width:100%;height:85vh;"></iframe>
 	</div>



<!-- REQUIRED SCRIPTS -->
<script>

function goPage(url,mCode){
	//alert(url);
	//alert(mCode);
	//$('iframe[name="ifr"]').attr("src",url);//스크립트 다 읽은 뒤 실행하기대문에 $사용가능 attr안에 onchange자동으로 걸려있음.
	document.querySelector('iframe[name="ifr"]').src=url;//전처리
	
	
	  // HTML5 지원브라우저에서 사용 가능
	if (typeof(history.pushState) == 'function') {
	    //현재 주소를 가져온다.
	    var renewURL = location.href;
	    //현재 주소 중 .do 뒤 부분이 있다면 날려버린다.
	    renewURL = renewURL.substring(0, renewURL.indexOf(".do")+3);
	    
	    if (mCode != 'M000000') {
	        renewURL += "?mCode="+mCode;
	    }
	    //페이지를 리로드하지 않고 페이지 주소만 변경할 때 사용
	    history.pushState(mCode, null, renewURL);
	    //history.pushState(state,title[,url]);메서드는 브라우저의 세션기록 스택에 상태를 추가함.
	 
	} else {
	    location.hash = "#"+mCode;
	}
}

function subMenu_go(mCode){
	//alert(mCode);
	if(mCode!="M000000"){	
		//ajax형식을 약식으로 만ㄷ르었음, 간단히 사용할때, 근데 이 메세지는 error피드백을 하지 않음.
		$.getJSON("<%=request.getContextPath()%>/subMenu.do?mCode="+mCode,function(data){
		//console.log(data);	
			//handlebars 탬플릿 호출	
			printData(data,$('.subMenuList'),$('#subMenu-list-template'),'.subMenu');
							//target 				template			removeClass
		});
	}else{
		$('.subMenuList').html("");		
	}
}


//handelbars printElement (args : data Array, appent target, handlebar template, remove class)
function printData(dataArr,target,templateObject,removeClass){
	
	var template=Handlebars.compile(templateObject.html());//컴파일시켜줌.
	
	var html = template(dataArr);//컴파일 후에 실행.
	
	/* 순수 자바스크립트로 하고싶다. 하지만 인터넷 익스플로어에선 안됨.*/
	//document.querySelectorAll(removeClass).remove();
	
	/* jQuery선택자, css,등 잘되있지만, 데이터를 flow할때는 한계가 있다. */
	$(removeClass).remove();//그전에 있는데이터 삭제.
	
	target.append(html);//html객체를 target객체의 마지막에 추가한다.
}


window.onload=function(){//어딘가에 또 window.onload=function()은 한번만써야함. 또 있으면, 에러뜸. 그게 어디든 에러남. 이페이지가 포함되게끔 인벨리드형태로...
	goPage('<%=request.getContextPath()%>${menu.murl}','${menu.mcode}');
	subMenu_go('${menu.mcode}'.substring(0,3)+"0000");
	/* 강제로 호출한번해줘야 유지되어짐 goPage처럼.. 서브메뉴자체는 유지되지만 그걸가지고 한 행위는 유지되지않음. 굳이 해줄필요없음 안되는건 안되는거!! 메뉴에서 펼쳤다가 새로고침하면 다시 닫힘. 새로고침은 새로고침답게 처음에 열린 그대로 보여주려고하는것. */
}



</script>

<!-- handlebars로 subMenu에 필요한 템플릿 만듬 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.7/handlebars.min.js" ></script>
<script type="text/x-handlebars-template"  id="subMenu-list-template" >
{{#each .}}
	<li class="nav-item subMenu" >
    	<a href="javascript:goPage('<%=request.getContextPath()%>{{murl}}','{{mcode}}');" 
			class="nav-link">
        	<i class="{{micon}}"></i>
            <p>{{mname}}</p>
       	</a>
	</li>
{{/each}}
</script>
</body>











