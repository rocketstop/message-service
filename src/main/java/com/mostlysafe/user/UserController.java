package com.mostlysafe.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name= "user", description= "An api for simple user operations")
public class UserController {

    protected Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public UserController() {
        logger.info("User Controller loaded.");
    }

    @Operation(summary = "Reflect user information",
               description= "Reflect user information",
               tags= {"user"})
    @RequestMapping(
            value= "/user/show",
            method= RequestMethod.GET,
            produces= "application/json"
    )
    public User getCallingUser(){
        logger.info("user-controller getCallingUser called");
        User user = new User();
        logger.info("user-controller getCallingUser returning: "+ user);
        return user;
    }
}

