package com.deongeon.ai.global.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;

	public void send2FACode(String to, String code) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject("[AI-Assistant] 2차 인증 코드");
		msg.setText("인증 코드: " + code + "\n유효시간: 5분");

		mailSender.send(msg);
	}
	
	public void sendResetLink(String to, String token) {

	    String link = "http://localhost:8080/reset-password.html?token=" + token;

	    SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(to);
	    msg.setSubject("[AI-Assistant] 비밀번호 재설정");
	    msg.setText("아래 링크에서 새 비밀번호를 설정하세요:\n" + link);

	    mailSender.send(msg);
	}
	
	public void sendPasswordResetLink(String to, String link) {
	    SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(to);
	    msg.setSubject("[AI-Assistant] 비밀번호 재설정");
	    msg.setText("아래 링크에서 새 비밀번호를 설정하세요.\n\n" + link + "\n\n(유효시간: 30분)");
	    mailSender.send(msg);
	}


	

}
