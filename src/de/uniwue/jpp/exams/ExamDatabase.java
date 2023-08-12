package de.uniwue.jpp.exams;

import java.util.Collection;
import java.util.Map;

public class ExamDatabase {
    private final Map<Student, Map<Exam, ExamResult>> data;
    public ExamDatabase(Map<Student, Map<Exam, ExamResult>> data) {
        this.data = data;
    }

    public Student getStudent(int matriculation) {
        for (Student s : this.data.keySet()) {
            if (s.getMatriculation() == matriculation) return s;
        }
        return null;
    }

    public Map<Exam, ExamResult> getAllExamWithResultsOf(Student stud) {
        if (stud == null) throw new NullPointerException();
        if (!this.data.containsKey(stud)) return null;
        return this.data.get(stud);
    }

    public Collection<Exam> getAllExamsOf(Student stud) {
        if (stud == null) throw new NullPointerException();
        if (!this.data.containsKey(stud)) return null;
        return this.data.get(stud).keySet();
    }

    public Collection<Exam> getAllExamsOf(int matriculation) {
        Student stud = this.getStudent(matriculation);
        if (stud == null) return null;
        else return this.getAllExamsOf(stud);
    }

}
