package me.williamhester.model;

import me.williamhester.network.CourseCodes;

/**
 * Created by william on 2/13/16.
 */
public class AuburnPerson {

    private String role;
    private String curriculum;
    private String mailingAddress;
    private String phone;
    private String email;

    // Dean's List Fields
    private String lastName;
    private String firstName;
    private String middleName;
    private String suffix;
    private String degree;
    private String major;
    private String level;
    private String college;
    private String city;
    private String state;
    private String zipCode;
    private String county;
    // Special Fields
    private String id;
    private String majorDesc;

    public AuburnPerson() {

    }

    public AuburnPerson(String[] deanListInput) {
        lastName = deanListInput[0];
        firstName = deanListInput[1];
        middleName = deanListInput[2];
        suffix = deanListInput[3];
        degree = deanListInput[4];
        major = deanListInput[5];
        level = deanListInput[6];
        college = deanListInput[7];
        city = deanListInput[8];
        state = deanListInput[9];
        zipCode = deanListInput[10];
        county = deanListInput[11];

        id = makeId(firstName, lastName);
        majorDesc = CourseCodes.getDescription(major);
    }

    public static String makeId(String firstName, String lastName) {
        String[] fName = firstName.split(" ");
        String[] lName = lastName.split(" ");
        return (fName.length > 0 ? pad(fName[0]) : "ZZZZZZ") + "_" + (lName.length > 0 ? pad(lName[lName.length - 1]) : "ZZZZZZ");
    }

    public static String pad(String s) {
        String out = s.toUpperCase().substring(0, Math.min(s.length(), 6));
        for (int i = 0; i < 6 - s.length(); i++) {
            out += "Z";
        }
        return out;
    }

    public String getSummary() {
        String out = "NAME: " + firstName + " " + lastName + "\tMAJOR: " + majorDesc + "\tHOMETOWN: " + city + ", "
                + state + " (" + zipCode + ")";
        return out;
    }

    public String printDBInfo() {
        return id + "|" + lastName + "|" + firstName + "|" + middleName + "|" + suffix + "|" + degree + "|" + major
                + "|" + level + "|" + college + "|" + city + "|" + state + "|" + zipCode + "|" + county;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajorDesc() {
        return majorDesc;
    }

    public void setMajorDesc(String majorDesc) {
        this.majorDesc = majorDesc;
    }

    @Override
    public String toString() {
        return "Role: " + role + "\n" +
                "Curriculum: " + curriculum + "\n" +
                "MailingAddress: " + mailingAddress + "\n" +
                "Phone: " + phone + "\n" +
                "Email: " + email + "\n";
    }
}
