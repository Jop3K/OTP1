package models;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dataAccessObjects.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 *
 * Staattinen luokka ottaa sisään EventModel oliota ja palauttaa näiden lasketun palkan.
 *
 * @author Tuomas
 * @author Joonas
 *
 */
public class Calculation {

	private static EventObservableDataList data;

    private Calculation() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Laskee peruspalkan WorkProfile tuntipalkan avulla sekä ExtraPay palkkalisät mikäli sellaisia eventin ajankohtaan kuuluu
     * @param event EventModel minkä palkka halutaan laskea
     * @return Laskettu palkka
     */
    public static double Calculate(EventModel event) {

        double totalPay;

        double basicPay = calcBasicPay(event);

        System.out.println("Peruspalkka: " + basicPay);

        double extraPay = calcExtraPay(event);

        System.out.println("Extrapalkka: " + extraPay);

        totalPay = basicPay + extraPay;
        System.out.println(totalPay);
        return (double) Math.round(totalPay * 100d) / 100d;
    }

    /**
     * Peruspalkan laskenta
     * @param event EventModel minkä palkka halutaan laskea
     * @return Laskettu peruspalkka
     */
    static double calcBasicPay(EventModel event) {

        long start;
        long end;
        long seconds;
        double minutes;
        double pay;

        //Aletaan hakemaan ja laskemaan työtapahtuman pituutta tunteina ja minuutteina
        start = event.getBeginTime().getTime();
        end = event.getEndTime().getTime();
        seconds = (end - start) / 1000;
        minutes = seconds / 60;
        //Lasketaan minuuttipalkka
        pay = event.getWorkProfile().getPay() / 60;
        //lasketaan kokonaisansiot
        pay = pay * minutes;

        // Päivitetään tuntimäärä eventtiin
        event.setHours((double) Math.round((minutes / 60) * 100d) / 100d);

        return pay;
    }

    /**
     * Palkkalisien laskenta
     * @param event EventModel minkä palkka halutaan laskea
     * @return Laskettu palkkalisien antama lisäpalkka
     */
    static double calcExtraPay(EventModel event) {

        double extraPay = 0;

        // Muutetaan päivämäärät käyttämään java.time API:a
        Instant eventStart = event.getBeginTime().toInstant();
        Instant eventEnd = event.getEndTime().toInstant();

        LocalDateTime eventStartDate = LocalDateTime.ofInstant(eventStart, ZoneId.systemDefault());
        LocalDateTime eventEndDate = LocalDateTime.ofInstant(eventEnd, ZoneId.systemDefault());

        // Haetaan viikonpäivät
        DayOfWeek startDay = DayOfWeek.from(eventStartDate);
        DayOfWeek endDay = DayOfWeek.from(eventEndDate);

        List<ExtraPay> extraPays = event.getWorkProfile().getExtraPays();

        for (ExtraPay ep : extraPays) {

            // Testataan osuuko ExtraPay eventin viikonpäiville
            WeekDays wds = ep.getWeekdays();

            LocalDateTime epStartDate;
            LocalDateTime epEndDate;
            LocalDateTime dateToUse;

            if (wds.isDayOfWeek(startDay)) {
                dateToUse = eventStartDate;
            } else if (wds.isDayOfWeek(endDay)) {
                dateToUse = eventEndDate;
            } else {
                continue;
            }

            int epStartHour = Integer.parseInt(ep.getBeginHour());
            int epStartMinute = Integer.parseInt(ep.getBeginMinute());
            int epStartMinutes = epStartHour * 60 + epStartMinute;

            int epEndHour = Integer.parseInt(ep.getEndHour());
            int epEndMinute = Integer.parseInt(ep.getEndMinute());
            int epEndMinutes = epEndHour * 60 + epEndMinute;

            // Lasketaan minuutti kesto
            int minutesDifference = epEndMinutes - epStartMinutes;
            if (minutesDifference < 0) {
                minutesDifference = minutesDifference + (24 * 60);
            }

            epStartDate = LocalDateTime.of(dateToUse.toLocalDate(), LocalTime.of(epStartHour, epStartMinute));
            epEndDate = epStartDate.plusMinutes(minutesDifference);

            LocalDateTime laterStart;
            LocalDateTime earlierEnd;

            // Haetaan myöhempi alkaminen
            if(eventStartDate.isAfter(epStartDate)) {
                laterStart = eventStartDate;
            } else {
                laterStart = epStartDate;
            }

            // Haetaan aikaisempi loppuminen
            if(eventEndDate.isBefore(epEndDate)) {
                earlierEnd = eventEndDate;
            } else {
                earlierEnd = epEndDate;
            }

            Duration durationOfOverlap = Duration.between(laterStart, earlierEnd);

            if(durationOfOverlap.isNegative())
                continue;

            long minutesOfOverlap = durationOfOverlap.toMinutes();

            System.out.println("Minutes: " + minutesOfOverlap);

            extraPay += minutesOfOverlap * ep.getExtraPay() / 60;

        }

        return extraPay;

    }

    static List calcPayForEveryYear() {
    	
    	List<Year> years = new ArrayList();
    	List<SalaryPerYearModel> result = new ArrayList();
    	   	
    	years = FindYearsFromEvents();
	  
    	  for (Year y : years) {
    		double salary = calcPayForYear(y);
      		SalaryPerYearModel tmp = new SalaryPerYearModel(y, salary);
      		result.add(tmp);
    	  }

    	  return result;
    }

