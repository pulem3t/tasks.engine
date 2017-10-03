/**
 * @author pulem3t
 */
package org.pulem3t.tasks.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.pulem3t.tasks.entry.User;
import org.pulem3t.tasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	private Logger logger = Logger.getLogger(UserController.class);

	@RequestMapping(value="/addUser", method=RequestMethod.POST, consumes = "application/json; charset=utf-8", 
			produces = "application/json; charset=utf-8")
	public @ResponseBody String addUser(@RequestBody String json) {

		JSONObject o = new JSONObject();
		try {
			String id = userService.addUser(json);
			logger.info("USERS: Added user with id = " + id);
			o.put("id", id);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			o.put("id", "");
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/delUser/{id}", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String delUser(@PathVariable("id") String id) {

		JSONObject o = new JSONObject();
		o.put("id", id);
		try {
			logger.info("USERS: Deleting user with id = " + id);
			userService.delUser(id);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/getUser/{id}", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getUser(@PathVariable("id") String id) {

		JSONObject o = new JSONObject();
		try {
			User user = userService.getUser(id);
			JSONObject u = new JSONObject();
			u.put("id", user.getId());
			u.put("firstName", user.getFirstName());
			u.put("lastName", user.getLastName());
			u.put("patronym", user.getPatronym());
			u.put("email", user.getEmail());
			u.put("phone", user.getPhone());
			u.put("company", user.getCompany());
			u.put("adminRole", user.isAdminRole());
			u.put("userRole", user.isUserRole());
			u.put("supportRole", user.isSupportRole());
			u.put("createDate", user.getCreateDate());
			u.put("lastmodDate", user.getLastmodDate());
			o.put("user", u);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/getUsers", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getTasks() {

		JSONObject o = new JSONObject();
		try {
			List<User> userList = userService.getTasks();
			o.put("users", userList);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("users", new ArrayList<User>());
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/updateUser", method=RequestMethod.POST, consumes = "application/json; charset=utf-8", 
			produces = "application/json; charset=utf-8")
	public @ResponseBody String updateUser(@RequestBody String json) {

		JSONObject o = new JSONObject();
		try {
			logger.info("USERS: Updating user with id = " + new JSONObject(json).getString("id"));
			userService.updateUser(json);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}
	
	@RequestMapping(value="/setUserRole", method=RequestMethod.POST, consumes = "application/json; charset=utf-8", 
			produces = "application/json; charset=utf-8")
	public @ResponseBody String setUserRole(String id, int role) {
		JSONObject o = new JSONObject();
		try {
			logger.info("USERS: Setting role for user with id = " + id);
			userService.setUserRole(id, role);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}
}
