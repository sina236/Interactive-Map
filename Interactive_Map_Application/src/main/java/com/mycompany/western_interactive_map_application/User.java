package com.mycompany.western_interactive_map_application;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


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

}


