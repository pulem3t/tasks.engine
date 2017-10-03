package org.pulem3t.tasks.dao.cratedb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.pulem3t.tasks.config.CrateDBConfig;
import org.pulem3t.tasks.dao.UserDAO;
import org.pulem3t.tasks.entry.User;
import org.pulem3t.tasks.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class UserDAOCrateDB implements UserDAO {

	@Autowired
	private CrateDBConfig config;
	private Logger logger = Logger.getLogger(UserDAOCrateDB.class);

	@Override
	public List<User> getUsers() {

		String SQL_GET_USERS = "SELECT adminrole, company, createdate, email, "
				+ "firstname, id, lastmoddate, lastname, patronym, phone, "
				+ "supportrole, userrole FROM doc.users";
		
		logger.debug(SQL_GET_USERS);
		
		List<User> userList = new ArrayList<>();
		try {
			userList = config.getNpjt().query(SQL_GET_USERS, new MapSqlParameterSource(), new UserRowMapper());
		} catch (Exception e) {
			logger.error(null, e);
		}
		return userList;
	}

	@Override
	public User getUser(String id) {
		
		String SQL_GET_USER_BY_ID = "SELECT adminrole, company, createdate, email, "
				+ "firstname, id, lastmoddate, lastname, patronym, phone, "
				+ "supportrole, userrole FROM doc.users WHERE id = :ID";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID", id);
		
		logger.debug(SQL_GET_USER_BY_ID);
		logger.debug(map.getValues());
		
		try {
			return config.getNpjt().queryForObject(SQL_GET_USER_BY_ID, map, new UserRowMapper());
		} catch (Exception e) {
			logger.error(null, e);
			return null;
		}
	}

	@Override
	public String addUser(User user) {
		
		String SQL_GET_USER_BY_ID = "INSERT INTO doc.users " 
				+ "(adminrole, company, createdate, email, firstname, "
				+ "id, lastmoddate, lastname, patronym, phone, supportrole, userrole) "
				+ "VALUES (:ADMINROLE, :COMPANY, :CREATEDATE, :EMAIL, :FIRSTNAME, :ID, "
				+ ":LASTMODDATE, :LASTNAME, :PATRONYM, :PHONE, :SUPPORTROLE, :USERROLE)";

		MapSqlParameterSource map = new MapSqlParameterSource();
		String guid = UUID.randomUUID().toString();
		map.addValue("ID", guid);
		map.addValue("COMPANY", user.getCompany());
		map.addValue("CREATEDATE", user.getCreateDate());
		map.addValue("EMAIL", user.getEmail());
		map.addValue("FIRSTNAME", user.getFirstName());
		map.addValue("LASTMODDATE", user.getLastmodDate());
		map.addValue("LASTNAME", user.getLastName());
		map.addValue("PATRONYM", user.getPatronym());
		map.addValue("PHONE", user.getPhone());
		map.addValue("SUPPORTROLE", user.isSupportRole());
		map.addValue("USERROLE", user.isUserRole());
		map.addValue("ADMINROLE", user.isAdminRole());

		logger.debug(SQL_GET_USER_BY_ID);
		logger.debug(map.getValues());

		try {
			int res = config.getNpjt().update(SQL_GET_USER_BY_ID, map);
			if(res == 1) {
				return guid;
			}else {
				return null;
			}
		} catch (Exception e) {
			logger.error(null, e);
			return null;
		}
	}

	@Override
	public void deleteUser(String id) {
		
		String SQL_DELETE_USER_BY_ID = "DELETE FROM doc.users WHERE id = :ID";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID", id);
		
		logger.debug(SQL_DELETE_USER_BY_ID);
		logger.debug(map.getValues());
		
		try {
			config.getNpjt().update(SQL_DELETE_USER_BY_ID, map);
		} catch (Exception e) {
			logger.error(null, e);
		}
	}

	@Override
	public void updateUser(User user) {
		
		String SQL_UPDATE_USER = "UPDATE doc.users SET adminrole=:ADMINROLE, company=:COMPANY, "
				+ "email=:EMAIL, firstname=:FIRSTNAME, lastmoddate=:LASTMODDATE, "
				+ "lastname=:LASTNAME, patronym=:PATRONYM, phone=:PHONE, supportrole=:SUPPORTROLE, "
				+ "userrole=:USERROLE WHERE id=:ID";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID", user.getId());
		map.addValue("COMPANY", user.getCompany());
		map.addValue("EMAIL", user.getEmail());
		map.addValue("FIRSTNAME", user.getFirstName());
		map.addValue("LASTMODDATE", user.getLastmodDate());
		map.addValue("LASTNAME", user.getLastName());
		map.addValue("PATRONYM", user.getPatronym());
		map.addValue("PHONE", user.getPhone());
		map.addValue("SUPPORTROLE", user.isSupportRole());
		map.addValue("USERROLE", user.isUserRole());
		map.addValue("ADMINROLE", user.isAdminRole());
		
		logger.debug(SQL_UPDATE_USER);
		logger.debug(map.getValues());
		
		try {
			config.getNpjt().update(SQL_UPDATE_USER, map);
		} catch (Exception e) {
			logger.error(null, e);
		}
	}
}
