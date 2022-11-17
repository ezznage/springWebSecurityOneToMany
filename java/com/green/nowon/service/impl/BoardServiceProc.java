package com.green.nowon.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.nowon.domain.dto.BoardDetailDTO;
import com.green.nowon.domain.dto.BoardListDTO;
import com.green.nowon.domain.dto.BoardSaveDTO;
import com.green.nowon.domain.dto.BoardUpdateDTO;
import com.green.nowon.domain.dto.ReplyListDTO;
import com.green.nowon.domain.entity.BoardEntity;
import com.green.nowon.domain.entity.BoardEntityRepository;
import com.green.nowon.domain.entity.MemberEntity;
import com.green.nowon.domain.entity.ReplyEntity;
import com.green.nowon.domain.entity.ReplyEntityRepository;
import com.green.nowon.security.MyUserDetails;
import com.green.nowon.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceProc implements BoardService {

	
    private final BoardEntityRepository repository;
    private final ReplyEntityRepository replies;
  
    @Override
    public void getListAll(int page, Model model) {
        //Entity자체(DB 테이블 자체)를 모델에 넣으면 다른 값(pass) 등을 참조할 수 있으므로
        //DTO로 매핑해서 가져가는것이 맞다..!

        //int page=2;
        int size=10;
        Sort sort=Sort.by(Direction.DESC,"bno");

        Pageable pageable = PageRequest.of(page-1,size,sort);
        Page<BoardEntity> result = repository.findAll(pageable);
        //List<BoardEntity> list = result.getContent();
     //  int pageTot=result.getTotalPages();
      // result.
       	model.addAttribute("p",result);
//       	System.out.println("getNumber() : "+result.getNumber());
//       	System.out.println("getNumberOfElements() : "+result.getNumberOfElements());
//       	System.out.println("isFirst() : "+result.isFirst());
//       	System.out.println("isLast() : "+result.isLast());
//       	System.out.println("hasNext() : "+result.hasNext());
//       	System.out.println("hasPrevious() : "+result.hasPrevious());
//       
        model.addAttribute("list",result.stream()
                //.map(ent->new BoardListDTO(ent))
                .map(BoardListDTO::new)
                .collect(Collectors.toList()) );
        /*
        model.addAttribute("list",repository.findAllByOrderByBnoDesc(Pageable).stream()
                //.map(ent->new BoardListDTO(ent))
                .map(BoardListDTO::new)
                .collect(Collectors.toList()) );
        */
        
    }
    @Transactional//댓글을 읽어오기위해서 
    @Override
	public void sendDetail(long bno, Model model) {
		
		//Id : pk컬럼 
		//Optional<BoardEntity> result=repository.findById(bno);
		model.addAttribute("detail", repository.findById(bno)
				.map(BoardDetailDTO::new)
				.orElseThrow());
	
		
		
    }
    @Override
	public void save(BoardSaveDTO dto, String email) {
	}
    
	@Override
	public void save(BoardSaveDTO dto) {
		repository.save(dto.toBoardEntity());
	}

	

	@Override
	public void delete(long bno) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(long bno, BoardUpdateDTO dto) {
		//수정처리 대상은 bno(pk), 수정할 데이터 dto:제목,내용	
		//1.대상의 존재여부 조회
		Optional<BoardEntity> result= repository.findById(bno);
		//2.존재하면 수정
		if(result.isPresent()) {
			BoardEntity entity=result.get();
			//수정하기위한 편의메서드 아니면 setter메서드 이용
			entity.update(dto);
			///원본-업데이트 반영
			repository.save(null);//이미 존재하는 PK면 수정반영 된다!
		}
	}
	//수정처리 간결하게 가능함!
	@Transactional
	@Override
	public void updateProc(long bno, BoardUpdateDTO dto) {
		System.out.println(">>>>>>>>>>>>수정처리");
		repository.findById(bno).map(entity->entity.update(dto));
		System.out.println(">>>>>>>>>>>>수정처리완료");
	}//@Transactional 메서드가 끝나면 commit 이루어지므로 이전에 Entity객체가 변경되면 update가 반영
	
	//OneToMany를 이용한 댓글저장
	@Transactional
	@Override
	public void save(String text, long bno, Authentication auth) {
		MyUserDetails userInfo = (MyUserDetails)auth.getPrincipal();
		long mno=userInfo.getMno();
		
		BoardEntity entity =repository.findById(bno).get().addReply(ReplyEntity.builder()
				.text(text)
				.member(MemberEntity.builder().mno(mno).build())
				.build());
		
		//repository.save(entity);
	}
//	@Override
//	public void save(BoardSaveDTO dto) {
//		//repository.save(dto.toBoardEntity());
//		BoardEntity entity=BoardEntity.builder()
//				.title(dto.getTitle()).content(dto.getContent())
//				.member(MemberEntity.builder().mno(dto.getMno()).build())
//				.build();
//		repository.save(entity);
//		
//	}
	
}