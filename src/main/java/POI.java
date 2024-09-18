/**
 * Class to store POI information retrieved from JSON or added.
 *
 * @version 1.0.0
 * @author Hussein Abdallah
 */
public class POI {
    private float[] coord;
    private Short floor;
    private String name, description;
    private String[] category;
    private boolean bookmarked, custom;

    /**
     * POI constructor. Creates a new POI object.
     *
     * @param coord the coordinates on the map
     * @param floor the floor number the POI is on
     * @param name the name of the POI
     * @param category the category of the POI
     * @param bookmarked whether the POI should be bookmarked or not
     * @param custom if a custom POI added by user, then this will be true
     * @param description description of the POI
     */
    public POI (float[] coord, Short floor, String name, String[] category, boolean bookmarked, boolean custom, String description) {
        this.coord = coord;
        this.floor = floor;
        this.name = name;
        this.category = category;
        this.bookmarked = bookmarked;
        this.custom = custom;
        this.description = description;
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
    public float[] getCoord() {
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
     * Get the description of this POI
     * @return the description of the POI
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the category of this POI
     * @return the category of the POI
     */
    public String[] getCategory() {
        return category;
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
}
