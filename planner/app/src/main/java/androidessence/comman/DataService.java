package androidessence.comman;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidessence.pojo.IncompleteItems;
import androidessence.pojo.ShiftItems;

/**
 * Created by himanshu.sharma on 24-01-2017.
 */

public class DataService
{
    private  ArrayList<IncompleteItems> listIncompleteItems = null;

    private ArrayList<ShiftItems> shiftItems = null;

    private static DataService object;

    public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;

    private DataService(){
    }

    public static DataService getService()
    {
        if(object == null) {
            synchronized (DataService.class) {
                if (object == null) {
                    object = new DataService();
                }
            }
        }
        return object;
    }

    public void parseJsonData() throws Exception
    {
        listIncompleteItems = new ArrayList<>();
        shiftItems = new ArrayList<>();

        JsonData data = new JsonData();
        JSONObject jsonObject = new JSONObject(data.getJsonData());
        JSONArray incompArray  = jsonObject.getJSONArray(Constants.INCOMPLETE_JOBS);
        JSONArray shiftArray = jsonObject.getJSONArray(Constants.SHIFT_JOBS);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");

        Calendar todayDate = Calendar.getInstance();
        todayDate.setTime(new Date());

        for (int i = 0; i< incompArray.length() ; i++)
        {
            JSONObject jsonItem = incompArray.getJSONObject(i);
            String time = jsonItem.getString(Constants.TIME);
            String hr = jsonItem.getString(Constants.HOUR);
            String min = jsonItem.getString(Constants.MIN);

            Calendar actualDate = Calendar.getInstance();
            actualDate.setTime(new Date());
            actualDate.add(Calendar.DATE, -1);

            int hour = Integer.parseInt(time.substring(0,2));
            actualDate.set(Calendar.HOUR, hour);

            int minute = Integer.parseInt(time.substring(3,5));
            actualDate.set(Calendar.MINUTE, minute);
            String date1 = sdf.format(actualDate.getTime());

            String name = jsonItem.getString(Constants.NAME);
            String gender = jsonItem.getString(Constants.GENDER);
            String dob = jsonItem.getString(Constants.DOB);
            String age = jsonItem.getString(Constants.AGE);
            String action = jsonItem.getString(Constants.ACTION);
            String status = jsonItem.getString(Constants.STATUS);
            String eta = jsonItem.getString(Constants.ETA);
            IncompleteItems item = new IncompleteItems(time, hr, min, name, gender, age , action, dob, status, date1, eta);

            boolean moreThanDay = Math.abs(actualDate.getTime().getTime() - todayDate.getTime().getTime()) > MILLIS_PER_DAY;

            if (!moreThanDay) {
                listIncompleteItems.add(item);
            }
        }



        for (int i = 0; i< shiftArray.length() ; i++) {
            JSONObject jsonObjectShift = shiftArray.getJSONObject(i);
            String time = jsonObjectShift.getString(Constants.TIME);
            String hr = jsonObjectShift.getString(Constants.HOUR);
            String min = jsonObjectShift.getString(Constants.MIN);
            String name = jsonObjectShift.getString(Constants.NAME);
            String gender = jsonObjectShift.getString(Constants.GENDER);
            String dob = jsonObjectShift.getString(Constants.DOB);
            String age = jsonObjectShift.getString(Constants.AGE);
            String action = jsonObjectShift.getString(Constants.ACTION);
            String status = jsonObjectShift.getString(Constants.STATUS);
            String eta = jsonObjectShift.getString(Constants.ETA);

            ShiftItems item = new ShiftItems(time, hr, min, name, gender, age , action, dob, status, eta);
            shiftItems.add(item);
        }
    }

    public ArrayList<ShiftItems> getShiftItems() {
        return shiftItems;
    }

    public ArrayList<IncompleteItems> getListIncompleteItems(){
        return listIncompleteItems;
    }

}
