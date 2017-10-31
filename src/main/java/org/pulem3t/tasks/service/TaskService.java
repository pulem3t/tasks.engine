/**
 * @author pulem3t
 */
package org.pulem3t.tasks.service;

import java.util.List;

import org.json.JSONObject;
import org.pulem3t.tasks.dao.TaskDAO;
import org.pulem3t.tasks.dao.UserDAO;
import org.pulem3t.tasks.entry.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
	
	@Autowired
	private TaskDAO taskDAO;
	@Autowired
	private UserDAO userDAO;
	
	
	public String addTask(String json) {
		
		JSONObject o = new JSONObject(json).getJSONObject("task");
		Task task = new Task();
		task.setAuthor(userDAO.getUser(o.getString("authorId")));
		task.setDeadLine(o.getLong("deadLine"));
		task.setDescription(o.getString("description"));
		task.setPerformer(userDAO.getUser(o.getString("performerId")));
		task.setPrefix(o.getString("prefix"));
		task.setPriority(o.getInt("priority"));
		//task.setStatus(o.getInt("status"));
		task.setTitle(o.getString("title"));
		return taskDAO.addTask(task);
	}

	public void delTask(String id) {

		taskDAO.deleteTask(id);
	}

	public void updateTask(String json) {

		JSONObject o = new JSONObject(json).getJSONObject("task");
		Task task = new Task();
		task.setId(o.getString("id"));
		task.setAuthor(userDAO.getUser(o.getString("authorId")));
		task.setDeadLine(o.getLong("deadLine"));
		task.setDescription(o.getString("description"));
		task.setPerformer(userDAO.getUser(o.getString("performerId")));
		task.setPrefix(o.getString("prefix"));
		task.setPriority(o.getInt("priority"));
		task.setStatus(o.getInt("status"));
		task.setTitle(o.getString("title"));
		task.setLastmodDate(System.currentTimeMillis());
		taskDAO.updateTask(task);
	}

	public List<Task> getLastAddedTasks() {

		return taskDAO.getLastAddedTasks();
	}

	public  Task getTask(String id) {

		return taskDAO.getTask(id);
	}

	public List<Task> getTasks() {

		return taskDAO.getTasks();
	}
	
	public void setTaskPriority(String id, int priority) {
		
		Task task = getTask(id);
		task.setPriority(priority);
		taskDAO.updateTask(task);
	}

	public void setTaskStatus(String id, int status) {
		
		Task task = getTask(id);
		task.setStatus(status);
		taskDAO.updateTask(task);
	}
}
