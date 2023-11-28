package com.example.mahasuraksha;

public class UploadImageInfo { String textView4,Complaint;

    public UploadImageInfo( String textView4,String complaint) {
        this.textView4 = textView4;
        this.Complaint  = complaint;
    }

    public String getComplaint() {
        return this.Complaint;
    }

    public void setComplaint(final String complaint) {
        this.Complaint = complaint;
    }

    public String getTextView4() {
        return this.textView4;
    }

    public void setTextView4(final String textView4) {
        this.textView4 = textView4;
    }
}
