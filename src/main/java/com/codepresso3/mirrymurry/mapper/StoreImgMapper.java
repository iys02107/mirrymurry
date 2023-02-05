package com.codepresso3.mirrymurry.mapper;

import com.codepresso3.mirrymurry.vo.StoreImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreImgMapper {
    Integer saveStoreImg(@Param("storeImg") StoreImg storeImg);

    StoreImg findById(@Param("img_id") Integer img_id);

    List<StoreImg> getStoreImgList(@Param("img_store_id") Integer store_id);

    Boolean deleteImg(@Param("img_id") Integer img_id);

    Integer updateImg(@Param("storeImg") StoreImg storeImg);

}
