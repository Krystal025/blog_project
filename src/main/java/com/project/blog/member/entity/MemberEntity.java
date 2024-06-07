package com.project.blog.member.entity;

import com.project.blog.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Entity : 테이블 역할, 정의한대로 DB에 테이블이 생성됨 (Jpa : 테이블을 Java객체처럼 사용하라 수 있게 해줌)
@Entity
@Getter
@Setter
@Table(name = "member_table") //DB에 생성될 테이블명
public class MemberEntity {

    @Id //PK처리
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(unique = true) //unique 제약조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    // MemberDto를 받아서 MemberEntity로 변환하는 메소드
    public static MemberEntity toMemberEntity(MemberDto memberDto){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberName(memberDto.getMemberName());
        return memberEntity;
    }

}
