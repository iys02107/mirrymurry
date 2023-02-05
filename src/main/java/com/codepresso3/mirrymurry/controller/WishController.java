package com.codepresso3.mirrymurry.controller;

import com.codepresso3.mirrymurry.dto.WishRequestDto;
import com.codepresso3.mirrymurry.dto.WishStoreDto;
import com.codepresso3.mirrymurry.mapper.MemberMapper;
import com.codepresso3.mirrymurry.service.WishService;
import com.codepresso3.mirrymurry.vo.Member;
import com.codepresso3.mirrymurry.vo.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    private final MemberMapper memberMapper;

    @PostMapping("/wish")
    @ResponseBody
    public String storeWish(@RequestBody WishRequestDto wishRequestDto) {
        Wish wish = wishRequestDto.getWish();
        wishService.wishStore(wish);
        return "success";
    }

    @DeleteMapping("/unWish")
    @ResponseBody
    public String storeUnWish(@RequestBody WishRequestDto wishRequestDto){
        Wish wish = wishRequestDto.getWish();
        wishService.unWishStore(wish);
        return "success";
    }

    @GetMapping(value = "/wish")
    public String wishList(Principal principal, Model model){
        Member member = memberMapper.findByEmail(principal.getName());
        List<WishStoreDto> wishStoreDtoList = wishService.getWishList(member.getMember_id());
        model.addAttribute("wishStoreDtoList", wishStoreDtoList);
        model.addAttribute("name",member.getUser_name());
        return "member/wishList";
    }

    @DeleteMapping(value = "/unWishList")
    public @ResponseBody String deleteWishItems(@RequestBody WishStoreDto wishStoreDto){
        List<WishStoreDto> wishStoreDtoList = wishStoreDto.getWishStoreDtoList();
        for(WishStoreDto wishStoreDto1 : wishStoreDtoList){
            Integer wish_id = wishStoreDto1.getWish_id();
            wishService.unWishList(wish_id);
        }
        return "success";
    }
}
