package com.green.nowon.domain.dto;

import java.time.LocalDateTime;

import com.green.nowon.domain.entity.ReplyEntity;

import lombok.Getter;
@Getter
//상세페이지에 표현할 댓글 데이터 : Entity ->DTO 매핑
public class ReplyListDTO {

	private long rno;
	private String text;
	//작성자::회원의 이메일로 적용 
	private String writer;
	
	private LocalDateTime updatedDate;

	
	public ReplyListDTO(ReplyEntity e) {
		
		this.rno = e.getRno();
		this.text = e.getText();
		this.writer = e.getMember().getEmail();
		this.updatedDate = e.getUpdatedDate();
	}
	
}
