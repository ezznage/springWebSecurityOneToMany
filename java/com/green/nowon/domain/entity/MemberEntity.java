package com.green.nowon.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.green.nowon.security.MyRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SequenceGenerator(name = "g_mem_seq",
	sequenceName = "mem_seq", initialValue = 1, allocationSize = 1)
@Table(name = "mem")
@Entity
public class MemberEntity extends BaseDateTimeColumns{

	@Id
	@GeneratedValue(generator = "g_mem_seq", strategy = GenerationType.SEQUENCE)
	private long mno;
	
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String pass;
	@Column(nullable = false)
	private String name;
	@Column(unique = true)
	private String nickName;
	
	//social uesr 여부
	//탈퇴여부 필드
	//휴면여부 필드
	//userip 등 회원가입에 필요한 사항들 추가로 적용
	
	//1:M -- 회원:MyRole(enum)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	@CollectionTable(name="my_role")
	@ElementCollection(fetch = FetchType.EAGER)
	Set<MyRole> roles=new HashSet<>();
	
	public MemberEntity addRole(MyRole role) {
		roles.add(role);
		return this;
	}
	
	
	
	
	
}