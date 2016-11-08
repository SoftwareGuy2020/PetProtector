package edu.orangecoastcollege.cs273.tmorrissey1.petprotector;

import android.net.Uri;

/**
 * Class for representing a Pet.
 * Created by tmorrissey1 on 10/25/2016.
 */

public class Pet {
    private int mId;
    private String mName;
    private String mDetails;
    private String mPhone;
    private Uri mImageURI;

    public Pet(String mName, String mDetails, String mPhone, Uri mImageURI) {
        this.mId = 0;
        this.mName = mName;
        this.mDetails = mDetails;
        this.mPhone = mPhone;
        this.mImageURI = mImageURI;
    }

    public Pet(int mId, String mName, String mDetails, String mPhone, Uri mImageURI) {
        this.mId = mId;
        this.mName = mName;
        this.mDetails = mDetails;
        this.mPhone = mPhone;
        this.mImageURI = mImageURI;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public Uri getImageURI() {
        return mImageURI;
    }

    public void setImageURI(Uri imageURI) {
        mImageURI = imageURI;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mDetails='" + mDetails + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mImageURI=" + mImageURI +
                '}';
    }
}
