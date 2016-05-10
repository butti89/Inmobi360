package lens.inmo360.model;

/**
 * Created by estebanbutti on 4/25/16.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
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
    private Map<String, Object> additionalProperties;

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
