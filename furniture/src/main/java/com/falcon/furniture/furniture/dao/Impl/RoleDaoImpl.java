package com.falcon.furniture.furniture.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.falcon.furniture.furniture.dao.RoleDao;
import com.falcon.furniture.furniture.model.Role;
import com.falcon.furniture.furniture.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao {
    private static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public Optional<Role> findByName(String name) {
        try{
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("userRole = :name")
                    .withExpressionAttributeValues(Map.of(":name", new AttributeValue().withS(name)));

            List<Role> roles = dynamoDBMapper.scan(Role.class, scanExpression);

            return roles.size() > 0 ? Optional.ofNullable(roles.get(0)) : null;
        }catch (DataAccessException e){
            logger.error("Error ---> {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void addRole(Role role) {
        dynamoDBMapper.save(role);
    }

    @Override
    public Role findByRoleId(String roleId) {
        try{
             return dynamoDBMapper.load(Role.class, roleId);
        }catch (DataAccessException e){
            logger.error(e.getMessage());
            return null;
        }
    }
}
