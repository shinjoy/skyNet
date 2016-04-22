package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import kr.nomad.mars.dto.AdminFiles;

public class AdminFilesDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper AdminfilesMapper = new RowMapper() {
		public AdminFiles mapRow(ResultSet rs, int rowNum) throws SQLException {
			AdminFiles AdminFiles = new AdminFiles();
			AdminFiles.setFileSeq(rs.getInt("file_seq"));
			AdminFiles.setBbsSeq(rs.getInt("bbs_seq"));
			AdminFiles.setFileName(rs.getString("file_name"));
			AdminFiles.setFileExt(rs.getString("file_ext"));
			return AdminFiles;
		}
	};

	public void addBbsFiles(final AdminFiles AdminFiles) {
		String query = "" +
				"INSERT INTO T_NF_Admin_FILES " +
				"	( bbs_seq, file_name,file_ext) " +
				"VALUES " +
				"	( ?, ?,?) ";
		this.jdbcTemplate.update(query, new Object[] {
		
				AdminFiles.getBbsSeq(),
				AdminFiles.getFileName(),
				AdminFiles.getFileExt()
		});
	}

	public void deleteBbsFiles(int bbs_seq) {
		String query = "DELETE FROM T_NF_Admin_FILES WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq });
	}

	public void deleteBbsFiles(int bbs_seq, String file_name) {
		String query = "DELETE FROM T_NF_Admin_FILES WHERE bbs_seq = ? AND file_name = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq, file_name });
	}

	public void deleteBbsFilesfileSeq(int file_seq) {
		String query = "DELETE FROM T_NF_Admin_FILES WHERE file_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { file_seq });
	}

	
	public void updateBbsFiles(AdminFiles AdminFiles) { 
		String query = "" + 
				"UPDATE T_NF_Admin_FILES SET " +
				"	file_seq = ?, " +
				"	bbs_seq = ?, " +
				"	file_name = ?, " +
				"	file_ext = ? " +
				"WHERE file_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
				AdminFiles.getFileSeq(),
				AdminFiles.getBbsSeq(),
				AdminFiles.getFileName(),
				AdminFiles.getFileExt()
		});
	}

	public AdminFiles getBbsFiles(String file_seq) {
		String query = "" + 
				"SELECT file_seq, bbs_seq, file_name " +
				"FROM T_NF_Admin_FILES " +
				"WHERE file_seq = ? ";
		return (AdminFiles)this.jdbcTemplate.queryForObject(query, new Object[] { file_seq }, this.AdminfilesMapper);
	}

	public List<AdminFiles> getBbsFilesList(int bbsSeq) {
		String query = "" +
			" select * from T_NF_Admin_FILES where bbs_seq = ?";
		try{
			return (List<AdminFiles>)this.jdbcTemplate.query(query,new Object[] { bbsSeq }, this.AdminfilesMapper);
		}catch(Exception e){
			return new ArrayList();
		}
	}

}
