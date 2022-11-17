package com.green.nowon.domain.entity;

import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.green.nowon.domain.dto.BoardUpdateDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "gen_j_board_seq",
		sequenceName = "j_board_seq", initialValue = 1, allocationSize = 1)
@Table(name = "j_board")
@Entity
public class BoardEntity extends BaseDateTimeColumns{
	
	@Id
	@GeneratedValue(generator = "gen_j_board_seq", strategy = GenerationType.SEQUENCE)
	private long bno;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;
	private int readCount;
	//작성자 - MemeberEntity
	
	//@JoinColumn이 명시되면 주엔티티
	@JoinColumn(name = "mno")//물리DB에 생성되는 FK컬럼명 : 생략시 필드명_pk컬럼 (member_mno)
	//부모엔티티에서 자식 엔티티의 상태변화의 영향을 주는 것 : cascade
	@ManyToOne(cascade = CascadeType.DETACH)
	private MemberEntity member; //작성자 

	//1:M 단방향설정::BoardEntity->ReplyEntity
	@Builder.Default
	@JoinColumn(name = "bno")//FK생성 : ReplyEntity에 생성
	@OneToMany(cascade = CascadeType.ALL)//단방향설정에서 @JoinColumn 명시하지않으면 별도의 연계테이블이 생성된다.
	//양방향설정시에는 @OneToMany(mapprdBy = "필드명") 를 이용하여 연계테이블을 제거할 수 있다.  
	private List<ReplyEntity> replies=new Vector<>();
	
	//편의메서드
	public BoardEntity addReply(ReplyEntity reply) {
		replies.add(reply);
		return this;
	}
	
	//편의메서드
	public BoardEntity update(BoardUpdateDTO dto) {
		this.title=dto.getTitle();
		this.content=dto.getContent();
		return this;
		
		
	}

	
}
