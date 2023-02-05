package com.codepresso3.mirrymurry.dto;


import com.codepresso3.mirrymurry.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QnaMngDto {

    private Integer qna_id;

    private String title;

    private String content;

    private String answer;

    private Integer secret;

    private String qna_time;

    private String email;

    private String role;


}
