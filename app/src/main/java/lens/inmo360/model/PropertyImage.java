package lens.inmo360.model;

/**
 * Created by estebanbutti on 4/25/16.
 */
public class PropertyImage {
    String Title;
    String LastUpdate;
    String URL;
    String LocalPath;
    Integer Id;

    public PropertyImage() {
    }

    public String getLocalPath() {
        return LocalPath;
    }

    public void setLocalPath(String localPath) {
        LocalPath = localPath;
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

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
