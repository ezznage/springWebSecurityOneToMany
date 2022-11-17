package com.green.nowon;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.green.nowon.domain.entity.BoardEntity;
import com.green.nowon.domain.entity.BoardEntityRepository;
import com.green.nowon.domain.entity.MemberEntity;
import com.green.nowon.domain.entity.MemberEntityRepository;
import com.green.nowon.domain.entity.ReplyEntity;
import com.green.nowon.domain.entity.ReplyEntityRepository;
import com.green.nowon.security.MyRole;

@SpringBootTest
class SpringWebSecurity03ApplicationTests {

	@Autowired
	BoardEntityRepository bRepository;
	
	@Autowired
	MemberEntityRepository mRepository;
	
	@Autowired
	ReplyEntityRepository rRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@Commit
	@Transactional
	//@Test
	void board를이용해서댓글저장() {
		long bno=107;
		long mno=21;
		bRepository.findById(bno).orElseThrow()
		.addReply(ReplyEntity.builder()
				.text(bno+"게시글에 대한 댓굴")
				.member(MemberEntity.builder().mno(mno).build())
				.build());
	}
	
	
	
	//@test
	void 댓글저장bno에null로저장됩니다() {
		//long bno=0;
		long mno=2;
		ReplyEntity entity=ReplyEntity.builder()
				.text("댓글입니다.")
				.member(MemberEntity.builder().mno(mno).build())
				.build();
		rRepository.save(entity);
	}
	
	//@Test
	void 회원가입() {
		
		IntStream.rangeClosed(1, 20).forEach(i->{
		String email="test"+i+"@test.com";
		mRepository.save(MemberEntity.builder()
				.email(email)
				.name("테스트"+i)
				.pass(encoder.encode("1234"))
				.nickName("test"+i+"Nick")
				.build()
				.addRole(MyRole.USER)
				);	
		});
	}
	//@Test
    void 관리자() {
        String email = "admin@test.com";
        mRepository.save(MemberEntity.builder()
                .email(email)
                .name("관리자")
                .pass(encoder.encode("1234"))
                .nickName("admin")
                .build().addRole(MyRole.GUEST).addRole(MyRole.USER).addRole(MyRole.ADMIN));

    }
	//@Test
	void 게시글저장() {
		MemberEntity me=MemberEntity.builder()
				.mno(3)
				.build();
		BoardEntity entity=BoardEntity.builder()
				.title("게시글제목22").content("내용테스트22")
				.member(me)
				.build();
		bRepository.save(entity);
	}

	//@Test
	void 게시글저장2() {
		mRepository.findAll().forEach(e->{

			IntStream.rangeClosed(1, 5).forEach(i->{
				bRepository.save(BoardEntity.builder()
						
						.member(e)//작성자
						.title(e.getNickName()+"이 작성한 제목"+i)
						.content(e.getNickName()+"이 작성한 내용"+i)
						.build());
			});
		});	
		
	
	}
	
}
