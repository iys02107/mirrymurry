package com.codepresso3.mirrymurry.controller;

import com.codepresso3.mirrymurry.constant.Role;
import com.codepresso3.mirrymurry.dto.QnaFormDto;
import com.codepresso3.mirrymurry.dto.QnaMngDto;
import com.codepresso3.mirrymurry.mapper.MemberMapper;
import com.codepresso3.mirrymurry.mapper.QnaMapper;
import com.codepresso3.mirrymurry.mapper.StoreMapper;
import com.codepresso3.mirrymurry.service.QnaService;
import com.codepresso3.mirrymurry.vo.Member;
import com.codepresso3.mirrymurry.vo.Qna;
import com.codepresso3.mirrymurry.vo.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/qna")
@Controller
@RequiredArgsConstructor
public class QnaController {

    private final MemberMapper memberMapper;
    private final StoreMapper storeMapper;

    private final QnaService qnaService;

    private final QnaMapper qnaMapper;


    @GetMapping(value = "/qnaMng")
    public String qnaMng(Principal principal, Model model){
        Member member = memberMapper.findByEmail(principal.getName());
        if(member.getRole().equals(Role.STORE)) {
            Store store = storeMapper.findByMemberId(member.getMember_id());
            List<QnaMngDto> qnaMngDtoList = qnaService.getQnaMngList(store.getId());

            model.addAttribute("role", member.getRole());
            model.addAttribute("qnaList", qnaMngDtoList);
        }else{
            List<QnaMngDto> qnaMngDtoList = qnaService.getQnaListByMemberId(member.getMember_id());
            model.addAttribute("role", member.getRole());
            model.addAttribute("name", member.getUser_name());
            model.addAttribute("qnaList", qnaMngDtoList);
        }
        return "qna/qnaMng";
    }

    @GetMapping("/new/{store_id}")
    public String qnaForm(Model model, @PathVariable("store_id") Integer store_id){
        model.addAttribute("store_id", store_id);
        model.addAttribute("qnaFormDto", new QnaFormDto());
        return "qna/qnaForm";
    }
    @PostMapping("/new")
    public String newQna(@Valid QnaFormDto qnaFormDto, @RequestParam("store_id") Integer store_id, Principal principal){
        Member member = memberMapper.findByEmail(principal.getName());
        qnaFormDto.setQna_member_id(member.getMember_id());
        qnaFormDto.setQna_store_id(store_id);

        Qna qna = new Qna(qnaFormDto);
        qnaService.newQna(qna);
        return "qna/qnaAlert";

    }

    @GetMapping(value = "/answer/new/{qna_id}")
    public String answerForm(Model model, @PathVariable("qna_id") Integer qna_id){
        Qna qna = qnaMapper.findById(qna_id);
        model.addAttribute("qna",qna);
        model.addAttribute("id",qna_id);
        return "qna/answerForm";
    }

    @PostMapping(value = "/answer/new")
    public String newAnswer(@RequestParam("answer") String answer, @RequestParam("qna_id") Integer qna_id){
        qnaService.newAnswer(answer, qna_id);
        return "qna/answerAlert";
    }

}
