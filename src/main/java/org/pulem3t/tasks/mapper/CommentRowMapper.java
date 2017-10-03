package org.pulem3t.tasks.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.pulem3t.tasks.adapter.CommentAdapter;
import org.pulem3t.tasks.entry.Comment;
import org.springframework.jdbc.core.RowMapper;

public class CommentRowMapper implements RowMapper<Comment> {

	@Override
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
		CommentAdapter ca = new CommentAdapter();
		ca.setAuthorId(rs.getString("author_id"));
		ca.setCreateDate(rs.getLong("createdate"));
		ca.setId(rs.getString("id"));
		ca.setLastmodDate(rs.getLong("lastmoddate"));
		ca.setRating(rs.getInt("rating"));
		ca.setTaskId(rs.getString("task_id"));
		ca.setText(rs.getString("text"));
		return ca.getComment();
	}

}
