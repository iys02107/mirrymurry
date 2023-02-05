package com.codepresso3.mirrymurry.vo;

import com.codepresso3.mirrymurry.dto.QnaFormDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Qna {
    private Integer qna_id;
    private String title;
    private String content;
    private String answer;
    private Integer secret;
    private Date qna_time;
    private Integer qna_member_id;
    private Integer qna_store_id;

    public Qna(Integer qna_id, String title, String content, String answer, Integer secret, Date qna_time, Integer qna_member_id, Integer qna_store_id) {
        this.qna_id = qna_id;
        this.title = title;
        this.content = content;
        this.answer = answer;
        this.secret = secret;
        this.qna_time = qna_time;
        this.qna_member_id = qna_member_id;
        this.qna_store_id = qna_store_id;
    }

    public Qna(QnaFormDto qnaFormDto){
        this.title = qnaFormDto.getTitle();
        this.content = qnaFormDto.getContent();
        this.secret = qnaFormDto.getSecret();
        this.qna_member_id = qnaFormDto.getQna_member_id();
        this.qna_store_id = qnaFormDto.getQna_store_id();
    }
}
