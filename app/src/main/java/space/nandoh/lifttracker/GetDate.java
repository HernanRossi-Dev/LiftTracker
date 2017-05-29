package space.nandoh.lifttracker;

import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by herna on 10/29/2016.
 */

public class GetDate {
    public GetDate(){

    }

    public HashMap<String, String> dateMap(){
        Date date = new Date();
        HashMap<String, String> date_map =new HashMap<>();
        String BUTTON_TODAY = "Add a lift";
        // Create regex to decompose the date into parts
        String pattern = "(\\w+)(\\s+)(\\w+)(\\s+)(\\d+)(\\s+)(\\S+)(\\s+)(\\w+)(\\s+)(\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(date.toString());
        if(m.find()) {
            date_map.put("day_word", m.group(1));
            date_map.put("day_number", m.group(5));
            date_map.put("month", m.group(3));
            date_map.put("year", m.group(11));
            String full_date = m.group(1) +  " " + m.group(3) + " " + m.group(5)+ " " + m.group(11);
            // Add the current day's date to the add lift button
            date_map.put("full_date",full_date );
            String addLiftText = BUTTON_TODAY + " \n" +full_date;
            date_map.put("addLiftText", addLiftText);
            return(date_map);
        }else{
            // Error parsing date do not alter the text
            return null;
        }

    }
}
