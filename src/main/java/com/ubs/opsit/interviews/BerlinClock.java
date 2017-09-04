package com.ubs.opsit.interviews;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class BerlinClock implements TimeConverter {

    private char[] fiveHoursRow = new char[4];
    private char[] oneHourRow = new char[4];
    private char[] fiveMinutesRow = new char[11];
    private char[] oneMinuteRow = new char[4];
    private static final int COMMON_DIVISOR = 5;
    int hours, minutes, seconds;
    private static final String REMOVAL_PATTERN="[\\s\\[\\] ,]";

    public BerlinClock(){

        Arrays.fill(fiveHoursRow, 'O');
        Arrays.fill(oneHourRow, 'O');
        Arrays.fill(fiveMinutesRow, 'O');
        Arrays.fill(oneMinuteRow, 'O');
    }

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
        String[] timeComponents = aTime.split(":");
        hours = Integer.parseInt(timeComponents[0]);
        minutes = Integer.parseInt(timeComponents[1]);
        seconds = Integer.parseInt(timeComponents[2]);
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
}

