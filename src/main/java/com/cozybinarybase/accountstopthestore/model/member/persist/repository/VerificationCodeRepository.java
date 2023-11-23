package com.cozybinarybase.accountstopthestore.model.member.persist.repository;

import com.cozybinarybase.accountstopthestore.model.member.persist.entity.VerificationCode;
import org.springframework.data.repository.CrudRepository;

public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
}
