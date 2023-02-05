package com.codepresso3.mirrymurry.mapper;


import com.codepresso3.mirrymurry.dto.ReviewCalcDto;
import com.codepresso3.mirrymurry.dto.ReviewDetailDto;
import com.codepresso3.mirrymurry.dto.ReviewMngDto;

import com.codepresso3.mirrymurry.vo.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface ReviewMapper {

    List<ReviewMngDto> getReviewMngList(@Param("store_id") Integer store_id);

    List<ReviewMngDto> getMyReviewMngList(@Param("member_id") Integer member_id);

    List<ReviewDetailDto> getReviewList(@Param("store_id") Integer store_id);

    Integer deleteReview(@Param("review_id") Integer review_id);

    Double getRate(@Param("store_id") Integer store_id);

    ReviewCalcDto calcReview(@Param("store_id") Integer store_id);

    Integer saveReview(@Param("review")Review review);
}
