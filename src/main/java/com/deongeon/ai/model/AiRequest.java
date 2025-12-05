package com.deongeon.ai.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//AIRequest 엔티티
//AI 요청 기록 저장용
//요청 데이터, 응답 데이터, 생성 시간 포함

@Entity
public class AiRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	private String requestData;
	private String responseData;
	private LocalDateTime timetamp = LocalDateTime.now();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRequestData() {
		return requestData;
	}
	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	public String getResponseData() {
		return responseData;
	}
	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}
	public LocalDateTime getTimetamp() {
		return timetamp;
	}
	public void setTimetamp(LocalDateTime timetamp) {
		this.timetamp = timetamp;
	}
}
