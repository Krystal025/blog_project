package com.project.blog.member.repository;

import com.project.blog.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // Optional : Null방지 (일반적으로 1개를 조회할 때는 Optional로 감싸서 넘겨줌)
    // 이메일로 회원정보 조회  (SELECT * FROM member_table WHERE member_email=")
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
