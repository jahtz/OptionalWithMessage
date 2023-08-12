package de.uniwue.jpp.exams;

import java.util.Objects;

public class Student {
    private final String name;
    private final int matriculationNumber;
    private final int age;

    public Student(String name, int matriculation, int age) {
        if (name == null)  throw new NullPointerException();
        else if (name.isEmpty()) throw new IllegalArgumentException("name cannot be empty!");
        else {
            this.name = name;
            this.matriculationNumber = matriculation;
            this.age = age;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getMatriculation() {
        return this.matriculationNumber;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return this.matriculationNumber == student.matriculationNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.matriculationNumber);
    }

    @Override
    public String toString() {
        return String.format("Student \"%s\"", this.name);
    }
}
