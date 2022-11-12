package umc.CarrotMarket_Clone.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends Throwable {
    private BaseResponseStatus status;  //BaseResoinseStatus 객체에 매핑
}
