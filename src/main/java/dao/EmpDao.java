package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.Emp;

public class EmpDao {
	// 로그인
	public Emp selectEmpByLogin(Emp e) throws SQLException {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT 
					EMP_CODE as empCode,
					EMP_ID as empId,
					EMP_PW as empPw,
					EMP_NAME as empName,
					ACTIVE,
					CREATEDATE
				FROM emp
				WHERE emp_id = ? AND emp_pw = ? AND active > 0
		""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);

		psmt.setString(1, e.getEmpId());
		psmt.setString(2, e.getEmpPw());
		
		rs = psmt.executeQuery();
		
		Emp emp = null;
		
		if (rs.next()) {	
			emp = new Emp();
			emp.setEmpCode(rs.getInt("empCode"));
			emp.setEmpId(rs.getString("empId"));
			emp.setEmpPw(rs.getString("empPw"));
			emp.setEmpName(rs.getString("empName"));
			emp.setActive(rs.getString("ACTIVE"));
			emp.setCreatedate(rs.getString("CREATEDATE"));
		}
		
		rs.close();
	    psmt.close();
	    conn.close();
		
		return emp;
	}
}
