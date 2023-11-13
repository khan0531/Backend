package com.cozybinarybase.accountstopthestore.model.accountbook.persist.repository;

import javax.persistence.EntityManager;

public interface AccountBookRepositoryCustom {
  EntityManager getEntityManager();
}