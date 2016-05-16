package lens.inmo360.model;

/**
 * Created by sebastian on 11/5/2016.
 */
public class Filter {

    private String province=null;
    private String[] locationArray=null;
    private String operation=null;
    private String[] categoryArray=null;
    private String category=null;
    private String location=null;
    private Integer rooms=null;
    private String antiquity=null;
    private String[] filter = {this.antiquity,this.operation};

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setCategoryArray(String[] categoryArray) {this.categoryArray = categoryArray;}

    public void setCategory(String category) {this.category = category;}

    public void setLocation(String location) {
        this.location = location;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setLocationArray(String[] locationArray) {
        this.locationArray = locationArray;
    }

    public void setRooms(Integer rooms) {this.rooms = rooms;}

    public void setAntiquity(String antiquity) {
        this.antiquity = antiquity;
    }

    public String[] getFilter()
    {
        return filter;
    }

    public String getOperation(){return this.operation;}

    public String[] getCategoryArray(){return this.categoryArray;}

    public String getProvince(){return this.province;}

    public String[] getLocationArray(){return this.locationArray;}

    public Integer getRooms(){return this.rooms;}

    public String getCategory(){return this.category;}

    public String getLocation(){return this.location;}

    public String getAntiquity(){return this.antiquity;}

    public void clearFilter(){
        this.operation = null;
        this.categoryArray = null;
        this.province = null;
        this.locationArray = null;
        this.rooms = null;
        this.antiquity = null;
        this.category = null;
        this.location = null;
    }

    public void setValues(Filter filtro)
    {
        this.operation = filtro.operation;
        this.categoryArray = filtro.categoryArray;
        this.province = filtro.province;
        this.locationArray = filtro.locationArray;
        this.rooms = filtro.rooms;
        this.antiquity = filtro.antiquity;
        this.category = filtro.category;
        this.location = filtro.location;
    }
}
