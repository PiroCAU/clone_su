package clone.carrotMarket.apiPayload;

import clone.carrotMarket.config.exception.status.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

    @Schema(description = "상태 코드", example = "200")
    private int status;

    @Schema(description = "에러 발생시 에러코드", example = "S500")
    private String code;

    @Schema(description = "응답 메세지")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    //성공 시 응답코드 반환
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .code(null)
                .message(message)
                .data(data)
                .build();
    }

    //실패 시 응답코드 반환
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return ApiResponse.<T>builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .data(null)
                .build();
    }

    // 에러 응답 생성 메서드(커스텀 메세지 추가)
    public static <T> ApiResponse<T> error(String message ,ErrorCode errorCode, T data) {
        return ApiResponse.<T>builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(message)
                .data(data)
                .build();
    }
}
