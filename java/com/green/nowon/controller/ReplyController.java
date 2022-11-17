package com.green.nowon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.nowon.service.BoardService;

@Controller
public class ReplyController {
	
	@Autowired
	private BoardService service;
	
	@PostMapping("/boards/{bno}/replies")
	public String save(String text, @PathVariable long bno, Authentication auth) {
		//입력한 댓글, 게시글 번호, 작성자 정보
		
		service.save(text, bno, auth);
		
		
		return "redirect:/boards/{bno}";
	}

}