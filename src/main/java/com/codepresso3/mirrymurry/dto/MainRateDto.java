package com.codepresso3.mirrymurry.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MainRateDto {
    private double rate;

    public MainRateDto(double rate) {
        this.rate = Math.round(rate*10)/10.0;
    }
}
