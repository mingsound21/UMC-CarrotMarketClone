package umc.CarrotMarket_Clone.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.SUCCESS;

@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@AllArgsConstructor
public class BaseResponse<T> {//BaseResponse 객체를 사용할때 성공, 실패 경우
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    @JsonProperty("message")
    private final String message;

    @JsonProperty("code")
    private final int code;

    @JsonProperty("result")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }

    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }
}
