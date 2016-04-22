package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import kr.nomad.mars.dto.WeekFiles;

public class WeekFilesDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper weekfilesMapper = new RowMapper() {
		public WeekFiles mapRow(ResultSet rs, int rowNum) throws SQLException {
			WeekFiles weekFiles = new WeekFiles();
			weekFiles.setFileSeq(rs.getInt("file_seq"));
			weekFiles.setBbsSeq(rs.getInt("bbs_seq"));
			weekFiles.setFileName(rs.getString("file_name"));
			weekFiles.setFileExt(rs.getString("file_ext"));
			return weekFiles;
		}
	};

	public void addBbsFiles(final WeekFiles weekFiles) {
		String query = "" +
				"INSERT INTO T_NF_WEEK_FILES " +
				"	( bbs_seq, file_name,file_ext) " +
				"VALUES " +
				"	( ?, ?,?) ";
		this.jdbcTemplate.update(query, new Object[] {
		
				weekFiles.getBbsSeq(),
				weekFiles.getFileName(),
				weekFiles.getFileExt()
		});
	}

	public void deleteBbsFiles(int bbs_seq) {
		String query = "DELETE FROM T_NF_WEEK_FILES WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq });
	}

	public void deleteBbsFiles(int bbs_seq, String file_name) {
		String query = "DELETE FROM T_NF_WEEK_FILES WHERE bbs_seq = ? AND file_name = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq, file_name });
	}

	public void deleteBbsFilesfileSeq(int file_seq) {
		String query = "DELETE FROM T_NF_WEEK_FILES WHERE file_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { file_seq });
	}

	
	public void updateBbsFiles(WeekFiles weekFiles) { 
		String query = "" + 
				"UPDATE T_NF_WEEK_FILES SET " +
				"	file_seq = ?, " +
				"	bbs_seq = ?, " +
				"	file_name = ?, " +
				"	file_ext = ? " +
				"WHERE file_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
				weekFiles.getFileSeq(),
				weekFiles.getBbsSeq(),
				weekFiles.getFileName(),
				weekFiles.getFileExt()
		});
	}

	public WeekFiles getBbsFiles(String file_seq) {
		String query = "" + 
				"SELECT file_seq, bbs_seq, file_name " +
				"FROM T_NF_WEEK_FILES " +
				"WHERE file_seq = ? ";
		return (WeekFiles)this.jdbcTemplate.queryForObject(query, new Object[] { file_seq }, this.weekfilesMapper);
	}

	public List<WeekFiles> getBbsFilesList(int bbsSeq) {
		String query = "" +
			" select * from T_NF_WEEK_FILES where bbs_seq = ?";
		try{
			return (List<WeekFiles>)this.jdbcTemplate.query(query,new Object[] { bbsSeq }, this.weekfilesMapper);
		}catch(Exception e){
			return new ArrayList();
		}
	}

}
