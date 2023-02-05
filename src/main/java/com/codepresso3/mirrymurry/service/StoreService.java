package com.codepresso3.mirrymurry.service;

import com.codepresso3.mirrymurry.dto.MainDto;
import com.codepresso3.mirrymurry.dto.StoreFormDto;
import com.codepresso3.mirrymurry.mapper.StoreImgMapper;
import com.codepresso3.mirrymurry.mapper.StoreMapper;
import com.codepresso3.mirrymurry.vo.Store;
import com.codepresso3.mirrymurry.vo.StoreImg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreMapper storeMapper;
    private final StoreImgService storeImgService;

    private final StoreImgMapper storeImgMapper;



    public void saveStoreImg(Store store, List<MultipartFile> storeImgFileList) throws Exception{
        Store store_id = storeMapper.findByMemberId(store.getMember_id());
        for(int i=0;i<storeImgFileList.size();i++){
            StoreImg storeImg = new StoreImg();
            if(i == 0)
                storeImg.setRepImgYn("Y");
            else
                storeImg.setRepImgYn("N");

            storeImgService.saveStoreImg(storeImg, storeImgFileList.get(i), store_id.getId());


        }
    }

    public Boolean updateStore(StoreFormDto storeFormDto, List<MultipartFile> storeImgFileList, Integer member_id) throws Exception{
        //상품 수정
        Store store = storeMapper.findByMemberId(member_id);
        store.setStore_info(storeFormDto.getStore_info());
        Integer result = storeMapper.updateStore(store);

        List<Integer> storeImgIds = storeFormDto.getStoreImgIds();

        //이미지 등록
        for(int i=0;i<storeImgFileList.size();i++){
            StoreImg storeImg = storeImgMapper.findById(storeImgIds.get(i));
            storeImgService.updateStoreImg(storeImgIds.get(i),
                    storeImgFileList.get(i));
//            storeImgService.saveStoreImg(storeImg,
//                    storeImgFileList.get(i), store.getId());
        }

        return result == 1;

    }

    public List<MainDto> getStoreList(){
        List<MainDto> mainDtoList = storeMapper.getStoreList();
        return mainDtoList;
    }
}