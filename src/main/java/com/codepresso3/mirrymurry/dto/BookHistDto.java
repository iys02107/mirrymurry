package com.codepresso3.mirrymurry.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BookHistDto {
    private String reviewStatus;
    private String user_name;
    private String road_address;
    private String detail_address;
    private String imgUrl;
    private Integer store_id;
    private Integer book_id;
    private String book_date;
    private String book_time;
    private String menu_name;
    private String category;
    private Integer price;
    private Integer member_id;

    private List<BookHistDto> bookHistDtoList;
}
