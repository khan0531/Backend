package com.cozybinarybase.accountstopthestore.model.accountbook.persist.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AccountBookRepositoryImpl implements AccountBookRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public EntityManager getEntityManager() {
    return entityManager;
  }
}
