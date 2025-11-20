package com.scheduleupgrade.common.config;

import com.scheduleupgrade.common.filter.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {

    @Bean
    public FilterRegistrationBean<SessionFilter> sessionFilterRegistration(SessionFilter sessionFilter) {
        FilterRegistrationBean<SessionFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(sessionFilter);  // @Component로 등록된 빈 주입
        registration.addUrlPatterns("/*");      // 모든 URL 필터링
        registration.setOrder(1);               // 다른 필터보다 먼저 실행
        return registration;
    }
}