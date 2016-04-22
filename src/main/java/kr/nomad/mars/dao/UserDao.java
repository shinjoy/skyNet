package kr.nomad.mars.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import kr.nomad.mars.dto.User;

public class UserDao {
	
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper userMapper = new RowMapper() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserPw(rs.getString("user_pw"));
			user.setUserName(rs.getString("user_name"));
			user.setUserGroup(rs.getString("user_group"));
			user.setUserPosition(rs.getString("user_position"));
			user.setUserPhone(rs.getString("user_phone"));
			user.setUserLevel(rs.getInt("user_level"));
			user.setCompanySeq(rs.getInt("company_seq"));
			user.setUserEmail(rs.getString("user_email"));
			user.setUserEct(rs.getString("user_ect"));
			user.setUserType(rs.getInt("user_type"));
			return user;
		}
	};
	private RowMapper VuserMapper = new RowMapper() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserPw(rs.getString("user_pw"));
			user.setUserName(rs.getString("user_name"));
			user.setUserGroup(rs.getString("user_group"));
			user.setUserPosition(rs.getString("user_position"));
			user.setUserPhone(rs.getString("user_phone"));
			user.setUserLevel(rs.getInt("user_level"));
			user.setCompanySeq(rs.getInt("company_seq"));
			user.setUserEmail(rs.getString("user_email"));
			user.setUserEct(rs.getString("user_ect"));
			user.setUserType(rs.getInt("user_type"));
			user.setCompanyName(rs.getString("company_name"));
			user.setCompanyComment(rs.getString("company_comment"));
			return user;
		}
	};
	public void addUser(final User user) {
		String query = "" +
				"INSERT INTO t_nf_user " +
				"	(user_id, user_pw, user_name,"
				+ " user_group, user_position, user_phone,"
				+ " user_level, company_seq, user_email,"
				+ " user_ect, user_type) " +
				"VALUES " +
				"	(?, ?, ?,"
				+ " ?, ?, ?,"
				+ " ?, ?, ?,"
				+ " ?, ?) ";
		this.jdbcTemplate.update(query, new Object[] {
			user.getUserId(),
			user.getUserPw(),
			user.getUserName(),
			user.getUserGroup(),
			user.getUserPosition(),
			user.getUserPhone(),
			user.getUserLevel(),
			user.getCompanySeq(),
			user.getUserEmail(),
			user.getUserEct(),
			user.getUserType()
		});
	}

	public void deleteUser(String user_id) {
		String query = "DELETE FROM t_nf_user WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] { user_id });
	}
	public void updateUserPw(User user) { 
		String query = "" + 
				"UPDATE t_nf_user SET " +
				" user_pw = ? "+
				"WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			user.getUserPw(),
			user.getUserId()
		});
	}
	
	public void updateUser(User user) { 
		String query = "" + 
				"UPDATE t_nf_user SET " +
				"	user_name = ?, " +
				"	user_group = ?, " +
				"	user_position = ?, " +
				"	user_phone = ?, " +
				"	user_level = ?, " +
				"	company_seq = ?, " +
				"	user_email = ?, " +
				"	user_ect = ?, " +
				"	user_type = ? " +
				"WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
		
			user.getUserName(),
			user.getUserGroup(),
			user.getUserPosition(),
			user.getUserPhone(),
			user.getUserLevel(),
			user.getCompanySeq(),
			user.getUserEmail(),
			user.getUserEct(),
			user.getUserType(),
			user.getUserId()
		});
	}

	//유저 아이디 체크
	public int correctId(String userId) {
		String query = "" + 
				"SELECT count(*)  " +
				"FROM t_nf_user " +
				"WHERE user_id = ? ";
		return this.jdbcTemplate.queryForInt(query, new Object[] { userId});
	}
	//유저 체크
	public int getUserChk(String userId,String userPw) {
		String query = "" + 
				"SELECT count(*)  " +
				"FROM t_nf_user " +
				"WHERE user_id = ? and user_pw = ?";
		return this.jdbcTemplate.queryForInt(query, new Object[] { userId,userPw });
	}
	
	// 유저 휴대폰 체크
	public int correctIdPhone(String userId,String phone) {
		String query = "" 
				+ "SELECT count(*)  " 
				+ "FROM t_nf_user " 
				+ "WHERE user_id = ? and user_phone = ?  ";
		return this.jdbcTemplate.queryForInt(query, new Object[] { userId,phone });
	}
	
	// 유저 폰체크
	public User Phone(String phone) {
		String query = "" 
				+ "SELECT *  " 
				+ "FROM t_nf_user " 
				+ "WHERE user_phone = ?  ";
		try{
			return (User)this.jdbcTemplate.queryForObject(query, new Object[] { phone },this.userMapper);
		}catch(Exception e){
			return null;
		}
	}
	public User getUser(String user_id) {
		String query = "" + 
				"SELECT * " +
				"FROM v_nf_user " +
				"WHERE user_id = ? ";
		try{
			return (User)this.jdbcTemplate.queryForObject(query, new Object[] { user_id }, this.userMapper);
		}catch(Exception e){
			return null;
		}
	}
	
	public User getUserCompany(String user_id) {
		String query = "" + 
				"SELECT * " +
				"FROM v_nf_user " +
				"WHERE user_id = ? ";
		return (User)this.jdbcTemplate.queryForObject(query, new Object[] { user_id }, this.VuserMapper);
	}
	public List<User> getUserListNoPaging(int companySeq) {
		
		String query = "" 
				+ "	SELECT * FROM  "
			
				+"   v_nf_user  where company_seq = "+companySeq;
		return (List<User>)this.jdbcTemplate.query(query, this.VuserMapper);
	}

	public List<User> getUserList(String colName,String sort,int companySeq,int page, int itemCountPerPage) {
		String order=colName;
		String order2=sort;
		String query = "" 
				+ "	SELECT * FROM ( "
				+ "		SELECT "
				+ "			ROW_NUMBER() OVER(order by "+order+" "+order2+" ) as row_seq, "
				+ "			A.* "
				+"  from v_nf_user as A where company_seq = "+companySeq
				+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
		return (List<User>)this.jdbcTemplate.query(query, this.VuserMapper);
	}
	public int getUserCount(int companySeq) {
		String query = "" 
				+ "	SELECT count(*) FROM  "
				+"   v_nf_user where company_seq = "+companySeq;
		return this.jdbcTemplate.queryForInt(query);
	}
	public List<User> getUserAdminList() {
		
	
	
		String query = "" 
				+ "	SELECT *  "
	
				+"  from v_nf_user  where user_type = 1 ";
		return (List<User>)this.jdbcTemplate.query(query, this.VuserMapper);
	}
	
	public List<User> getUserAdminList(String keyword,String sort,String colName,int page, int itemCountPerPage) {
	
		String order= " user_level ";
		String order2=" asc ";
		if(!colName.equals("")){
			order=" "+colName;
		}
		if(!sort.equals("")){
			order2=" "+sort;
		}
		String con="  ";
		if(!keyword.equals("")){
			con+=" and user_name like '%"+keyword+"%'";
		}
		String query = "" 
				+ "	SELECT * FROM ( "
				+ "		SELECT "
				+ "			ROW_NUMBER() OVER(order by "+order+""+order2+") as row_seq, "
				+ "			A.* "
				+"  from v_nf_user as A where user_type = 1 "+con
				+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
		return (List<User>)this.jdbcTemplate.query(query, this.VuserMapper);
	}
	
	public int getUserAdminCount(String keyword) {
		String con=" ";
		if(!keyword.equals("")){
			con+=" and user_name like '%"+keyword+"%'";
		}
	
		String query = "" 
				+ "	SELECT count(*)  FROM  "
				+"   v_nf_user as A where user_type = 1 "+con;
		return this.jdbcTemplate.queryForInt(query, this.VuserMapper);
	}

}
