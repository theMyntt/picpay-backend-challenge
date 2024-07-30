package com.themyntt.challenges.picpay.infrastructure.middlewares;

import com.themyntt.challenges.picpay.infrastructure.entities.UserEntity;
import com.themyntt.challenges.picpay.infrastructure.repositories.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AuthMiddleware extends GenericFilterBean {
    private final List<String> paths;

    public AuthMiddleware(List<String> paths) {
        this.paths = paths;
    }

    @Autowired
    private IUserRepository repository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestUri = httpRequest.getRequestURI();

        for (String path : paths) {
            if (requestUri.startsWith(path)) {
                String token = httpRequest.getHeader("x-access-token");

                if (token != null) {
                    Optional<UserEntity> optional = repository.findByToken(token);

                    if (optional.isEmpty()) {
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        return;
                    }

                    chain.doFilter(request, response);
                    return;
                }

                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
