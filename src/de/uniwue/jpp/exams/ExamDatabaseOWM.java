package de.uniwue.jpp.exams;

import de.uniwue.jpp.errorhandling.OptionalWithMessage;

import java.util.*;

public class ExamDatabaseOWM {
    private final Map<Student, Map<Exam, ExamResult>> data;

    public ExamDatabaseOWM(Map<Student, Map<Exam, ExamResult>> data) {
        this.data = data;
    }

    public OptionalWithMessage<Student> getStudent(int matriculation) {
        Student student = null;
        for (Student s : this.data.keySet()) {
            if (s.getMatriculation() == matriculation) student = s;  // vergleicht jeden Student in data mit matriculation
        }
        return OptionalWithMessage.ofNullable(student, "No student with matriculation " + matriculation);
        // ist Student am Ende noch null (keine Übereinstimmung gefunden) wird OWMMsg, ansonsten OWMVal mit Student zurückgegeben
    }

    public OptionalWithMessage<Map<Exam, ExamResult>> getAllExamsWithResultsOf(Student stud) {
        if (stud == null) throw new NullPointerException();
        else return OptionalWithMessage.ofNullable(this.data.get(stud), "Student is unknown!");
        // ist data.get(stud) == null, OWMMsg, ansonsten OWMVal mit value des Studenten
    }

    public OptionalWithMessage<Collection<Exam>> getAllExamsOf(Student stud) {
        if (stud == null) throw new NullPointerException();
        else return this.getAllExamsWithResultsOf(stud).map(examsWithResults -> examsWithResults.keySet());
        // holt Daten von getAllExamsWithResults, map auf entsprechenden keySet des Studenten
    }

    public OptionalWithMessage<Collection<Exam>> getAllExamsOf(int matriculation) {
        return this.getStudent(matriculation).flatMap(stud -> getAllExamsOf(stud));
        // flatmap, da getAllExamsOf(stud) auch ein OWM zurückgibt. Student via getStudent
    }

    public static OptionalWithMessage<ExamResult> getResultOf(Exam exam, Map<Exam, ExamResult> results) {
        if (exam == null || results == null) throw new NullPointerException();
        else return OptionalWithMessage.ofNullable(results.get(exam), "No result for this exam!");
        // gibt Ergebnis einer Exam als OWMVal zurück. Falls Exam nicht vorhanden, OWMMsg
    }

    public OptionalWithMessage<ExamResult> getResult(int matriculation, Exam exam) {
        return this.getStudent(matriculation).flatMap(stud -> getAllExamsWithResultsOf(stud).flatMap(examsMap -> getResultOf(exam, examsMap)));
        // getStudent(...) gibt OWM mit Student zurück
        // dann alle ExamsWithResults via getAllExamsWithResultsOf(...), gibt auch OWM zurück (daher flatMap)
        // jetzt noch Result auslesen via getResultOf(...), gibt auch OWM zurück (daher flatMap)
    }

    public OptionalWithMessage<String> getNameOf(int matriculation) {
        return this.getStudent(matriculation).map(stud -> stud.getName());
        // getStudent auslesen und aus Student Namen auslesen (falls getStudent kein OWMMsg zurückgibt).
        /*
        Hier kann am Anfang etwas schiefgehen, danach wird nur der eventuell vorhandene Studierende "modifiziert". Welche OptionalWithMessage-Methode eignet sich?
        Da getStudent definitiv ein OWM Objekt zurückgibt und stud.getName() ein String, wird die Map-Methode benötigt (reicht msg weiter (OWMMsg) oder führt Lamda aus (OWMVal))
         */
    }

    public boolean hasPassed(int matriculation, Exam exam) {
        return this.getResult(matriculation, exam).map(result -> result.isPassed()).orElse(false);
        // OWM aus getResult mappen mit isPassed (Attribut von result) falls result exisitert. Entweder Value auslesen (OWMVal) oder false zurückgeben (OWMMsg)
    }

    public Collection<Student> hasPassed(Collection<Integer> matriculations, Exam exam) {
        ArrayList<Student> resultStudents = new ArrayList<>();
        for (int matriculation : matriculations) {  // Iteration über matriculations, falls bestanden, in Liste aufnehmen
            if (this.hasPassed(matriculation, exam)) resultStudents.add(this.getStudent(matriculation).get());  // Nach Aufgabenstellung darf/muss hier .get verwendet werden
        }
        return resultStudents;

        /*
        Wenn man die Matrikelnummern filtert, sodass nur noch die verbleiben, die die Klausur bestehen, kann es dann sein, dass kein Student gefunden wird, wenn man nach der Matrikelnummer sucht?
        Nein, da man nur eine Klausur bestehen kann, wenn man existiert :).
        Durch die If-Abfrage, ob die Klausur bestanden wurde (nach Matrikelnummer), so kann nur true zurückgegeben werden, wenn der Student existiert. Per Definition aus der vorherigen Methode wird sonst false zurückgegeben.
        */
    }

    public static OptionalWithMessage<Double> getAvg(Collection<Integer> nums) {
        if (nums.isEmpty()) return OptionalWithMessage.ofMsg("Cannot calculate average of no values!");  // falls nums leer ist OWMMsg zurückgeben
        else return OptionalWithMessage.of((double)nums.stream().mapToInt(i -> i).sum() / nums.size());  // Ansonsten existiert definitiv ein Avg, daher .of(...)
    }

    public OptionalWithMessage<Double> getAvgAge(List<Integer> matriculations) {
        ArrayList<OptionalWithMessage<Integer>> studs = new ArrayList<>();
        for (int matriculation : matriculations) {
            studs.add(this.getStudent(matriculation).map(stud -> stud.getAge()));
            // OWM Elemente mit jeweiligem Alter abspeichern (OWMVal, falls alles klappt. Bei falscher Eingabe OWMMsg)
        }
        return OptionalWithMessage.sequence(studs).flatMap(values -> getAvg(values));
        // Ist Liste leer: OWMMsg mit Fehlermeldung aus getAvg(...), ansonsten Fehlermeldungen aus getStudent(...). Klappt alles OWMVal mit Avg zurückgeben
    }
}
