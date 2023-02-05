package com.codepresso3.mirrymurry.mapper;

import com.codepresso3.mirrymurry.dto.QnaDetailDto;
import com.codepresso3.mirrymurry.dto.QnaMngDto;
import com.codepresso3.mirrymurry.vo.Qna;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface QnaMapper {

    List<QnaDetailDto> getQnaList(@Param("store_id") Integer store_id);
    List<QnaMngDto> getQnaMngList(@Param("store_id") Integer store_id);

    List<QnaMngDto> getQnaListByMemberId(@Param("member_id") Integer member_id);

    Integer deleteQna(@Param("qna_id") Integer qna_id);

    Qna findById(@Param("qna_id") Integer qna_id);

    Integer newQna(@Param("qna") Qna qna);

    Integer newAnswer(@Param("answer") String answer, @Param("qna_id") Integer qna_id);

    Integer countQna(@Param("store_id") Integer store_id);
}
