package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.Data;
import kr.nomad.mars.dto.FormData;

public class FormDataDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper formdataMapper = new RowMapper() {
		public FormData mapRow(ResultSet rs, int rowNum) throws SQLException {
			FormData formdata = new FormData();
			formdata.setFormSeq(rs.getInt("form_seq"));
			formdata.setFormType(rs.getString("form_type"));
			formdata.setFormTitle(rs.getString("form_title"));
			formdata.setUserId(rs.getString("user_id"));
			formdata.setFormRegDate(rs.getString("form_reg_date"));
			formdata.setFormFileName(rs.getString("form_file_name"));
			return formdata;
		}
	};
	private RowMapper VformdataMapper = new RowMapper() {
		public FormData mapRow(ResultSet rs, int rowNum) throws SQLException {
			FormData formdata = new FormData();
			formdata.setFormSeq(rs.getInt("form_seq"));
			formdata.setFormType(rs.getString("form_type"));
			formdata.setFormTitle(rs.getString("form_title"));
			formdata.setUserId(rs.getString("user_id"));
			formdata.setFormRegDate(rs.getString("form_reg_date"));
			formdata.setFormFileName(rs.getString("form_file_name"));
			formdata.setUserName(rs.getString("user_name"));
			return formdata;
		}
	};

	public void addFormData(final FormData formdata) {
		String query = "" +
				"INSERT INTO T_NF_FORM_DATA " +
				"	( form_type, form_title, user_id, form_reg_date, form_file_name) " +
				"VALUES " +
				"	( ?, ?, ?, getDate(), ?) ";
		this.jdbcTemplate.update(query, new Object[] {
		
			formdata.getFormType(),
			formdata.getFormTitle(),
			formdata.getUserId(),
	
			formdata.getFormFileName()
		});
	}

	public void deleteFormData(int form_seq) {
		String query = "DELETE FROM T_NF_FORM_DATA WHERE form_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { form_seq });
	}

	public void updateFormData(FormData formdata) { 
		String query = "" + 
				"UPDATE T_NF_FORM_DATA SET " +
				
				"	form_type = ?, " +
				"	form_title = ?, " +
				"	user_id = ?, " +
			
				"	form_file_name = ? " +
				"WHERE form_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			formdata.getFormType(),
			formdata.getFormTitle(),
			formdata.getUserId(),
		
			formdata.getFormFileName(),
			formdata.getFormSeq()
		});
	}

	public FormData getFormData(int form_seq) {
		String query = "" + 
				"SELECT * " +
				"FROM V_NF_FORM_DATA " +
				"WHERE form_seq = ? ";
		return (FormData)this.jdbcTemplate.queryForObject(query, new Object[] { form_seq }, this.VformdataMapper);
	}

	public List<FormData> getFormDataList(String keyword,String formType,int page, int itemCountPerPage) {

		
			String con=" where 1=1 ";
			
			if(!keyword.equals("")){
				con+=" and form_title like '%"+keyword+"%' ";
			}
			if(!formType.equals("")){
				con+=" and form_type = '"+formType+"'";
			}
			
			String query = "" 
					+ "	SELECT * FROM ( "
					+ "		SELECT "
					+ "			ROW_NUMBER() OVER(order by form_reg_date desc) as row_seq, "
					+ "			A.* "
					+"  from v_nf_form_data as A"+con
					+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
				
	
	
		return (List<FormData>)this.jdbcTemplate.query(query, this.VformdataMapper);
	}
	
	public int getFormDataCount(String keyword,String formType) {
		
		
		String con=" where 1=1 ";
		
		if(!keyword.equals("")){
			con+=" and form_title like '%"+keyword+"%' ";
		}
		if(!formType.equals("")){
			con+=" and form_type = '"+formType+"'";
		}
		
		String query = "" 
				+ "	SELECT count(*) FROM  "
				+"   v_nf_form_data "+con;
			
		return this.jdbcTemplate.queryForInt(query);
	}

}
