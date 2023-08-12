package de.uniwue.jpp.exams;

import java.util.Objects;

public class Exam {
    private final int year;
    private final boolean isWinterTerm;
    private final String name;
    public Exam(int year, boolean isWinterTerm, String name) {
        if (name == null) throw new NullPointerException();
        else if (name.isEmpty()) throw new IllegalArgumentException("name cannot be empty!");
        else {
            this.year = year;
            this.isWinterTerm = isWinterTerm;
            this.name = name;
        }
    }

    public int getYear() {
        return this.year;
    }

    public boolean isWinterTerm() {
        return this.isWinterTerm;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return this.year == exam.year && this.isWinterTerm == exam.isWinterTerm && this.name.equals(exam.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.year, this.isWinterTerm, this.name);
    }

    @Override
    public String toString() {
        return String.format("Exam \"%s\" %d %s", this.name, this.year, this.isWinterTerm ? "WS" : "SS");
    }
}
