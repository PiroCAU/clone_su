package clone.carrotMarket.dto.sell;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class SellListResponseDTO {

    private String location;

    private List<SellListSummeryDTO> sellListSummeryDTOS;
}
