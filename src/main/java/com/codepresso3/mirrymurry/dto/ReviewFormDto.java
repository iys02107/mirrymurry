package com.codepresso3.mirrymurry.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ReviewFormDto {

    private String content;

    private int rate;

    private Integer store_id;

    private Integer member_id;

    private Integer book_id;
}