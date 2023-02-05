package com.codepresso3.mirrymurry.controller;

import com.codepresso3.mirrymurry.dto.*;
import com.codepresso3.mirrymurry.mapper.MemberMapper;
import com.codepresso3.mirrymurry.mapper.MenuMapper;
import com.codepresso3.mirrymurry.mapper.StoreMapper;
import com.codepresso3.mirrymurry.mapper.WishMapper;
import com.codepresso3.mirrymurry.service.*;
import com.codepresso3.mirrymurry.vo.Member;
import com.codepresso3.mirrymurry.vo.Menu;
import com.codepresso3.mirrymurry.vo.Store;
import com.codepresso3.mirrymurry.vo.StoreImg;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RequestMapping("/stores")
@Controller
@RequiredArgsConstructor
public class StoreController {
    private final MemberMapper memberMapper;
    private final StoreMapper storeMapper;
    private final WishMapper wishMapper;
    private final MenuMapper menuMapper;

    private PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    private final StoreImgService storeImgService;

    private final StoreService storeService;
    private final ReviewService reviewService;
    private final QnaService qnaService;


    @RequestMapping("/storeDtl/{id}")
    public String storeDtl(@PathVariable("id")Integer store_id, Model model, Principal principal){
        Store store = storeMapper.findById(store_id);
        Member member = memberMapper.findById(store.getMember_id());
        List<Menu> manMenu = menuMapper.manMenuList(store.getId());
        List<Menu> womanMenu = menuMapper.womanMenuList(store.getId());
        List<StoreImg> storeImgList = storeImgService.storeImgList(store.getId());
        store.setStoreImgList(storeImgList);

        ReviewCalcDto temp = reviewService.calcReview(store_id);
        ReviewCalcDto reviewCalcDto = new ReviewCalcDto(temp.getRate(), temp.getReviewCount());
        List<ReviewDetailDto> reviewList = reviewService.getReviewList(store_id);

        Integer qnaNumber = qnaService.countQna(store_id);
        List<QnaDetailDto> qnaList = qnaService.getQnaList(store_id);

        if(principal!=null){
            Member currentMember = memberMapper.findByEmail(principal.getName());
            model.addAttribute("currentMember", currentMember);
            if(wishMapper.wishStatus(currentMember.getMember_id(),store_id) != null){
                model.addAttribute("wishStatus", true);
            } else {
                model.addAttribute("wishStatus", false);
            }
        }
        model.addAttribute("manMenu", manMenu);
        model.addAttribute("womanMenu", womanMenu);
        model.addAttribute("store", store);
        model.addAttribute("member", member);
        model.addAttribute("reviewCalc", reviewCalcDto);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("qnaNum", qnaNumber);
        model.addAttribute("qnaList", qnaList);

        return "store/storeDtl";
    }

    @GetMapping(value = "/storeUpdate")
    public String storeUpdateForm(Principal principal, Model model){
        Member member = memberMapper.findByEmail(principal.getName());
        Store store = storeMapper.findByMemberId(member.getMember_id());


        StoreFormDto storeFormDto = new StoreFormDto(member,store);
        List<StoreImg> storeImgList = storeImgService.storeImgList(store.getId());
        storeFormDto.setStoreImgList(storeImgList);

        model.addAttribute("member_id", member.getMember_id());
        model.addAttribute("storeFormDto", storeFormDto);

        return "store/newStoreUpdateForm";
    }
    @PostMapping(value = "/storeUpdate")
    public String storeUpdate(@Valid StoreFormDto storeFormDto, Principal principal,
                              @RequestParam("storeImgFile") List<MultipartFile> storeImgFileList, Model model){

        try {
            Member member = memberMapper.findByEmail(principal.getName());
            member.updateMember(storeFormDto, member.getMember_id());
//            member.setPassword(passwordEncoder.encode(storeFormDto.getPassword()));
            memberService.updateStore(member);
            storeService.updateStore(storeFormDto, storeImgFileList, member.getMember_id());

        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "/main";
        }

        return "store/updateStoreAlert";
    }


    @GetMapping(value = "/storePage")
    public String storePage(Principal principal, Model model){
        Member member = memberMapper.findByEmail(principal.getName());
        model.addAttribute("name", member.getUser_name());
        return "store/storePage";
    }


}
