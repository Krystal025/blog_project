package com.project.blog.member.service;

import com.project.blog.member.dto.MemberDto;
import com.project.blog.member.entity.MemberEntity;
import com.project.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    }

    // 로그인 기능
    public MemberDto login(MemberDto memberDto) {  // 로그인되었을 때 해당 정보를 Controller에 Dto로 넘겨줌
        // 1. 회원이 입력한 이메일을 DB에서 조회
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDto.getMemberEmail());
        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 확인
        if(byMemberEmail.isPresent()) {
            // 조회결과가 있을 경우
            // get() : Optional 내부의 객체를 가져옴 (Optional을 벗겨내야 Entity객체 사용가능)
            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDto.getMemberPassword())){
                // 비밀번호가 일치하는 경우
                MemberDto dto = MemberDto.toMemberDto(memberEntity);
                return dto;
            }else{
                // 비밀번호가 불일치하는 경우
                return null;
            }
        }else{
            // 조회결과가 없을 경우
            return null;
        }
    }

    // 회원목록 조회 기능
    public List<MemberDto> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();
        // Entity 리스트를 Dto 리스트로 변환 (하나씩 꺼내서 배열에 담아야함)
        for (MemberEntity memberEntity : memberEntityList){
            MemberDto memberDto = MemberDto.toMemberDto(memberEntity);
            memberDtoList.add(memberDto);
        }
        return memberDtoList;
    }

    // 회원 상세정보 조회 기능
    public MemberDto findById(Long id) {
        System.out.println("id 조회 확인"+id);
        Optional<MemberEntity> byId = memberRepository.findById(id);
        if(byId.isPresent()){
            // 조회하는 Id가 있을 경우
            MemberEntity memberEntity = byId.get();  // get()으로 Optional을 벗겨냄
            MemberDto memberDto = MemberDto.toMemberDto(memberEntity);
            return memberDto;
        }else{
            // 조회하는 Id가 없을 경우
            return null;
        }
    }

}
