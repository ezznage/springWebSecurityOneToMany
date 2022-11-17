package com.green.nowon.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter//Conteoller 파라미터 매핑을 위해 세터메서드 필요
public class BoardUpdateDTO {

	private String title;
	private String content;
	
}
