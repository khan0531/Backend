package com.cozybinarybase.accountstopthestore.common.aop;

import com.cozybinarybase.accountstopthestore.common.dto.ApiResponse;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Profile("!unit-test")
public class ApiResponseAspect {

  private final DomainNameMapping domainNameMapping;

  @Around("execution(* com.cozybinarybase.accountstopthestore.model..*.*(..)) && "
      + "(@annotation(org.springframework.web.bind.annotation.GetMapping) || "
      + "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
      + "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
      + "@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
  public Object wrapApiResponse(ProceedingJoinPoint pjp) throws Throwable {
    Object result = pjp.proceed();

    if (result instanceof ResponseEntity) {
      ResponseEntity responseEntity = (ResponseEntity) result;

      String domainName = domainNameMapping.getDomainName(
          pjp.getSignature().getDeclaringType().getSimpleName());

      if (responseEntity.getStatusCode().is2xxSuccessful()
          && !(responseEntity.getBody() instanceof ApiResponse)) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(domainName, responseEntity.getBody());
        return new ResponseEntity<>(ApiResponse.success(data), responseEntity.getStatusCode());
      }
    }
    return result;
  }
}
