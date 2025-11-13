package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.CartDao;
import dao.GoodsDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/customer/addOrders")
public class AddOrderServlet extends HttpServlet {
	GoodsDao goodsDao;
	CartDao cartDao;
	// addOrders.jsp action
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// insert orders
		// insert point_history : 포인트 사용시 - & +, 포인트 미사용시 +
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// goodsOne action
		String goodsCode = request.getParameter("goodsCode");
		String cartQuantity = request.getParameter("cartQuantity");
		System.out.println("goodsCode: "+goodsCode);
		System.out.println("cartQuantity: "+cartQuantity);
				
		// cartList action
		String[] cartCodeList = request.getParameterValues("cartCodeList");
		System.out.println("cartCodeList: "+cartCodeList);
		
		List<Map<String, Object>> list = new ArrayList<>();
		// Map : 상품정보 + (이미지정보) + 수량
		if(goodsCode != null) { // goodsOne action
			// list.size() -> 하나의 상품
			// goodsCode를 사용하여 조인
			goodsDao = new GoodsDao();
			Map<String, Object> m = goodsDao.selectGoodsOne(Integer.parseInt(goodsCode));
			m.put("cartQuantity", cartQuantity);
			list.add(m);
		} else { // cartList action
			// list.size() -> 하나이상의 상품
			// cartCodeList -> goods정보 -> 조인
			cartDao = new CartDao(); 
			for(String cc : cartCodeList) {
				int cartCode = Integer.parseInt(cc);
				Map<String, Object> m = cartDao.selectCartListByKey(cartCode);
				list.add(m);
				
				// cartDao.deleteCart(cc); 주문목록으로 이동 후 cart에서는 삭제
			}

		}	
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/view/customer/addOrders.jsp").forward(request, response);
	}
	
}