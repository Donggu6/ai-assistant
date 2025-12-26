package com.deongeon.ai.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
	
	@GetMapping("/user/me")
	public Object me(@AuthenticationPrincipal User user) {
		return user;
	}
}
