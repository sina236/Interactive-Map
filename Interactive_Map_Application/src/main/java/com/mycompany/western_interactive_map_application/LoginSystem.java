/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.western_interactive_map_application;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
/**
 *
 * @author sunny
 */
public class LoginSystem {

    
    public boolean CreateUser(String Username,String Password,boolean isAdmin){

        String filePath = "JSON_OBJECTS/Users.json";
        //Append JSON file
        boolean userfound=ValidateUser(Username,Password)[0];
        
        if (userfound)
            System.out.println("User already in record!");
        else{
        try {
            // Read the contents of the JSON file into a JSONArray object
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("JSON_OBJECTS/Users.json"));

            // Append the new JSONObject to the JSONArray
            JSONObject UserInfo = new JSONObject();
            UserInfo.put("Username", Username);
            UserInfo.put("Password", Password);
            UserInfo.put("isAdmin", isAdmin);

            jsonArray.add(UserInfo);

            // Write the updated JSONArray to the file
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
            System.out.println("New user added to record!");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }        
        
        }

        return true;
    }
    public boolean[] ValidateUser(String Username,String Password){
    
    
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         boolean userfound[]={false,false};
        try (FileReader reader = new FileReader("JSON_OBJECTS/Users.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray UserList = (JSONArray) obj;
            //System.out.println(UserList);
           
            //Iterate over employee array
           //UserList.forEach( emp -> parseUser( (JSONObject) emp,Username,Password ) );
           for (Object userObj : UserList) {
                JSONObject user = (JSONObject) userObj;
                String Usn = (String) user.get("Username");        
                String Pw = (String) user.get("Password");
                Boolean iAd = (Boolean) user.get("isAdmin");
                //System.out.println("Checking Username: " + Usn + ", Password: " + Pw);
                if (Username.equals(Usn)&&Password.equals(Pw)){
                    System.out.println("User found!");
                    userfound[0]=true;
                    userfound[1]=iAd;
                    return userfound;
                }
           }
                System.out.println("Incorrect password or username.");
           


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    return userfound;
    }   
    

    
//    public static void main(String args[]) {
//        // TODO code application logic here
//        LoginSystem LS=new LoginSystem();
//        LS.CreateUser("Sunny","abcd",true);
//        boolean result[]=LS.ValidateUser("Sunny","abcd");
//        LS.CreateUser("wth","man",false);
//    }
}
