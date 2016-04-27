package lens.inmo360.model;

/**
 * Created by estebanbutti on 4/25/16.
 */

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class User {

    private Integer Id;
    private String FistName;
    private String LastName;
    private Integer CompanyId;
    private String Email;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * The FistName
     */
    public String getFistName() {
        return FistName;
    }

    /**
     *
     * @param FistName
     * The FistName
     */
    public void setFistName(String FistName) {
        this.FistName = FistName;
    }

    /**
     *
     * @return
     * The LastName
     */
    public String getLastName() {
        return LastName;
    }

    /**
     *
     * @param LastName
     * The LastName
     */
    public void setLastName(String LastName) {
        this.LastName = LastName;
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
     * The Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     *
     * @param Email
     * The Email
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

