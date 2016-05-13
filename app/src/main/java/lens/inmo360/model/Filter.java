package lens.inmo360.model;

/**
 * Created by sebastian on 11/5/2016.
 */
public class Filter {

    private String Province;
    private CharSequence[] Regions;
    private String Operation;
    private String[] Property;
    private String Ambient;
    private String Antiquity;
    private String[] filter = {this.Antiquity,this.Ambient,this.Operation};

    public void setOperation(String operation) {
        this.Operation = operation;
    }
    public void setProperty(String[] property) {
        this.Property = property;
    }
    public void setProvince(String province) {
        this.Province = province;
    }
    public void setRegions(CharSequence[] regions) {
        this.Regions = regions;
    }
    public void setAmbient(String ambient) {
        this.Ambient = ambient;
    }
    public void setAntiquity(String antiquity) {
        this.Antiquity = antiquity;
    }
    public String[] getFilter()
    {
        return filter;
    }
}
