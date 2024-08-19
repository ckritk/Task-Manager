package test;

import java.util.ArrayList;

public class Tracker extends User{
  ArrayList<Team> teams = new ArrayList<>();

  public Tracker(int id, String email, String name, String pswd){
      super(id, email,name,pswd);
      //////Database update add/change in User
    }

    public Tracker(User user){
      super(user.getID(), user.getEmail(),user.getName(),user.getPswd());
    }
   
}
