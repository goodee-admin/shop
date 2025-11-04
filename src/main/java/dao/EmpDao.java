package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Emp;

public class EmpDao {
	// 사원 목록
	public List<Emp> selectEmpListByPage(int beginRow, int rowPerPage) throws SQLException {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = """
					select emp_code empCode, emp_id empId, emp_name empName, active, createdate
					from emp
					order by emp_code
					offset ? rows fetch next ? rows only
						
		""";
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);
		psmt.setInt(1, beginRow);
		psmt.setInt(2, rowPerPage);	
		rs = psmt.executeQuery();
		List<Emp> list = new ArrayList<>();
		while(rs.next()) {
			Emp e = new Emp();
			e.setEmpCode(rs.getInt("empCode"));
			e.setEmpId(rs.getString("empId"));
			e.setEmpName(rs.getString("empName"));
			e.setActive(rs.getInt("active"));
			e.setCreatedate(rs.getString("createdate"));
			list.add(e);
		}	
		return list;
	}
	
	
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
			emp.setActive(rs.getInt("active"));
			emp.setCreatedate(rs.getString("createdate"));
		}
		
		rs.close();
	    psmt.close();
	    conn.close();
		
		return emp;
	}
}
