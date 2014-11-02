package com.highcharts.Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.highcharts.MongoDBUtils.MongoDBManager;

/**
 * Servlet implementation class CountyDetail
 */
@WebServlet("/countydetail")
public class CountyDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CountyDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		switch (op) {
		case "countydetail":
			String countyID = request.getParameter("countyID");
			getCountyDetail(response,countyID);
			break;
			// diğer operasyonlar buraya tanımlanabilir
		default:
			// ...
			break;
		}
	}

	private void getCountyDetail(HttpServletResponse response, String countyID) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		MongoDBManager mongoDB = new MongoDBManager();
		list = mongoDB.getCountyResult(countyID);
		write(response,list);
	}

	private void write(HttpServletResponse response, List<Map<String, Object>> list) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(new Gson().toJson(list));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
