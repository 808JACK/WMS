package warehouse.LogicDTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DailyCountsDTO {

    private Long storedCount;
    private Long retrievedCount;
}
