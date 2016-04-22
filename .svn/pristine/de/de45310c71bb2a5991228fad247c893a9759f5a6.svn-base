package kr.nomad.util.push;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class PushMapper {
	
	public static RowMapper<PushKey> pushMapper = new RowMapper<PushKey>() {
		public PushKey mapRow(ResultSet rs, int rowNum) throws SQLException {
			PushKey pushKey = new PushKey();
			pushKey.setUserId(rs.getString("user_id"));
			pushKey.setPushKey(rs.getString("pushkey"));
			pushKey.setOs(rs.getString("os_type"));
			pushKey.setPushType(rs.getInt("use_pushservice"));			
			return pushKey;
		}
	};

}
