package com.ubs.opsit.interviews;

import javax.annotation.PreDestroy;
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

    // Char constants for lamp's colour.
    private static final char YELLOW_LAMP = 'Y';
    private static final char RED_LAMP = 'R';
    private static final char OFF_LAMP = 'O';

    // The constant String pattern is for string format purpose because the Array.toString()
    // method automatically insert left square bracket(]), right square bracket(]),
    // comma (,) and space.
    private static final String REMOVAL_PATTERN="[\\s\\[\\] ,]";

    // Common divisor for working out the quotient and remainder
    private static final int COMMON_DIVISOR = 5;

    /**
     * This is the function required to be implemented for time conversion from
     * 24-hour time to Berlin Clock
     * @param aTime A string representing a 24-hour time that a user wants to convert from.
     * @return A string representing a Berlin Clock time that a user wants to convert to.
     * @throws IllegalArgumentException
     */
    @Override
    public String convertTime(String aTime) throws IllegalArgumentException {
        // Call internal time validation method to verify user input.
        // if validation returns false, throw illegalArgumentException
        if(!validateTime(aTime)){
            throw new IllegalArgumentException("Incorrect Time Format");
        }

        // Initializing all lamp to be at state of OFF.
        initializeLampArrays();

        // Decompose the 24-hour time format into hours, minutes and seconds portions.
        String[] timeComponents = aTime.trim().split(":");
        hours = Integer.parseInt(timeComponents[0]);
        minutes = Integer.parseInt(timeComponents[1]);
        seconds = Integer.parseInt(timeComponents[2]);

        // Initalizing a StringBuilder object to compose the final String
        StringBuilder sb = new StringBuilder();

        // Appending the Berlin Clock Seconds
        sb.append(getSeconds() + "\n");

        // Appending the Berlin Clock Hours
        sb.append(getHours() + "\n");

        // Appending the Berlin Clock Minutes
        sb.append(getMinutes());

        return sb.toString();
    }

    /**
     * This method convert the hour portion of 24-hour time into Berlin Clock hour with
     * two rows of lamps.
     * @return The String representation of the two rows of lamps for Berlin Clock Hour
     * format.
     */
    private String getHours(){
        // Because the hours are divided into two rows, the top row is hours value of multiple
        // of 5, and the bottom row is the remainder of 5, so the hour portion of 24-hour time
        // format can be represented using the math division and modulo operations.
        // The quotient portion is assigned to the top row, whereas the remainder portion is
        // assigned to the bottom row.
        int fiveHoursRowEntries = hours / COMMON_DIVISOR;
        int oneHourRowEntries = hours % COMMON_DIVISOR;

        // As specified by the instruction, hours' lamps are red, demoted by 'R'
        // so filled the array with R when the lamp is on.
        Arrays.fill(fiveHoursRow, 0, fiveHoursRowEntries, RED_LAMP);
        Arrays.fill(oneHourRow, 0, oneHourRowEntries, RED_LAMP);

        // Return the two-row string representation of Hour value in Berlin Clock
        // using the REMOVAL_PATTERN to get rid of [], and space.
        return Arrays.toString(fiveHoursRow).replaceAll(REMOVAL_PATTERN, "") + "\n" +
                Arrays.toString(oneHourRow).replaceAll(REMOVAL_PATTERN, "");
    }

    /**
     * This method convert the minute portion of 24-hour time into Berlin Clock minute with
     * two rows of lamps.
     * @return The String representation of the two rows of lamps for Berlin Clock Minute
     * format.
     */
    private String getMinutes(){

        // Same logic as calculating hours. The top row represent the minutes of multiple of 5
        // the bottom row represents the remainder. these two potions together denotes the 0-59
        // minutes of 24-hour format
        int fiveMinutesRowEntries = minutes / COMMON_DIVISOR;
        int oneMinuteRowEntries = minutes % COMMON_DIVISOR;

        // As the instruction suggest, the minutes lamps are yellow colour denoted by 'Y'
        Arrays.fill(fiveMinutesRow, 0, fiveMinutesRowEntries, YELLOW_LAMP);
        Arrays.fill(oneMinuteRow, 0, oneMinuteRowEntries, YELLOW_LAMP);

        // Except that 15-min (quarter), 30-min (half-hour) and 45-min(quarter-to).
        // Because of the array is 0-index based, so the actual indexes are 2,5 and 8
        // Whenever these lamps are on, the program needs to switch its colour from Yellow
        // to Red.
        if(fiveMinutesRow[2] == YELLOW_LAMP){
            fiveMinutesRow[2] = RED_LAMP;
        }
        if(fiveMinutesRow[5] == YELLOW_LAMP){
            fiveMinutesRow[5] = RED_LAMP;
        }
        if(fiveMinutesRow[8] == YELLOW_LAMP){
            fiveMinutesRow[8] = RED_LAMP;
        }

        // Return the two-row string representation of Hour value in Berlin Clock
        // using the REMOVAL_PATTERN to get rid of [], and space.
        return Arrays.toString(fiveMinutesRow).replaceAll(REMOVAL_PATTERN, "") + "\n" +
                Arrays.toString(oneMinuteRow).replaceAll(REMOVAL_PATTERN, "");
    }

    /**
     * This method convert the second portion of 24-hour time into Berlin Clock second with
     * one lamp.
     * @return The String representation of the second in Berlin Clock.
     */
    private String getSeconds(){

        // There is only one lamp for the second portion of Berlin Clock
        // It is on only when the second value is multiple of two including 0.
        if(seconds % 2 == 0){
            return Character.toString(YELLOW_LAMP);
        }else{
            return Character.toString(OFF_LAMP);
        }
    }

    /**
     * This method validates user input time value.(00:00:00 ~ 24:59:59)
     * @param aTime User input time value
     * @return true if the time is valid or false if the time is invalid
     */
    private boolean validateTime(String aTime){

        // Defines the expected valid time format.
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss");
        boolean result = false;

        // If the time can be parsed successfully, set the result to true
        try{
            timeFormatter.parse(aTime);
            result = true;
        }catch(ParseException pe){

            // if there is parse exception, set the result to false;
            result = false;
        }
        return result;
    }

    /**
     * This function switches off all lamps
     */
    private void initializeLampArrays(){
        Arrays.fill(fiveHoursRow, OFF_LAMP);
        Arrays.fill(oneHourRow, OFF_LAMP);
        Arrays.fill(fiveMinutesRow, OFF_LAMP);
        Arrays.fill(oneMinuteRow, OFF_LAMP);
    }
}

