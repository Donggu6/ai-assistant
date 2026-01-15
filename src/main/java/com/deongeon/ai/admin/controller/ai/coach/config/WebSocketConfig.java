//package com.deongeon.ai.admin.controller.ai.coach.config;
//
//import com.deongeon.ai.admin.controller.ai.coach.socket.CoachSocketHandler;
//import com.deongeon.ai.admin.controller.ai.coach.service.CoachReplyService;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.*;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//  private final CoachSocketHandler handler;
//
//  public WebSocketConfig(CoachSocketHandler handler) {
//    this.handler = handler;
//  }
//
//  @Override
//  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//    registry.addHandler(handler, "/ws/coach")
//        .setAllowedOriginPatterns("*");
//  }
//}
