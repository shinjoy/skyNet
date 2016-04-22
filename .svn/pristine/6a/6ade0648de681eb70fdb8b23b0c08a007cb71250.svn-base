package kr.nomad.mars.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.nomad.mars.dto.Module;




public class ModuleDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper moduleMapper = new RowMapper() {
		public Module mapRow(ResultSet rs, int rowNum) throws SQLException {
			Module module = new Module();
			module.setModuleSeq(rs.getInt("module_seq"));
			module.setModule(rs.getString("module"));
			module.setProcess(rs.getString("process"));
			module.setTodo(rs.getString("todo"));
			return module;
		}
	};

	public void addModule(final Module module) {
		String query = "" +
				"INSERT INTO T_NF_MODULE " +
				"	( module, process, todo) " +
				"VALUES " +
				"	( ?, ?, ?) ";
		this.jdbcTemplate.update(query, new Object[] {
		
			module.getModule(),
			module.getProcess(),
			module.getTodo()
		});
	}

	public void deleteModule(int module_seq) {
		String query = "DELETE FROM T_NF_MODULE WHERE module_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] { module_seq });
	}

	public void updateModule(Module module) { 
		String query = "" + 
				"UPDATE T_NF_MODULE SET " +
				
				"	module = ?, " +
				"	process = ?, " +
				"	todo = ? " +
				"WHERE module_seq = ? ";
		this.jdbcTemplate.update(query, new Object[] {
		
			module.getModule(),
			module.getProcess(),
			module.getTodo(),
			module.getModuleSeq()
		});
	}

	public Module getModule(String module_seq) {
		String query = "" + 
				"SELECT module_seq, module, process, todo " +
				"FROM T_NF_MODULE " +
				"WHERE module_seq = ? ";
		return (Module)this.jdbcTemplate.queryForObject(query, new Object[] { module_seq }, this.moduleMapper);
	}

	public List<Module> getModuleList(String module,int page, int itemCountPerPage) {
		String con=" where 1=1 ";
		if(!module.equals("")){
			con+=" module = '"+module+"'";
		}
		String query = "" 
				+ "	SELECT * FROM ( "
				+ "		SELECT "
				+ "			ROW_NUMBER() OVER(order by module asc) as row_seq, "
				+ "			A.* "
				+"  from T_NF_MODULE as A "+con
				+ "	) AS a WHERE row_seq BETWEEN (("+page+" - 1) * "+itemCountPerPage+") +1 AND "+page+" * "+itemCountPerPage+" ";
		return (List<Module>)this.jdbcTemplate.query(query, this.moduleMapper);
	}

	public int getModuleCount(String module) {
		String con=" where 1=1 ";
		if(!module.equals("")){
			con+=" module = '"+module+"'";
		}
		String query = "" 
				+ "	SELECT count(*) FROM  "
				
				+"   T_NF_MODULE  "+con;
		return this.jdbcTemplate.queryForInt(query);
	}
}
