package com.swisscom.junit.one.models;

import org.springframework.stereotype.Component;

@Component
public class OldCollegeStudent implements Student {

    private String firstname;
    private String lastname;
    private String emailAddress;
    private OldStudentGrades studentGrades;

    public OldCollegeStudent() {
    }

    public OldCollegeStudent(String firstname, String lastname, String emailAddress) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public OldStudentGrades getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(OldStudentGrades studentGrades) {
        this.studentGrades = studentGrades;
    }

    @Override
    public String toString() {
        return "CollegeStudent{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", studentGrades=" + studentGrades +
                '}';
    }

    @Override
    public String studentInformation() {
        return getFullName() + " " + getEmailAddress();
    }

    @Override
    public String getFullName() {
        return getFirstname() + " " + getLastname();
    }
}
