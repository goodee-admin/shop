package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dto.Customer;
import dto.Outid;

public class CustomerDao {
	// 직원에 의해 강제탈퇴
	public void deleteCustomerByEmp(Outid outid) {
		Connection conn = null;
		PreparedStatement pstmtCustomer = null;
		PreparedStatement pstmtOutid = null;
		String sqlCustomer = """
			delete from customer where customer_id = ?	
		""";
		String sqlOutid = """
			insert into outid(id, memo, createdate)
			values(?, ?, ?)	
		""";
		
		// JDBC Connection의 기본 Commit설정값 auto commit = true : false 변경 후 transaction 적용
		try {
			conn = DBConnection.getConn();
			conn.setAutoCommit(false); // 개발자가 commit / rollback 직접 구현이 필요
			pstmtCustomer = conn.prepareStatement(sqlCustomer);
			
			// param 설정 ? : outid.getId()
			
			int row = pstmtCustomer.executeUpdate(); // customer 삭제
			if(row == 1) {
				pstmtOutid = conn.prepareStatement(sqlOutid);
				
				// param 설정 : ? outid.getId() ? outid.getMemo() ? sysdate
				
				pstmtOutid.executeUpdate(); // outid 입력
			} else {
				throw new SQLException();
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pstmtCustomer.close();
				pstmtOutid.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
	
	// 직원 로그인시 전체 고객 리스트 확인
	public List<Customer> selectCustomerList(int beginRow, int rowPerPage) throws SQLException {
		return null;
	}
	
	
	// ID 사용가능 여부
	// return : null 사용가능 ID, 아니면 사용불가 ID
	public String selectCustomerIdCheck(String id) throws SQLException {
		String sql = """
			select t.id
			from
				(select customer_id id from customer
				union all
				select emp_id id from emp
				union all
				select id from outid) t
				where t.id = ?	
		""";
		return null;
	}
	
	// 로그인
	public Customer selectCustomerByLogin(Customer c) throws SQLException {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		String sql ="""
				SELECT
					CUSTOMER_CODE AS customerCode,
					CUSTOMER_ID AS customerId,
					CUSTOMER_PW AS customerPw,
					CUSTOMER_NAME AS customerName,
					customer_phone AS customerPhone,
					POINT,
					CREATEDATE
				FROM customer
				WHERE CUSTOMER_ID = ? AND CUSTOMER_PW = ?
		""";
		
		conn = DBConnection.getConn();
		psmt = conn.prepareStatement(sql);

		psmt.setString(1, c.getCustomerId());
		psmt.setString(2, c.getCustomerPw());
		
		rs = psmt.executeQuery();
		
		Customer customer = null;
		
		if (rs.next()) {
			
			customer = new Customer();
			customer.setCustomerCode(rs.getInt("customerCode"));
			customer.setCustomerId(rs.getString("customerId"));
			customer.setCustomerPw(rs.getString("customerPw"));
			customer.setCustomerName(rs.getString("customerName"));
			customer.setCustomerPhone(rs.getString("customerPhone"));
			customer.setPoint(rs.getInt("POINT"));
			customer.setCreatedate(rs.getString("CREATEDATE"));
		}
		
		rs.close();
	    psmt.close();
	    conn.close();
		
		return customer;
	}
}
