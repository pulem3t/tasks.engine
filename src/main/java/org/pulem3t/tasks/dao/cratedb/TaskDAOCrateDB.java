package org.pulem3t.tasks.dao.cratedb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.pulem3t.tasks.config.CrateDBConfig;
import org.pulem3t.tasks.dao.TaskDAO;
import org.pulem3t.tasks.entry.Task;
import org.pulem3t.tasks.mapper.TaskRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class TaskDAOCrateDB implements TaskDAO {

	@Autowired
	private CrateDBConfig config;
	private Logger logger = Logger.getLogger(TaskDAOCrateDB.class);
	
	@Override
	public List<Task> getTasks() {
		
		String SQL_GET_TASKS = "SELECT author_id, createdate, deadline, description, "
				+ "id, lastmoddate, parenttask_id, performer_id, prefix, priority, status, title " + 
				"FROM doc.tasks";
		logger.debug(SQL_GET_TASKS);
		List<Task> taskList = new ArrayList<>();
		try {
			taskList = config.getNpjt().query(SQL_GET_TASKS, new MapSqlParameterSource(), new TaskRowMapper());
		} catch (Exception e) {
			logger.error(null, e);
		}
		return taskList;
	}

	@Override
	public Task getTask(String id) {
		
		String SQL_GET_TASK_BY_ID = "SELECT author_id, createdate, deadline, description, "
				+ "id, lastmoddate, parenttask_id, performer_id, prefix, priority, status, title " + 
				"FROM doc.tasks WHERE id=:ID";
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID", id);
		logger.debug(SQL_GET_TASK_BY_ID);
		logger.debug(map.getValues());
		try {
			return config.getNpjt().queryForObject(SQL_GET_TASK_BY_ID, map,  new TaskRowMapper());
		} catch (Exception e) {
			logger.error(null, e);
			return null;
		}
	}

	@Override
	public List<Task> getLastAddedTasks() {
		
		String SQL_GET_LAST_TASKS = "SELECT author_id, createdate, deadline, description, "
				+ "id, lastmoddate, parenttask_id, performer_id, prefix, priority, status, title " + 
				"FROM doc.tasks ORDER BY createdate DESC LIMIT 10";
		logger.debug(SQL_GET_LAST_TASKS);
		List<Task> taskList = new ArrayList<>();
		try {
			taskList = config.getNpjt().query(SQL_GET_LAST_TASKS, new MapSqlParameterSource(),  new TaskRowMapper());
		} catch (Exception e) {
			logger.error(null, e);
		}
		return taskList;
	}

	@Override
	public String addTask(Task task) {
		
		String SQL_INSERT_TASK = "INSERT INTO doc.tasks " + 
				"(author_id, createdate, deadline, description, id, lastmoddate, parenttask_id, "
				+ "performer_id, prefix, priority, status, title) "
				+ "VALUES(:AUTHOR_ID, :CREATEDATE, :DEADLINE, :DESCRIPTION, :ID, :LASTMODDATE, "
				+ ":PARENTTASK_ID, :PERFORMER_ID, :PREFIX, :PRIORITY, :STATUS, :TITLE)";
		MapSqlParameterSource map = new MapSqlParameterSource();
		String guid = UUID.randomUUID().toString();
		map.addValue("ID", guid);
		map.addValue("AUTHOR_ID", task.getAuthor().getId());
		map.addValue("CREATEDATE", task.getCreateDate());
		map.addValue("DEADLINE", task.getDeadLine());
		map.addValue("DESCRIPTION", task.getDescription());
		map.addValue("LASTMODDATE", task.getLastmodDate());
		map.addValue("PARENTTASK_ID", task.getParentTask().getId());
		map.addValue("PERFORMER_ID", task.getPerformer().getId());
		map.addValue("PREFIX", task.getPrefix());
		map.addValue("PRIORITY", task.getPriority());
		map.addValue("STATUS", task.getStatus());
		map.addValue("TITLE", task.getTitle());
		
		logger.debug(SQL_INSERT_TASK);
		logger.debug(map.getValues());

		try {
			int res = config.getNpjt().update(SQL_INSERT_TASK, map);
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
	public void deleteTask(String id) {
		
		String SQL_DELETE_TASK_BY_ID = "DELETE FROM doc.tasks WHERE id = :ID";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID", id);
		
		logger.debug(SQL_DELETE_TASK_BY_ID);
		logger.debug(map.getValues());
		
		try {
			config.getNpjt().update(SQL_DELETE_TASK_BY_ID, map);
		} catch (Exception e) {
			logger.error(null, e);
		}
	}

	@Override
	public void updateTask(Task task) {
		
		String SQL_UPDATE_TASK = "UPDATE doc.tasks SET author_id=:AUTHOR_ID, deadline=:DEADLINE, description=:DESCRIPTION, "
				+ "lastmoddate=:LASTMODDATE, parenttask_id=:PARENTTASK_ID, performer_id=:PERFORMER_ID, prefix=:PREFIX, "
				+ "priority=:PRIORITY, status=:STATUS, title=:TITLE WHERE id=:ID";
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID", task.getId());
		map.addValue("AUTHOR_ID", task.getAuthor().getId());
		map.addValue("DEADLINE", task.getDeadLine());
		map.addValue("DESCRIPTION", task.getDescription());
		map.addValue("LASTMODDATE", task.getLastmodDate());
		map.addValue("PARENTTASK_ID", task.getParentTask().getId());
		map.addValue("PERFORMER_ID", task.getPerformer().getId());
		map.addValue("PREFIX", task.getPrefix());
		map.addValue("PRIORITY", task.getPriority());
		map.addValue("STATUS", task.getStatus());
		map.addValue("TITLE", task.getTitle());
		
		logger.debug(SQL_UPDATE_TASK);
		logger.debug(map.getValues());

		try {
			config.getNpjt().update(SQL_UPDATE_TASK, map);
		} catch (Exception e) {
			logger.error(null, e);
		}
	}
}
