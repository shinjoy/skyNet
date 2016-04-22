package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.BbsFiles;

public class BbsFilesDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper bbsfilesMapper = new RowMapper() {
		public BbsFiles mapRow(ResultSet rs, int rowNum) throws SQLException {
			BbsFiles bbsfiles = new BbsFiles();
			bbsfiles.setFileSeq(rs.getInt("file_seq"));
			bbsfiles.setBbsSeq(rs.getInt("bbs_seq"));
			bbsfiles.setFileName(rs.getString("file_name"));
			bbsfiles.setFileExt(rs.getString("file_ext"));
			return bbsfiles;
		}
	};

	public void addBbsFiles(final BbsFiles bbsfiles) {
		String query = "" +
				"INSERT INTO T_NF_BBS_FILES " +
				"	( bbs_seq, file_name,file_ext) " +
				"VALUES " +
				"	( ?, ?,?) ";
		this.jdbcTemplate.update(query, new Object[] {
		
			bbsfiles.getBbsSeq(),
			bbsfiles.getFileName(),
			bbsfiles.getFileExt()
		});
	}

	public void deleteBbsFiles(int bbs_seq) {
		String query = "DELETE FROM T_NF_BBS_FILES WHERE bbs_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq });
	}

	public void deleteBbsFiles(int bbs_seq, String file_name) {
		String query = "DELETE FROM T_NF_BBS_FILES WHERE bbs_seq = ? AND file_name = ? ";
		this.jdbcTemplate.update(query, new Object[] { bbs_seq, file_name });
	}

	public void deleteBbsFilesfileSeq(int file_seq) {
		String query = "DELETE FROM T_NF_BBS_FILES WHERE file_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { file_seq });
	}

	
	public void updateBbsFiles(BbsFiles bbsfiles) { 
		String query = "" + 
				"UPDATE T_NF_BBS_FILES SET " +
				"	file_seq = ?, " +
				"	bbs_seq = ?, " +
				"	file_name = ?, " +
				"	file_ext = ? " +
				"WHERE file_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			bbsfiles.getFileSeq(),
			bbsfiles.getBbsSeq(),
			bbsfiles.getFileName(),
			bbsfiles.getFileExt()
		});
	}

	public BbsFiles getBbsFiles(String file_seq) {
		String query = "" + 
				"SELECT file_seq, bbs_seq, file_name " +
				"FROM T_NF_BBS_FILES " +
				"WHERE file_seq = ? ";
		return (BbsFiles)this.jdbcTemplate.queryForObject(query, new Object[] { file_seq }, this.bbsfilesMapper);
	}

	public List<BbsFiles> getBbsFilesList(int bbsSeq) {
		String query = "" +
			" select * from t_nf_bbs_files where bbs_seq = ?";
		try{
			return (List<BbsFiles>)this.jdbcTemplate.query(query,new Object[] { bbsSeq }, this.bbsfilesMapper);
		}catch(Exception e){
			return new ArrayList();
		}
	}

}
