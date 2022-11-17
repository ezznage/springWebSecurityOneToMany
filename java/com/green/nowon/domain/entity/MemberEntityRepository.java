package com.green.nowon.domain.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity,Long> {
    //jpa에서 기본으로 제공하고 있는 쿼리 메서드를 이용할 수 있음
    //개발자가 필요로하는 쿼리 메서드를 구성할 수 있는 영역
    //메서드 생성시 메서드명 생성 규칙을 이용하여 사용하거나
    //@Query 어노테이션을 이용하여 @Query("JPQL 언어로 쿼리문 작성") 하여 이용할 수 있다.
    //JPQL -> MariaDB106Dialect
    Optional<MemberEntity> findByEmail(String username);    //문법에 맞지 않으면 실행오류

}