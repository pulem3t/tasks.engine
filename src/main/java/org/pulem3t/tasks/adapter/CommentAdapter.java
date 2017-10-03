package org.pulem3t.tasks.adapter;

import org.pulem3t.tasks.dao.TaskDAO;
import org.pulem3t.tasks.dao.UserDAO;
import org.pulem3t.tasks.entry.Comment;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentAdapter {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private TaskDAO taskDAO;
	private String id;
	private String authorId;
	private String taskId;
	private String text;
	private int rating;
	private long createDate;
	private long lastmodDate;
	
	public CommentAdapter() {
		this.id = "";
		this.authorId = "";
		this.taskId = "";
		this.text = "";
		this.rating = 0;
		this.createDate = System.currentTimeMillis();
		this.lastmodDate = System.currentTimeMillis();
	}
	
	public Comment getComment() {
		Comment c = new Comment();
		c.setAuthor(userDAO.getUser(authorId));
		c.setId(id);
		c.setLastmodDate(lastmodDate);
		c.setRating(rating);
		c.setTask(taskDAO.getTask(taskId));
		c.setText(text);
		return c;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
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
}
