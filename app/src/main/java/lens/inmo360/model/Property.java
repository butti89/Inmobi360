package lens.inmo360.model;

/**
 * Created by estebanbutti on 4/25/16.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Property extends PersistentObject{

    private Integer Id;
    private String Title;
    private String Address;
    private Integer CompanyId;
    private String Description;
    private String LastUpdate;
    private ArrayList<PropertyImage> Images;
    private boolean isDownloaded = false;
    private String Location;
    private String Operation;
    private String Category;
    private Integer Rooms;
    private Float Price;
    private Integer SquaredMeters;
    private Boolean HasGarage;
    private Province Province;
    private City City;
    private String Type;
    private Map<String, Object> additionalProperties;


    public Integer getAntiquity() {
        return Antiquity;
    }

    public lens.inmo360.model.Province getProvince() {
        return Province;
    }

    public lens.inmo360.model.City getCity() {
        return City;
    }

    public Boolean getHasGarage() {
        return HasGarage;
    }

    public Integer getSquaredMeters() {
        return SquaredMeters;
    }

    private Integer Antiquity;

    public void setHasGarage(Boolean hasGarage) {
        HasGarage = hasGarage;
    }

    public void setAntiquity(Integer antiquity) {
        Antiquity = antiquity;
    }

    public void setSquaredMeters(Integer squaredMeters) {
        SquaredMeters = squaredMeters;
    }


    public void setCity(lens.inmo360.model.City city) {
        City = city;
    }


    public void setProvince(lens.inmo360.model.Province province) {
        Province = province;
    }



    /**
     *
     * @return
     * The Id
     */
    public Integer getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The Id
     */
    public void setId(Integer Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     *
     * @param Title
     * The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     *
     * @return
     * The Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     *
     * @param Address
     * The Address
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

    /**
     *
     * @return
     * The CompanyId
     */
    public Integer getCompanyId() {
        return CompanyId;
    }

    /**
     *
     * @param CompanyId
     * The CompanyId
     */
    public void setCompanyId(Integer CompanyId) {
        this.CompanyId = CompanyId;
    }

    /**
     *
     * @return
     * The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     *
     * @param Description
     * The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     *
     * @return
     * The LastUpdate
     */
    public String getLastUpdate() {
        return LastUpdate;
    }

    /**
     *
     * @param LastUpdate
     * The LastUpdate
     */
    public void setLastUpdate(String LastUpdate) {
        this.LastUpdate = LastUpdate;
    }

    /**
     *
     * @return
     * The Images
     */
    public ArrayList<PropertyImage> getImages() {
        return Images;
    }

    /**
     *
     * @param Images
     * The Images
     */
    public void setImages(ArrayList<PropertyImage> Images) {
        this.Images = Images;
    }


    public void setLocation(String location) {
        this.Location = location;
    }

    public void setOperation(String operation) {
        this.Operation = operation;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public void setRooms(Integer rooms) {this.Rooms = rooms; }

    public String getLocation() {
        return Location;
    }

    public String getOperation() {
        return Operation;
    }

    public String getCategory() {
        return Category;
    }

    public Integer getRooms() {
        return Rooms;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public Float getPrice() {
        return Price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public boolean toggleIsDownloaded() {
        this.setIsDownloaded(!this.isDownloaded());
        return this.isDownloaded;
    }

    public void setIsDownloaded(boolean isDownloaded) {
        this.isDownloaded = isDownloaded;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void delete() {
        super.delete(this.Id.toString());
        deleteImages();
    }

    private boolean deleteImages(){
        boolean success = true;

        for (int j =0; j < this.Images.size(); j++){
            File directory = new File(this.Images.get(j).getLocalPath());

            if (directory.isDirectory())
            {
                String[] children = directory.list();
                for (int i = 0; i < children.length; i++)
                {
                    success = success && new File(directory, children[i]).delete();
                }
                success = success && directory.delete();
            }else{
                success = directory.delete();
            }
        }

        return success;
    }
}
