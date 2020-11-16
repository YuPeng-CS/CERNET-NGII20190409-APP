package sics.exception;

public class DbException extends Exception {

    private int id;
    private String what;

    public DbException(int id, String what) {
        super();
        this.id = id;
        this.what = what;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }
}
