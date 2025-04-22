package clone.carrotMarket.dto.sell;

import clone.carrotMarket.domain.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter @Getter
public class CreateSellDTO {

    @NotEmpty(message = "파일은 최소 하나 이상 업로드해야 합니다.")
    private List<MultipartFile> files;

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    private String title;

    @NotNull(message = "카테고리를 선택해 주세요")
    private Category category;

    @Min(value = 1, message = "가격은 1원 이상이어야 합니다.")
    private Integer price;

    @NotBlank(message = "본문은 비어 있을 수 없습니다.")
    private String content;

    @NotBlank(message = "희망 거래 위치를 작성해 주세요")
    private String place;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
