package me.williamhester.model;

/**
 * Created by william on 2/13/16.
 */
public class AuburnPerson {

    private String role;
    private String curriculum;
    private String mailingAddress;
    private String phone;
    private String email;

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

    @Override
    public String toString() {
        return "Role: " + role + "\n" +
                "Curriculum: " + curriculum + "\n" +
                "MailingAddress: " + mailingAddress + "\n" +
                "Phone: " + phone + "\n" +
                "Email: " + email + "\n";
    }
}
