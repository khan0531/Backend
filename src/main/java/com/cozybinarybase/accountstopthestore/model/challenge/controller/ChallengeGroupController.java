package com.cozybinarybase.accountstopthestore.model.challenge.controller;

import com.cozybinarybase.accountstopthestore.model.challenge.dto.ChallengeGroupRequestDto;
import com.cozybinarybase.accountstopthestore.model.challenge.service.ChallengeGroupService;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class ChallengeGroupController {

  private final ChallengeGroupService challengeGroupService;

  @PostMapping
  public ResponseEntity<?> createGroup(@RequestBody ChallengeGroupRequestDto challengeGroupRequestDto,
      @AuthenticationPrincipal Member member) {
    challengeGroupService.createGroup(challengeGroupRequestDto, member);
    return null;
  }

  @GetMapping("/{groupId}/create-invite-link")
  public ResponseEntity<String> createInviteLink(@PathVariable Long groupId, @AuthenticationPrincipal Member member) {
    String inviteLink = challengeGroupService.createInviteLink(groupId, member);
    return ResponseEntity.ok(inviteLink);
  }

  @PostMapping("/join/{inviteLink}")
  public ResponseEntity<?> joinGroup(@PathVariable String inviteLink, @AuthenticationPrincipal Member member) {
    challengeGroupService.joinGroup(inviteLink, member);
    return ResponseEntity.ok().build();
  }

  // 서버 추방 & 나가기
  @DeleteMapping("/{groupId}/member/{memberId}")
  public ResponseEntity<?> leaveGroup(@PathVariable Long groupId, @PathVariable Long memberId,
      @AuthenticationPrincipal Member member) {
    challengeGroupService.leaveGroup(groupId, memberId, member);
    return ResponseEntity.ok().build();
  }

}
