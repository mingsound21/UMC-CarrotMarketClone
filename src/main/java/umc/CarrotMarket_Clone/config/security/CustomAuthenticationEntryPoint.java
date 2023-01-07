package umc.CarrotMarket_Clone.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

// 인증과정에서 실패하거나, 인증 헤더(Authorization)을 보내지 않는 경우 401(UnAuthorized)응답 받음
// 401에러 처리해주는 로직이 AuthenticationEntryPoint 인터페이스
// 401에러 발생할 경우 AuthenticationEntryPoint의 commence 메소드 실행
@Component // 스프링 시큐리티 컨텍스트내에서 관리해주어야하기때문에 @Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // 401 에러 발생시 고정적인 response body 반환
    private static ExceptionResponse exceptionResponse =
            new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "UnAuthorized!!!", null);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //log.error("UnAuthorizaed!!! message : " + e.getMessage());

        //response에 넣기
        // Response 헤더 수정해야하기에 contentType, status 수정
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        try (OutputStream os = httpServletResponse.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            
            // 고정적인 body 형태
            objectMapper.writeValue(os, exceptionResponse);
            os.flush();
        }
    }
}
