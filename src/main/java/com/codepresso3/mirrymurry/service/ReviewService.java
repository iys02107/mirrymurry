package com.codepresso3.mirrymurry.service;

import com.codepresso3.mirrymurry.dto.ReviewCalcDto;
import com.codepresso3.mirrymurry.dto.ReviewDetailDto;
import com.codepresso3.mirrymurry.dto.ReviewMngDto;
import com.codepresso3.mirrymurry.mapper.ReviewMapper;
import com.codepresso3.mirrymurry.vo.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    public List<ReviewMngDto> getReviewMngList(Integer store_id){return reviewMapper.getReviewMngList(store_id);}
    public List<ReviewMngDto> getMyReviewMngList(Integer member_id){return reviewMapper.getMyReviewMngList(member_id);}

    public List<ReviewDetailDto> getReviewList(Integer store_id){return reviewMapper.getReviewList(store_id);}

    public Boolean deleteReview(Integer review_id){
        Integer result = reviewMapper.deleteReview(review_id);
        return result == 1;
    }

    public ReviewCalcDto calcReview(Integer store_id){
        ReviewCalcDto reviewCalcDto = reviewMapper.calcReview(store_id);
        return reviewCalcDto;
    }

    public boolean saveReview(Review review){
        Integer result = reviewMapper.saveReview(review);
        return result==1;
    }

}
