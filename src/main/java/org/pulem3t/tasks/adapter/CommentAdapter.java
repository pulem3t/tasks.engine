package org.pulem3t.tasks.adapter;

import org.pulem3t.tasks.dao.TaskDAO;
import org.pulem3t.tasks.dao.UserDAO;
import org.pulem3t.tasks.entry.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

public class CommentAdapter {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private TaskDAO taskDAO;
	@Getter @Setter private String id;
	@Getter @Setter private String authorId;
	@Getter @Setter private String taskId;
	@Getter @Setter private String text;
	@Getter @Setter private int rating;
	@Getter @Setter private long createDate;
	@Getter @Setter private long lastmodDate;
	
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
}
