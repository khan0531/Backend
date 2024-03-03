package com.cozybinarybase.accountstopthestore.model.challenge.controller;

import com.cozybinarybase.accountstopthestore.model.challenge.dto.ChallengeGroupRequestDto;
import com.cozybinarybase.accountstopthestore.model.challenge.dto.SavingMoneyRequestDto;
import com.cozybinarybase.accountstopthestore.model.challenge.service.ChallengeGroupService;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.message.domain.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class ChallengeGroupController {

  private final ChallengeGroupService challengeGroupService;

  @PostMapping
  public ResponseEntity<?> createGroup(@RequestBody ChallengeGroupRequestDto challengeGroupRequestDto,
      @AuthenticationPrincipal Member member) {
    return ResponseEntity.ok(challengeGroupService.createChallengeGroup(challengeGroupRequestDto, member));
  }

  @GetMapping
  public ResponseEntity<?> getGroups(@AuthenticationPrincipal Member member) {
    return ResponseEntity.ok(challengeGroupService.getChallengeGroups(member));
  }

  @GetMapping("/{groupId}/invite-link")
  public ResponseEntity<?> createInviteLink(@PathVariable Long groupId, @AuthenticationPrincipal Member member) {
    return ResponseEntity.ok(challengeGroupService.createInviteLink(groupId, member));
  }

  @PostMapping("/join/{inviteLink}")
  public ResponseEntity<?> joinGroup(@PathVariable String inviteLink, @AuthenticationPrincipal Member member) {
    return ResponseEntity.ok(challengeGroupService.joinChallengeGroup(inviteLink, member));
  }

  @PutMapping("/{groupId}")
  public ResponseEntity<?> updateGroup(@PathVariable Long groupId,
      @RequestBody ChallengeGroupRequestDto challengeGroupRequestDto, @AuthenticationPrincipal Member member) {
    return ResponseEntity.ok(challengeGroupService.updateChallengeGroup(groupId, challengeGroupRequestDto, member));
  }

  @DeleteMapping("/{groupId}")
  public ResponseEntity<?> deleteGroup(@PathVariable Long groupId, @AuthenticationPrincipal Member member) {
    return ResponseEntity.ok(challengeGroupService.deleteChallengeGroup(groupId, member));
  }

  @PostMapping("/{groupId}/saving")
  public ResponseEntity<?> saveMoney(@PathVariable Long groupId,
      @RequestBody SavingMoneyRequestDto savingMoneyRequestDto, @AuthenticationPrincipal Member member) {
    return ResponseEntity.ok(challengeGroupService.saveMoney(groupId, savingMoneyRequestDto, member));
  }

  // 서버 추방 & 나가기
  @DeleteMapping("/{groupId}/member/{memberId}")
  public ResponseEntity<?> leaveGroup(@PathVariable Long groupId, @PathVariable Long memberId,
      @AuthenticationPrincipal Member member) {
    challengeGroupService.leaveChallengeGroup(groupId, memberId, member);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{groupId}/messages")
  public ResponseEntity<?> getMessages(@PathVariable Long groupId, @AuthenticationPrincipal Member member) {
    List<Message> messages = challengeGroupService.getMessages(groupId, member);
    return ResponseEntity.ok(messages);
  }
}
