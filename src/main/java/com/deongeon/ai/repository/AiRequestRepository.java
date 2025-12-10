package com.deongeon.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.deongeon.ai.model.AiRequest;

@Repository
public interface AiRequestRepository extends JpaRepository<AiRequest, Long> {
    // 필요 시 사용자별 조회 메서드 추가 가능
}
