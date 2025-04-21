package warehouse.LogicDTOs;

import java.util.List;

public class WarehouseConfigRequestDTO {

    private String wareHouseName;
    private Double totalArea;
    private List<RackConfig> racks;

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

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

        private String rackName;
        private Double capacity;

        public String getRackName() {
            return rackName;
        }

        public void setRackName(String rackName) {
            this.rackName = rackName;
        }

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