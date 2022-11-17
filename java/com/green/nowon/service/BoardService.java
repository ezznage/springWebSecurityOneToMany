package com.green.nowon.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.green.nowon.domain.dto.BoardSaveDTO;
import com.green.nowon.domain.dto.BoardUpdateDTO;

public interface BoardService {

	void getListAll(int page, Model model);

	void sendDetail(long bno, Model model);

	void save(BoardSaveDTO dto, String name);

	void save(BoardSaveDTO dto);

	void update(long bno, BoardUpdateDTO dto);

	void delete(long bno);

	void updateProc(long bno, BoardUpdateDTO dto);

	void save(String text, long bno, Authentication auth);

}