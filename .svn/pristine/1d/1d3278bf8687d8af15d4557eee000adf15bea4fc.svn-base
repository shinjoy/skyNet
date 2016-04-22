package kr.nomad.mars.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.Company;

public class CompanyDao {

	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper companyMapper = new RowMapper() {
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			Company company = new Company();
			company.setCompanySeq(rs.getInt("company_seq"));
			company.setCompanyName(rs.getString("company_name"));
			company.setCompanyComment(rs.getString("company_comment"));
			return company;
		}
	};

	public void addCompany(final Company company) {
		String query = "" +
				"INSERT INTO T_NF_COMPANY " +
				"	( company_name, company_comment) " +
				"VALUES " +
				"	( ?, ?) ";
		this.jdbcTemplate.update(query, new Object[] {
		
			company.getCompanyName(),
			company.getCompanyComment()
		});
	}

	public void deleteCompany(int company_seq) {
		String query = "DELETE FROM T_NF_COMPANY WHERE company_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { company_seq });
	}

	public void updateCompany(Company company) { 
		String query = "" + 
				"UPDATE T_NF_COMPANY SET " +
				
				"	company_name = ?, " +
				"	company_comment = ? " +
				"WHERE company_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			company.getCompanyName(),
			company.getCompanyComment(),
			company.getCompanySeq()
		});
	}

	public Company getCompany(int company_seq) {
		String query = "" + 
				"SELECT * " +
				"FROM T_NF_COMPANY " +
				"WHERE company_seq = ? ";
		try{
			return (Company)this.jdbcTemplate.queryForObject(query, new Object[] { company_seq }, this.companyMapper);
		}catch(Exception e){
			return new Company();
		}
	}

	public List<Company> getCompanyList(String keyword,String colName,String sort,int page, int itemCountPerPage) {
		String order =colName;
		String order2=sort;
		String con=" where 1=1 ";
		if(!keyword.equals("")){
			con+=" and company_name like '%"+keyword+"%'";
		}
		
		String query = "" 
				+ "	SELECT * FROM ( "
				+ "		SELECT "
				+ "			ROW_NUMBER() OVER(order by "+order+" "+order2+" ) as row_seq, "
				+ "			A.* "
				+"  from T_NF_COMPANY as A "+con
				+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
				
				
		return (List<Company>)this.jdbcTemplate.query(query, this.companyMapper);
	}
	
	public List<Company> getCompanyList() {
	
		String query = "" 
				+ "	SELECT * FROM "
			
				+"   T_NF_COMPANY  ";
			
				
		return (List<Company>)this.jdbcTemplate.query(query, this.companyMapper);
	}
	public int getCompanyCount(String keyword) {
		String con=" where 1=1 ";
		if(!keyword.equals("")){
			con+=" and company_name like '%"+keyword+"%'";
		}
		
		
		String query = "" 
				+ "	SELECT count(*) FROM  "
			
				+"   T_NF_COMPANY "+con;
				
				
		return this.jdbcTemplate.queryForInt(query);
	}
}
