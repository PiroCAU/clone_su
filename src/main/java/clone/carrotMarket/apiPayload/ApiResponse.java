package clone.carrotMarket.apiPayload;

import clone.carrotMarket.config.exception.status.ErrorCode;
import clone.carrotMarket.config.exception.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @Schema(description = "성공 실패 여부", example = "false")
    private boolean isSuccess;

    @Schema(description = "에러 발생시 에러코드", example = "S500")
    private String code;

    @Schema(description = "응답 메세지")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    //성공 시 응답코드 반환
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, SuccessStatus._OK.getCode() , SuccessStatus._OK.getMessage(), data);
    }

    //실패 시 응답코드 반환
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return ApiResponse.<T>builder()
                .isSuccess(false)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .data(null)
                .build();
    }

    // 에러 응답 생성 메서드(커스텀 메세지 추가)
    public static <T> ApiResponse<T> error(String message ,ErrorCode errorCode, T data) {
        return ApiResponse.<T>builder()
                .isSuccess(false)
                .code(errorCode.getCode())
                .message(message)
                .data(data)
                .build();
    }
}
