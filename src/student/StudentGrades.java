package student;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentGrades {
    private final StringProperty ID;
    private final StringProperty Math;
    private final StringProperty Science;
    private final StringProperty History;
    private final StringProperty English;

    public StudentGrades (String IDParam, String MathParam, String ScienceParam, String HistoryParam, String EnglishParam){

        this.ID = new SimpleStringProperty(IDParam);
        this.Math = new SimpleStringProperty(MathParam);
        this.Science = new SimpleStringProperty(ScienceParam);
        this.History = new SimpleStringProperty(HistoryParam);
        this.English = new SimpleStringProperty(EnglishParam);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getMath() {
        return Math.get();
    }

    public StringProperty mathProperty() {
        return Math;
    }

    public void setMath(String math) {
        this.Math.set(math);
    }

    public String getScience() {
        return Science.get();
    }

    public StringProperty scienceProperty() {
        return Science;
    }

    public void setScience(String science) {
        this.Science.set(science);
    }

    public String getHistory() {
        return History.get();
    }

    public StringProperty historyProperty() {
        return History;
    }

    public void setHistory(String history) {
        this.History.set(history);
    }

    public String getEnglish() {
        return English.get();
    }

    public StringProperty englishProperty() {
        return English;
    }

    public void setEnglish(String english) {
        this.English.set(english);
    }
}
