package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.dei.qcs.ejb.InsulinDoseCalculatorEJBRemote;
import edu.dei.qcs.voter.Result;

public class PersonalCalculation extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	public InsulinDoseCalculatorEJBRemote ejb;

	public PersonalCalculation() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<Result> result = calculateDose(request);


		if (result != null && result.size() >= 1)
		{

			if(result.size() > 1)
			{

				/*Output both results*/
				request.setAttribute("multipleResults", true);

				request.setAttribute("title", "Mealtime insulin dose - Personal insulin sensitivity");
				if(result.get(1).getResult() >= 0){
					request.setAttribute("text", "Your result is:");
					request.setAttribute("value", result.get(1).getResult());
				}
				else
				{
					request.setAttribute("text", "It was not possible to calculate the insulin dose <br> Please, try again!");
				}
				request.setAttribute("result", result.get(1));

				request.setAttribute("title2", "Personal Insulin Sensitivity");
				request.setAttribute("result2", result.get(0));
				request.setAttribute("text2", "Your result is:");
				request.setAttribute("value2", result.get(0).getResult());

				RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
				rd.forward(request, response);
			}
			else {
				/*Output only sensibility result*/
				request.setAttribute("title", "Personal Insulin Sensitivity");
				if(result.get(0).getResult() >= 15){
					request.setAttribute("text", "Your result is:");
					request.setAttribute("value", result.get(0).getResult());
				}
				else
				{
					request.setAttribute("text", "It was not possible to calculate the personal sensitivity to insulin <br> Please, try again!");
				}
				request.setAttribute("result", result.get(0));

				request.setAttribute("errortext", "Unfortunatly we were unable to calculate your Mealtime insulin dose. Please, try again.");

				RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
				rd.forward(request, response);
			}

		} else {
			/*No results to show -  go to error page*/
			RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
			rd.forward(request, response);
		}
	}

	private ArrayList<Result> calculateDose(HttpServletRequest request)
	{
		String ActivitySampleName[] = {"activityLevelSample1", "activityLevelSample2", "activityLevelSample3",
				"activityLevelSample4", "activityLevelSample5", "activityLevelSample6",
				"activityLevelSample7", "activityLevelSample8", "activityLevelSample9", "activityLevelSample10"};
		String BloodSampleName[] = {"bloodSample1", "bloodSample2", "bloodSample3", "bloodSample4", "bloodSample5", "bloodSample6",
				"bloodSample7", "bloodSample8", "bloodSample9", "bloodSample10"};
		String activityLevelString;
		int PhysicalActivity[] = new int[10], PA[];
		int BloodSample[] = new int[10], BS[];
		int activityLevel, value, i, j;
		String p1, p2, p3, p4;
		int total_CH, CH_processed, blood_sugar, target_blood_sugar;
		ArrayList<Result> result = null;

		/*Get Activity Sample from user input*/
		i=0;
		for (String sample : ActivitySampleName)
		{
			sample = request.getParameter(sample);
			if (sample != null && !sample.isEmpty())
			{
				value = Integer.parseInt(sample);
				PhysicalActivity[i] = value;
				i++;
			}
		}

		/*Get Blood Sample from user input*/
		i=0;
		for (String sample : BloodSampleName)
		{
			sample = request.getParameter(sample);
			if (sample != null && !sample.isEmpty())
			{
				value = Integer.parseInt(sample);
				BloodSample[i] = value;
				i++;
			}
		}

		/*Create arrays with proper size*/
		PA = new int[i];
		BS = new int[i];
		for (j=0; j<i; j++)
		{
			PA[j] = PhysicalActivity[j];
			BS[j] = BloodSample[j];
		}

		/*Get parameter from user input*/
		activityLevelString = request.getParameter("activityLevel");
		p1 = request.getParameter("total_CH");
		p2 = request.getParameter("CH_processed");
		p3 = request.getParameter("blood_sugar");
		p4 = request.getParameter("target_blood_sugar");


		/*Verify all values are valid and personal insulin dose*/
		if (activityLevelString != null && p1 !=null && p2 !=null && p3 !=null && p4 !=null && PA.length>0)
		{
			activityLevel = Integer.parseInt(activityLevelString);
			total_CH = Integer.parseInt(p1);
			CH_processed = Integer.parseInt(p2);
			blood_sugar = Integer.parseInt(p3);
			target_blood_sugar = Integer.parseInt(p4);

			//System.out.println(total_CH + " " + CH_processed + " " + blood_sugar + " " + target_blood_sugar);
			result = ejb.personalSensitivityToInsulin(total_CH, CH_processed, blood_sugar, target_blood_sugar, activityLevel, PA, BS);
		}

		return result;
	}
}
