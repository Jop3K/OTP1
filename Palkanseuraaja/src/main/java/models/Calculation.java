package models;

import java.util.Date;

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
		/*
		 * Raakile palkkalisän laskemiselle
		 *
		 *
		 * 
		Date EAlku = new Date(2018 + 1900, 9, 20, 14,0,0 );
		Date ELoppu = new Date(2018 + 1900, 9, 21, 2,0,0);
		Date LAlku = new Date(2018 + 1900, 9, 20, 18,0,0);
		Date LLoppu = new Date(2018 + 1900, 9, 21, 3,0,0);

		long eAlku = EAlku.getTime();
		long eLoppu = ELoppu.getTime();
		long lAlku = LAlku.getTime();
		long lLoppu = LLoppu.getTime();
		long erotus;
		//Jos eventti alkaa aikaisemmin kuin palkkalisä
		if (eAlku < lAlku) {
			//Jos eventti loppuu ennen palkkalisää
			if(eLoppu < lLoppu) {
				erotus = eLoppu - lAlku;
			}
			else {
				erotus = lLoppu - lAlku;
			}
		}
		// jos eventti alkaa myöhemmin kuin palkkalisä
		else {
			//jos eventti loppuu ennen palkkalisää
			if (eLoppu < lLoppu) {
				erotus = lLoppu - eAlku;
			}
			else {
				erotus = eLoppu - eAlku;
			}

		}

		erotus = (erotus/1000)/60;
		System.out.println(erotus);*/

	}


}
