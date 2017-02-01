package androidessence.comman;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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

        for (int i = 0; i< incompArray.length() ; i++)
        {
            JSONObject jsonItem = incompArray.getJSONObject(i);
            String time = jsonItem.getString(Constants.TIME);
            String hr = jsonItem.getString(Constants.HOUR);
            String min = jsonItem.getString(Constants.MIN);
            String date = jsonItem.getString(Constants.DATE);
            String name = jsonItem.getString(Constants.NAME);
            String gender = jsonItem.getString(Constants.GENDER);
            String dob = jsonItem.getString(Constants.DOB);
            String age = jsonItem.getString(Constants.AGE);
            String action = jsonItem.getString(Constants.ACTION);
            String status = jsonItem.getString(Constants.STATUS);
            String eta = jsonItem.getString(Constants.ETA);
            IncompleteItems item = new IncompleteItems(time, hr, min, name, gender, age , action, dob, status, date, eta);
            listIncompleteItems.add(item);
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
