package com.green.nowon.domain.dto;

import com.green.nowon.domain.entity.BoardEntity;
import com.green.nowon.domain.entity.MemberEntity;

import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class BoardSaveDTO { //write페이지에서 입력한 제목, 내용 --> DB

	//write.html에 작성되는 데이터를 매핑하기위한 편의 메서드
	private String title;//method mapping
	private String content;//method mapping
//	private MemberEntity member;
	private long mno;
	
	//셋팅된 dto data -> Entity객체로 변환
//	public BoardEntity toEntity() {
//		return BoardEntity.builder()
//				.title(title).content(content).member(member)
//				.build();
//	}
//	
	//셋팅된 dto data->Entity객체로 변환
		public BoardEntity toBoardEntity() {
			return BoardEntity.builder()
					.title(title).content(content).member(MemberEntity.builder().mno(mno).build())
					.build();
		}
}
