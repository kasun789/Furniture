package com.falcon.furniture.furniture.dto.enums;

public enum UserRole {
    ROLE_ADMIN(0),
    ROLE_USER(1);

    private int id;

    public Integer getId(){
        return id;
    }

    UserRole(int id){
        this.id = id;
    }

    public static UserRole getById(int id){
        for(UserRole e : UserRole.values()){
            if(e.getId() == id){
                return e;
            }
        }
        return null;
    }

    public String getDescription(){
        switch (id){
            case 0 :
                return "ROLE_ADMIN";
            case 1:
                return "ROLE_USER";
            default:
                return "";
        }
    }

    public static UserRole getByDescription(String name){
        for(UserRole e : UserRole.values()){
            if(e.getDescription().equals(name)){
                return e;
            }
        }
        return null;
    }
}
