package com.green.nowon.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.green.nowon.domain.entity.BoardEntity;

import lombok.Getter;

@Getter
public class BoardDetailDTO {

    private long bno;
    private String title;
    private String content;
    private int readCount;
    private long writerMno;
    private String writerEmail;
    private String writerNickName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private List<ReplyListDTO> replies;     //댓글
    public BoardDetailDTO(BoardEntity entity) {
        this.bno = entity.getBno();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.readCount = entity.getReadCount();
        this.writerMno = entity.getMember().getMno();
        this.writerEmail = entity.getMember().getEmail();    //작성자는 이메일 정보로 대체(닉네임 쓰는게 보통일듯)
        this.writerNickName = entity.getMember().getNickName();
        this.createdDate = entity.getCreadtedDate();
        this.updatedDate = entity.getUpdatedDate();

        replies = entity.getReplies()
                .stream()
                .map(ReplyListDTO::new)
                .collect(Collectors.toList()); //List<ReplyListDTO>
    }
}