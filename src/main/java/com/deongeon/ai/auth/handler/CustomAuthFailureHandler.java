package com.deongeon.ai.auth.handler;

import com.deongeon.ai.auth.service.LoginFailureService;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthFailureHandler 
        extends SimpleUrlAuthenticationFailureHandler {

    private final LoginFailureService failureService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest req,
                                        HttpServletResponse res,
                                        AuthenticationException ex) {

        String email = req.getParameter("username");
        failureService.onFailure(email);

    }
}
