package com.mycompany.western_interactive_map_application;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @version 1.0.0
 * @author Hussein Abdallah
 */
public class POISystem {
    private ArrayList<ArrayList<POI>> universalPOIs, userPOIs;
    private JSONObject initialFile;
    private String userName, buildingName;
    private boolean isAdmin;
    private int numOfFloors = 0;
    private ArrayList<ArrayList<JSONObject>> universalPOIBookmarks, userPOIBookmarks;
    
    /**
     * POISystem constructor, creates a POI system which is loaded from POIs.json
     *
     * @param buildingName name of building, must match POIs.json
     * @param userName user to find POIs of
     * @param isAdmin whether the user is admin or not
     */
    public POISystem (String buildingName, String userName, boolean isAdmin) {
        //INITIALIZE VARIABLES
        this.buildingName = buildingName;
        this.userName = userName;
        this.isAdmin = isAdmin;
        JSONParser jsonParser = new JSONParser();
        universalPOIs = new ArrayList<>(0);
        userPOIs = new ArrayList<>(0);
        universalPOIBookmarks = new ArrayList<>(0);
        userPOIBookmarks = new ArrayList<>(0);
        
        //LOAD JSON FILE, FURTHER INITIATE VARIABLES
        try (FileReader reader = new FileReader("JSON_OBJECTS/POIs.json"))
        {
            initialFile = (JSONObject) jsonParser.parse(reader);
            JSONObject obj = (JSONObject) initialFile.get(buildingName);
            numOfFloors = ((Long) obj.get("floors")).intValue();
            for (int i = 0; i < numOfFloors; i++) {
                universalPOIs.add(new ArrayList<>(0));
                userPOIs.add(new ArrayList<>(0));
                universalPOIBookmarks.add(new ArrayList<>(0));
                userPOIBookmarks.add(new ArrayList<>(0));
            }
            
            populateUniversalPOIs();
            populateUserPOIs();
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Function to load user specific POIs
     */
    private void  populateUserPOIs () {
        JSONObject obj = (JSONObject) initialFile.get(buildingName);
        if (((JSONObject) obj.get("userPOIs")).containsKey(userName)) { //FOR EACH POI, LOAD INFORMATION AND ADD TO POIs
            JSONArray POIs = (JSONArray) ((JSONObject) obj.get("userPOIs")).get(userName);
            for (Object object : POIs) {
                JSONObject POIObject = (JSONObject) object;
                Point coord = new Point(((Long) POIObject.get("xCoord")).intValue(), ((Long) POIObject.get("yCoord")).intValue());
                Short floor = ((Long) POIObject.get("floor")).shortValue();
                String name = (String) POIObject.get("name");
                String category = (String) POIObject.get("category");
                boolean bookmarked = false;
                if (((JSONObject) POIObject.get("bookmarks")).containsKey(userName)) {
                    bookmarked = (boolean) ((JSONObject) POIObject.get("bookmarks")).get(userName);
                }
                userPOIBookmarks.get(floor).add(((JSONObject) POIObject.get("bookmarks")));
                boolean custom = true;
                String roomNumber = "";
                if (POIObject.containsKey("roomNumber")) roomNumber = (String) POIObject.get("roomNumber");
                String desc = "";
                if (POIObject.containsKey("desc")) desc = (String) POIObject.get("desc");
                
                userPOIs.get(floor).add(new POI(coord, floor, name, category, roomNumber, desc, bookmarked, custom));
            }
        }
    }
    
    /**
     * Function to load universal POIs
     */
    private void populateUniversalPOIs () { //FOR EACH POI, LOAD INFORMATION AND ADD TO POIs
        JSONObject obj = (JSONObject) initialFile.get(buildingName);
        JSONArray POIs = (JSONArray) obj.get("POIs");
        for (Object object : POIs) {
            JSONObject POIObject = (JSONObject) object;
            Point coord = new Point(((Long) POIObject.get("xCoord")).intValue(), ((Long) POIObject.get("yCoord")).intValue());
            Short floor = ((Long) POIObject.get("floor")).shortValue();
            String name = (String) POIObject.get("name");
            String category = (String) POIObject.get("category");
            boolean bookmarked = false;
            if (((JSONObject) POIObject.get("bookmarks")).containsKey(userName)) {
                bookmarked = (boolean) ((JSONObject) POIObject.get("bookmarks")).get(userName);
            }
            universalPOIBookmarks.get(floor).add(((JSONObject) POIObject.get("bookmarks")));
            boolean custom = false;
            String roomNumber = "";
            if (POIObject.containsKey("roomNumber")) roomNumber = (String) POIObject.get("roomNumber");
            String desc = "";
            if (POIObject.containsKey("desc")) desc = (String) POIObject.get("desc");

            universalPOIs.get(floor).add(new POI(coord, floor, name, category, roomNumber, desc, bookmarked, custom));
        }
    }
    
    /**
     * Function to get 2d list of user POIs, where each floor is its own list of POIs
     * 
     * @return 2d list of POIs
     */
    public ArrayList<ArrayList<POI>> getUserPOIs() {
        return this.userPOIs;
    }
    
    /**
     * Function to get 2d list of universal POIs, where each floor is its own list of POIs
     * 
     * @return 2d list of POIs
     */
    public ArrayList<ArrayList<POI>> getUniversalPOIs() {
        return this.universalPOIs;
    }
    
    /**
     * Function to get 2d list of bookmarked POIs, where each floor is its own list of POIs
     * 
     * @return 2d list of POIs
     */
    public ArrayList<ArrayList<POI>> getBookmarkedPOIs() {
        ArrayList<ArrayList<POI>> bookmarks = new ArrayList<>();
        
        for (int i = 0; i < numOfFloors; i++) {
            bookmarks.add(new ArrayList<POI>());
            for (POI POIObject : universalPOIs.get(i)) {
                if (POIObject.isBookmarked()) bookmarks.get(i).add(POIObject);
            }
            for (POI POIObject : userPOIs.get(i)) {
                if (POIObject.isBookmarked()) bookmarks.get(i).add(POIObject);
            }
        }
        
        return bookmarks;
    }
    
    /**
     * Function to get number of floors
     * 
     * @return number of floors
     */
    public int getNumOfFloors() {
        return this.numOfFloors;
    }
    
    /**
     * Function to add a POI to the system
     * 
     * @param coord the coordinates to add POI at
     * @param name the name of the POI
     * @param category the category of the POI
     * @param roomNumber the room number of the POI
     * @param desc the description of the POI
     * @param floor what floor the POI is on
     */
    public void addPOI(Point coord, String name, String category, String roomNumber, String desc, Short floor) {
        if (isAdmin) {
            universalPOIs.get(floor).add(new POI(coord, floor, name, category, roomNumber, desc, false, false));
            universalPOIBookmarks.get(floor).add(new JSONObject());
        } else {
            userPOIs.get(floor).add(new POI(coord, floor, name, category, roomNumber, desc, false, true));
            userPOIBookmarks.get(floor).add(new JSONObject());
        }
        
        saveToJSON();
    }
    
    /**
     * Function to remove a specific POI
     * @param poi the POI to remove
     */
    public void removePOI(POI poi) {
        if (isAdmin) {
            int index = universalPOIs.get(poi.getFloor()).indexOf(poi);
            universalPOIBookmarks.get(poi.getFloor()).remove(index);
            universalPOIs.get(poi.getFloor()).remove(poi);
        } else {
            int index = userPOIs.get(poi.getFloor()).indexOf(poi);
            userPOIBookmarks.get(poi.getFloor()).remove(index);
            userPOIs.get(poi.getFloor()).remove(poi);
        }
        
        saveToJSON();
    }
    
    /**
     * Function to bookmark a POI on the user's account
     * @param poi the POI to bookmark
     */
    public void bookmarkPOI (POI poi) {
        poi.bookmark();
        if(!poi.isCustom()) {
            int index = universalPOIs.get(poi.getFloor()).indexOf(poi);
            universalPOIBookmarks.get(poi.getFloor()).get(index).put(userName, poi.isBookmarked());
        }
        else {
            int index = userPOIs.get(poi.getFloor()).indexOf(poi);
            userPOIBookmarks.get(poi.getFloor()).get(index).put(userName, poi.isBookmarked());
        }
        
        saveToJSON();
    }
    
    /**
     * Function to save current list of POIs to the JSON file
     */
    private void saveToJSON() {
        JSONArray universalPOIArray = new JSONArray();
        for (int i = 0; i < universalPOIs.size(); i++) {
            for (int j = 0; j < universalPOIs.get(i).size(); j++) {
                universalPOIArray.add(universalPOIs.get(i).get(j).toJSON(universalPOIBookmarks.get(i).get(j), userName));
            }
        }
        JSONArray userPOIArray = new JSONArray();
        for (int i = 0; i < userPOIs.size(); i++) {
            for (int j = 0; j < userPOIs.get(i).size(); j++) {
                userPOIArray.add(userPOIs.get(i).get(j).toJSON(userPOIBookmarks.get(i).get(j), userName));
            }
        }
        ((JSONObject) ((JSONObject) initialFile.get(buildingName))).put("POIs", universalPOIArray);
        ((JSONObject) ((JSONObject) ((JSONObject) initialFile.get(buildingName))).get("userPOIs")).put(userName, userPOIArray);
        try (FileWriter file = new FileWriter("JSON_OBJECTS/POIs.json")) {
            file.write(initialFile.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
