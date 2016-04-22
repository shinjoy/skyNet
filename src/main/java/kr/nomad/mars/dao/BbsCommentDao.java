package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.BbsComment;

public class BbsCommentDao {

	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper bbscommentMapper = new RowMapper() {
		public BbsComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			BbsComment bbscomment = new BbsComment();
			bbscomment.setBbsCommentSeq(rs.getInt("bbs_comment_seq"));
			bbscomment.setBbsSeq(rs.getInt("bbs_seq"));
			bbscomment.setUserId(rs.getString("user_id"));
			bbscomment.setBbsContents(rs.getString("bbs_contents"));
			bbscomment.setFiles(rs.getString("files"));
			bbscomment.setLinkUrl(rs.getString("link_url"));
			bbscomment.setRegDate(rs.getString("reg_date"));
			bbscomment.setrLevel(rs.getInt("r_level"));
			bbscomment.setrCommentSeq(rs.getInt("r_comment_seq"));
			bbscomment.setrRegDate(rs.getString("r_reg_date"));
			bbscomment.setrAnswerStatus(rs.getInt("r_answer_status"));
			return bbscomment;
		}
	};
	
	private RowMapper VbbscommentMapper = new RowMapper() {
		public BbsComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			BbsComment bbscomment = new BbsComment();
			bbscomment.setBbsCommentSeq(rs.getInt("bbs_comment_seq"));
			bbscomment.setBbsSeq(rs.getInt("bbs_seq"));
			bbscomment.setUserId(rs.getString("user_id"));
			bbscomment.setBbsContents(rs.getString("bbs_contents"));
			bbscomment.setFiles(rs.getString("files"));
			bbscomment.setLinkUrl(rs.getString("link_url"));
			bbscomment.setRegDate(rs.getString("reg_date"));
			bbscomment.setrLevel(rs.getInt("r_level"));
			bbscomment.setrCommentSeq(rs.getInt("r_comment_seq"));
			bbscomment.setrRegDate(rs.getString("r_reg_date"));
			bbscomment.setUserName(rs.getString("user_name"));
			bbscomment.setComapanyName(rs.getString("company_name"));
			bbscomment.setrAnswerStatus(rs.getInt("r_answer_status"));
			return bbscomment;
		}
	};


	public void addBbsComment(final BbsComment bbscomment) {
		String query = "" +
				"INSERT INTO T_NF_BBS_COMMENT " +
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
		
			bbscomment.getBbsSeq(),
			bbscomment.getUserId(),
			bbscomment.getBbsContents(),
			bbscomment.getFiles(),
			bbscomment.getLinkUrl(),
			bbscomment.getrLevel(),
			bbscomment.getrCommentSeq(),
			bbscomment.getrRegDate(),
			bbscomment.getrAnswerStatus()
		
		});
	}

	public void deleteBbsComment(int bbs_comment_seq) {
		String query = "DELETE FROM T_NF_BBS_COMMENT WHERE bbs_comment_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_comment_seq });
	}


	public void deleteBbsCommentBbsSeq(int bbs_seq) {
		String query = "DELETE FROM T_NF_BBS_COMMENT WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq });
	}

	public void updateBbsComment(BbsComment bbscomment) { 
		String query = "" + 
				"UPDATE T_NF_BBS_COMMENT SET " +
			
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
		
			bbscomment.getBbsSeq(),
			bbscomment.getUserId(),
			bbscomment.getBbsContents(),
			bbscomment.getFiles(),
			bbscomment.getLinkUrl(),
			bbscomment.getrAnswerStatus(),
			bbscomment.getrLevel(),
			bbscomment.getrCommentSeq(),
			bbscomment.getrRegDate(),
			bbscomment.getBbsCommentSeq()
		});
	}

	public BbsComment getTopBbsComment(int bbs_comment_seq) {
		String query = "" + 
				"SELECT top 1 * " +
				"FROM V_NF_BBS_COMMENT " +
				"WHERE bbs_seq = ? and r_answer_status!=0 order by reg_date desc, bbs_comment_seq desc ";
		try{
			return (BbsComment)this.jdbcTemplate.queryForObject(query, new Object[] { bbs_comment_seq }, this.VbbscommentMapper);
		}catch(Exception e){
			return new BbsComment();
		}
	}
	
	public BbsComment getBbsComment(int bbs_comment_seq) {
		String query = "" + 
				"SELECT * " +
				"FROM V_NF_BBS_COMMENT " +
				"WHERE bbs_comment_seq = ? ";
		try{
			return (BbsComment)this.jdbcTemplate.queryForObject(query, new Object[] { bbs_comment_seq }, this.VbbscommentMapper);
		}catch(Exception e){
			return new BbsComment();
		}
	}

	public List<BbsComment> getBbsCommentList(int bbsSeq) {
		String query = "" 
			/*	+ "	SELECT * FROM ( "*/
				+ "		SELECT * "
			/*	+ "			ROW_NUMBER() OVER(order by reg_date asc) as row_seq, "
				+ "			A.* "*/
				+"  from v_nf_bbs_comment  where bbs_seq = ? order by reg_date asc ";
			//	+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
		return (List<BbsComment>)this.jdbcTemplate.query(query,new Object[] { bbsSeq }, this.VbbscommentMapper);
	}
	
	public int getBbsCommentCount(int bbsSeq) {
		String query = "" 
				+ "	SELECT count(*) FROM  "
				+"   v_nf_bbs_comment where bbs_seq = ? ";
		return this.jdbcTemplate.queryForInt(query,new Object[] { bbsSeq });
	}
}
