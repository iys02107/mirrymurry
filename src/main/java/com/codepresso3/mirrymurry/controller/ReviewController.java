package com.codepresso3.mirrymurry.controller;

import com.codepresso3.mirrymurry.constant.Role;
import com.codepresso3.mirrymurry.dto.ReviewFormDto;
import com.codepresso3.mirrymurry.dto.ReviewMngDto;
import com.codepresso3.mirrymurry.mapper.BookMapper;
import com.codepresso3.mirrymurry.mapper.MemberMapper;
import com.codepresso3.mirrymurry.mapper.StoreMapper;
import com.codepresso3.mirrymurry.service.ReviewService;
import com.codepresso3.mirrymurry.vo.Book;
import com.codepresso3.mirrymurry.vo.Member;
import com.codepresso3.mirrymurry.vo.Review;
import com.codepresso3.mirrymurry.vo.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/reviews")
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final MemberMapper memberMapper;
    private final StoreMapper storeMapper;
    private final BookMapper bookMapper;

    private final ReviewService reviewService;


    @GetMapping(value = "/reviewMng")
    public String reviewMng(Principal principal, Model model){
        Member member = memberMapper.findByEmail(principal.getName());
        if(member.getRole().equals(Role.STORE)){
            Store store = storeMapper.findByMemberId(member.getMember_id());
            List<ReviewMngDto> reviewMngDtoList = reviewService.getReviewMngList(store.getId());
            model.addAttribute("role", member.getRole());
            model.addAttribute("reviews", reviewMngDtoList);
        } else {
            List<ReviewMngDto> reviewMngDtoList = reviewService.getMyReviewMngList(member.getMember_id());
            model.addAttribute("name", member.getUser_name());
            model.addAttribute("role", member.getRole());
            model.addAttribute("reviews", reviewMngDtoList);
        }
        return "review/reviewMng";
    }

    @DeleteMapping(value = "/delete/{review_id}")
    @ResponseBody
    public String reviewDelete(@PathVariable("review_id") Integer review_id , Principal principal){
        reviewService.deleteReview(review_id);
        return "리뷰가 삭제되었습니다.";
    }

    @GetMapping(value = "/new/{book_id}")
    public String reviewForm(Model model, @PathVariable("book_id") Integer book_id){
        model.addAttribute("reviewFormDto", new ReviewFormDto());
        Book book = bookMapper.findById(book_id);
        model.addAttribute("book", book);
        return "review/reviewForm";
    }

    @PostMapping(value = "/new")
    public String newReview(@Valid ReviewFormDto reviewFormDto, Model model){

        try {
            Review review = new Review(reviewFormDto);
            reviewService.saveReview(review);
        } catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "review/reviewForm";
        }

        return "review/reviewAlert";
    }

}
