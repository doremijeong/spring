<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<script>
function summernote_go(target){
	target.summernote({
		placeholder : '여기에 내용을 적으세요.',
		lang : 'ko-KR',
		height : 250,
		disableResizeEditor : true,
		callbacks:{//이게 없으면 기존 default값을준다.
			onImageUpload : function(files, editor, welEditable){//file : javascript file,
				for(var file of files){
					//alert(file.name);//file선택할때마다 alert문 뜸. file은 안들어오지만.어떤 file인지 알고싶을때 file.name
					if(file.name.substring(file.name.lastIndexOf(".")+1).toUpperCase() != "JPG"){
						alert("JPG 이미지형식만 가능합니다.");
						return;
					}
					if(file.size > 1024 * 1024 * 5){
						alert("이미지는 5MB 미만입니다.");
						return;
					}
				}
	
				for(var file of files){
					sendFile(file,this);//this : textarea
				}
			},
			onMediaDelete : function(target){
				//alert(target.attr("src"));
				//alert(target[0].src);//순수 자바스크립트로 꺼내올땐 [0]째로꺼내오면 위와 같다.
				
				//파일명 =구분자로 뽑아와서 그 파일명을 삭제한걸 날려서 삭제하려고하는과정.
				/* var splitSrc = target[0].src.split("=");
				var fileName = splitSrc[splitSrc.length-1];
				alert(fileName); *///여기서 한글이 깨져서나옴.
				//src에 file을 추출하는 방법.(파라미터)의 구현방법에 따라 달라질수있음. 위에서 파일명을 추출했기때문에 fileName을 넘겨도 되고, target을 넘겨도 된다.
				deleteFile(target[0].src);
			}
		}
	}); 
		
}


//이미지태그는 사이지를 지정하지않으면 이미지 크기대로 나옴.
function sendFile(file, el){//el => this
	var form_data = new FormData();
	form_data.append("file",file);
	$.ajax({
		data : form_data,
		type : "POST",
		url : '<%=request.getContextPath() %>/uploadImg.do',
		cache: false,
		contentType : false,
		processData : false,
		success : function(img_url){
			//alert(img_url);
			$(el).summernote('editor.insertImage',img_url); //editor.insertImage를 summernote가 호출하고, img_url: src에 주어짐.
			// <img src="img_url"/>
		},
		error:function(){
			alert(file.name + "업로드에 실패했습니다.");
		}
	});
}


function deleteFile(src){
	var splitSrc = src.split("=");
	var fileName = splitSrc[splitSrc.length-1];
	
	var fileData = {fileName:fileName};
	
	$.ajax({
		url : "<%=request.getContextPath()%>/deleteImg.do",
		data : JSON.stringify(fileData),//stringify:
		type : "post",
		contentType : "application/json",
		success : function(res){
			console.log(res);
		},
		error:function(){
			alert('이미지 삭제가 불가합니다.');
		}
	});
}
</script>
