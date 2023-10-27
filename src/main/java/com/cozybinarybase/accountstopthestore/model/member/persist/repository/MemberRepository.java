package com.cozybinarybase.accountstopthestore.model.member.persist.repository;

import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

}
