package ch.hosttech.schulio.model;

public class MarkModal {

    private String subject;
    private float mark;
    private String testName;
    private int id;

    // creating getter and setter methods
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // constructor
    public MarkModal(String subject, String testName, float mark) {
        this.subject = subject;
        this.testName = testName;
        this.mark = mark;
    }

}
