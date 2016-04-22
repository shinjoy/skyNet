package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.Bbs;




public class BbsDao {

	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper bbsMapper = new RowMapper() {
		public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {
			Bbs bbs = new Bbs();
			bbs.setBbsSeq(rs.getInt("bbs_seq"));
			bbs.setBbsType(rs.getString("bbs_type"));
			bbs.setBbsType2(rs.getString("bbs_type2"));
			bbs.setProjectSeq(rs.getInt("project_seq"));
			bbs.setBbsTitle(rs.getString("bbs_title"));
			bbs.setAnswerStatus(rs.getInt("answer_status"));
			bbs.setBbsContents(rs.getString("bbs_contents"));
			bbs.setSendSms(rs.getInt("send_sms"));
			bbs.setUserId(rs.getString("user_id"));
			bbs.setBbsRegDate(rs.getString("bbs_reg_date"));
			bbs.setGrade(rs.getString("grade"));
			bbs.setCommentCount(rs.getInt("comment_count"));
			bbs.setBbsStartday(rs.getString("bbs_startday"));
			bbs.setBbsEndday(rs.getString("bbs_endday"));
			return bbs;
		}
	};
	
	private RowMapper VbbsMapper = new RowMapper() {
		public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {
			Bbs bbs = new Bbs();
			bbs.setBbsSeq(rs.getInt("bbs_seq"));
			bbs.setBbsType(rs.getString("bbs_type"));
			bbs.setBbsType2(rs.getString("bbs_type2"));
			bbs.setProjectSeq(rs.getInt("project_seq"));
			bbs.setBbsTitle(rs.getString("bbs_title"));
			bbs.setAnswerStatus(rs.getInt("answer_status"));
			bbs.setBbsContents(rs.getString("bbs_contents"));
			bbs.setSendSms(rs.getInt("send_sms"));
			bbs.setUserId(rs.getString("user_id"));
			bbs.setBbsRegDate(rs.getString("bbs_reg_date"));
			bbs.setUserName(rs.getString("user_name"));
			bbs.setCompanyName(rs.getString("company_name"));
			bbs.setGrade(rs.getString("grade"));
			bbs.setCommentCount(rs.getInt("comment_count"));
			bbs.setUserPhone(rs.getString("user_phone"));
			bbs.setBbsStartday(rs.getString("bbs_startday"));
			bbs.setBbsEndday(rs.getString("bbs_endday"));
			return bbs;
		}
	};

	public int addBbs(final Bbs bbs) {
		String query = "" +
				"INSERT INTO T_NF_BBS " +
				"	( bbs_type, bbs_type2,"
				+ " project_seq, bbs_title, answer_status,"
				+ " bbs_contents, send_sms, user_id,"
				+ " bbs_reg_date,grade,comment_count,"
				+ "	bbs_startday,bbs_endday) " +
				"VALUES " +
				"	( ?, ?, "
				+ "?, ?, ?,"
				+ " ?, ?, ?,"
				+ " getDate(),?,?,"
				+ " ?,?) SELECT SCOPE_IDENTITY() AS bbs_seq ";
		return this.jdbcTemplate.queryForInt(query, new Object[] {
		
			bbs.getBbsType(),
			bbs.getBbsType2(),
			bbs.getProjectSeq(),
			bbs.getBbsTitle(),
			bbs.getAnswerStatus(),
			bbs.getBbsContents(),
			bbs.getSendSms(),
			bbs.getUserId(),
			bbs.getGrade(),
			bbs.getCommentCount(),
			bbs.getBbsStartday(),
			bbs.getBbsEndday()
		});
	}

	public void deleteBbs(int bbs_seq) {
		String query = "DELETE FROM T_NF_BBS WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq });
	}

