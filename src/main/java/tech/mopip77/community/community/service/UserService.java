package tech.mopip77.community.community.service;

import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.mopip77.community.community.mapper.User2Mapper;
import tech.mopip77.community.community.model.User;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private User2Mapper user2Mapper;


    public void createOrUpdate(User user) {
        User dbUser = user2Mapper.findByAccountId(user.getAccountId());
        if (dbUser == null) {
            // create
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user2Mapper.insert(user);
        } else {
            // update
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            user2Mapper.update(dbUser);
        }

    }
}
