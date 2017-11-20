package action;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.dei.qcs.ejb.InsulinDoseCalculatorEJBRemote;
import edu.dei.qcs.voter.Result;

public class StandardCalculation extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	public InsulinDoseCalculatorEJBRemote ejb;

	public StandardCalculation() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String p1, p2, p3, p4, p5;
		int total_CH, CH_processed, blood_sugar, target_blood_sugar, sensitivity, resultValue;
		Result result = null;

		p1 = request.getParameter("total_CH");
		p2 = request.getParameter("CH_processed");
		p3 = request.getParameter("blood_sugar");
		p4 = request.getParameter("target_blood_sugar");
		p5 = request.getParameter("sensitivity");

		if (p1 !=null && p2 !=null && p3 !=null && p4 !=null && p5 !=null)
		{
			total_CH = Integer.parseInt(p1);
			CH_processed = Integer.parseInt(p2);
			blood_sugar = Integer.parseInt(p3);
			target_blood_sugar = Integer.parseInt(p4);
			sensitivity = Integer.parseInt(p5);

			result = ejb.mealtimeInsulinDose(total_CH, CH_processed, blood_sugar, target_blood_sugar, sensitivity);
			resultValue = result.getResult();

			request.setAttribute("title", "Mealtime insulin dose - Standard Insulin Sensitivity");
			request.setAttribute("result", result);

			if (resultValue >= 0)
			{
				/*Put response*/
				request.setAttribute("value", resultValue);
				request.setAttribute("text", "Your result is:");
			}
			else
			{
				request.setAttribute("text", "It was not possible to calculate the insulin dose <br> Please, try again!");
			}

			RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
			rd.forward(request, response);

		} else {
			RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
			rd.forward(request, response);
		}

	}

}