    private static List<Year> FindYearsFromEvents() {
    	List<Year> years = new ArrayList();
    	int yearToCompare = 0;
    	
    	for (EventModel e : data.getInstance()){
  		LocalDate eventBegin = e.getBeginDay();
  		
  		if (yearToCompare != eventBegin.getYear()) {
  			Year year = Year.parse(Integer.toString(eventBegin.getYear()));
  			years.add(year);
  			
  			yearToCompare = eventBegin.getYear();
  		}
  	  }
		return years;
	}

	private static double calcPayForYear(Year year) {
    	
    	double totalPay = 0;

    	for (EventModel e : data.getInstance()){
    	 LocalDate eventBegin = e.getBeginDay();
	    	 if (year.getValue() == eventBegin.getYear()){
	    		 totalPay += e.getEventPay();
	    	 }
    	 }


    	return totalPay;
    }
    static List calcPayForEveryDayInMonth(YearMonth YearMonth) {

    	List result = new ArrayList();
    	for (EventModel event : data.getInstance()) {

    	}

    	return result;
    }


    /**
     * Tilastojen laskenta - kalenterivuoden tulot kuukausittain
     * @param year Year
     * @return List, joka pitää sisällään Java.Time API:n kuukauden enum arvon sekä kyseisen kuukauden tulot.
     */
    static List calcPayForEveryMonthInYear(Year year) {

    	List result = new ArrayList();

    	for (Month month : Month.values()) {

    		double salary = calcPayForMonth(year, month);
    		SalaryPerMonthModel tmp = new SalaryPerMonthModel(month, salary);
    		result.add(tmp);

    		}

		return result;
    }
    /**
     * Tilastojen laskenta - kuukauden tulot
     * @param month Month Java.time API:n kuukausi enum arvo
     * @param events List<EventModel> tapahtumat mistä tilastot lasketaan
     * @return Valitun kuukauden tulojen summa
     */
   private static double calcPayForMonth(Year year, Month month) {
        double totalPay = 0;

        for (EventModel event : data.getInstance()) { System.out.println(event.getId());
            LocalDate eventBegin = event.getBeginDay();
            Month eventMonth = eventBegin.getMonth();

            if(eventBegin.getYear() != year.getValue()) continue;

            if (eventMonth == month) {
                totalPay += event.getEventPay();
            }

        }

        return totalPay;
    }

    /**
     * Tilastojen laskenta - tulot yhteensä
     * @param daysFromNow int montako päivää tästä päivästä lähtien otetaan mukaan tilastoon (0 = pelkästään tänään, -1 = tänään ja eilen, 1 = tänään ja huomenna)
     * @param events List<EventModel> tapahtumat mistä tilastot lasketaan
     * @return Valitun aikavälin tapahtumien palkkojen summa
     */
    static double calcPayForTimePeriod(int daysFromNow, List<EventModel> events) {
        double totalPay = 0;
        LocalDate now = LocalDate.now();

        if(daysFromNow >= 0) {
            LocalDate endOfTimePeriod = now.plusDays(daysFromNow);

            for (EventModel event : events) {
                LocalDate eventBegin = event.getBeginDay();

                if ((eventBegin.isEqual(now) || eventBegin.isAfter(now)) && (eventBegin.isBefore(endOfTimePeriod) || eventBegin.isEqual(endOfTimePeriod))) {
                    totalPay += event.getEventPay();
                }

            }
        } else {
            LocalDate endOfTimePeriod = now.minusDays(Math.abs(daysFromNow));

            for (EventModel event : events) {
                LocalDate eventBegin = event.getBeginDay();

                if ((eventBegin.isEqual(now) || eventBegin.isBefore(now)) && (eventBegin.isAfter(endOfTimePeriod) || eventBegin.isEqual(endOfTimePeriod))) {
                    totalPay += event.getEventPay();
                }

            }

        }

        return totalPay;

    }

    /**
     * Tilastojen laskenta - työvuorojen määrä
     * @param daysFromNow int montako päivää tästä päivästä lähtien otetaan mukaan tilastoon (0 = pelkästään tämä päivä, -1 = tänään ja eilen, 1 = tänään ja huomenna)
     * @param events List<EventModel> tapahtumat mistä tilastot lasketaan
     * @return Valitun aikavälin tapahtumien määrä
     */
    static int calcAmountOfEvents(int daysFromNow, List<EventModel> events) {
        int amountOfEvents = 0;
        LocalDate now = LocalDate.now();

        if(daysFromNow >= 0) {
            LocalDate endOfTimePeriod = now.plusDays(daysFromNow);

            for (EventModel event : events) {
                LocalDate eventBegin = event.getBeginDay();

                if ((eventBegin.isEqual(now) || eventBegin.isAfter(now)) && (eventBegin.isBefore(endOfTimePeriod) || eventBegin.isEqual(endOfTimePeriod))) {
                    amountOfEvents++;
                }

            }

        } else {
            LocalDate endOfTimePeriod = now.minusDays(Math.abs(daysFromNow));

            for (EventModel event : events) {
                LocalDate eventBegin = event.getBeginDay();

                if ((eventBegin.isEqual(now) || eventBegin.isBefore(now)) && (eventBegin.isAfter(endOfTimePeriod) || eventBegin.isEqual(endOfTimePeriod))) {
                    amountOfEvents++;
                }

            }
        }

        return amountOfEvents;

    }

}
