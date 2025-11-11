package ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dao.StatsDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/totalOrderAndPrice")
public class TotalOrderAndPriceRestController extends HttpServlet {
	private StatsDao statsDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fromYM = request.getParameter("fromYM");
		String toYM = request.getParameter("toYM");

		response.setContentType("application/json;charset=UTF-8");
		statsDao = new StatsDao();
		List<Map<String, Object>> orderMap = statsDao.selectOrderTotalCntByYM(fromYM, toYM);
		List<Map<String, Object>> priceMap = statsDao.selectOrderTotalPriceByYM(fromYM, toYM);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("orderMap", orderMap);
		resultMap.put("priceMap", priceMap);
		Gson gson = new Gson();
		String jsonResult = gson.toJson(resultMap);
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
		out.flush();
	}
}
