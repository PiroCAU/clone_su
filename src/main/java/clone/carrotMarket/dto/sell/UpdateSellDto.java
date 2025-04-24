package clone.carrotMarket.dto.sell;

import clone.carrotMarket.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter @Builder
public class UpdateSellDto {

    @NotNull
    private Long sellId; // 판매글 ID

    @NotBlank
    private String title;

    @NotBlank
    private Category category;

    @NotNull
    private Integer price;

    @NotBlank
    private String content;

    @NotBlank
    private String place;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private List<MultipartFile> imageFiles;

    private List<ProductImageDTO> productImages;
}
