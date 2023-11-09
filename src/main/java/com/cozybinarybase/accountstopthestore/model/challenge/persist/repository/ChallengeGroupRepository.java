package com.cozybinarybase.accountstopthestore.model.challenge.persist.repository;

import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.ChallengeGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeGroupRepository extends JpaRepository<ChallengeGroupEntity, Long> {

}
