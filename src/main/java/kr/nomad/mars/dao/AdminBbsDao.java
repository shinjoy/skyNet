package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import kr.nomad.mars.dto.AdminBbs;




public class AdminBbsDao {

	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper bbsMapper = new RowMapper() {
		public AdminBbs mapRow(ResultSet rs, int rowNum) throws SQLException {
			AdminBbs AdminBbs = new AdminBbs();
			AdminBbs.setBbsSeq(rs.getInt("bbs_seq"));
			AdminBbs.setBbsType(rs.getString("bbs_type"));
			AdminBbs.setBbsType2(rs.getString("bbs_type2"));
			AdminBbs.setProjectSeq(rs.getInt("project_seq"));
			AdminBbs.setBbsTitle(rs.getString("bbs_title"));
			AdminBbs.setAnswerStatus(rs.getInt("answer_status"));
			AdminBbs.setBbsContents(rs.getString("bbs_contents"));
			AdminBbs.setSendSms(rs.getInt("send_sms"));
			AdminBbs.setUserId(rs.getString("user_id"));
			AdminBbs.setBbsRegDate(rs.getString("bbs_reg_date"));
			AdminBbs.setGrade(rs.getString("grade"));
			AdminBbs.setCommentCount(rs.getInt("comment_count"));
			return AdminBbs;
		}
	};
	
	private RowMapper VbbsMapper = new RowMapper() {
		public AdminBbs mapRow(ResultSet rs, int rowNum) throws SQLException {
			AdminBbs AdminBbs = new AdminBbs();
			AdminBbs.setBbsSeq(rs.getInt("bbs_seq"));
			AdminBbs.setBbsType(rs.getString("bbs_type"));
			AdminBbs.setBbsType2(rs.getString("bbs_type2"));
			AdminBbs.setProjectSeq(rs.getInt("project_seq"));
			AdminBbs.setBbsTitle(rs.getString("bbs_title"));
			AdminBbs.setAnswerStatus(rs.getInt("answer_status"));
			AdminBbs.setBbsContents(rs.getString("bbs_contents"));
			AdminBbs.setSendSms(rs.getInt("send_sms"));
			AdminBbs.setUserId(rs.getString("user_id"));
			AdminBbs.setBbsRegDate(rs.getString("bbs_reg_date"));
			AdminBbs.setUserName(rs.getString("user_name"));
			AdminBbs.setCompanyName(rs.getString("company_name"));
			AdminBbs.setGrade(rs.getString("grade"));
			AdminBbs.setCommentCount(rs.getInt("comment_count"));
			AdminBbs.setUserPhone(rs.getString("user_phone"));
			return AdminBbs;
		}
	};

	public int addBbs(final AdminBbs AdminBbs) {
		String query = "" +
				"INSERT INTO T_NF_Admin_BBS " +
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
		
				AdminBbs.getBbsType(),
				AdminBbs.getBbsType2(),
				AdminBbs.getProjectSeq(),
				AdminBbs.getBbsTitle(),
				AdminBbs.getAnswerStatus(),
				AdminBbs.getBbsContents(),
				AdminBbs.getSendSms(),
				AdminBbs.getUserId(),
				AdminBbs.getGrade(),
				AdminBbs.getCommentCount()
		});
	}

	public void deleteBbs(int bbs_seq) {
		String query = "DELETE FROM T_NF_Admin_BBS WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq });
	}

	public void updateBbs(AdminBbs AdminBbs) { 
		String query = "" + 
				"UPDATE T_NF_Admin_BBS SET " +
				
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
		
				AdminBbs.getBbsType(),
				AdminBbs.getBbsType2(),
				AdminBbs.getProjectSeq(),
				AdminBbs.getBbsTitle(),
				AdminBbs.getAnswerStatus(),
				AdminBbs.getBbsContents(),
				AdminBbs.getSendSms(),
				AdminBbs.getUserId(),
				AdminBbs.getCommentCount(),
				AdminBbs.getGrade(),
				AdminBbs.getBbsSeq()
		});
	}
	public void updateStatusBbs(int bbsSeq,int count) { 
		String query = "" + 
				"UPDATE T_NF_Admin_BBS SET " +
				
			
			
				" comment_count = ? "+
				"WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			count,bbsSeq
		});
	}
	public AdminBbs getBbs(int bbs_seq) {
		String query = "" + 
				"SELECT * " +
				"FROM V_NF_Admin_BBS " +
				"WHERE bbs_seq = ? ";
		try{
			return (AdminBbs)this.jdbcTemplate.queryForObject(query, new Object[] { bbs_seq }, this.VbbsMapper);
		}catch(Exception e){
			return new AdminBbs();
		}
	}

	public List<AdminBbs> getBbsList(String keyword,String sort,String colName,int projectSeq,/*String bbsType,String bbsType2,*/int page, int itemCountPerPage) {
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
				+"  from V_NF_Admin_BBS as A "+con
				+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
		return (List<AdminBbs>)this.jdbcTemplate.query(query, this.VbbsMapper);
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
				+"   V_NF_Admin_BBS "+con;
		return this.jdbcTemplate.queryForInt(query);
	}
	
}
