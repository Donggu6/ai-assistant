package com.deongeon.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.dto.RegisterRequest;
import com.deongeon.ai.dto.RegisterResponse;
import com.deongeon.ai.service.AppUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final AppUserService appUserService;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest req){
		AppUser user = appUserService.createUser(req.email(), req.password());
		return ResponseEntity.ok(new RegisterResponse(user.getId(), user.getEmail(), user.getRole().name()));
	}
}
