package kr.or.ddit.scheduler;

import java.io.File;

import kr.or.ddit.service.spring.ScheduledMemberServiceForModify;

//사용자가 사용하지 않는사진들을 폴더에서 삭제하기위한작업.
//하나밖에없을땐 새로 service를 만들어도되지만, 연습삼아 extends해서 사용해보자.
//쿼리문에서 getPicture를 가지고 와서 비교함.
public class RemoveMemberPictureScheduler {

	private ScheduledMemberServiceForModify scheduledMemberService;
	public void setScheduledMemberService(ScheduledMemberServiceForModify schduledMemberService) {
		this.scheduledMemberService=schduledMemberService;
	}
	
	private String picturePath;
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	
	public void removePicture()throws Exception{
		File dir = new File(picturePath);
		File[] files = dir.listFiles();
		
		if(files != null) {
			for(File file : files) {
				if(scheduledMemberService.getMemberByPicture(file.getName())==null) {
					file.delete();
				}
			}
		}
	}
}
