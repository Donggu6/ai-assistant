package com.deongeon.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.deongeon.ai.model.AiRequest;

public interface AiRequestRepository extends JpaRepository<AiRequest, Long> {

}
