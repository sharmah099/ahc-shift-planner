package androidessence.comman;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidessence.planner.MainActivity;
import androidessence.pojo.IncompleteItems;
import androidessence.pojo.ShiftItems;


public class DataService {
    private ArrayList<IncompleteItems> listIncompleteItems = null;

    private ArrayList<ShiftItems> shiftItems = null;


    private static DataService object;

    private Context context;

    private final static long MILLIS_PER_DAY = 48 * 60 * 60 * 1000L;

    private final static long MILLIS_PER_DAY1 = 60 * 60 * 1000L;

    private DataService(Context context) {
        this.context = context;
    }

    public static DataService getService(Context context) {
        if (object == null) {
            synchronized (DataService.class) {
                if (object == null) {
                    object = new DataService(context);
                }
            }
        }
        return object;
    }

    public void parseJsonData() throws Exception {
        listIncompleteItems = new ArrayList<>();
        shiftItems = new ArrayList<>();

        JsonData data = new JsonData();
        JSONObject jsonObject = new JSONObject(data.getJsonData());
        JSONArray incompArray = jsonObject.getJSONArray(Constants.INCOMPLETE_JOBS);
        JSONArray shiftArray = jsonObject.getJSONArray(Constants.SHIFT_JOBS);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");

        Calendar todayDate = Calendar.getInstance();
        todayDate.setTime(new Date());

        for (int i = 0; i < incompArray.length(); i++) {
            JSONObject jsonItem = incompArray.getJSONObject(i);
            String time4 = jsonItem.getString(Constants.TIME);

            DateFormat DATE_TIME = new SimpleDateFormat("HH:mm", Locale.getDefault());

            MainApp app = (MainApp) context.getApplicationContext();
            String time = app.getCurrentTime();

            Calendar actualDate = Calendar.getInstance();
            actualDate.setTime(new Date());
            actualDate.add(Calendar.DATE, -2);

            Calendar newDate = Calendar.getInstance();
            newDate.setTime(new Date());

            int minute = Integer.parseInt(time.substring(3, 5));

            if (i == 0) {
                minute = Integer.parseInt(time.substring(3, 5)) - 1560;
            } else if (i == 1) {
                minute = Integer.parseInt(time.substring(3, 5)) - 240;
            } else if (i == 2) {
                minute = Integer.parseInt(time.substring(3, 5)) - 30;
            }
            newDate.set(Calendar.MINUTE, minute);
            String finalTime = DATE_TIME.format(newDate.getTime());
            String finalDate = sdf.format(newDate.getTime());

            String hr = jsonItem.getString(Constants.HOUR);
            String min = jsonItem.getString(Constants.MIN);
            String name = jsonItem.getString(Constants.NAME);
            String gender = jsonItem.getString(Constants.GENDER);
            String dob = jsonItem.getString(Constants.DOB);
            String age = jsonItem.getString(Constants.AGE);
            String action = jsonItem.getString(Constants.ACTION);
            String status = jsonItem.getString(Constants.STATUS);
            String eta = jsonItem.getString(Constants.ETA);
            IncompleteItems item = new IncompleteItems(finalTime, hr, min, name, gender, age, action, dob, status, finalDate, eta);

            boolean moreThanDay = Math.abs(actualDate.getTime().getTime() - todayDate.getTime().getTime()) > MILLIS_PER_DAY;

            if (!moreThanDay) {
                listIncompleteItems.add(item);
            }
        }

        for (int i = 0; i < shiftArray.length(); i++) {
            JSONObject jsonObjectShift = shiftArray.getJSONObject(i);
            String time1 = jsonObjectShift.getString(Constants.TIME);

            DateFormat DATE_TIME = new SimpleDateFormat("HH:mm", Locale.getDefault());

            MainApp app = (MainApp) context.getApplicationContext();
            String time = app.getCurrentTime();

            Calendar actualDate = Calendar.getInstance();
            actualDate.setTime(new Date());

            Calendar newDate = Calendar.getInstance();
            newDate.setTime(new Date());

            int minute = Integer.parseInt(time.substring(3, 5));

            if (i == 0) {
                minute = Integer.parseInt(time.substring(3, 5)) + 30;
            } else if (i == 1) {
                minute = Integer.parseInt(time.substring(3, 5)) + 150;
            } else if (i == 2) {
                minute = Integer.parseInt(time.substring(3, 5)) + 300;
            } else if (i == 3) {
                minute = Integer.parseInt(time.substring(3, 5)) + 420;
            } else if (i == 4) {
                minute = Integer.parseInt(time.substring(3, 5)) + 60;
            }

            actualDate.set(Calendar.MINUTE, minute);
            String finalTime = DATE_TIME.format(actualDate.getTime());

            // String time = app.getCurrentTime();
            String hr = jsonObjectShift.getString(Constants.HOUR);
            String min = jsonObjectShift.getString(Constants.MIN);
            String name = jsonObjectShift.getString(Constants.NAME);
            String gender = jsonObjectShift.getString(Constants.GENDER);
            String dob = jsonObjectShift.getString(Constants.DOB);
            String age = jsonObjectShift.getString(Constants.AGE);
            String action = jsonObjectShift.getString(Constants.ACTION);
            String status = jsonObjectShift.getString(Constants.STATUS);
            String eta = jsonObjectShift.getString(Constants.ETA);

            ShiftItems item = new ShiftItems(finalTime, hr, min, name, gender, age, action, dob, status, eta);

            boolean moreThanDay = Math.abs(actualDate.getTime().getTime() - newDate.getTime().getTime()) > MILLIS_PER_DAY1 * app.getCurrentHour();
            if (!moreThanDay) {
                shiftItems.add(item);
            }
        }
    }

    public ArrayList<ShiftItems> getShiftItems() {
        return shiftItems;
    }

    public ArrayList<IncompleteItems> getListIncompleteItems() {
        return listIncompleteItems;
    }
}
