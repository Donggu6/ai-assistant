package com.deongeon.ai.consignment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deongeon.ai.consignment.domain.ConsignmentItem;
import com.deongeon.ai.auth.domain.AppUser;


public interface ConsignmentRepository extends JpaRepository<ConsignmentItem, Long>{
	List<ConsignmentItem> findByUser(AppUser user);
}
