package com.cozybinarybase.accountstopthestore.common.aop;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DomainNameMapping {

  private final Map<String, String> mapping;

  public DomainNameMapping() {
    mapping = new HashMap<>();
    mapping.put("AssetController", "asset");
    mapping.put("AssetService", "asset");
  }

  public String getDomainName(String className) {
    return mapping.getOrDefault(className, className.toLowerCase());
  }
}
