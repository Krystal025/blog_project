package com.project.blog.member.service;

import com.project.blog.member.dto.MemberDto;
import com.project.blog.member.entity.MemberEntity;
import com.project.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입 기능
    public void signup(MemberDto memberDto){
        // 1. Dto => Entity로 변환 (Repository에는 Entity객체로 넘겨줘야하기 때문)
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
        // 2. Repository의 save메소드를 호출 (save() : JPA에서 제공하는 기본 메소드)
        memberRepository.save(memberEntity);

    };

}
