package convertion;


public class Solution {

	static String timeConversion(String s){
		
		int militarHour;
		
		String typeHour = s.substring(s.length() -2, s.length());
		String hour = s.substring(0, 8); 
		String[] hours = hour.split(":");
		militarHour = Integer.parseInt(hours[0]);
		
		if (typeHour.equals("AM")){
			if(militarHour==12) {
				return "00" + ":" + hours[1] + ":" + hours[2];
			}
		} else {
			if (militarHour!=12) {
				militarHour = 12 + Integer.parseInt(hours[0]);
			} 
		}
		
		return ((militarHour<12)?"0"+militarHour:militarHour) + ":" + hours[1] + ":" + hours[2];
        
    }
	
	public static void main(String[] args){
		
		String result = timeConversion("04:59:59AM");
		
		System.out.println(result);
	}
}
