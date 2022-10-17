package seedu.address.model.person.position;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.person.Assignment;

/**
 * Represents the Student position in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class Student extends Position {

    public static final String ATTENDANCE_CONSTRAINTS =
            "Attendance should be in the format [number]/[number], where the first number is greater "
                    + "or equal to the second number (max 999).";

    public static final String ATTENDANCE_VALIDATION_REGEX = "\\d{1,3}" + "/" + "\\d{1,3}";

    public static final String GRADE_CONSTRAINTS =
            "Grade should be in the format [number]/[number], where the first number is greater "
                    + "or equal to the second number (max 99999).";

    public static final String ASSIGNMENT_CONSTRAINTS =
            "Incorrect Assignments";

    public static final String GRADE_VALIDATION_REGEX = "\\d{1,5}" + "/" + "\\d{1,5}";

    private String attendance;

    private String grade;

    private ArrayList<Assignment> assignmentsList;

    /**
     * Creates a student and initialises their attendance to 0/0.
     */
    public Student() {
        super("Student");
        this.attendance = "0/0";
        this.grade = "0/0";
        this.assignmentsList = new ArrayList<>();

    }

    public void setAttendance(String attendance) {
        requireNonNull(attendance);
        this.attendance = attendance;
    }

    /**
     * Returns true if a given string is a valid attendance.
     */
    public static boolean isValidAttendance(String test) {
        if (!test.matches(ATTENDANCE_VALIDATION_REGEX)) {
            return false;
        } else {
            String[] split = test.split("/");
            return Integer.parseInt(split[0]) <= Integer.parseInt(split[1]);
        }
    }

    /**
     * Returns true if a given string is a valid string of Assignments
     */
    public static boolean isValidAssignments(String test) {
        String[] splitStr = test.split(", ");
        int len = splitStr.length;
        int totalWeightage = 0;

        for (int i = 0; i < len; i++) {
            String[] weightageStr = splitStr[i].split("w/");
            if (weightageStr.length != 2) {
                return false;
            }
            int weightage = Integer.parseInt(weightageStr[1]);
            if (weightage < 0) {
                return false;
            }
            totalWeightage += weightage;
        }

        return totalWeightage == 100;
    }

    public void setGrade(String grade) {
        requireNonNull(grade);
        this.grade = grade;
    }
    @Override
    public void setDetails(String details) {
        String[] gradeAndAttendance = isolateDetails(details);
        String grade = gradeAndAttendance[0];
        String attendance = gradeAndAttendance[1];
        String assignments = gradeAndAttendance[2];
        setGrade(grade);
        setAttendance(attendance);
        setPreviousAssignments(assignments);
    }

    /**
     * method used to isolate the grade and attendance values in its details
     * @param details A valid detail.
     * @return a String[] with its first element being the grade and the 2nd element the attendance
     */
    public String[] isolateDetails(String details) {
        String[] gradeAttendanceAssignments = new String[3];
        String[] splitDetails = details.split(", grade - ");
        String[] splitDetails2 = splitDetails[0].split("attendance - ");
        String[] splitDetails3 = splitDetails[1].split(" Assignments: ");
        gradeAttendanceAssignments[0] = splitDetails3[0];
        gradeAttendanceAssignments[1] = splitDetails2[1];
        gradeAttendanceAssignments[2] = splitDetails3[1];
        return gradeAttendanceAssignments;
    }

    /**
     * Returns true if a given string is a valid grade.
     */
    public static boolean isValidGrade(String test) {
        if (!test.matches(GRADE_VALIDATION_REGEX)) {
            return false;
        } else {
            String[] split = test.split("/");
            return Integer.parseInt(split[0]) <= Integer.parseInt(split[1]);
        }
    }


    public void setAssignments(String assignments) {
        String[] splitStr = assignments.split(", ");
        int len = splitStr.length;
        if (assignmentsList.size() > 0) {
            assignmentsList = new ArrayList<>();
        }
        for (int i = 0; i < len; i++) {
            String[] weightageStr = splitStr[i].split(" w/");
            String name = weightageStr[0];
            String weightage = weightageStr[1];
            Assignment a = new Assignment(name, weightage);
            addAssignments(a);
        }
    }

    public void setPreviousAssignments(String assignments) {
        String trimmedAssignments = trimAssignments(assignments);

        String[] assignmentsArr = trimmedAssignments.split(", ");
        int assignmentArrLen = assignmentsArr.length;

        for (int i = 0; i < assignmentArrLen; i++) {
            String curr = assignmentsArr[i];
            String[] splitStr = curr.split(" Score: ");
            String[] splitStr2 = splitStr[1].split(" Weightage: ");
            String name = splitStr[0];
            String grade = splitStr2[0];
            String weightage = splitStr2[1].replace("%", "");
            Assignment a = new Assignment(name, grade, weightage);
            addAssignments(a);
        }
    }
    /**
     * Returns true if a given string is a valid grade.
     */
    public String trimAssignments(String assignments) {
        String trimmedAssignments = assignments.replace("[", "")
                .replace("]", "")
                .replace("(", "")
                .replace(")", "");

        return trimmedAssignments;
    }

    public void addAssignments(Assignment assignment) {
        this.assignmentsList.add(assignment);
    }

    @Override
    public String toString() {
        return "Student: attendance - " + attendance + ", grade - " + grade
                + "\nAssignments: " + assignmentsList.toString();
    }

    @Override
    public boolean equals(Object other) {
        return true;
    }

    @Override
    public int hashcode() {
        return 0;
    }

    @Override
    public String getDetails() {
        return "attendance - " + attendance + ", grade - " + grade + " Assignments: " + assignmentsList;

    }

}
