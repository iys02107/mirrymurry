package com.codepresso3.mirrymurry.vo;

import com.codepresso3.mirrymurry.dto.ReviewFormDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Review {
    private Integer id;
    private String content;
    private Date reg_date;
    private Integer rate;
    private Integer book_id;
    private Integer store_id;

    private Integer member_id;

    public Review(Integer id, String content, Date reg_date, Integer rate, Integer book_id, Integer store_id, Integer member_id) {
        this.id = id;
        this.content = content;
        this.reg_date = reg_date;
        this.rate = rate;
        this.book_id = book_id;
        this.store_id = store_id;
        this.member_id = member_id;
    }

    public Review(ReviewFormDto reviewFormDto) {
        this.content = reviewFormDto.getContent();
        this.rate = reviewFormDto.getRate();
        this.book_id = reviewFormDto.getBook_id();
        this.store_id = reviewFormDto.getStore_id();
        this.member_id = reviewFormDto.getMember_id();
    }

}
