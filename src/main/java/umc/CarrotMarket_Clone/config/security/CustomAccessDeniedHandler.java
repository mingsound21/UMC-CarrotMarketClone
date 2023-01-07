package umc.CarrotMarket_Clone.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

// 403(Forbidden)처리 - 권한에 대한 처리
// 관리자, 일반유저 중에서 관리자만 권한이 있음을 확인
// AuthenticationEntryPoint와 로직 유사
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static ExceptionResponse exceptionResponse =
            new ExceptionResponse(HttpStatus.FORBIDDEN.value(), "Forbidden!!!", null);


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //log.error("Forbidden!!! message : " + e.getMessage());

        //response에 넣기
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        try (OutputStream os = httpServletResponse.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, exceptionResponse);
            os.flush();
        }
    }
}
