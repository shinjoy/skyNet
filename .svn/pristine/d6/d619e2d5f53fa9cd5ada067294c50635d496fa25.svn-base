package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import kr.nomad.mars.dto.WeekBbs;
import kr.nomad.util.T;




public class WeekBbsDao {

	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper bbsMapper = new RowMapper() {
		public WeekBbs mapRow(ResultSet rs, int rowNum) throws SQLException {
			WeekBbs weekBbs = new WeekBbs();
			weekBbs.setBbsSeq(rs.getInt("bbs_seq"));
			weekBbs.setBbsType(rs.getString("bbs_type"));
			weekBbs.setBbsType2(rs.getString("bbs_type2"));
			weekBbs.setProjectSeq(rs.getInt("project_seq"));
			weekBbs.setBbsTitle(rs.getString("bbs_title"));
			weekBbs.setAnswerStatus(rs.getInt("answer_status"));
			weekBbs.setBbsContents(rs.getString("bbs_contents"));
			weekBbs.setSendSms(rs.getInt("send_sms"));
			weekBbs.setUserId(rs.getString("user_id"));
			weekBbs.setBbsRegDate(rs.getString("bbs_reg_date"));
			weekBbs.setGrade(rs.getString("grade"));
			weekBbs.setCommentCount(rs.getInt("comment_count"));
			return weekBbs;
		}
	};
	
	private RowMapper VbbsMapper = new RowMapper() {
		public WeekBbs mapRow(ResultSet rs, int rowNum) throws SQLException {
			WeekBbs weekBbs = new WeekBbs();
			weekBbs.setBbsSeq(rs.getInt("bbs_seq"));
			weekBbs.setBbsType(rs.getString("bbs_type"));
			weekBbs.setBbsType2(rs.getString("bbs_type2"));
			weekBbs.setProjectSeq(rs.getInt("project_seq"));
			weekBbs.setBbsTitle(rs.getString("bbs_title"));
			weekBbs.setAnswerStatus(rs.getInt("answer_status"));
			weekBbs.setBbsContents(rs.getString("bbs_contents"));
			weekBbs.setSendSms(rs.getInt("send_sms"));
			weekBbs.setUserId(rs.getString("user_id"));
			weekBbs.setBbsRegDate(rs.getString("bbs_reg_date"));
			weekBbs.setUserName(rs.getString("user_name"));
			weekBbs.setCompanyName(rs.getString("company_name"));
			weekBbs.setGrade(rs.getString("grade"));
			weekBbs.setCommentCount(rs.getInt("comment_count"));
			weekBbs.setUserPhone(rs.getString("user_phone"));
			return weekBbs;
		}
	};

	public int addBbs(final WeekBbs weekBbs) {
		String query = "" +
				"INSERT INTO T_NF_Week_BBS " +
				"	( bbs_type, bbs_type2,"
				+ " project_seq, bbs_title, answer_status,"
				+ " bbs_contents, send_sms, user_id,"
				+ " bbs_reg_date,grade,comment_count) " +
				"VALUES " +
				"	( ?, ?, "
				+ "?, ?, ?,"
				+ " ?, ?, ?,"
				+ " getDate(),?,?) SELECT SCOPE_IDENTITY() AS bbs_seq ";
		return this.jdbcTemplate.queryForInt(query, new Object[] {
		
				weekBbs.getBbsType(),
				weekBbs.getBbsType2(),
				weekBbs.getProjectSeq(),
				weekBbs.getBbsTitle(),
				weekBbs.getAnswerStatus(),
				weekBbs.getBbsContents(),
				weekBbs.getSendSms(),
				weekBbs.getUserId(),
				weekBbs.getGrade(),
				weekBbs.getCommentCount()
		});
	}

	public void deleteBbs(int bbs_seq) {
		String query = "DELETE FROM T_NF_Week_BBS WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq });
	}

	public void updateBbs(WeekBbs weekBbs) { 
		String query = "" + 
				"UPDATE T_NF_Week_BBS SET " +
				
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
		
				weekBbs.getBbsType(),
				weekBbs.getBbsType2(),
				weekBbs.getProjectSeq(),
				weekBbs.getBbsTitle(),
				weekBbs.getAnswerStatus(),
				weekBbs.getBbsContents(),
				weekBbs.getSendSms(),
				weekBbs.getUserId(),
				weekBbs.getCommentCount(),
				weekBbs.getGrade(),
				weekBbs.getBbsSeq()
		});
	}
	public void updateStatusBbs(int bbsSeq,int count) { 
		String query = "" + 
				"UPDATE T_NF_Week_BBS SET " +
				
			
			
				" comment_count = ? "+
				"WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			count,bbsSeq
		});
	}
	public WeekBbs getBbs(int bbs_seq) {
		String query = "" + 
				"SELECT * " +
				"FROM V_NF_WEEK_BBS " +
				"WHERE bbs_seq = ? ";
		try{
			return (WeekBbs)this.jdbcTemplate.queryForObject(query, new Object[] { bbs_seq }, this.VbbsMapper);
		}catch(Exception e){
			return new WeekBbs();
		}
	}

	public List<WeekBbs> getBbsList(String keyword,String sort,String colName,int projectSeq,/*String bbsType,String bbsType2,*/int page, int itemCountPerPage) {
		String con="where 1=1 ";
		if(projectSeq!=0){
			con+=" and project_seq = "+projectSeq;
		}
		/*if(!bbsType.equals("0")){
			con+=" and bbs_type = '"+bbsType+"'";
		}
		if(!(bbsType2.equals("0") || bbsType2.equals(""))){
			con+=" and bbs_type2 != '"+bbsType2+"'";
		}*/
		if(!keyword.equals("")){
			con+=" and (bbs_title like '%"+keyword+"%' or bbs_contents like '%"+keyword+"%')";
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
				+"  from V_NF_WEEK_BBS as A "+con
				+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
		return (List<WeekBbs>)this.jdbcTemplate.query(query, this.VbbsMapper);
	}
	
	public int getBbsCount(String keyword,int projectSeq,/*String bbsType,String bbsType2,*/int status) {
		String con="where 1=1 ";
		if(projectSeq!=0){
			con+=" and project_seq = "+projectSeq;
		}
		/*if(!bbsType.equals("0")){
			con+=" and bbs_type = '"+bbsType+"'";
		}
		if(!(bbsType2.equals("0") || bbsType2.equals(""))){
			con+=" and bbs_type2 = '"+bbsType2+"'";
		}*/
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
				+"   V_NF_WEEK_BBS "+con;
		return this.jdbcTemplate.queryForInt(query);
	}
	
	public int getnewBbsCount(int projectSeq) {
	
		String today=T.getToday();
		String beforeday=T.getDateAdd(today, -2);
		
		String query = "" 
				+ "	SELECT count(*) FROM  "
				+"   V_NF_WEEK_BBS where project_seq = ? and bbs_reg_date between '"+beforeday +" 00:00:00' and '"+today+" 23:59:59' " ;
		return this.jdbcTemplate.queryForInt(query, new Object[] { projectSeq });
	}
}
