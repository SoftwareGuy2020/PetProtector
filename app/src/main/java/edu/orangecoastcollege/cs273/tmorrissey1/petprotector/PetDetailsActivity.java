package edu.orangecoastcollege.cs273.tmorrissey1.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Controller for activity_pet_details.xml
 */
public class PetDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        ImageView petDetailsImageView = (ImageView) findViewById(R.id.petDetailsImageView);
        TextView petNameDetailsTextView = (TextView) findViewById(R.id.petNameDetailsTextView);
        TextView petDetailsDetailsTextView = (TextView) findViewById(R.id.petDetailsDetailsTextView);
        TextView petPhoneDetailsTextView = (TextView) findViewById(R.id.petPhoneDetailsTextView);

        Intent intent = getIntent();

        petDetailsImageView.setImageURI(Uri.parse(intent.getStringExtra("Uri")));
        petNameDetailsTextView.setText(intent.getStringExtra("Name"));
        petDetailsDetailsTextView.setText(intent.getStringExtra("Details"));
        petPhoneDetailsTextView.setText(intent.getStringExtra("Phone"));
    }
}
