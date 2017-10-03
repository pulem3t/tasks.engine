package org.pulem3t.tasks.dao.cratedb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.pulem3t.tasks.config.CrateDBConfig;
import org.pulem3t.tasks.dao.CommentDAO;
import org.pulem3t.tasks.entry.Comment;
import org.pulem3t.tasks.entry.User;
import org.pulem3t.tasks.mapper.CommentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class CommentDAOCrateDB implements CommentDAO {

	@Autowired
	private CrateDBConfig config;
	private Logger logger = Logger.getLogger(CommentDAOCrateDB.class);

	private List<User> getRelatedUserList(String commentId) {

		String SQL_GET_REL_USERS_IDS = "SELECT id_user FROM doc.comment_rel_users WHERE id_comment = :ID_COMMENT";

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID_COMMENT", commentId);

		logger.debug(SQL_GET_REL_USERS_IDS);
		logger.debug(map.getValues());

		List<User> relatedUserList = new ArrayList<>();
		try {
			List<String> userIdList = config.getNpjt().queryForList(SQL_GET_REL_USERS_IDS, new MapSqlParameterSource(), String.class);
			for(String userId: userIdList) {
				User u = new User();
				u.setId(userId);
				relatedUserList.add(u);
			}
		} catch (Exception e) {
			logger.error(null, e);
		}
		return relatedUserList;
	}

	private void insertRelatedUser(String commentId, String userId) {

		String SQL_INSERT_REL_USER = "INSERT INTO doc.comment_rel_users (id_comment, id_user) VALUES(:ID_COMMENT, :ID_USER)";

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID_COMMENT", commentId);
		map.addValue("ID_USER", userId);
		
		logger.debug(SQL_INSERT_REL_USER);
		logger.debug(map.getValues());

		try {
			config.getNpjt().update(SQL_INSERT_REL_USER, map);
		} catch (Exception e) {
			logger.error(null, e);
		}
	}
	
	private void deleteRelatedUser(String commentId) {

		String SQL_DELETE_REL_USER = "DELETE FROM doc.comment_rel_users WHERE id_comment = :ID_COMMENT";

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID_COMMENT", commentId);
		
		logger.debug(SQL_DELETE_REL_USER);
		logger.debug(map.getValues());

		try {
			config.getNpjt().update(SQL_DELETE_REL_USER, map);
		} catch (Exception e) {
			logger.error(null, e);
		}
	}

	@Override
	public List<Comment> getComments(String taskId) {
		String SQL_GET_COMMENTS = "SELECT author_id, createdate, id, lastmoddate, rating, "
				+ "task_id, text FROM doc.comments WHERE task_id = :TASK_ID";

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("TASK_ID", taskId);

		logger.debug(SQL_GET_COMMENTS);
		logger.debug(map.getValues());

		List<Comment> commentList = new ArrayList<>();
		List<Comment> fullCommentList = new ArrayList<>();
		try {
			commentList = config.getNpjt().query(SQL_GET_COMMENTS, new MapSqlParameterSource(), new CommentRowMapper());
			for(Comment c: commentList) {
				c.setRelatedUsers(getRelatedUserList(c.getId()));
				fullCommentList.add(c);
			}
		} catch (Exception e) {
			logger.error(null, e);
		}
		return fullCommentList;
	}

	@Override
	public Comment getComment(String id) {
		String SQL_GET_COMMENT_BY_ID = "SELECT author_id, createdate, id, lastmoddate, rating, "
				+ "task_id, text FROM doc.comments WHERE id = :ID";

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID", id);

		logger.debug(SQL_GET_COMMENT_BY_ID);
		logger.debug(map.getValues());

		try {
			Comment c = config.getNpjt().queryForObject(SQL_GET_COMMENT_BY_ID, new MapSqlParameterSource(), new CommentRowMapper());
			c.setRelatedUsers(getRelatedUserList(c.getId()));
			return c;
		} catch (Exception e) {
			logger.error(null, e);
			return null;
		}
	}

	@Override
	public String addComment(Comment comment) {
		String SQL_INSERT_COMMENT = "INSERT INTO doc.comments "
				+ "(author_id, createdate, id, lastmoddate, rating, task_id, text) "
				+ "VALUES(:AUTHOR_ID, :CREATEDATE, :ID, :LASTMODDATE, :RATING, :TASK_ID, :TEXT)";

		MapSqlParameterSource map = new MapSqlParameterSource();
		String guid = UUID.randomUUID().toString();
		map.addValue("ID", guid);
		map.addValue("AUTHOR_ID", comment.getAuthor().getId());
		map.addValue("CREATEDATE", comment.getCreateDate());
		map.addValue("LASTMODDATE", comment.getLastmodDate());
		map.addValue("RATING", comment.getRating());
		map.addValue("TASK_ID", comment.getTask().getId());
		map.addValue("TEXT", comment.getText());
		
		logger.debug(SQL_INSERT_COMMENT);
		logger.debug(map.getValues());
		
		try {
			config.getNpjt().update(SQL_INSERT_COMMENT, map);
			for(User u: comment.getRelatedUsers()) {
				insertRelatedUser(guid, u.getId());
			}
		} catch (Exception e) {
			logger.error(null, e);
		}
		return guid;
	}

	@Override
	public void deleteComment(String id) {
		String SQL_DELETE_COMMENT = "DELETE FROM doc.comments WHERE id = :ID";
		String SQL_DELETE_REL_USERS = "DELETE FROM doc.comment_rel_users WHERE id_comment = :ID";
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID", id);
		
		logger.debug(SQL_DELETE_COMMENT);
		logger.debug(SQL_DELETE_REL_USERS);
		logger.debug(map.getValues());
		
		try {
			config.getNpjt().update(SQL_DELETE_COMMENT, map);
			config.getNpjt().update(SQL_DELETE_REL_USERS, map);
		} catch (Exception e) {
			logger.error(null, e);
		}
	}

	@Override
	public void updateComment(Comment comment) {
		
		String SQL_UPDATE_COMMENT = "UPDATE doc.comments SET lastmoddate=:LASTMODDATE, "
				+ "rating=:RATING, text=:TEXT WHERE id = :ID";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		String guid = UUID.randomUUID().toString();
		map.addValue("ID", comment.getId());
		map.addValue("LASTMODDATE", comment.getLastmodDate());
		map.addValue("RATING", comment.getRating());
		map.addValue("TEXT", comment.getText());
		
		logger.debug(SQL_UPDATE_COMMENT);
		logger.debug(map.getValues());
		
		try {
			config.getNpjt().update(SQL_UPDATE_COMMENT, map);
			deleteRelatedUser(comment.getId());
			for(User u: comment.getRelatedUsers()) {
				insertRelatedUser(guid, u.getId());
			}
		} catch (Exception e) {
			logger.error(null, e);
		}
	}

}
