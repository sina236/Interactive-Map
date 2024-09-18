/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */


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

        String filePath = "Users.json";
        //Append JSON file
        boolean userfound=ValidateUser(Username,Password);
        
        if (userfound)
            System.out.println("User already in record!");
        else{
        try {
            // Read the contents of the JSON file into a JSONArray object
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("Users.json"));

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
    public boolean ValidateUser(String Username,String Password){
    
    
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader("Users.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray UserList = (JSONArray) obj;
            //System.out.println(UserList);
            boolean userfound=false;
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
                    userfound=true;
                    return true;
                }
           }
           if (!userfound)
                System.out.println("Incorrect password or username.");
           


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    return false;
    }   
    
private static boolean parseUser(JSONObject Users,String Name,String Pass) //unused
    {
       
         
        //Get employee first name
        String Username = (String) Users.get("Username");    
//        System.out.println(Username);
         
        //Get employee last name
        String Password = (String) Users.get("Password");  
 //       System.out.println(Password);
         
        //Get employee website name
        Boolean isAdmin = (Boolean) Users.get("isAdmin");    
//        System.out.println(isAdmin);
        
        if (Username.equals(Name)&&Password.equals(Pass)){
            System.out.println("Username: "+Name);
            System.out.println("Password: "+Pass);
            
        return true;
        }else{
            System.out.println("Username: "+Name);
            System.out.println("Password: "+Pass);
            System.out.println("Does not match record.");
        }
        return false;
    }

    
    public static void main(String args[]) {
        // TODO code application logic here
        LoginSystem LS=new LoginSystem();
        LS.CreateUser("Sunny","abcd",true);
        LS.ValidateUser("Sunny","abcd");
        LS.CreateUser("wth","man",false);
    }
}
