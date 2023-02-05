package com.codepresso3.mirrymurry.controller;

import com.codepresso3.mirrymurry.dto.MainDto;
import com.codepresso3.mirrymurry.dto.MainRateDto;
import com.codepresso3.mirrymurry.dto.StoreFormDto;
import com.codepresso3.mirrymurry.dto.StoreImgDto;
import com.codepresso3.mirrymurry.mapper.MemberMapper;
import com.codepresso3.mirrymurry.mapper.ReviewMapper;
import com.codepresso3.mirrymurry.mapper.StoreMapper;
import com.codepresso3.mirrymurry.service.StoreImgService;
import com.codepresso3.mirrymurry.service.StoreService;
import com.codepresso3.mirrymurry.vo.Member;
import com.codepresso3.mirrymurry.vo.Store;
import com.codepresso3.mirrymurry.vo.StoreImg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final StoreService storeService;
    private final StoreImgService storeImgService;
    private final StoreMapper storeMapper;
    private final MemberMapper memberMapper;
    private final ReviewMapper reviewMapper;

    @GetMapping(value = "/")
    public String main(Model model){

        List<MainDto> storeList = storeService.getStoreList();
        for(int i=0;i<storeList.size();i++){
            Integer store_id = storeList.get(i).getStore_id();

            if(reviewMapper.getRate(store_id) == 0d){
                storeList.get(i).setRate(0d);
            }
            double rate1 = reviewMapper.getRate(store_id);
            MainRateDto rateDto = new MainRateDto(rate1);
            double rate = rateDto.getRate();
            storeList.get(i).setRate(rate);
        }

        model.addAttribute("stores", storeList);

        return "main";
    }

}