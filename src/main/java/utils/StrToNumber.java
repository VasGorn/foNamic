package utils;

public final class StrToNumber {
	public static double getDouble(String sNumber) throws Exception {
		double number = 0.0;
		if(sNumber.trim().isEmpty()) {
			throw new Exception("String is empty!");
		}
		number = Double.parseDouble(sNumber);
	
		return number;
	}

	public static int getIntger(String sNumber) throws Exception {
		int number = 0;
		if(sNumber.trim().isEmpty()) {
			throw new Exception("String is empty!");
		}
		number = Integer.parseInt(sNumber);
	
		return number;
	}
}
