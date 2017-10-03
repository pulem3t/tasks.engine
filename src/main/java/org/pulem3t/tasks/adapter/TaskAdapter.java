package org.pulem3t.tasks.adapter;

import org.pulem3t.tasks.dao.TaskDAO;
import org.pulem3t.tasks.dao.UserDAO;
import org.pulem3t.tasks.entry.Task;
import org.pulem3t.tasks.entry.User;
import org.pulem3t.tasks.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskAdapter {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private TaskDAO taskDAO;
	private String id;
	private String prefix;
	private String title;
	private String authorId;
	private String parentTaskId;
	private String performerId;
	private String description;
	private int status;
	private int priority;
	private long createDate;
	private long lastmodDate;
	private long deadLine;
	
	public TaskAdapter() {
		this.id = "";
		this.prefix = "";
		this.title = "";
		this.authorId = "";
		this.parentTaskId = "";
		this.performerId = "";
		this.description = "";
		this.status = Status.NEW.getStatus();
		this.priority = 0;
		this.createDate = System.currentTimeMillis();
		this.lastmodDate = System.currentTimeMillis();
		this.lastmodDate = 0;
		this.deadLine = 0;
	}
	
	public Task getTask() {
		Task t = new Task();
		t.setAuthor(userDAO.getUser(authorId));
		t.setCreateDate(createDate);
		t.setDeadLine(deadLine);
		t.setDescription(description);
		t.setId(authorId);
		t.setLastmodDate(lastmodDate);
		t.setParentTask(taskDAO.getTask(parentTaskId));
		t.setPerformerId(userDAO.getUser(performerId));
		t.setPrefix(prefix);
		t.setPriority(priority);
		t.setStatus(status);
		t.setTitle(title);
		return t;
	}
 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getParentTaskId() {
		return parentTaskId;
	}
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public String getPerformerId() {
		return performerId;
	}
	public void setPerformerId(String performerId) {
		this.performerId = performerId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
	public long getLastmodDate() {
		return lastmodDate;
	}
	public void setLastmodDate(long lastmodDate) {
		this.lastmodDate = lastmodDate;
	}
	public long getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(long deadLine) {
		this.deadLine = deadLine;
	}
}
