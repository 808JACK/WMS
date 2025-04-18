package warehouse.LogicDTOs;

import java.util.List;

public class WarehouseConfigRequestDTO {

    private Double totalArea;
    private List<RackConfig> racks;

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Long totalArea) {
        this.totalArea = Double.valueOf(totalArea);
    }

    public List<RackConfig> getRacks() {
        return racks;
    }

    public void setRacks(List<RackConfig> racks) {
        this.racks = racks;
    }

    public static class RackConfig {
        private Double capacity;
        private Integer numberOfCompartments;

        public Double getCapacity() {
            return capacity;
        }

        public void setCapacity(Double capacity) {
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