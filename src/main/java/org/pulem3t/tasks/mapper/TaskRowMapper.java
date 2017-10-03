package org.pulem3t.tasks.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.pulem3t.tasks.adapter.TaskAdapter;
import org.pulem3t.tasks.entry.Task;
import org.springframework.jdbc.core.RowMapper;

public class TaskRowMapper implements RowMapper<Task> {

	@Override
	public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
		TaskAdapter ta = new TaskAdapter();
		ta.setAuthorId(rs.getString("author_id"));
		ta.setCreateDate(rs.getLong("createdate"));
		ta.setDeadLine(rs.getLong("deadline"));
		ta.setDescription(rs.getString("description"));
		ta.setId(rs.getString("id"));
		ta.setLastmodDate(rs.getLong("lastmoddate"));
		ta.setParentTaskId(rs.getString("parenttask_id"));
		ta.setPerformerId(rs.getString("performer_id"));
		ta.setPrefix(rs.getString("prefix"));
		ta.setPriority(rs.getInt("priority"));
		ta.setStatus(rs.getInt("status"));
		ta.setTitle(rs.getString("title"));
		return ta.getTask();
	}

}
