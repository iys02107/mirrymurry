package com.codepresso3.mirrymurry.service;

import com.codepresso3.mirrymurry.dto.QnaDetailDto;
import com.codepresso3.mirrymurry.dto.QnaMngDto;
import com.codepresso3.mirrymurry.mapper.QnaMapper;
import com.codepresso3.mirrymurry.vo.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaMapper qnaMapper;

    public List<QnaDetailDto> getQnaList(Integer store_id){return qnaMapper.getQnaList(store_id);}

    public List<QnaMngDto> getQnaMngList(Integer store_id){
        return qnaMapper.getQnaMngList(store_id);
    }

    public List<QnaMngDto> getQnaListByMemberId(Integer member_id){
        return qnaMapper.getQnaListByMemberId(member_id);
    }

    public Boolean deleteQna(Integer qna_id){
        Integer result = qnaMapper.deleteQna(qna_id);
        return result == 1;
    }

    public Boolean newQna(Qna qna){
        Integer result = qnaMapper.newQna(qna);
        return result == 1;
    }

    public Boolean newAnswer(String answer, Integer qna_id){
        Integer result = qnaMapper.newAnswer(answer, qna_id);
        return result == 1;
    }

    public Integer countQna(Integer store_id){
        Integer result = qnaMapper.countQna(store_id);
        return result;
    }


}
