package com.codepresso3.mirrymurry.dto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class QnaDetailDto {

    private Integer qna_id;

    private String title;

    private String content;

    private String answer;

    private Integer secret;

    private String qna_time;

    private String user_name;

    private String email;

    private Integer qna_member_id;

    private Integer qna_store_id;

}
