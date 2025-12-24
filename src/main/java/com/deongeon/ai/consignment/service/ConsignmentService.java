package com.deongeon.ai.consignment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deongeon.ai.auth.domain.AppUser;
import com.deongeon.ai.consignment.domain.ConsignmentItem;
import com.deongeon.ai.consignment.domain.ConsignmentStatus;
import com.deongeon.ai.consignment.dto.ConsignmentRequest;
import com.deongeon.ai.consignment.repository.ConsignmentRepository;
import com.deongeon.ai.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsignmentService {

    private final ConsignmentRepository consignmentRepository;
    private final UserService userService;   // 🔥 현재 로그인 유저 가져올 때 사용

    /**
     * 현재 로그인 유저 기준 위탁 신청 생성
     */
    public ConsignmentItem createForCurrentUser(ConsignmentRequest req) {

        AppUser user = userService.getCurrentUser();

        ConsignmentItem item = ConsignmentItem.builder()
                .user(user)
                .brand(req.getBrand())
                .modelName(req.getModelName())      // 🔥 size 넣던 버그도 수정
                .size(req.getSize())
                .conditionState(req.getConditionState())
                .targetPrice(req.getTargetPrice())
                .status(ConsignmentStatus.REQUESTED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return consignmentRepository.save(item);
    }

    /**
     * 현재 로그인 유저 위탁 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ConsignmentItem> getMyConsignments() {
        AppUser user = userService.getCurrentUser();
        return consignmentRepository.findByUser(user);
    }
}
