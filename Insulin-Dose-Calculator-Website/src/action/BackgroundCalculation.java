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

public class BackgroundCalculation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	public InsulinDoseCalculatorEJBRemote ejb;

	public BackgroundCalculation() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int weight;
		int resultValue;
		Result result = null;

		String parameter = request.getParameter("Weight");

		if (parameter !=null)
		{
			weight = Integer.parseInt(request.getParameter("Weight"));
			//System.out.println("Weight" + weight);

			result = ejb.backgroundInsulinDose(weight);
			resultValue = result.getResult();

			/*Put response*/
			request.setAttribute("title", "Background Insulin Dose");
			request.setAttribute("result", result);

			if (resultValue != -1)
			{
				request.setAttribute("text", "Your result is:");
				request.setAttribute("value", resultValue);
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