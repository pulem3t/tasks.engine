package org.pulem3t.tasks.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.pulem3t.tasks.entry.User;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User u = new User();
		u.setAdminRole(rs.getBoolean("adminrole"));
		u.setCompany(rs.getString("company"));
		u.setCreateDate(rs.getLong("createdate"));
		u.setEmail(rs.getString("email"));
		u.setFirstName(rs.getString("firstname"));
		u.setId(rs.getString("id"));
		u.setLastmodDate(rs.getLong("lastmoddate"));
		u.setLastName(rs.getString("lastname"));
		u.setPatronym(rs.getString("patronym"));
		u.setPhone(rs.getString("phone"));
		u.setSupportRole(rs.getBoolean("supportrole"));
		u.setUserRole(rs.getBoolean("userrole"));
		return u;
	}
}
