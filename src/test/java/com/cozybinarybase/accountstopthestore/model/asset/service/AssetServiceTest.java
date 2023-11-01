package com.cozybinarybase.accountstopthestore.model.asset.service;

import com.cozybinarybase.accountstopthestore.model.asset.persist.repository.AssetRepository;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

  @InjectMocks
  private AssetService assetService;

  @Mock
  private AssetRepository assetRepository;

  @Mock
  private MemberRepository memberRepository;

  @Spy
  private ObjectMapper objectMapper;
}