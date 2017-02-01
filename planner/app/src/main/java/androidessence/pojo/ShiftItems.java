package androidessence.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by himanshu.sharma on 24-01-2017.
 */

public class ShiftItems implements Parcelable
{

    private String time;
    private String hour;
    private String min;
    private String name;
    private String gender;
    private String dob;
    private String age;
    private String action;
    private String status;
    private String eta;

    public ShiftItems(String time, String hour, String min, String name, String gender, String age, String action, String dob, String status, String eta) {
        this.time = time;
        this.hour = hour;
        this.min = min;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.action = action;
        this.dob = dob;
        this.status = status;
        this.eta = eta;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String sta) {
        this.status = sta;
    }

    private String draggedMsg;

    public void setDraggedMsg(String draggedMsg) {
        this.draggedMsg = draggedMsg;
    }

    public String getDraggedMsg() {
        return draggedMsg;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getEta() {
        return eta;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {

        out.writeString(time);
        out.writeString(hour);
        out.writeString(min);
        out.writeString(name);
        out.writeString(gender);
        out.writeString(dob);
        out.writeString(age);
        out.writeString(action);
        out.writeString(status);
        out.writeString(draggedMsg);
        out.writeString(eta);
    }

    public static final Parcelable.Creator<ShiftItems> CREATOR
            = new Parcelable.Creator<ShiftItems>() {
        public ShiftItems createFromParcel(Parcel in) {
            return new ShiftItems(in);
        }

        public ShiftItems[] newArray(int size) {
            return new ShiftItems[size];
        }
    };

    private ShiftItems(Parcel in) {
        time = in.readString();
        hour = in.readString();
        min = in.readString();
        name = in.readString();
        gender = in.readString();
        dob = in.readString();
        age = in.readString();
        action = in.readString();
        status = in.readString();
        draggedMsg = in.readString();
        eta = in.readString();
    }

}
