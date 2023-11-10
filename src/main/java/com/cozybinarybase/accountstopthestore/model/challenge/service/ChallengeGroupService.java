package com.cozybinarybase.accountstopthestore.model.challenge.service;

import com.cozybinarybase.accountstopthestore.model.challenge.domain.ChallengeGroup;
import com.cozybinarybase.accountstopthestore.model.challenge.domain.MemberGroup;
import com.cozybinarybase.accountstopthestore.model.challenge.dto.ChallengeGroupRequestDto;
import com.cozybinarybase.accountstopthestore.model.challenge.dto.ChallengeGroupResponseDto;
import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.ChallengeGroupEntity;
import com.cozybinarybase.accountstopthestore.model.challenge.persist.entity.MemberGroupEntity;
import com.cozybinarybase.accountstopthestore.model.challenge.persist.repository.ChallengeGroupRepository;
import com.cozybinarybase.accountstopthestore.model.challenge.persist.repository.MemberGroupRepository;
import com.cozybinarybase.accountstopthestore.model.member.domain.Member;
import com.cozybinarybase.accountstopthestore.model.member.persist.entity.MemberEntity;
import com.cozybinarybase.accountstopthestore.model.member.persist.repository.MemberRepository;
import com.cozybinarybase.accountstopthestore.model.message.domain.Message;
import com.cozybinarybase.accountstopthestore.model.message.service.MessageService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeGroupService {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final SecureRandom RANDOM = new SecureRandom();
  private final ChallengeGroupRepository challengeGroupRepository;
  private final MemberGroupRepository memberGroupRepository;
  private final MemberRepository memberRepository;
  private final SimpMessagingTemplate messagingTemplate;
  private final MessageService messageService;
  private final int INVITE_LINK_LENGTH = 10;
  private Map<String, Long> inviteLinkToGroupId = new ConcurrentHashMap<>();

  public static String generateRandomString(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int randomIndex = RANDOM.nextInt(CHARACTERS.length());
      char randomChar = CHARACTERS.charAt(randomIndex);
      sb.append(randomChar);
    }
    return sb.toString();
  }

  public String createInviteLink(Long groupId, Member member) {
    if (!isGroupMember(groupId, member)) {
      throw new IllegalArgumentException("그룹에 속해 있지 않습니다.");
    }

    String inviteLink = generateRandomString(INVITE_LINK_LENGTH);
    inviteLinkToGroupId.put(inviteLink, groupId);
    return inviteLink;
  }

  public ChallengeGroupResponseDto createGroup(ChallengeGroupRequestDto challengeGroupRequestDto, Member member) {
    ChallengeGroup challengeGroup = ChallengeGroup.fromRequest(challengeGroupRequestDto, member);
    return ChallengeGroupResponseDto.fromEntity(challengeGroupRepository.save(challengeGroup.toEntity()));
  }

  public void joinGroup(String inviteLink, Member member) {
    Long groupId = inviteLinkToGroupId.get(inviteLink);

    ChallengeGroupEntity challengeGroupEntity = challengeGroupRepository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("그룹이 존재하지 않습니다."));

    if (memberGroupRepository.existsByMemberAndChallengeGroup(member.toEntity(), challengeGroupEntity)) {
      throw new IllegalArgumentException("이미 그룹에 속해있습니다.");
    }

    Message message = Message.createEnterMessage(groupId, member.getId());

    messageService.save(message);
    messagingTemplate.convertAndSend("/group/" + message.getGroupId(), message);

    MemberGroup memberGroup = MemberGroup.create(member.getId(), groupId);
    memberGroupRepository.save(memberGroup.toEntity());
  }

  public void leaveGroup(Long groupId, Long memberId, Member member) {
    ChallengeGroup challengeGroup = ChallengeGroup.fromEntity(challengeGroupRepository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("그룹이 존재하지 않습니다.")));

    if (challengeGroup.getAdminId().equals(member.getId())) {
      MemberEntity memberEntity = memberRepository.findById(memberId)
          .orElseThrow(() -> new IllegalArgumentException("멤버가 존재하지 않습니다."));

      memberGroupRepository.findByMemberAndChallengeGroup(memberEntity, challengeGroup.toEntity())
          .ifPresent(memberGroupRepository::delete);

    } else if (memberId.equals(member.getId())) {
      memberGroupRepository.findByMemberAndChallengeGroup(member.toEntity(), challengeGroup.toEntity())
          .ifPresent(memberGroupRepository::delete);
    }

    Message message = Message.createLeaveMessage(groupId, memberId);
    messageService.save(message);
    messagingTemplate.convertAndSend("/group/" + message.getGroupId(), message);
  }

  public boolean isGroupMember(Long groupId, Member member) {
    ChallengeGroupEntity challengeGroupEntity = challengeGroupRepository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("그룹이 존재하지 않습니다."));
    if (challengeGroupEntity.getAdmin().getId().equals(member.getId())) {
      return true;
    }

    List<MemberGroupEntity> memberGroups = memberGroupRepository.findByChallengeGroup(challengeGroupEntity);
    List<Long> memberIds = new ArrayList<>();

    for (MemberGroupEntity memberGroup : memberGroups) {
      Long memberId = memberGroup.getMember().getId();
      memberIds.add(memberId);
    }

    return memberIds.contains(member.getId());
  }
}
