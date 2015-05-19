package history;

public class HistoryLog {

    private int typeid;
    private int subid;
    private int amount;
    private String date;
    private String note;
    private String userId;

    public HistoryLog() {

    }

    public HistoryLog(int typeid,int subid,int amount,String date,String note) {
        this.typeid = typeid;
        this.subid = subid;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
