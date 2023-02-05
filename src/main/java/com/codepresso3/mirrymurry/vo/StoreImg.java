package com.codepresso3.mirrymurry.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StoreImg {

    private Integer img_id;

    private String imgName; //이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 조회 경로

    private String repImgYn; //대표 이미지 여부

    private Integer img_store_id;

    public StoreImg(){

    }

    public StoreImg(String imgName, String oriImgName, String imgUrl, String repImgYn, Integer img_store_id){
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repImgYn = repImgYn;
        this.img_store_id=img_store_id;
    }

    public void updateItemImg(Integer img_id, String oriImgName, String imgName, String imgUrl) {
        this.img_id = img_id;
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
