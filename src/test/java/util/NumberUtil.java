package util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtil {
	public static int generateRandomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
	
	public static String formartToTwoDecimalPlace(double inputNumber) {
		NumberFormat formatter = new DecimalFormat("#0.00");  
		return formatter.format(inputNumber);
	}

}
