package org.pulem3t.tasks.adapter;

import org.pulem3t.tasks.dao.UserDAO;
import org.pulem3t.tasks.entry.Task;
import org.pulem3t.tasks.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

public class TaskAdapter {

	@Autowired
	private UserDAO userDAO;
	@Getter @Setter private String id;
	@Getter @Setter private String prefix;
	@Getter @Setter private String title;
	@Getter @Setter private String authorId;
	@Getter @Setter private String performerId;
	@Getter @Setter private String description;
	@Getter @Setter private int status;
	@Getter @Setter private int priority;
	@Getter @Setter private long createDate;
	@Getter @Setter private long lastmodDate;
	@Getter @Setter private long deadLine;
	
	public TaskAdapter() {
		this.id = "";
		this.prefix = "";
		this.title = "";
		this.authorId = "";
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
		t.setPerformer(userDAO.getUser(performerId));
		t.setPrefix(prefix);
		t.setPriority(priority);
		t.setStatus(status);
		t.setTitle(title);
		return t;
	}
}
