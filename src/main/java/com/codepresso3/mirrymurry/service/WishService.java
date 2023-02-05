package com.codepresso3.mirrymurry.service;

import com.codepresso3.mirrymurry.dto.WishStoreDto;
import com.codepresso3.mirrymurry.mapper.MemberMapper;
import com.codepresso3.mirrymurry.mapper.WishMapper;
import com.codepresso3.mirrymurry.vo.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishMapper wishMapper;
    private final MemberMapper memberMapper;

    public boolean wishStore(Wish wish){
        Integer result = wishMapper.wishStore(wish);
        return  result == 1;
    }

    public boolean unWishStore(Wish wish){
        Integer result = wishMapper.unWishStore(wish);
        return  result == 1;
    }

    public List<WishStoreDto> getWishList(Integer member_id){
        List<WishStoreDto> wishStoreDtoList = wishMapper.getWishList(member_id);
        return wishStoreDtoList;
    }


    public boolean unWishList(Integer wish_id) {
        Integer result = wishMapper.unWishStoreList(wish_id);
        return result == 1;
    }
}
