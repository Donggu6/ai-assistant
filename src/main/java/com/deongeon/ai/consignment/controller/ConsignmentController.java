package com.deongeon.ai.consignment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deongeon.ai.consignment.domain.ConsignmentItem;
import com.deongeon.ai.consignment.dto.ConsignmentRequest;
import com.deongeon.ai.consignment.service.ConsignmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/consignment")
@RequiredArgsConstructor
public class ConsignmentController {

    private final ConsignmentService consignmentService;

    // 위탁 신청
    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestBody ConsignmentRequest request) {
        ConsignmentItem item = consignmentService.createForCurrentUser(request);
        return ResponseEntity.ok(item);
    }

    // 내 위탁 목록 조회
    @GetMapping("/my")
    public ResponseEntity<?> myItems() {
        return ResponseEntity.ok(consignmentService.getMyConsignments());
    }
}
