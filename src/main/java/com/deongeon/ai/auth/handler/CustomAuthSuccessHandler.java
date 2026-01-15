//package com.deongeon.ai.auth.handler;
//
//import com.deongeon.ai.auth.service.LoginFailureService;
//import jakarta.servlet.http.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class CustomAuthSuccessHandler 
//        extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    private final LoginFailureService failureService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest req,
//                                        HttpServletResponse res,
//                                        Authentication auth) {
//        failureService.onSuccess(auth.getName());
//    }
//}
