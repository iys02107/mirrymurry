package com.codepresso3.mirrymurry.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewMngDto {
    private Integer review_id;

    private String content;

    private Integer rate;

    private String reg_date;

    private String email;


}
