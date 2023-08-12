package de.uniwue.jpp.exams;


public class ExamResult {
    private final int maxPoints;
    private final int points;
    private final int requiredPoints;

    public ExamResult(int maxPoints, int points, int requiredPoints) {
        if (points < 0 || maxPoints < 0 || requiredPoints < 0) throw new IllegalArgumentException("parameters cannot be negative");
        else if (points > maxPoints) throw new IllegalArgumentException("points cannot be greater than maxPoints!");
        else if (requiredPoints > maxPoints) throw new IllegalArgumentException("requiredPoints cannot be greater than maxPoints!");
        else {
            this.maxPoints = maxPoints;
            this.points = points;
            this.requiredPoints = requiredPoints;
        }
    }

    public int getMaxPoints() {
        return this.maxPoints;
    }

    public int getPoints() {
        return this.points;
    }

    public int getRequiredPoints() {
        return this.requiredPoints;
    }

    public boolean isPassed() {
        return points >= requiredPoints;
    }

    @Override
    public String toString() {
        return String.format("ExamResult %d/%d points (%s)", this.points, this.maxPoints, this.isPassed() ? "passed" : "not passed");
    }
}
