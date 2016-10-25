package edu.orangecoastcollege.cs273.tmorrissey1.petprotector;

import android.net.Uri;

/**
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


}
