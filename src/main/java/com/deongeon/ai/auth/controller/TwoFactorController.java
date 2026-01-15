package com.deongeon.ai.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deongeon.ai.auth.service.TwoFactorService;
import com.deongeon.ai.auth.jwt.JwtProvider;
import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.repository.AppUserRepository;

import lombok.*;

@RestController
@RequestMapping("/api/2fa")
@RequiredArgsConstructor
public class TwoFactorController {

	private final TwoFactorService service;
	private final JwtProvider jwtProvider;
	private final AppUserRepository userRepo;

	@PostMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody VerifyReq req) {

		boolean ok = service.verify(req.getEmail(), req.getCode());
		if (!ok) {
			return ResponseEntity.status(401).body("2FA 실패");
		}

		AppUser user = userRepo.findByEmail(req.getEmail()).orElseThrow();

		String token = jwtProvider.createAccessToken(user.getEmail(), user.getPlan().name());

		return ResponseEntity.ok(new VerifyRes(token));
	}

	@Getter
	@Setter
	static class VerifyReq {
		private String email;
		private String code;
	}

	@AllArgsConstructor
	@Getter
	static class VerifyRes {
		private String token;
	}
}
