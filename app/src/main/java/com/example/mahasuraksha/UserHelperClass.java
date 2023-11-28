package com.example.mahasuraksha;

public class UserHelperClass {

    String PersonName, Phone, Address,TextLatLong,Complaint;

    public UserHelperClass() {
    }

    public UserHelperClass(String personName, String phone, String address,String textLatLong,String complaint) {
        PersonName = personName;
        Address = address;
        Phone = phone;
        TextLatLong = textLatLong;
        Complaint = complaint;

    }

    public String getTextLatLong() {
        return this.TextLatLong;
    }

    public void setTextLatLong(final String textLatLong) {
        this.TextLatLong = textLatLong;
    }

    public String getPersonName() {
        return this.PersonName;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(final String address) {
        this.Address = address;
    }

    public String getComplaint() {
        return this.Complaint;
    }

    public void setComplaint(final String complaint) {
        this.Complaint = complaint;
    }

    public String getPhone() {
        return this.Phone;
    }

    public void setPhone(final String phone) {
        this.Phone = phone;
    }

    public void setPersonName(final String personName) {
        this.PersonName = personName;
    }
}


