package androidessence.comman;

import java.util.ArrayList;
import java.util.List;

import androidessence.pojo.GridViewTime;

public class Utils
{
    static List<GridViewTime> timeList = new ArrayList<>();

    static List<GridViewTime> getTimeList()
    {
        timeList.add(new GridViewTime("9:00"));
        timeList.add(new GridViewTime("9:15"));
        timeList.add(new GridViewTime("9:30"));
        timeList.add(new GridViewTime("9:45"));
        timeList.add(new GridViewTime("10:00"));
        timeList.add(new GridViewTime("10:15"));
        timeList.add(new GridViewTime("10:30"));
        timeList.add(new GridViewTime("10:45"));
        timeList.add(new GridViewTime("11:00"));
        timeList.add(new GridViewTime("11:15"));
        timeList.add(new GridViewTime("11:30"));
        timeList.add(new GridViewTime("11:45"));
        timeList.add(new GridViewTime("12:00"));
        timeList.add(new GridViewTime("12.15"));
        timeList.add(new GridViewTime("12:30"));
        timeList.add(new GridViewTime("12:45"));
        timeList.add(new GridViewTime("13:00"));
        timeList.add(new GridViewTime("13:15"));
        timeList.add(new GridViewTime("13:30"));
        timeList.add(new GridViewTime("13:45"));
        timeList.add(new GridViewTime("14:00"));
        timeList.add(new GridViewTime("14:15"));
        timeList.add(new GridViewTime("14:15"));
        timeList.add(new GridViewTime("14:45"));
        timeList.add(new GridViewTime("15:00"));
        timeList.add(new GridViewTime("15:15"));
        timeList.add(new GridViewTime("15:30"));
        timeList.add(new GridViewTime("15:45"));
        timeList.add(new GridViewTime("16:00"));
        timeList.add(new GridViewTime("16:15"));
        timeList.add(new GridViewTime("16:30"));
        timeList.add(new GridViewTime("16:45"));
        timeList.add(new GridViewTime("17:00"));
        timeList.add(new GridViewTime("17:15"));
        timeList.add(new GridViewTime("17:30"));
        timeList.add(new GridViewTime("17:45"));
        timeList.add(new GridViewTime("18:00"));
        timeList.add(new GridViewTime("18:15"));
        timeList.add(new GridViewTime("18:30"));
        timeList.add(new GridViewTime("18:45"));
        timeList.add(new GridViewTime("19:00"));
        timeList.add(new GridViewTime("19:15"));
        timeList.add(new GridViewTime("19:30"));
        timeList.add(new GridViewTime("19:45"));

        return timeList;
    }
}
