package models;

public class Calculation {
	
	
	
	
	public Calculation() {
		// TODO Auto-generated constructor stub
	}
	
	public static double Calculate (EventModel event) {
		long start;
		long end;
		long seconds;
		double hours;
		double minutes;
		double sum;
		double pay;
		//Aletaan hakemaan ja laskemaan työtapahtuman pituutta tunteina ja minuutteina
		start = event.getBeginTime().getTime();
		end = event.getEndTime().getTime();
		seconds = (end-start)/1000;
		minutes = seconds/60;
		/*if (sum<60) {
			hours = 0;
			minutes = sum;
		}
		else {
			minutes = sum%60;
			sum -= minutes;
			hours = sum/60;
		}*/
		//Lasketaan minuuttipalkka
		pay = event.getWorkProfile().getPay()/60;
		//lasketaan kokonaisansiot
		pay = pay * minutes;
			
		return (double)Math.round(pay * 100d) / 100d;
		
	}
	

}
