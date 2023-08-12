package de.uniwue.jpp.exams;

import java.util.HashMap;
import java.util.Map;

public class ExamDatabaseUtil {

    private ExamDatabaseUtil() {}

    public static Map<Student, Map<Exam, ExamResult>> createSampleData() {
        HashMap<Student, Map<Exam, ExamResult>> exams = new HashMap<>();

        Exam pp = new Exam(2021, false, "Programmierpraktikum");
        Exam gdp = new Exam(2021, true, "Grundlagen der Programmierung");
        Exam swt = new Exam(2021, false, "Softwaretechnik");

        HashMap<Exam, ExamResult> s1exams = new HashMap<>();
        s1exams.put(pp, new ExamResult(90, 90, 45));
        s1exams.put(gdp, new ExamResult(60, 54, 30));
        s1exams.put(swt, new ExamResult(60, 59, 30));
        exams.put(new Student("Max Mustermann", 123456, 20), s1exams);

        HashMap<Exam, ExamResult> s2exams = new HashMap<>();
        s2exams.put(pp, new ExamResult(90, 37, 45));
        s2exams.put(gdp, new ExamResult(60, 33, 30));
        s2exams.put(swt, new ExamResult(60, 39, 30));
        exams.put(new Student("Erika Musterfrau", 123123, 23), s2exams);

        HashMap<Exam, ExamResult> s3exams = new HashMap<>();
        s3exams.put(pp, new ExamResult(90, 63, 45));
        s3exams.put(gdp, new ExamResult(60, 5, 30));
        s3exams.put(swt, new ExamResult(60, 51, 30));
        exams.put(new Student("Juan Nadie", 124578, 27), s3exams);

        return exams;
    }
}
