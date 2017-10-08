/**
 * @author pulem3t
 */
package org.pulem3t.tasks.service;

import java.util.List;

import org.json.JSONObject;
import org.pulem3t.tasks.dao.UserDAO;
import org.pulem3t.tasks.entry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	public String addUser(String json) {
		
		JSONObject o = new JSONObject(json).getJSONObject("user");
		User user = new User();
		user.setAdminRole(o.getBoolean("adminRole"));
		user.setCompany(o.getString("company"));
		user.setEmail(o.getString("email"));
		user.setFirstName(o.getString("firstName"));
		user.setLastName(o.getString("lastName"));
		user.setPatronym(o.getString("patronym"));
		user.setPhone(o.getString("phone"));
		user.setSupportRole(o.getBoolean("supportRole"));
		user.setUserRole(o.getBoolean("userRole"));
		return userDAO.addUser(user);
	}

	public void delUser(String id) {

		userDAO.deleteUser(id);
	}

	public User getUser(String id) {

		return userDAO.getUser(id);
	}

	public List<User> getTasks() {

		return userDAO.getUsers();
	}

	public void updateUser(String json) {

		JSONObject o = new JSONObject(json).getJSONObject("user");
		User user = new User();
		user.setAdminRole(o.getBoolean("adminRole"));
		user.setCompany(o.getString("company"));
		user.setEmail(o.getString("email"));
		user.setFirstName(o.getString("firstName"));
		user.setLastName(o.getString("lastName"));
		user.setPatronym(o.getString("patronym"));
		user.setPhone(o.getString("phone"));
		user.setSupportRole(o.getBoolean("supportRole"));
		user.setUserRole(o.getBoolean("userRole"));
		user.setLastmodDate(System.currentTimeMillis());
		userDAO.updateUser(user);
	}
	
	public void setUserRole(String id, int role) {
		
		User user = userDAO.getUser(id);
		switch (role) {
			case 1:
				user.setAdminRole(!user.isAdminRole());
				break;
			case 2:
				user.setSupportRole(!user.isSupportRole());
				break;
			case 3:
				user.setUserRole(!user.isUserRole());
				break;
		}
		userDAO.updateUser(user);
	}
}
