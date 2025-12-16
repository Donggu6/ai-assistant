package com.deongeon.ai.controller;

import org.springframework.web.bind.annotation.*;
import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.service.AppUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final AppUserService userService;

	public UserController(AppUserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public AppUser getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}
}
