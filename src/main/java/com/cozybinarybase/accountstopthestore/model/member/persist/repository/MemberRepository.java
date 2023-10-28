package com.cozybinarybase.accountstopthestore.model.member.persist.repository;

import com.cozybinarybase.accountstopthestore.model.member.dto.constants.AuthType;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

  Optional<MemberEntity> findByEmail(String email);
  Optional<MemberEntity> findByRefreshToken(String refreshToken);

  /**
   * 소셜 타입과 소셜의 식별값으로 회원 찾는 메소드
   * 정보 제공을 동의한 순간 DB에 저장해야 하지만, 아직 추가 정보(구글에서 넘겨주는 정보 이외의 것)를 입력받지 않았으므로
   * 유저 객체는 DB에 있지만, 추가 정보가 빠진 상태 이다.
   * 따라서 추가 정보를 입력받아 회원 가입을 진행할 때 소셜 타입, 식별자로 해당 회원을 찾기 위한 메소드
   */
  Optional<MemberEntity> findByAuthTypeAndOauthId(AuthType authType, String oauthId);

}