	public void updateBbs(Bbs bbs) { 
		String query = "" + 
				"UPDATE T_NF_BBS SET " +
				
				"	bbs_type = ?, " +
				"	bbs_type2 = ?, " +
				"	project_seq = ?, " +
				"	bbs_title = ?, " +
				"	answer_status = ?, " +
				"	bbs_contents = ?, " +
				"	send_sms = ?, " +
				"	user_id = ?, " +
				"	comment_count = ?, " +
				"	grade = ? " +
				"WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			bbs.getBbsType(),
			bbs.getBbsType2(),
			bbs.getProjectSeq(),
			bbs.getBbsTitle(),
			bbs.getAnswerStatus(),
			bbs.getBbsContents(),
			bbs.getSendSms(),
			bbs.getUserId(),
			bbs.getCommentCount(),
			bbs.getGrade(),
			bbs.getBbsSeq()
		});
	}
	public void updateStatusBbs(int bbsSeq,int status,int count) { 
		String query = "" + 
				"UPDATE T_NF_BBS SET " +
				
			
				"	answer_status = ?, " +
				" comment_count = ? "+
				"WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			status,count,bbsSeq
		});
	}
	
	public void updateDateBbs(String type,String date,int bbsSeq) { 
		String query = "" + 
				"UPDATE T_NF_BBS SET " +
				
			
				"	"+type+" = ? " +
		
				"WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
				date,bbsSeq
		});
	}
	public Bbs getBbs(int bbs_seq) {
		String query = "" + 
				"SELECT * " +
				"FROM V_NF_BBS " +
				"WHERE bbs_seq = ? ";
		try{
			return (Bbs)this.jdbcTemplate.queryForObject(query, new Object[] { bbs_seq }, this.VbbsMapper);
		}catch(Exception e){
			return new Bbs();
		}
	}

	public List<Bbs> getBbsList(String keyword,String sort,String colName,int projectSeq,String bbsType,String bbsType2,int page, int itemCountPerPage) {
		String con="where 1=1 ";
		if(projectSeq!=0){
			con+=" and project_seq = "+projectSeq;
		}
		if(!bbsType.equals("0")){
			con+=" and bbs_type = '"+bbsType+"'";
		}
		if(!(bbsType2.equals("0") || bbsType2.equals(""))){
			con+=" and bbs_type2 != '"+bbsType2+"'";
		}
		if(!keyword.equals("")){
			con+=" and (bbs_title like '%"+keyword+"%' or bbs_contents like '%"+keyword+"%' or user_name like '%"+keyword+"%')";
		}
		String order=" bbs_reg_date ";
		String order2=" desc ";
		
		if(!colName.equals("")){
			order=" "+colName;
		}
		if(!sort.equals("")){
			order2=" "+sort;
		}
		
		
		String query = "" 
				+ "	SELECT * FROM ( "
				+ "		SELECT "
				+ "			ROW_NUMBER() OVER(order by "+order+" "+order2+" ) as row_seq, "
				+ "			A.* "
				+"  from v_nf_bbs as A "+con
				+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
		return (List<Bbs>)this.jdbcTemplate.query(query, this.VbbsMapper);
	}
	
	public int getBbsCount(String keyword,int projectSeq,String bbsType,String bbsType2,int status) {
		String con="where 1=1 ";
		if(projectSeq!=0){
			con+=" and project_seq = "+projectSeq;
		}
		if(!bbsType.equals("0")){
			con+=" and bbs_type = '"+bbsType+"'";
		}
		if(!(bbsType2.equals("0") || bbsType2.equals(""))){
			con+=" and bbs_type2 = '"+bbsType2+"'";
		}
		if(!keyword.equals("")){
			con+=" and (bbs_title like '%"+keyword+"%' or bbs_contents like '%"+keyword+"%')";
		}
		if(status==1){//완료카운트
			con+=" and answer_status = 3";
		}
		if(status==2){//진행중카운트
			con+=" and answer_status != 3";
		}
		String query = "" 
				+ "	SELECT count(*) FROM  "
				+"   v_nf_bbs "+con;
		return this.jdbcTemplate.queryForInt(query);
	}
	
}
