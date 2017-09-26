package org.pulem3t.tasks.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.pulem3t.tasks.entry.Comment;
import org.pulem3t.tasks.entry.User;
import org.pulem3t.tasks.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	private Logger logger = Logger.getLogger("CommentController");

	@RequestMapping(value="/addComment", method=RequestMethod.POST, consumes = "application/json; charset=utf-8", 
			produces = "application/json; charset=utf-8")
	public @ResponseBody String addComment(@RequestBody String json) {
		
		JSONObject o = new JSONObject();
		String id = "";
		try {
			id = commentService.addComment(json);
			logger.info("COMMENTS: Added comment with id = " + id);
			o.put("id", id);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("id", id);
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/delComment/{id}", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String delComment(@PathVariable("id") String id) {
		
		JSONObject o = new JSONObject();
		try {
			logger.info("COMMENTS: Deleting comment with id = " + id);
			commentService.deleteComment(id);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/updateComment", method=RequestMethod.POST, consumes = "application/json; charset=utf-8", 
			produces = "application/json; charset=utf-8")
	public @ResponseBody String updateComment(@RequestBody String json) {
		
		JSONObject o = new JSONObject();
		try {
			logger.info("COMMENTS: Updating comment with id = " + new JSONObject(json).getString("id"));
			commentService.updateComment(json);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/getComment/{id}", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getComment(@PathVariable("id") String id) {

		JSONObject o = new JSONObject();
		try {
			Comment comment = commentService.getComment(id);
			JSONObject c = new JSONObject();
			c.put("id", comment.getId());
			c.put("authorId", comment.getAuthor().getId());
			c.put("taskId", comment.getTask().getId());
			c.put("text", comment.getText());
			c.put("rating", comment.getRating());
			JSONArray relatedUsers = new JSONArray();
			for(User u: comment.getRelatedUsers()) {
				relatedUsers.put(u.getId());
			}
			c.put("relatedUsers", relatedUsers);
			c.put("createDate", comment.getCreateDate());
			c.put("lastmodDate", comment.getLastmodDate());
			o.put("comment", c);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}

	@RequestMapping(value="/getComments", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getComments(@PathVariable("taskId") String taskId) {

		JSONObject o = new JSONObject();
		try {
			List<Comment> commentList = commentService.getComments(taskId);
			o.put("items", commentList);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}
	
	@RequestMapping(value="/setCommentRating", method=RequestMethod.POST, consumes = "application/json; charset=utf-8", 
			produces = "application/json; charset=utf-8")
	public @ResponseBody String setCommentRating(String id, int rating) {
		
		JSONObject o = new JSONObject();
		try {
			logger.info("COMMENTS: Setting rating for comment with id = " + id);
			commentService.setCommentRating(id, rating);
			o.put("success", true);
		} catch (Exception e) {
			logger.error(null, e);
			o.put("success", false);
		}
		return o.toString(4);
	}
}
