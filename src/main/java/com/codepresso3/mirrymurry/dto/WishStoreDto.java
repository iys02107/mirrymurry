package com.codepresso3.mirrymurry.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class WishStoreDto {
    private Integer wish_id;
    private Integer store_id;
    private String user_name;
    private String road_address;
    private String detail_address;
    private String imgUrl;

    private List<WishStoreDto> wishStoreDtoList;

}
