
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.opencsv.CSVReader;

import beans.CoefficientParameter;
import beans.ReferenceFunction;
import utils.StrToNumber;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String base = "/jsp/";
		String url = base + "uploadData.jsp";
		String action = request.getParameter("action");
		System.out.println("ACTION: " + action);
		if (action != null) {
			switch (action) {
			case "upload":
				url = base + "optimization.jsp";
				String sStepTime = request.getParameter("stepTime");
				double stepTimeSize = Double.parseDouble(sStepTime);
				
				String sStepFunctionValue = request.getParameter("stepFunctionValue");
				double stepFunctionValue = Double.parseDouble(sStepFunctionValue);
				
				System.out.println("Step time: " + request.getParameter("stepTime"));
				System.out.println("Step function value: " + request.getParameter("stepFunctionValue"));
				
				Part filePart = request.getPart("csvFile");
				InputStream fileContent = filePart.getInputStream();

				Reader in = new InputStreamReader(fileContent);
				CSVReader reader;
				Iterator<String[]> iterator;

				try {
					reader = new CSVReader(in);
					iterator = reader.iterator();
					
					List<Double> lyData = new LinkedList<>();
					String[] row;
					while(iterator.hasNext()) {
						row = iterator.next();
						String sItem = row[0];
						double item = StrToNumber.getDouble(sItem);
						lyData.add(item);
						System.out.println("ITEM: " + item);
					}
					
					ReferenceFunction refFunction = new ReferenceFunction(lyData, stepFunctionValue, stepTimeSize);
					
					reader.close();
					
				} catch (Exception e) {
					System.out.println("Error in CSVReader: " + e.toString());
				} finally {
					if(in != null) {
						in.close();
					}
				}

				break;
			case "start":
				String ctlrObject = request.getParameter("controlObject");
				System.out.println(ctlrObject);
				
				CoefficientParameter[] coefParameters = new CoefficientParameter[4];

				try {
					CoefficientParameter paramK = getTransferFuncSetting(request, "k");
					CoefficientParameter paramMu = getTransferFuncSetting(request, "mu");
					CoefficientParameter paramA0 = getTransferFuncSetting(request, "a0");
					CoefficientParameter paramA1 = getTransferFuncSetting(request, "a1");
						
					coefParameters[0] = paramK;
					coefParameters[1] = paramMu;
					coefParameters[2] = paramA0;
					coefParameters[3] = paramA1;
						
					System.out.println("Array settings:" + Arrays.deepToString(coefParameters));

					String sGenerationSize = request.getParameter("generationSize");
					int generationSize = Integer.parseInt(sGenerationSize);
						
					String sNumElitism = request.getParameter("numElitism");
					int numElitism = Integer.parseInt(sNumElitism);
						
					String sNumSizeSubgroup = request.getParameter("numSizeSubgroup");
					int numSizeSubgroup = Integer.parseInt(sNumSizeSubgroup);
						

				}catch(Exception e){
					System.out.println("Error in set settings: " + e.toString());
				}
				break;
			default:
				break;
			}
		}
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
		requestDispatcher.forward(request, response);
	}
	
	private CoefficientParameter getTransferFuncSetting(HttpServletRequest request, String sValue) throws Exception {
		CoefficientParameter parameter;
		String sStartValue = request.getParameter(sValue + "Start");
		String sFinalValue = request.getParameter(sValue + "Final");
		String sStepValue = request.getParameter(sValue + "Step");
		
		double startValue = Double.parseDouble(sStartValue);
		double finalValue = Double.parseDouble(sFinalValue);
		double stepValue = Double.parseDouble(sStepValue);
		
		parameter = new CoefficientParameter(startValue, finalValue, stepValue);
		
		return parameter;
	}
	

}
