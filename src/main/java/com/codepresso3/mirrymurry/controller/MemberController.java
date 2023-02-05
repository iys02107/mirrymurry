package com.codepresso3.mirrymurry.controller;

import com.codepresso3.mirrymurry.constant.Role;
import com.codepresso3.mirrymurry.dto.*;
import com.codepresso3.mirrymurry.mapper.MemberMapper;
import com.codepresso3.mirrymurry.mapper.StoreMapper;
import com.codepresso3.mirrymurry.service.MemberService;
import com.codepresso3.mirrymurry.service.StoreImgService;
import com.codepresso3.mirrymurry.service.StoreService;
import com.codepresso3.mirrymurry.vo.Member;
import com.codepresso3.mirrymurry.vo.Store;
import com.codepresso3.mirrymurry.vo.StoreImg;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/members")
@Controller
public class MemberController {

    private MemberService memberService;
    private StoreService storeService;
    private PasswordEncoder passwordEncoder;

    private MemberMapper memberMapper;

    private StoreMapper storeMapper;

    public MemberController(MemberService memberService, StoreService storeService, PasswordEncoder passwordEncoder, MemberMapper memberMapper, StoreMapper storeMapper) {
        this.memberService = memberService;
        this.storeService = storeService;
        this.passwordEncoder = passwordEncoder;
        this.memberMapper = memberMapper;
        this.storeMapper = storeMapper;
    }

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/newMemberForm";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "member/newMemberForm";
        }

        try {
            Member member = new Member(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/newMemberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/store/new")
    public String storeForm(Model model){
        model.addAttribute("storeFormDto", new StoreFormDto());
        return "store/newStoreForm";
    }

    @PostMapping(value = "/store/new")
    public String newStore(@Valid StoreFormDto storeFormDto, BindingResult bindingResult, Model model, @RequestParam("storeImgFile") List<MultipartFile> storeImgFileList){

        if(bindingResult.hasErrors()){
            return "store/newStoreForm";
        }
        if(storeImgFileList.get(0).isEmpty()){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "store/newStoreForm";
        }

        try {
            Member member = new Member(storeFormDto, passwordEncoder);
            memberService.saveMember(member);
            Member member_id = memberMapper.findByEmail(storeFormDto.getEmail());
            Store store = new Store(member_id.getMember_id(),storeFormDto.getStore_info());
            storeMapper.createStore(store);
            storeService.saveStoreImg(store, storeImgFileList);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "store/newStoreForm";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "member/loginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/loginForm";
    }
    @GetMapping(value = "/myPage")
    public String myPage(Principal principal, Model model){
        Member member = memberMapper.findByEmail(principal.getName());
        model.addAttribute("name", member.getUser_name());
        return "member/myPage";
    }

    @GetMapping(value = "/memberDelete")
    public String memberDeleteForm(Principal principal, Model model){
        Member member = memberMapper.findByEmail(principal.getName());
        model.addAttribute("name", member.getUser_name());
        return "member/memberDeleteForm";
    }

    @PostMapping(value = "/memberDelete")
    public String memberDelete(@RequestParam("password") String pw, Principal principal, Model model) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Member member = memberMapper.findByEmail(principal.getName());
        model.addAttribute("name", member.getUser_name());
        if(!encoder.matches(pw, member.getPassword())) {
            model.addAttribute("pwCheckErrorMsg", "비밀번호가 일치하지 않습니다");
            return "member/memberDeleteForm";
        } else {
            memberMapper.deleteMember(member.getMember_id());
            SecurityContextHolder.clearContext();
            return "member/deleteAlert";
        }
    }

    @GetMapping(value = "/memberUpdate")
    public String memberUpdateForm(Principal principal, Model model){
        Member member = memberMapper.findByEmail(principal.getName());
        model.addAttribute("name", member.getUser_name());;
        model.addAttribute("memberUpdateDto",new MemberUpdateDto(member));
        return "member/memberUpdateForm";
    }
    @PostMapping(value = "/memberUpdate")
    public String memberUpdate(@Valid MemberUpdateDto memberUpdateDto, BindingResult bindingResult, Principal principal, Model model){
        if(bindingResult.hasErrors()){
            return "member/memberUpdateForm";
        }
        try{
            Member member = memberMapper.findByEmail(principal.getName());
            member.setPassword(passwordEncoder.encode(memberUpdateDto.getPassword()));
            memberService.updateMember(member, memberUpdateDto);
            return "member/updateAlert";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberUpdateForm";
        }
    }

}
