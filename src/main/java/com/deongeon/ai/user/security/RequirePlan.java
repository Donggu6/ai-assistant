package com.deongeon.ai.user.security;

import com.deongeon.ai.user.domain.Plan;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePlan {
    Plan value();
}
