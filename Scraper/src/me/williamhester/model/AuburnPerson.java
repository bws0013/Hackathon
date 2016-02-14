package me.williamhester.model;

import me.williamhester.network.CourseCodes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by william on 2/13/16.
 */
public class AuburnPerson {

    private String role;
    private String curriculum;
    private String mailingAddress;
    private String phone;
    private String email;
    private String name;
    private String organizations;

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

    public AuburnPerson(ResultSet set) throws SQLException {
        id = set.getString(1);
        name = set.getString(2);
        phone = set.getString(3);
        email = set.getString(4);
        mailingAddress = set.getString(5);
        city = set.getString(6);
        state = set.getString(7);
        county = set.getString(8);
        zipCode = set.getString(9);
        major = set.getString(10);
        level = set.getString(11);
        degree = set.getString(12);
        organizations = set.getString(13);
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

    public String getName() {
        return name;
    }

    private String getGradString() {
        switch (level) {
            case "UG":
            case "U2":
                return "an undergraduate";
            case "GR":
                return "a graduate";
        }
        return "an undergraduate";
    }

    private String getDegreeString() {
        if (degree.startsWith("B")) {
            return "Bachelor's";
        }
        if (degree.startsWith("M")) {
            return "Master's";
        }
        return "Bachelor's";
    }

    private String getOrganizationsString() {
        Scanner s = new Scanner(organizations).useDelimiter(",");
        StringBuilder sb = new StringBuilder();
        ArrayList<String> strings = new ArrayList<>();
        while (s.hasNext()) {
            strings.add(Organizations.getOrg(s.next()));
        }
        for (int i = 0; i < strings.size() - 2; i++) {
            sb.append(strings.get(i)).append(", ");
        }
        if (strings.size() > 1) {
            sb.append(strings.get(strings.size() - 2)).append(" and ");
        }
        return sb.append(strings.get(strings.size() - 1)).toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        boolean first = true;
        if (phone != null) {
            sb.append("'s phone number is ").append(phone).append(". ");
            first = false;
        }
        if (email != null) {
            if (first) {
                sb.append("'s ");
            } else {
                sb.append("Their ");
            }
            first = false;
            sb.append("email address is ").append(email).append(". ");
        }
        if (mailingAddress != null) {
            if (first) {
                sb.append("'s ");
            } else {
                sb.append("Their ");
            }
            first = false;
            sb.append("mailing address address is ").append(mailingAddress).append(". ");
        }
        if (city != null) {
            if (first) {
                sb.append(" ");
            } else {
                sb.append(name.substring(0, name.indexOf(' '))).append(" ");
            }
            first = false;
            sb.append("is from ").append(city).append(", ").append(state).append(" in ").append(county)
                    .append(" County, ").append(zipCode).append(". ");
        }
        if (major != null) {
            if (first) {
                sb.append(" ");
            } else {
                sb.append(name.substring(0, name.indexOf(' '))).append(" ");
            }
            first = false;
            sb.append("is currently enrolled as ").append(getGradString())
                    .append(" and is pursuing a ").append(getDegreeString()).append(" Degree")
                    .append(" in ").append(CourseCodes.getDescription(major))
                    .append(". ");
        }
        if (organizations != null) {
            if (first) {
                sb.append(" ");
            } else {
                sb.append(name.substring(0, name.indexOf(' '))).append(" ");
            }
            sb.append("is involved in ").append(getOrganizationsString()).append(".");
        }
        return sb.toString().trim();
    }
}
