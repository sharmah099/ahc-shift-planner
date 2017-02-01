package androidessence.pojo;

/**
 * Created by himanshu.sharma on 24-01-2017.
 */

public class IncompleteItems
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
    private String date;
    private String eta;

    public IncompleteItems(String time, String hour, String min, String name, String gender, String age, String action, String dob,
                           String status, String date, String eta) {
        this.time = time;
        this.hour = hour;
        this.min = min;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.action = action;
        this.dob = dob;
        this.status = status;
        this.date = date;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getEta() {
        return eta;
    }
}
