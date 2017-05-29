package space.nandoh.lifttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/***************************************************************************************************
 *  Created by Hernan Rossi.
 **************************************************************************************************/

public class MainActivity extends Activity {
    private static final String BUTTON_TODAY = "Add a lift";
    private static HashMap<String, String> date_map = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
             // Add today's date to the add lift button
        date_map = getDate();
        String date_text;
        if((date_text = date_map.get("addLiftTest")) == null){
        }else {
            Button today = (Button) findViewById(R.id.add_lift);
            today.setText(date_text);
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);
            today.setAnimation(myAnim);
        }
    }

    /**********************************************************************************************
     *                                      AddLiftActivity
     * @param view
     *
     *                  Launch the add lift activity that will ask the user for what muscle group
     *                       they are working on the current lift -> what specific exercise they are
     *                       doing -> what type of weight they are using for it.
     **********************************************************************************************/
    public void addLift(View view)  {
        Intent intent = new Intent(this, AddLiftActivity.class);
        intent.putExtra("DateMap", date_map);
        startActivity(intent);
    }

    /**********************************************************************************************
     *                                      viewToday
     * @param view
     *
     *              Launch the activity that will locate all of the lift data that has been entered
     *                  in the SAVE_DATA table that correspond to today's date
     *                      in day:month:year String format
     **********************************************************************************************/
    public void viewToday(View view) {
        Intent intent = new Intent(this,ViewToday.class );
        startActivity(intent);
    }
    /**********************************************************************************************
     *                                      viewHistory
     * @param view
     *
     *              Launch the activity that will locate all of the lift data that has been entered
     *                  in the SAVE_DATA table that correspond to each date
     *                      in day:month:year String format and output it in a list according to each
     *                      day and give the name of the most common muscle group worked on that day
     *                      as well the number of exercises done and total weight lifted over all
     *                      workouts that day.
     **********************************************************************************************/
    public void viewHistory(View view) {
        Intent intent = new Intent(this,ViewHistory.class );
        startActivity(intent);
    }

    /**********************************************************************************************
     *                                      addCustomLift
     * @param view
     *
     *              launch activity to add a custom user lift to the list of available
     *                               in the lift add activity
     **********************************************************************************************/
    public void addCustomLift(View view) {
        Intent intent = new Intent(this, CustomLiftInsert.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.select_language) {
            TextView view = (TextView) findViewById(R.id.textView);
            Toast.makeText(view.getContext(), "Language setting to be implemented", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**********************************************************************************************
     *                                      getDate
     * @return : HashMap<String, String>
     *
     *            Get date and parse it if an error occurs return String null
     **********************************************************************************************/
    public static HashMap<String, String> getDate() {
        Date date = new Date();
        String pattern = "(\\w+)(\\s+)(\\w+)(\\s+)(\\d+)(\\s+)(\\S+)(\\s+)(\\w+)(\\s+)(\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(date.toString());
        HashMap<String, String> date_map = new HashMap<>();
        if(m.find()) {
            date_map.put("day_word", m.group(1));
            date_map.put("day_number", m.group(5));
            date_map.put("month", m.group(3));
            date_map.put("year", m.group(11));
            String full_date = m.group(1) +  " " + m.group(3) + " " + m.group(5)+ " " + m.group(11);
            date_map.put("full_date",full_date );
            String addLiftText = BUTTON_TODAY + " \n" +full_date;
            date_map.put("addLiftText", addLiftText);
            return(date_map);
        }else{
            return null;
        }
    }
}
