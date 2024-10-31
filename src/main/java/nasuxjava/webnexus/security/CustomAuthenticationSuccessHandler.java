package nasuxjava.webnexus.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import nasuxjava.webnexus.model.UserLoginInfo;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {

        logger.info("Dang Nhap Thanh Cong");
        String email = authentication.getName();
        System.out.println("User " + authentication.getName() + " logged in successfully.");
        Collection<String> roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

        UserLoginInfo userLoginInfo = new UserLoginInfo(email, roles);

        // Lưu thông tin vào session
        request.getSession().setAttribute("userLoginInfo", userLoginInfo);
        request.getSession().setAttribute("message", "Đăng nhập thành công!");
        response.sendRedirect("/Admin/Dashboard");
    }

}
