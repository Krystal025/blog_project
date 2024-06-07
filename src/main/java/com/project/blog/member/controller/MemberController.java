package com.project.blog.member.controller;

import com.project.blog.member.dto.MemberDto;
import com.project.blog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지 요청
    @GetMapping("/member/signup")
    public String signupForm(){
        return "signup";  // templates 폴더의 signup.html 파일을 찾아줌 (thymeleaf의 기능)
    }

    // 회원가입 처리
    @PostMapping("/member/signup")
    public String signup(@ModelAttribute MemberDto memberDto){
        System.out.println("MemberController.signup");
        System.out.println("memberDto = " + memberDto);
        memberService.signup(memberDto);
        return "index";
    }

}
