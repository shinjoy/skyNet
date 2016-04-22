package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.AdminBbsComment;



public class AdminBbsCommentDao {

	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper bbscommentMapper = new RowMapper() {
		public AdminBbsComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			AdminBbsComment AdminBbsComment = new AdminBbsComment();
			AdminBbsComment.setBbsCommentSeq(rs.getInt("bbs_comment_seq"));
			AdminBbsComment.setBbsSeq(rs.getInt("bbs_seq"));
			AdminBbsComment.setUserId(rs.getString("user_id"));
			AdminBbsComment.setBbsContents(rs.getString("bbs_contents"));
			AdminBbsComment.setFiles(rs.getString("files"));
			AdminBbsComment.setLinkUrl(rs.getString("link_url"));
			AdminBbsComment.setRegDate(rs.getString("reg_date"));
			AdminBbsComment.setrLevel(rs.getInt("r_level"));
			AdminBbsComment.setrCommentSeq(rs.getInt("r_comment_seq"));
			AdminBbsComment.setrRegDate(rs.getString("r_reg_date"));
			AdminBbsComment.setrAnswerStatus(rs.getInt("r_answer_status"));
			return AdminBbsComment;
		}
	};
	
	private RowMapper VbbscommentMapper = new RowMapper() {
		public AdminBbsComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			AdminBbsComment AdminBbsComment = new AdminBbsComment();
			AdminBbsComment.setBbsCommentSeq(rs.getInt("bbs_comment_seq"));
			AdminBbsComment.setBbsSeq(rs.getInt("bbs_seq"));
			AdminBbsComment.setUserId(rs.getString("user_id"));
			AdminBbsComment.setBbsContents(rs.getString("bbs_contents"));
			AdminBbsComment.setFiles(rs.getString("files"));
			AdminBbsComment.setLinkUrl(rs.getString("link_url"));
			AdminBbsComment.setRegDate(rs.getString("reg_date"));
			AdminBbsComment.setrLevel(rs.getInt("r_level"));
			AdminBbsComment.setrCommentSeq(rs.getInt("r_comment_seq"));
			AdminBbsComment.setrRegDate(rs.getString("r_reg_date"));
			AdminBbsComment.setUserName(rs.getString("user_name"));
			AdminBbsComment.setComapanyName(rs.getString("company_name"));
			AdminBbsComment.setrAnswerStatus(rs.getInt("r_answer_status"));
			return AdminBbsComment;
		}
	};


	public void addBbsComment(final AdminBbsComment AdminBbsComment) {
		String query = "" +
				"INSERT INTO T_NF_Admin_BBS_COMMENT " +
				"	( bbs_seq, user_id, bbs_contents,"
				+ " files, link_url, reg_date,"
				+ " r_level, r_comment_seq, r_reg_date,"
				+ " r_answer_status) " +
				"VALUES " +
				"	( ?, ?, ?,"
				+ " ?, ?, getDate(),"
				+ " ?, ?, ?,"
				+ " ?) ";
		this.jdbcTemplate.update(query, new Object[] {
		
				AdminBbsComment.getBbsSeq(),
				AdminBbsComment.getUserId(),
				AdminBbsComment.getBbsContents(),
				AdminBbsComment.getFiles(),
				AdminBbsComment.getLinkUrl(),
				AdminBbsComment.getrLevel(),
				AdminBbsComment.getrCommentSeq(),
				AdminBbsComment.getrRegDate(),
				AdminBbsComment.getrAnswerStatus()
		
		});
	}

	public void deleteBbsComment(int bbs_comment_seq) {
		String query = "DELETE FROM T_NF_Admin_BBS_COMMENT WHERE bbs_comment_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_comment_seq });
	}


	public void deleteBbsCommentBbsSeq(int bbs_seq) {
		String query = "DELETE FROM T_NF_Admin_BBS_COMMENT WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq });
	}

	public void updateBbsComment(AdminBbsComment AdminBbsComment) { 
		String query = "" + 
				"UPDATE T_NF_Admin_BBS_COMMENT SET " +
			
				"	bbs_seq = ?, " +
				"	user_id = ?, " +
				"	bbs_contents = ?, " +
				"	files = ?, " +
				"	link_url = ?, " +
				"   r_answer_status = ?, "+
				"	r_level = ?, " +
				"	r_comment_seq = ?, " +
				"	r_reg_date = ? " +
				"WHERE bbs_comment_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
				AdminBbsComment.getBbsSeq(),
				AdminBbsComment.getUserId(),
				AdminBbsComment.getBbsContents(),
				AdminBbsComment.getFiles(),
				AdminBbsComment.getLinkUrl(),
				AdminBbsComment.getrAnswerStatus(),
				AdminBbsComment.getrLevel(),
				AdminBbsComment.getrCommentSeq(),
				AdminBbsComment.getrRegDate(),
				AdminBbsComment.getBbsCommentSeq()
		});
	}

	public AdminBbsComment getTopBbsComment(int bbs_comment_seq) {
		String query = "" + 
				"SELECT top 1 * " +
				"FROM V_NF_Admin_BBS_COMMENT " +
				"WHERE bbs_seq = ?  order by reg_date desc, bbs_comment_seq desc ";
		try{
			return (AdminBbsComment)this.jdbcTemplate.queryForObject(query, new Object[] { bbs_comment_seq }, this.VbbscommentMapper);
		}catch(Exception e){
			return new AdminBbsComment();
		}
	}
	
	public AdminBbsComment getBbsComment(int bbs_comment_seq) {
		String query = "" + 
				"SELECT * " +
				"FROM V_NF_Admin_BBS_COMMENT " +
				"WHERE bbs_comment_seq = ? ";
		try{
			return (AdminBbsComment)this.jdbcTemplate.queryForObject(query, new Object[] { bbs_comment_seq }, this.VbbscommentMapper);
		}catch(Exception e){
			return new AdminBbsComment();
		}
	}

	public List<AdminBbsComment> getBbsCommentList(int bbsSeq) {
		String query = "" 
			/*	+ "	SELECT * FROM ( "*/
				+ "		SELECT * "
			/*	+ "			ROW_NUMBER() OVER(order by reg_date asc) as row_seq, "
				+ "			A.* "*/
				+"  from V_NF_Admin_BBS_COMMENT  where bbs_seq = ? order by reg_date asc ";
			//	+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
		return (List<AdminBbsComment>)this.jdbcTemplate.query(query,new Object[] { bbsSeq }, this.VbbscommentMapper);
	}
	
	public int getBbsCommentCount(int bbsSeq) {
		String query = "" 
				+ "	SELECT count(*) FROM  "
				+"   V_NF_Admin_BBS_COMMENT where bbs_seq = ? ";
		return this.jdbcTemplate.queryForInt(query,new Object[] { bbsSeq });
	}
}
