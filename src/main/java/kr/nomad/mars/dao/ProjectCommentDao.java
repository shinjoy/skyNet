package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.ProjectComment;
public class ProjectCommentDao {
	
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper projectcommentMapper = new RowMapper() {
		public ProjectComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectComment projectcomment = new ProjectComment();
			projectcomment.setCommentSeq(rs.getInt("comment_seq"));
			projectcomment.setProjectSeq(rs.getInt("project_seq"));
			projectcomment.setDesignComment(rs.getString("design_comment"));
			projectcomment.setIosComment(rs.getString("ios_comment"));
			projectcomment.setAndComment(rs.getString("and_comment"));
			projectcomment.setWebComment(rs.getString("web_comment"));
			projectcomment.setServerComment(rs.getString("server_comment"));
			projectcomment.setPcComment(rs.getString("pc_comment"));
			projectcomment.setRegDate(rs.getString("reg_date"));
			return projectcomment;
		}
	};

	public void addProjectComment(final ProjectComment projectcomment) {
		String query = "" +
				"INSERT INTO T_NF_PROJECT_COMMENT " +
				"	( project_seq, design_comment, ios_comment,"
				+ " and_comment, web_comment, server_comment,"
				+ " pc_comment, reg_date) " +
				"VALUES " +
				"	( ?, ?, ?,"
				+ " ?, ?, ?,"
				+ " ?, getDate()) ";
		this.jdbcTemplate.update(query, new Object[] {
		
			projectcomment.getProjectSeq(),
			projectcomment.getDesignComment(),
			projectcomment.getIosComment(),
			projectcomment.getAndComment(),
			projectcomment.getWebComment(),
			projectcomment.getServerComment(),
			projectcomment.getPcComment()
		});
	}

	public void deleteProjectComment(String comment_seq) {
		String query = "DELETE FROM T_NF_PROJECT_COMMENT WHERE comment_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { comment_seq });
	}

	public void updateProjectComment(ProjectComment projectcomment) { 
		String query = "" + 
				"UPDATE T_NF_PROJECT_COMMENT SET " +
				
				"	project_seq = ?, " +
				"	design_comment = ?, " +
				"	ios_comment = ?, " +
				"	and_comment = ?, " +
				"	web_comment = ?, " +
				"	server_comment = ?, " +
				"	pc_comment = ? " +
			
				"WHERE comment_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			projectcomment.getProjectSeq(),
			projectcomment.getDesignComment(),
			projectcomment.getIosComment(),
			projectcomment.getAndComment(),
			projectcomment.getWebComment(),
			projectcomment.getServerComment(),
			projectcomment.getPcComment(),
			projectcomment.getCommentSeq()
		});
	}

	public ProjectComment getProjectCommentByTop(int projectSeq) {
		String query = "" + 
				"SELECT top 1 * " +
				"FROM T_NF_PROJECT_COMMENT " +
				"WHERE project_seq = ? ";
		try{
			return (ProjectComment)this.jdbcTemplate.queryForObject(query, new Object[] { projectSeq }, this.projectcommentMapper);
		}catch(Exception e){
			return new ProjectComment();
		}
	}
	public ProjectComment getProjectComment(int comment_seq) {
		String query = "" + 
				"SELECT  * " +
				"FROM T_NF_PROJECT_COMMENT " +
				"WHERE comment_seq = ? ";
		try{
			return (ProjectComment)this.jdbcTemplate.queryForObject(query, new Object[] { comment_seq }, this.projectcommentMapper);
		}catch(Exception e){
			return new ProjectComment();
		}
	}
	public List<ProjectComment> getProjectCommentList(int page, int itemCountPerPage) {
		String query = "" +
				"SELECT TOP "+ itemCountPerPage +" comment_seq, project_seq, design_comment, ios_comment, and_comment, web_comment, server_comment, pc_comment, reg_date " +
				"FROM T_NF_PROJECT_COMMENT " +
				"WHERE comment_seq <= ( " +
				"	SELECT MIN(comment_seq) " +
				"	FROM ( " +
				"		SELECT TOP "+ ((page-1) * itemCountPerPage + 1) +" comment_seq " +
				"		FROM T_NF_PROJECT_COMMENT " +
				"		ORDER BY comment_seq DESC " +
				"	) AS A) " +
				"ORDER BY comment_seq DESC";
		return (List<ProjectComment>)this.jdbcTemplate.query(query, this.projectcommentMapper);
	}

}
