package com.deongeon.ai.billing.controller;

import com.deongeon.ai.billing.domain.CreditWallet;
import com.deongeon.ai.billing.repository.CreditWalletRepository;
import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.domain.Plan;
import com.deongeon.ai.user.repository.AppUserRepository;
import com.deongeon.ai.user.service.PlanPolicy;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

	@Value("${stripe.webhook.secret}")
	private String webhookSecret;

	private final AppUserRepository userRepo;
	private final CreditWalletRepository walletRepo;

	public StripeWebhookController(AppUserRepository userRepo, CreditWalletRepository walletRepo) {
		this.userRepo = userRepo;
		this.walletRepo = walletRepo;
	}

	@PostMapping("/webhook")
	public void handle(HttpServletRequest request) throws Exception {

		String payload = request.getReader().lines().collect(Collectors.joining());
		String sig = request.getHeader("Stripe-Signature");

		Event event = Webhook.constructEvent(payload, sig, webhookSecret);

		if ("checkout.session.completed".equals(event.getType())) {

			Session session = (Session) event.getDataObjectDeserializer().getObject().orElseThrow();

			String email = session.getMetadata().get("email");
			Plan plan = Plan.valueOf(session.getMetadata().get("plan"));

			AppUser user = userRepo.findByEmail(email).orElseThrow();
			user.setPlan(plan);
			userRepo.save(user);

			CreditWallet wallet = walletRepo.findByUser(user).orElseGet(() -> walletRepo.save(new CreditWallet(user)));

			wallet.charge(PlanPolicy.initialCredits(plan));
			walletRepo.save(wallet);
		}
	}
}
