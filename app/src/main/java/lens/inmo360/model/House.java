package lens.inmo360.model;

import java.util.ArrayList;

/**
 * Created by estebanbutti on 4/23/16.
 */
public class House {
    private String address;
    private String squareMeters;
    private String zone;
    private String operation;
    private String category;
    private String location;
    private String description;
    private Integer rooms;
    private String mainPhotos;
    private ArrayList<String> photos;

    public House(String address, String squareMeters, String zone, String operation, String category, String location, String description, Integer rooms) {
        this.address = address;
        this.squareMeters = squareMeters;
        this.zone = zone;
        this.operation = operation;
        this.category = category;
        this.location = location;
        this.description = description;
        this.rooms = rooms;
    }

    public House(){
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(String squareMeters) {
        this.squareMeters = squareMeters;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public String getMainPhotos() {
        return mainPhotos;
    }

    public void setMainPhotos(String mainPhotos) {
        this.mainPhotos = mainPhotos;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }
}
