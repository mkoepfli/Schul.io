package ch.hosttech.schulio;

public class SubjectModal {

    // variables for our coursename,
    // description, tracks and duration, id.
    private String subjectName;
    private int id;

    // creating getter and setter methods
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // constructor
    public SubjectModal(String subjectName) {
        this.subjectName = subjectName;
    }
}
