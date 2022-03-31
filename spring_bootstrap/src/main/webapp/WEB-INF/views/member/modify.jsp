<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<title>회원수정</title>

<body>

<!-- Content Wrapper. Contains page content -->
<div>
 <section class="content-header">
	  	<div class="container-fluid">
	  		<div class="row md-2">
	  			<div class="col-sm-6">
	  				<h1>수정페이지</h1>  				
	  			</div>
	  			<div class="col-sm-6">
	  				<ol class="breadcrumb float-sm-right">
			        <li class="breadcrumb-item">
			        	<a href="#">
				        	<i class="fa fa-dashboard">회원관리</i>
				        </a>
			        </li>
			        <li class="breadcrumb-item active">
			        	수정
			        </li>		        
	    	  </ol>
	  			</div>
	  		</div>
	  	</div>
	</section> 
<!-- Main content -->
<section class="content register-page" >
<!-- 동기방식이기때문에 form안에 들어있는 모든 데이터가 한꺼번에 submit!! 통째로 데이터가 전달. -->
	<form role="form" class="form-horizontal" action="modify" method="post" enctype="multipart/form-data">	
		<div class="card" style="min-width:450px;">	
			<div class="card-body">	
				<div class="row">					
					<input type="hidden" name="oldPicture"  value="${member.picture }"/><!-- <-기존에 있는 파일 여기에 보관 -->
					<!-- input태그 picture 선택을 안해서 없으면 기존 사진 띄우려고 oldPicture. 선택하지않는한 file안에value가 안들어감. type="file"안에 절대 value안들어감 딱하나 empty만 들어감. 그래서 이전에 파일명을 어디다는 보관을 해야함. 그것을 oldPicture에다가 주었음. -->
					<input type="file" id="inputFile" onchange="changePicture_go();" name="picture" style="display:none" /><!-- 선택된 파일 -->
					<div class="input-group col-md-12">
						<div class="col-md-12" style="text-align: center;">
							<div class="" id="pictureView" style="border: 1px solid green; height: 200px; width: 140px; margin: 0 auto; margin-bottom:5px;"></div>														
							<div class="input-group input-group-sm">
								<label for="inputFile" class=" btn btn-warning btn-sm btn-flat input-group-addon">사진변경</label>
								<input id="inputFileName" class="form-control" type="text" name="tempPicture" disabled
									value="${member.picture }"/>
								<input id="picture" class="form-control" type="hidden" name="uploadPicture" /><!-- 이 행이 또 있는 이유 : 변경된 파일을 확인하려고 서버에서는 사용하는 데이터는 아님 받아도 그만 안받아도 그만... -->
							</div>						
						</div>												
					</div>
				</div>	
				<div class="form-group row">
					<label for="id" class="col-sm-3 control-label text-center">아이디</label>	
					<div class="col-sm-9">
						<input readonly name="id" type="text" class="form-control" id="id"
							placeholder="13글자 영문자,숫자 조합" value="${member.id }">
					</div>
				</div>
				
				<div class="form-group row">
					<label for="pwd" class="col-sm-3 control-label text-center" >패스워드</label>

					<div class="col-sm-9">
						<input name="pwd" type="password" class="form-control" id="pwd"
							placeholder="20글자 영문자,숫자,특수문자 조합" value="${member.pwd}">
					</div>
				</div>
				<div class="form-group row">
					<label for="pwd" class="col-sm-3 control-label text-center" >이 름</label>

					<div class="col-sm-9">
						<input name="name" type="text" class="form-control" id="name"
							placeholder="20글자 영문자,숫자,특수문자 조합" value="${member.name }">
					</div>
				</div>
						
									
				<div class="form-group row">
					<label for="authority" class="col-sm-3 control-label text-center" >권 한</label>
					<div class="col-sm-9">
						<select name="authority" class="form-control">
							<option ${member.authority eq 'ROLE_USER' ? 'selected' : '' } value="ROLE_USER">사용자</option>
							<option ${member.authority eq 'ROLE_MANAGER' ? 'selected' : '' } value="ROLE_MANAGER">운영자</option>
							<option ${member.authority eq 'ROLE_ADMIN' ? 'selected' : '' } value="ROLE_ADMIN">관리자</option>
						</select>
					</div>
				</div>
				
				<div class="form-group row">
					<label for="email" class="col-sm-3 control-label text-center">이메일</label>

					<div class="col-sm-9">
						<input name="email" type="email" class="form-control" id="email"
							placeholder="example@naver.com" value="${member.email }">
					</div>
				</div>
				<div class="form-group row">
                <label for="phone" class="col-sm-3 control-label text-center">전화번호</label>
                <div class="col-sm-9">   
                	<input name="phone" type="text" class="form-control" id="inputPassword3" value="${member.phone }">	                
                </div>                  
              </div>  
				
				<div class="card-footer row" style="margin-top: 0; border-top: none;">						
					<button type="button" id="modifyBtn"  onclick="modify_go();"
						class="btn btn-warning col-sm-4 text-center" >수정하기</button>
					<div class="col-sm-4"></div>
					<button type="button" id="cancelBtn" onclick="history.go(-1);"
						class="btn btn-default pull-right col-sm-4 text-center">취 소</button>
				</div>
			</div>
		</div>
	</form>
</section>
  <!-- /.content -->
</div>

<script>

window.onload=function(){ 
	MemberPictureThumb($('#pictureView')[0],'${member.picture}','<%=request.getContextPath()%>');
}


function changePicture_go(){
	//alert('test');
	var picture = $('input#inputFile')[0]; //[0]: 자바스크립트 객체 0번지 그래야 파일 가져올수 있음 자바스크립트 자체에는 파일을 핸들링할수잇는기능이 없음 jquery에있음.
	
	var fileFormat = picture.value.substr(picture.value.lastIndexOf(".")+1).toUpperCase();
	
	//이미지 확장자 jpg확인
	 if(!(fileFormat=="JPG" || fileFormat=="JPEG")){
		 alert("이미지는 jpg 형식만 가능합니다.");
		 return;
	 }
	 
	//이미지 파일 용량 체크
	if(picture.files[0].size>1024*1024*1){
		alert("사진 용량은 1MB 이하만 가능합니다.");
		return;
	};
	
	
	document.getElementById('inputFileName').value=picture.files[0].name;
	
	if(picture.files && picture.files[0]){
		var reader = new FileReader();
		reader.onload = function(e){
			$('div#pictureView')
				.css({'background-image' : 'url('+e.target.result+')',
					'background-position' : 'center',
					'background-size' : 'cover',
					'background-repeat' : 'no-repeat'
					});
		}
		reader.readAsDataURL(picture.files[0]);
		
		
	}
	
	$('input[name="uploadPicture"]').val(picture.files[0].name);
}

function modify_go(){
	/* alert("test"); */
	//dom방식 jquery방식을 다 할수 있음.
	//javascript를 잘하면 기능을 운영할수있는 많은 fect가 생김.
	var form=$('form[role="form"]');
	form.submit();
}

</script>
 
</body>