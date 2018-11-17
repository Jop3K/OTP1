package models;

import java.time.*;
import java.util.List;
import java.util.Set;

/**
 * 
 * Staattinen luokka ottaa sisään EventModel oliota ja palauttaa näiden lasketun palkan.
 * 
 * @author Tuomas
 * @author Joonas
 * 
 */
public class Calculation {


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

        Set<ExtraPay> extraPays = event.getWorkProfile().getExtraPays();

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

    /**
     * Tilastojen laskenta
     * @param daysFromNow int montako päivää tästä päivästä eteenpäin otetaan mukaan tilastoon (0 = pelkästään tämä päivä)
     * @param events List<EventModel> tapahtumat mistä tilastot lasketaan
     *
     */
    static double calcPayForTimePeriod(int daysFromNow, List<EventModel> events) {
        LocalDate now = LocalDate.now();
        LocalDate endOfTimePeriod = now.plusDays(daysFromNow);

        double totalPay = 0;

        for(EventModel event : events) {
            LocalDate eventBegin = event.getBeginDay();

            if((eventBegin.isEqual(now) ||eventBegin.isAfter(now)) && (eventBegin.isBefore(endOfTimePeriod) || eventBegin.isEqual(endOfTimePeriod))) {
                totalPay += event.getEventPay();
            }

        }

        return totalPay;

    }

}
