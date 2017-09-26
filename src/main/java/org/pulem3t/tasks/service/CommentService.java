/**
 * @author pulem3t
 */
package org.pulem3t.tasks.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;
import org.pulem3t.tasks.dao.CommentDAO;
import org.pulem3t.tasks.dao.TaskDAO;
import org.pulem3t.tasks.dao.UserDAO;
import org.pulem3t.tasks.entry.Comment;
import org.pulem3t.tasks.entry.User;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentService {
	
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private TaskDAO taskDAO;
	
	public List<Comment> getComments(String taskId) {
		
		return commentDAO.getComments(taskId);
	}
	
	public Comment getComment(String id) {
		
		return commentDAO.getComment(id);
	}
	
	public String addComment(String json) {
		
		JSONObject o = new JSONObject(json);
		Comment comment = new Comment();
		comment.setAuthor(userDAO.getUser(o.getString("authorId")));
		comment.setRating(o.getInt("rating"));
		comment.setTask(taskDAO.getTask(o.getString("taskId")));
		comment.setText(o.getString("text"));
		if(o.getJSONArray("relatedUsers").length() != 0) {
			List<User> userList = new ArrayList<>();
			Iterator<Object> it = o.getJSONArray("relatedUsers").iterator();
			while(it.hasNext()) {
				userList.add(userDAO.getUser((String) it.next()));
			}
			comment.setRelatedUsers(userList);
		}
		return commentDAO.addComment(comment);
	}
	
	public void deleteComment(String id) {
		
		commentDAO.deleteComment(id);
	}
	
	public void updateComment(String json) {
		
		JSONObject o = new JSONObject(json);
		Comment comment = new Comment();
		comment.setId(o.getString("id"));
		comment.setAuthor(userDAO.getUser(o.getString("authorId")));
		comment.setRating(o.getInt("rating"));
		comment.setTask(taskDAO.getTask(o.getString("taskId")));
		comment.setText(o.getString("text"));
		if(o.getJSONArray("relatedUsers").length() != 0) {
			List<User> userList = new ArrayList<>();
			Iterator<Object> it = o.getJSONArray("relatedUsers").iterator();
			while(it.hasNext()) {
				userList.add(userDAO.getUser((String) it.next()));
			}
			comment.setRelatedUsers(userList);
		}
		comment.setLastmodDate(System.currentTimeMillis());
		commentDAO.updateComment(comment);
	}
	
	public void setCommentRating(String id, int rating) {
		
		Comment comment = getComment(id);
		comment.setRating(comment.getRating()+rating);
		commentDAO.updateComment(comment);
	}
}
