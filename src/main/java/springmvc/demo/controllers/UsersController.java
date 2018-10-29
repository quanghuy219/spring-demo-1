package springmvc.demo.controllers;


import org.json.JSONObject;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springmvc.demo.models.User;
import springmvc.demo.utils.Commons;
import springmvc.demo.utils.Response;
import springmvc.demo.services.UsersService;

import java.util.Map;

@RestController
@RequestMapping("api/users")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class UsersController {

    @RequestMapping(method = RequestMethod.GET, produces = {"application/hal+json"})
    public ResponseEntity<String> getList() {

        return UsersService.getAllUsers().toResponse();
    }

    @PostMapping(produces = {"application/hal+json"})
    public @ResponseBody ResponseEntity<String> createUser(@RequestBody Map<String, String> pet) {

        User user = Commons.getUserFromParams(pet);

        if(user == null) {
            return Response.getErrorMessage("Invalid params", HttpStatus.BAD_REQUEST);
        }

        return UsersService.registerNewUser(user).toResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserDetail(@PathVariable String id) {

        if(!Commons.isManager() && !Commons.isOwner(id)) {

            return Response.getErrorMessage("you don't have authorization to do this action", HttpStatus.FORBIDDEN);
        }

        return UsersService.getUserById(id).toResponse();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody Map<String, String> params) {

        if(!Commons.isManager() && !Commons.isOwner(id)) {

            return Response.getErrorMessage("you don't have authorization to do this action", HttpStatus.FORBIDDEN);
        }

        return UsersService.updateUserById(id, params).toResponse();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {

        JSONObject resp = new JSONObject();

        if(!Commons.isManager() && !Commons.isOwner(id)) {

            resp.put("message", "You dont have authorize to do this action");
            return Response.getErrorMessage("You don't have authorization to do this action!", HttpStatus.FORBIDDEN);
        }

        return UsersService.deleteUserById(id).toResponse();
    }
}
