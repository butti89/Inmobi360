package lens.inmo360.dtos;

/**
 * Created by Nach on 5/21/2016.
 */
public class PropertyDTO {

    private String[] location;
    private String province;
    private String operation;
    private String[] category;
    private Integer rooms;
    private Integer minPrice;
    private Integer maxPrice;
    private String title;
    private String antiquity;
    private String locationsString;
    private String categoriesString;

    public String getAntiquity() {
        return antiquity;
    }

    public void setAntiquity(String antiquity) {
        this.antiquity = antiquity;
    }


    public String[] getLocation() {
        return location;
    }

    public void setLocation(String[] location) {
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoriesString() {
        return categoriesString;
    }

    public void setCategoriesString(String categoriesString) {
        this.categoriesString = categoriesString;
    }

    public String getLocationsString() {
        return locationsString;
    }

    public void setLocationsString(String locationsString) {
        this.locationsString = locationsString;
    }


    public void clone(PropertyDTO dto)
    {
        this.operation = dto.operation;
        this.category = dto.category;
        this.province = dto.province;
        this.location = dto.location;
        this.rooms = dto.rooms;
        this.antiquity = dto.antiquity;
        this.minPrice = dto.minPrice;
        this.maxPrice = dto.maxPrice;
        this.categoriesString = dto.categoriesString;
        this.locationsString = dto.locationsString;
    }

    public void clear(){
        this.operation = null;
        this.category = null;
        this.province = null;
        this.location = null;
        this.rooms = null;
        this.antiquity = null;
        this.minPrice = null;
        this.maxPrice = null;
        this.locationsString = null;
        this.categoriesString = null;
    }
}
