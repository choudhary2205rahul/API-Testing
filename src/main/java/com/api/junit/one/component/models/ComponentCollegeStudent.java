package com.api.junit.one.component.models;


import org.springframework.stereotype.Component;

@Component
public class ComponentCollegeStudent implements ComponentStudent {
    private int id;
    private String firstname;
    private String lastname;
    private String emailAddress;

    private ComponentStudentGrades studentGrades;

    public ComponentCollegeStudent() {
    }

    public ComponentCollegeStudent(String firstname, String lastname, String emailAddress) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ComponentStudentGrades getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(ComponentStudentGrades studentGrades) {
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

    private String getFirstNameAndId() {
        return getFirstname() + " " + getId();
    }
}