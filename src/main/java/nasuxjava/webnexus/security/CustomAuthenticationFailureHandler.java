package nasuxjava.webnexus.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        // Ghi lại thông tin lỗi
        System.out.println("Email = " + request.getParameter("email"));
        System.out.println("Password = " + request.getParameter("password"));
        System.out.println("Đăng nhập thất bại: " + exception.getMessage());

        // Chuyển hướng về trang đăng nhập với thông báo lỗi
        response.sendRedirect("/login?error=true");
    }

}