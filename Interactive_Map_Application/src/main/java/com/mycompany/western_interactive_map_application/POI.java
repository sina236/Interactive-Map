package com.mycompany.western_interactive_map_application;
import java.awt.Point;
import org.json.simple.JSONObject;

/**
 * Class to store POI information retrieved from JSON or added.
 *
 * @version 1.0.0
 * @author Hussein Abdallah
 */
public class POI {
    private Point coord;
    private Short floor;
    private String name;
    private String category;
    private String roomNumber;
    private String desc;
    private boolean bookmarked, custom;

    /**
     * POI constructor. Creates a new POI object.
     *
     * @param coord the coordinates on the map
     * @param floor the floor number the POI is on
     * @param name the name of the POI
     * @param category the category of the POI
     * @param roomNumber the room number of the POI
     * @param desc the description of the POI
     * @param bookmarked whether the POI should be bookmarked or not
     * @param custom if a custom POI added by user, then this will be true
     */
    public POI (Point coord, Short floor, String name, String category, String roomNumber, String desc, boolean bookmarked, boolean custom) {
        this.coord = coord;
        this.floor = floor;
        this.name = name;
        this.category = category;
        this.bookmarked = bookmarked;
        this.custom = custom;
        this.roomNumber = roomNumber;
        this.desc = desc;
    }

    /**
     * Function to bookmark or unbookmark the POI.
     */
    public void bookmark() {
        this.bookmarked = !bookmarked;
    }

    /**
     * Get the coordinates of this POI
     * @return the coordinates of the POI
     */
    public Point getCoord() {
        return coord;
    }

    /**
     * Get the floor number of this POI
     * @return the floor number of the POI
     */
    public Short getFloor() {
        return floor;
    }

    /**
     * Get the name of this POI
     * @return the name of the POI
     */
    public String getName() {
        return name;
    }

    /**
     * Get the category of this POI
     * @return the category of the POI
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Get the description of this POI
     * @return the description of the POI
     */
    public String getDesc() {
        return desc;
    }
    
    /**
     * Get the room of this POI
     * @return the room of the POI
     */
    public String getRoom() {
        return roomNumber;
    }

    /**
     * Check if POI is bookmarked
     * @return true if POI is bookmarked, false otherwise
     */
    public boolean isBookmarked() {
        return bookmarked;
    }

    /**
     * Check if POI is user-added
     * @return true if POI is user-added, false otherwise
     */
    public boolean isCustom() {
        return custom;
    }
    
    /**
     * Function to convert POI to JSON object
     * 
     * @param bookmarks the bookmark array for this POI
     * @param userName the current user
     * @return a JSONObject to save to the POIs file
     */
    public JSONObject toJSON (JSONObject bookmarks, String userName) {
        JSONObject json = new JSONObject();
        json.put("xCoord", coord.x);
        json.put("yCoord", coord.y);
        json.put("floor", floor);
        json.put("name", name);
        json.put("category", category);
        bookmarks.put(userName, bookmarked);
        json.put("bookmarks", bookmarks);
        json.put("custom", custom);
        json.put("roomNumber", roomNumber);
        json.put("desc", desc);
        return json;
    }
}
