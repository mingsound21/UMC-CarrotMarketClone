package umc.CarrotMarket_Clone.config.security;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {
    private int code;
    private String message;
    private String errorDetails;
    private String responseTime;

    public ExceptionResponse(int code, String message, String errorDetails) {
        this.code = code;
        this.message = message;
        this.errorDetails = errorDetails;
        this.responseTime = LocalDateTime.now().toString();
    }
}
