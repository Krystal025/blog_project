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
        Optional<MemberEntity> memberEntityById = memberRepository.findById(id);
        if(memberEntityById.isPresent()){
            // 아래 두줄의 코드를 한줄로 요약해서 리턴
            // MemberEntity memberEntity = memberEntityById.get();
            // MemberDto memberDto = MemberDto.toMemberDto(memberEntity);
            return MemberDto.toMemberDto(memberEntityById.get()); // get()으로 Optional을 벗겨냄
        }else{
            return null;
        }
    }

    // 회원정보 수정페이지 요청
    public MemberDto updateForm(String myEmail){
        Optional<MemberEntity> memberEntityByEmail = memberRepository.findByMemberEmail(myEmail);
        if(memberEntityByEmail.isPresent()){
            // 이메일이 조회되면 Optional 객체를 get()으로 벗겨내고, MemberDto 타입으로 변경
            return MemberDto.toMemberDto(memberEntityByEmail.get());
        }else{
            return null;
        }
    }

    // 회원정보 수정 기능
    public void update(MemberDto memberDto){
        // save() : ID가 없는 데이터는 INSERT 처리, ID가 있는 데이터는 UPDATE 처리함
        memberRepository.save(MemberEntity.toMemberEntity(memberDto));
    }


    // 회원정보 삭제 기능
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()){
            // 조회결과가 있다 = 사용 불가능
            return null;
        }else {
            // 조회결과가 없다 = 사용 가능
            return "ok";
        }
    }
}
