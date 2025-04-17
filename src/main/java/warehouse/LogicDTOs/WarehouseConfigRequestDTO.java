package warehouse.LogicDTOs;

import java.util.List;

public class WarehouseConfigRequestDTO {

    private Long totalArea;
    private List<RackConfig> racks;

    public Long getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Long totalArea) {
        this.totalArea = totalArea;
    }

    public List<RackConfig> getRacks() {
        return racks;
    }

    public void setRacks(List<RackConfig> racks) {
        this.racks = racks;
    }

    public static class RackConfig {
        private Long capacity;
        private Integer numberOfCompartments;

        public Long getCapacity() {
            return capacity;
        }

        public void setCapacity(Long capacity) {
            this.capacity = capacity;
        }

        public Integer getNumberOfCompartments() {
            return numberOfCompartments;
        }

        public void setNumberOfCompartments(Integer numberOfCompartments) {
            this.numberOfCompartments = numberOfCompartments;
        }
    }
}