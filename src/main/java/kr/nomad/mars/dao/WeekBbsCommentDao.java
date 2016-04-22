package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.WeekBbsComment;



public class WeekBbsCommentDao {

	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper bbscommentMapper = new RowMapper() {
		public WeekBbsComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			WeekBbsComment weekBbsComment = new WeekBbsComment();
			weekBbsComment.setBbsCommentSeq(rs.getInt("bbs_comment_seq"));
			weekBbsComment.setBbsSeq(rs.getInt("bbs_seq"));
			weekBbsComment.setUserId(rs.getString("user_id"));
			weekBbsComment.setBbsContents(rs.getString("bbs_contents"));
			weekBbsComment.setFiles(rs.getString("files"));
			weekBbsComment.setLinkUrl(rs.getString("link_url"));
			weekBbsComment.setRegDate(rs.getString("reg_date"));
			weekBbsComment.setrLevel(rs.getInt("r_level"));
			weekBbsComment.setrCommentSeq(rs.getInt("r_comment_seq"));
			weekBbsComment.setrRegDate(rs.getString("r_reg_date"));
			weekBbsComment.setrAnswerStatus(rs.getInt("r_answer_status"));
			return weekBbsComment;
		}
	};
	
	private RowMapper VbbscommentMapper = new RowMapper() {
		public WeekBbsComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			WeekBbsComment weekBbsComment = new WeekBbsComment();
			weekBbsComment.setBbsCommentSeq(rs.getInt("bbs_comment_seq"));
			weekBbsComment.setBbsSeq(rs.getInt("bbs_seq"));
			weekBbsComment.setUserId(rs.getString("user_id"));
			weekBbsComment.setBbsContents(rs.getString("bbs_contents"));
			weekBbsComment.setFiles(rs.getString("files"));
			weekBbsComment.setLinkUrl(rs.getString("link_url"));
			weekBbsComment.setRegDate(rs.getString("reg_date"));
			weekBbsComment.setrLevel(rs.getInt("r_level"));
			weekBbsComment.setrCommentSeq(rs.getInt("r_comment_seq"));
			weekBbsComment.setrRegDate(rs.getString("r_reg_date"));
			weekBbsComment.setUserName(rs.getString("user_name"));
			weekBbsComment.setComapanyName(rs.getString("company_name"));
			weekBbsComment.setrAnswerStatus(rs.getInt("r_answer_status"));
			return weekBbsComment;
		}
	};


	public void addBbsComment(final WeekBbsComment weekBbsComment) {
		String query = "" +
				"INSERT INTO T_NF_WEEK_BBS_COMMENT " +
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
		
				weekBbsComment.getBbsSeq(),
				weekBbsComment.getUserId(),
				weekBbsComment.getBbsContents(),
				weekBbsComment.getFiles(),
				weekBbsComment.getLinkUrl(),
				weekBbsComment.getrLevel(),
				weekBbsComment.getrCommentSeq(),
				weekBbsComment.getrRegDate(),
				weekBbsComment.getrAnswerStatus()
		
		});
	}

	public void deleteBbsComment(int bbs_comment_seq) {
		String query = "DELETE FROM T_NF_WEEK_BBS_COMMENT WHERE bbs_comment_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_comment_seq });
	}


	public void deleteBbsCommentBbsSeq(int bbs_seq) {
		String query = "DELETE FROM T_NF_WEEK_BBS_COMMENT WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq });
	}

	public void updateBbsComment(WeekBbsComment weekBbsComment) { 
		String query = "" + 
				"UPDATE T_NF_WEEK_BBS_COMMENT SET " +
			
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
		
				weekBbsComment.getBbsSeq(),
				weekBbsComment.getUserId(),
				weekBbsComment.getBbsContents(),
				weekBbsComment.getFiles(),
				weekBbsComment.getLinkUrl(),
				weekBbsComment.getrAnswerStatus(),
				weekBbsComment.getrLevel(),
				weekBbsComment.getrCommentSeq(),
				weekBbsComment.getrRegDate(),
				weekBbsComment.getBbsCommentSeq()
		});
	}

	public WeekBbsComment getTopBbsComment(int bbs_comment_seq) {
		String query = "" + 
				"SELECT top 1 * " +
				"FROM V_NF_WEEK_BBS_COMMENT " +
				"WHERE bbs_seq = ?  order by reg_date desc, bbs_comment_seq desc ";
		try{
			return (WeekBbsComment)this.jdbcTemplate.queryForObject(query, new Object[] { bbs_comment_seq }, this.VbbscommentMapper);
		}catch(Exception e){
			return new WeekBbsComment();
		}
	}
	
	public WeekBbsComment getBbsComment(int bbs_comment_seq) {
		String query = "" + 
				"SELECT * " +
				"FROM V_NF_WEEK_BBS_COMMENT " +
				"WHERE bbs_comment_seq = ? ";
		try{
			return (WeekBbsComment)this.jdbcTemplate.queryForObject(query, new Object[] { bbs_comment_seq }, this.VbbscommentMapper);
		}catch(Exception e){
			return new WeekBbsComment();
		}
	}

	public List<WeekBbsComment> getBbsCommentList(int bbsSeq) {
		String query = "" 
			/*	+ "	SELECT * FROM ( "*/
				+ "		SELECT * "
			/*	+ "			ROW_NUMBER() OVER(order by reg_date asc) as row_seq, "
				+ "			A.* "*/
				+"  from V_NF_WEEK_BBS_COMMENT  where bbs_seq = ? order by reg_date asc ";
			//	+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
		return (List<WeekBbsComment>)this.jdbcTemplate.query(query,new Object[] { bbsSeq }, this.VbbscommentMapper);
	}
	
	public int getBbsCommentCount(int bbsSeq) {
		String query = "" 
				+ "	SELECT count(*) FROM  "
				+"   V_NF_WEEK_BBS_COMMENT where bbs_seq = ? ";
		return this.jdbcTemplate.queryForInt(query,new Object[] { bbsSeq });
	}
}
