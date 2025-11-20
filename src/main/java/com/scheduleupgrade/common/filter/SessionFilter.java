package com.scheduleupgrade.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//컨트롤러 넘어가기전에 인터셉트하여 로그인 관련 세션 검증하는 필터 기능 클래스
@Component
public class SessionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // 로그인/회원가입은 필터링 제외
        if (path.startsWith("/users/login") || path.startsWith("/users/signup")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            // 상태코드 + JSON 메시지
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            String json = "{\"status\":401,\"message\":\"로그인이 필요합니다.\"}";
            response.getWriter().write(json);
            response.getWriter().flush();
            return;
        }

        chain.doFilter(request, response);
    }
}
