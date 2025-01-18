package com.falcon.furniture.furniture.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "User")
public class User {
    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String address;

    @DynamoDBAttribute
    private String userName;

    @DynamoDBAttribute
    private String password;

    @DynamoDBAttribute
    private String email;

    @DynamoDBAttribute
    private String phoneNo;

    @DynamoDBAttribute
    private String roleId;

    public User(String name, String address, String userName, String password, String email, String phoneNo){
        this.name = name;
        this.address = address;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNo = phoneNo;
    }
}
