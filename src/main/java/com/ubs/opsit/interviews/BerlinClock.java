package com.ubs.opsit.interviews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * Create By    : Xiaodai Ma
 * Created On   : 4th September 2017
 * This class implements the convertTime() method from interface
 * TimeConverter.
 */
public class BerlinClock implements TimeConverter {

    // This array models the row of 4 lamps representing 5-hour duration each.
    private char[] fiveHoursRow = new char[4];

    // This array models the row of 4 lamps representing 1-hour duration each.
    private char[] oneHourRow = new char[4];

    // This array models the row of 11 lamps representing 5-minute duration each.
    private char[] fiveMinutesRow = new char[11];

    // This array models the row of 4 lamps representing 1-minute duration each.
    private char[] oneMinuteRow = new char[4];

    // These variables hold the time components of the passed in time value
    int hours, minutes, seconds;

    // The constant String pattern is for string format purpose because the Array.toString()
    // method automatically insert left square bracket(]), right square bracket(]),
    // comma (,) and space.
    private static final String REMOVAL_PATTERN="[\\s\\[\\] ,]";

    // Common divisor for working out the quotient and remainder
    private static final int COMMON_DIVISOR = 5;

    /**
     * This method convert the hour portion of 24-hour time into Berlin Clock with
     * two rows of lamps.
     * @return The String representation of the two rows of lamps for Berlin Clock Hour
     * format.
     */
    private String getHours(){
        int fiveHoursRowEntries = hours / COMMON_DIVISOR;
        int oneHourRowEntries = hours % COMMON_DIVISOR;
        Arrays.fill(fiveHoursRow, 0, fiveHoursRowEntries, 'R');
        Arrays.fill(oneHourRow, 0, oneHourRowEntries, 'R');
        return Arrays.toString(fiveHoursRow).replaceAll(REMOVAL_PATTERN, "") + "\n" +
                Arrays.toString(oneHourRow).replaceAll(REMOVAL_PATTERN, "");
    }

    private String getMinutes(){
        int fiveMinutesRowEntries = minutes / COMMON_DIVISOR;
        int oneMinuteRowEntries = minutes % COMMON_DIVISOR;
        Arrays.fill(fiveMinutesRow, 0, fiveMinutesRowEntries, 'Y');
        Arrays.fill(oneMinuteRow, 0, oneMinuteRowEntries, 'Y');
        if(fiveMinutesRow[2] == 'Y'){
            fiveMinutesRow[2] = 'R';
        }
        if(fiveMinutesRow[5] == 'Y'){
            fiveMinutesRow[5] = 'R';
        }
        if(fiveMinutesRow[8] == 'Y'){
            fiveMinutesRow[8] = 'R';
        }
        return Arrays.toString(fiveMinutesRow).replaceAll(REMOVAL_PATTERN, "") + "\n" +
                Arrays.toString(oneMinuteRow).replaceAll(REMOVAL_PATTERN, "");
    }

    private String getSeconds(){
        if(seconds % 2 == 0){
            return Character.toString('Y');
        }else{
            return Character.toString('O');
        }
    }

    @Override
    public String convertTime(String aTime) throws IllegalArgumentException {
        if(!validateTime7(aTime)){
            throw new IllegalArgumentException("Incorrect Time Format");
        }

        initializeLampArrays();
        String[] timeComponents = aTime.trim().split(":");
        hours = Integer.parseInt(timeComponents[0]);
        minutes = Integer.parseInt(timeComponents[1]);
        System.out.println(minutes);
        seconds = Integer.parseInt(timeComponents[2]);
        System.out.println(seconds);
        StringBuilder sb = new StringBuilder();
        sb.append(getSeconds() + "\n");
        sb.append(getHours() + "\n");
        sb.append(getMinutes());

        return sb.toString();
    }

    /*private Optional<LocalTime> validateTime8(String aTime){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        Optional<LocalTime> validTime = Optional.empty();
        try{
            LocalTime inputTime = LocalTime.parse(aTime, formatter);
            validTime = Optional.of(inputTime);
        }catch(DateTimeParseException dtpe){
        }
        return validTime;
    } */

    private boolean validateTime7(String aTime){
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss");
        boolean result = false;
        try{
            timeFormatter.parse(aTime);
            result = true;
        }catch(ParseException pe){
            result = false;
        }
        return result;
    }

    private void initializeLampArrays(){
        Arrays.fill(fiveHoursRow, 'O');
        Arrays.fill(oneHourRow, 'O');
        Arrays.fill(fiveMinutesRow, 'O');
        Arrays.fill(oneMinuteRow, 'O');
    }
}

