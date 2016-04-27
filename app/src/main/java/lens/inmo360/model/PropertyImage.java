package lens.inmo360.model;

/**
 * Created by estebanbutti on 4/25/16.
 */
public class PropertyImage {
    String Title;
    String LastUpdate;
    String URL;

    public PropertyImage() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        LastUpdate = lastUpdate;
    }
}
