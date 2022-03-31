<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>    

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.7/handlebars.min.js"></script>
<script type="text/x-handlebars-template"  id="reply-list-template" >
{{#each .}}
<div class="replyLi" >
   <div class="user-block">
      <img src="<%=request.getContextPath()%>/member/getPictureById.do/{{replyer}}" class="img-circle img-bordered-sm"/>
    </div>
   
    <div class="timeline-item" >
        <span class="time">
          <i class="fa fa-clock"></i>{{prettifyDate regdate}}
          <a class="btn btn-primary btn-xs {{rno}}-a" id="modifyReplyBtn" data-rno={{rno}}
            onclick="replyModifyModal_go('{{rno}}');"            
            style="display:{{VisibleByLoginCheck replyer}};"
             data-replyer={{replyer}} data-toggle="modal" data-target="#modifyModal">Modify</a>
        </span>
   
        <h3 class="timeline-header"><strong style="display:none;">{{rno}}</strong>{{replyer}}</h3>
        <div class="timeline-body" id="{{rno}}-replytext">{{replytext}} </div>
   </div>
</div>
{{/each}}
</script>
<!-- 새로 만들어서 JSON객체 안에 넣어야한다. -->
<!-- PageNum -->
<!-- prevPageNum -->
<!-- nextPageNum -->
<script type="text/x-handlebars-template"  id="reply-pagination-template" >
<li class="paginate_button page-item">
   <a href="1" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">
      <i class='fas fa-angle-double-left'></i>
   </a>
</li>
<li class="paginate_button page-item">
   <a href="{{#if prev}}{{prevPageNum}}{{/if}}" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">
      <i class='fas fa-angle-left'></i>
   </a>
</li>
{{#each pageNum}}
<li class="paginate_button page-item {{signActive this}} ">
   <a href="{{this}}" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">
      {{this}}
   </a>
</li>
{{/each}}

<li class="paginate_button page-item ">
   <a href="{{#if next}}{{nextPageNum}}{{/if}}" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">
      <i class='fas fa-angle-right'></i>
   </a>
</li>
<li class="paginate_button page-item">
   <a href="{{realEndPage}}" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">
      <i class='fas fa-angle-double-right'></i>
   </a>
</li>   
</script>


<script>
window.onload=function(){
   getPage("<%=request.getContextPath()%>/replies/${board.bno}/"+replyPage);
   
   //직접 function 걸기 -
   $('ul.pagination').on('click','li a',function(event){
      if($(this).attr("href")){
         replyPage=$(this).attr("href");
         getPage("<%=request.getContextPath()%>/replies/${board.bno}/"+replyPage);
      }
      
      
      return false;
   })
   
}


var replyPage = 1;

// JQUERY로 맞추어 작성할것 - handlebar 사용시
function printData(replyArr,target,templateObject){
   var template = Handlebars.compile(templateObject.html());
   var html = template(replyArr);
   $('.replyLi').remove();
   target.after(html);
}

function printPagenation(pageMaker, target, templateObject){
   var pageNum = new Array(pageMaker.endPage - pageMaker.startPage + 1);
   for(var i = 0 ; i < pageMaker.endPage-pageMaker.startPage + 1; i++){
      pageNum[i] = pageMaker.startPage + i;
   }
   pageMaker.pageNum = pageNum;
   pageMaker.prevPageNum = pageMaker.startPage - 1;
   pageMaker.nextPageNum = pageMaker.endPage + 1;

   var template = Handlebars.compile(templateObject.html());
   var html = template(pageMaker);
   target.html("").html(html);
}


function getPage(pageInfo){
	
	$.ajax({
		url:pageInfo,
		type:"get",
		success:function(data){
			printData(data.replyList,$('#repliesDiv'),$('#reply-list-template'));
		    printPagenation(data.pageMaker,$('ul#pagination'),$('#reply-pagination-template'));
		},
		error:function(error){
			AjaxErrorSecurityRedirectHandler(error.status);
		}
	})
   /* $.getJSON(pageInfo,function(data){
      printData(data.replyList,$('#repliesDiv'),$('#reply-list-template'));
      printPagenation(data.pageMaker,$('ul#pagination'),$('#reply-pagination-template'));
   }); */
}

Handlebars.registerHelper({
   "prettifyDate" : function(timeValue){
      var dateObj = new Date(timeValue);
      var year = dateObj.getFullYear();
      var month = dateObj.getMonth() + 1;
      var date = dateObj.getDate();
      return year + "/" + month + "/" + date;
   },
   "VisibleByLoginCheck":function(replyer){
      var result = "none";
      if(replyer == "${loginUser.id}") result="visible";
      return result;
   },
   "signActive": function(pageNum){
      if(pageNum == replyPage) return 'active';
   }
});
function replyRegist_go(){
   let bno = document.querySelector("input[name='bno']").value;
   let replytext = document.querySelector('#newReplyText').value;
   let replyer = "${loginUser.id}";    //객체 자체가 아닌 객체가 문자화된 것을 저장한다.
   if(!replytext){
      alert('내용은 필수입니다.');
      return;
   }
   //원칙은 ""+ bno +"" 로 보내는 것이 맞다.
   let data = {
            "bno" : bno,
            "replyer" : replyer,
            "replytext" : replytext 
            }
   
   $.ajax({
      url: "<%=request.getContextPath()%>/replies",
      type:"post",
      data:JSON.stringify(data),
      contentType:'application/json',
      success:function(data){
         alert('댓글이 등록되었습니다.\n마지막 페이지로 이동합니다.');
         replyPage = data;
         getPage("<%=request.getContextPath()%>/replies/"+bno+"/"+data); //리스트 출력
         $('#newReplyText').val("");
      },
      error:function(){
         //alert('댓글 등록에 실패했습니다.')
    	  AjaxErrorSecurityRedirectHandler(error.status);
      }
   })
   
}

function replyModifyModal_go(rno){
   $('div#modifyModal div.modal-body #replytext').val($('div#'+rno+'-replytext').text());
   $('div#modifyModal div.modal-header h4.modal-title').text(rno);
   
}

function replyModify_go(){
   var rno =$('.modal-title').text();
   var replytext = $('#replytext').val();
   
   var sendData={
         "rno" : rno,
         "replytext" : replytext
   }
   
   
   $.ajax({
      url:"<%=request.getContextPath()%>/replies/"+rno,
      type:"put",
      data:JSON.stringify(sendData),
      headers:{
    	  "X-HTTP-Method-Override":"PUT"
      },
      contentType:"application/json",
      success:function(result){
         alert("수정되었습니다.");
         getPage("<%=request.getContextPath()%>/replies/${board.bno}/"+replyPage);
      },
      error:function(){
         //alert("수정에 실패했습니다.");
    	  AjaxErrorSecurityRedirectHandler(error.status);
      },
      complete:function(){
         $('#modifyModal').modal('hide');
      }
   });
}


function replyRemove_go(){
	//alert('delete btn');
	var rno=$('.modal-title').text();
	
	$.ajax({
		url:"<%=request.getContextPath()%>/replies/${board.bno}/"+rno+"/"+replyPage,
		type:"delete",
		success:function(page){
			alert("삭제되었습니다.");
			getPage("<%=request.getContextPath()%>/replies/${board.bno}/"+page);
			replyPage=page;
		},
		error:function(error){
			//alert('삭제 실패했습니다.');
			AjaxErrorSecurityRedirectHandler(error.status);
		},
		complete:function(){
			$('#modifyModal').modal('hide');
		}
	});
}
</script>