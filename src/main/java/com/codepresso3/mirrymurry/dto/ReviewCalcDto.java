package com.codepresso3.mirrymurry.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewCalcDto {


    private Double rate;
    private Integer reviewCount;

    public ReviewCalcDto(Double rate, Integer reviewCount){
        this.rate = Math.round(rate*10)/10.0;
        this.reviewCount = reviewCount;
    }
}
