
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

import algorithm.genetic.Algorithm;
import algorithm.genetic.CrossoverUniformImp;
import algorithm.genetic.Fitness;
import algorithm.genetic.Individual;
import algorithm.genetic.Population;
import algorithm.genetic.SelectionTournamentImp;
import beans.CoefficientParameter;
import beans.ReferenceFunction;
import interfaces.ITransferFunction;
import model.math.Const;
import model.math.FOIntegral;
import model.math.TranserFunction1MUImp;
import model.math.TranserFunctionAperiodicImp;
import model.math.TranserFunctionIntImp;
import threads.EvolveProcess;
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
					Fitness.setRefFunction(refFunction);

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
						
					coefParameters[Const.K_INDEX] = paramK;
					coefParameters[Const.MU_INDEX] = paramMu;
					coefParameters[Const.A0_INDEX] = paramA0;
					coefParameters[Const.A1_INDEX] = paramA1;
						
					System.out.println("Array settings:" + Arrays.deepToString(coefParameters));

					Individual.setGenotypes(coefParameters);
					
					int generationSize = getIntFromRequest("generationSize", request);
					int numElitism = getIntFromRequest("numElitism", request);
					int numSizeSubgroup = getIntFromRequest("numSizeSubgroup", request);
					double mutationRate = getIntFromRequest("chanceOfMutation", request) / 100.0;
					System.out.println("mutation rate: " + mutationRate );
						
					CrossoverUniformImp crossover = new CrossoverUniformImp(0.5);
					SelectionTournamentImp selection = new SelectionTournamentImp(numSizeSubgroup);
					Population randomPopulation = new Population(generationSize, true);
					Algorithm algorithm = new Algorithm(selection, crossover, true, mutationRate);
						
					setupFitness(ctlrObject, Fitness.getData(), coefParameters);

					EvolveProcess process = new EvolveProcess(algorithm, randomPopulation);
						

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
	
	private int getIntFromRequest(String parameter, HttpServletRequest request) {
		int value = 0;
		String sValue = request.getParameter(parameter);
		value = Integer.parseInt(sValue);
		return value;
	}
	
	private void setupFitness(String controlObject, ReferenceFunction refFunction, CoefficientParameter[] coefParameters) {
		double dt = refFunction.getStepTimeSize();
		int n_max = refFunction.getArrayOutput().size();
		double minK = coefParameters[Const.K_INDEX].getMinValue();
		double minMu = coefParameters[Const.MU_INDEX].getMinValue();
		double minA0 = coefParameters[Const.A0_INDEX].getMinValue();
		double minA1 = coefParameters[Const.A1_INDEX].getMinValue();
		
		FOIntegral foIntegral = new FOIntegral(minMu, dt, n_max);
		
		ITransferFunction trFunc;
		
		switch(controlObject) {
			case("fo0"):
				trFunc = new TranserFunctionAperiodicImp(foIntegral, minA0, minK);
				break;
			case("fo1"):
				trFunc = new TranserFunctionIntImp(foIntegral, minA0, minA1, minK);
				break;
			case("fo2"):
				trFunc = new TranserFunction1MUImp(foIntegral, minA0, minA1, minK);
				break;
			default:
				return;
		}
		
		Fitness.setTransferFuntion(trFunc);
		
	}
	

}
