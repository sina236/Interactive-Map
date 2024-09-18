/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.western_interactive_map_application; //edit this to whatever package!!!!!!

import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray; //need to add thej json-simple.jar to class path!!!!!!
import org.json.simple.JSONObject; //need to add thej json-simple.jar to class path!!!!!!

/**
 *
 * @author sunny
 */
public class User {
private String username;
private String password;
private Boolean isAdmin;

public String getUserName(){
return username;    
}
public String getPassword(){
return password;    
}

public Boolean getIsAdmin(){
return isAdmin;    
}


public void setUserName(String NewName){
this.username=NewName;
     
}
public void setPassword(String NewPassword){
this.password=NewPassword;
}

public void setIsAdmin(Boolean NewAdmin){
this.isAdmin=NewAdmin;    
}

public User(String Username,String Password,Boolean isAdmin){
   setUserName(Username);
   setPassword(Password);
   setIsAdmin(isAdmin);
}

public static void main(String[] args){
    File myObj = new File("Users.txt");
    User Sunny=new User("Sunny","abcd",true);
    if (myObj.exists()) {
      System.out.println("File name: " + myObj.getName());
      System.out.println("Absolute path: " + myObj.getAbsolutePath());
      System.out.println("Writeable: " + myObj.canWrite());
      System.out.println("Readable " + myObj.canRead());
      System.out.println("File size in bytes " + myObj.length());
    } else {
      System.out.println("The file does not exist."); 
    }
    
  }
}




