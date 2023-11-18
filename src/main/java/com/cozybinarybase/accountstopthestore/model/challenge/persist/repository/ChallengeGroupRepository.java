package com.cozybinarybase.accountstopthestore.model.challenge.persist.repository;

import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.ChallengeGroupEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeGroupRepository extends JpaRepository<ChallengeGroupEntity, Long> {

  Optional<ChallengeGroupEntity> findByInviteLink(String inviteLink);

  List<ChallengeGroupEntity> findByMember(MemberEntity entity);
}
