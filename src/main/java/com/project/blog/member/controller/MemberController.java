package com.project.blog.member.controller;

import com.project.blog.member.dto.MemberDto;
import com.project.blog.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
        return "login";
    }

    // 로그인 페이지 요청
    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }

    // 로그인 처리
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession session){
        MemberDto loginResult =  memberService.login(memberDto);
        if(loginResult != null){
            // 로그인 성공 (로그인한 회원의 이메일을 loginEmail이라는 이름으로 세션에 담아줌)
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        }else{
            // 로그인 실패
            return "login";
        }
    }

    // 회원목록 페이지 요청
    @GetMapping("/member/")
    public String list(Model model){  // html로 가져갈 데이터가 있을 때 Model을 사용
        List<MemberDto> memberDtoList = memberService.findAll();
        // Model의 addAttribute : Controller에서 View로 데이터를 전달하기 위해 사용
        model.addAttribute("memberList", memberDtoList);
        return "list";
    }


    // 회원정보 상세페이지 요청
    @GetMapping("/member/{id}")
    public String detail(@PathVariable("id") Long id, Model model){  // @PathVariable : 경로 상의 값을 가져올 때 사용
        MemberDto memberDto = memberService.findById(id);
        model.addAttribute("member",memberDto);
        if(memberDto != null){
            return "detail";
        }else{
            return "list";
        }
    }

    // 회원정보 수정페이지 요청
    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDto);
        return "update";
    }

    // 회원정보 수정 처리
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDto memberDto){
        memberService.update(memberDto);
        // 회원정보를 수정한 상세페이지를 보여줌
        return "redirect:/member/" + memberDto.getId();
    }

    // 회원정보 삭제 처리
    @GetMapping("/member/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        memberService.deleteById(id);
        return "redirect:/member/";
    }

    // 로그아웃 처리
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        // invalidate() : 현재 세션을 무효화하는 메소드
        session.invalidate();
        return "index";
    }

}
