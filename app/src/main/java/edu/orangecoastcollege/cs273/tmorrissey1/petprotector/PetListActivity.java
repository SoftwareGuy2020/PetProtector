package edu.orangecoastcollege.cs273.tmorrissey1.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for PetProtector
 */
public class PetListActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 100;
    private ImageView petImageView;

    private PetListAdapter petListAdapter;
    private ListView petListView;
    private DBHelper db;
    private List<Pet> petList;
    // store the uri to whatever image was selected. default is none.png
    private Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petImageView = (ImageView) findViewById(R.id.petImageView);
        imageURI = getUriToResource(this, R.drawable.none);
        petImageView.setImageURI(imageURI);

        db = new DBHelper(this);
        petList = db.getAllPets();

        petListAdapter = new PetListAdapter(this, R.layout.pet_list_item, petList);
        petListView = (ListView) findViewById(R.id.petListView);
        petListView.setAdapter(petListAdapter);
    }

    /**
     * Starts PetDetailsActivity with the info of the pet that was selected
     * @param view the view that was clicked
     */
    public void viewPetDetails(View view) {
        Intent petDetailsIntent = new Intent(this, PetDetailsActivity.class);
        Pet pet = (Pet) view.getTag();

        petDetailsIntent.putExtra("Name", pet.getName());
        petDetailsIntent.putExtra("Details", pet.getDetails());
        petDetailsIntent.putExtra("Phone", pet.getPhone());
        petDetailsIntent.putExtra("Uri", pet.getImageURI().toString());

        startActivity(petDetailsIntent);
    }

    /**
     * Adds pet to database, ListView, and ArrayList
     * @param view the view
     */
    public void addPet(View view) {
        EditText petNameEditText = (EditText) findViewById(R.id.petNameEditText);
        EditText petDetailsEditText = (EditText) findViewById(R.id.petDetailsEditText);
        EditText petPhoneEditText = (EditText) findViewById(R.id.petPhoneEditText);

        if (petNameEditText.getText().length() > 0 && petDetailsEditText.getText().length() > 0 &&
                petPhoneEditText.getText().length() > 0) {
            Pet newPet = new Pet(petNameEditText.getText().toString(),
                    petDetailsEditText.getText().toString(), petPhoneEditText.getText().toString(),
                    imageURI);
            db.addPet(newPet);
            petList.add(newPet);
            petListAdapter.notifyDataSetChanged();

            petNameEditText.setText("");
            petDetailsEditText.setText("");
            petPhoneEditText.setText("");
            imageURI = getUriToResource(this, R.drawable.none);
            petImageView.setImageURI(imageURI);
        }
        else {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Gets Uri of specified resource
     * @param context context
     * @param resId id of resource
     * @return Uri of resource
     * @throws Resources.NotFoundException
     */
    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resId) throws Resources.NotFoundException {
        Resources res = context.getResources();
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
        "://" + res.getResourcePackageName(resId)
        + '/' + res.getResourceTypeName(resId)
        + '/' + res.getResourceEntryName(resId));
    }

    /**
     * Selects pet image from photo gallery
     * @param v the view
     */
    public void selectPetImage(View v) {
        // list of all permissions needed to request by user
        ArrayList<String> permList = new ArrayList<>();

        // start by seeing if we have permission to the camera

        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.CAMERA);

        int writeExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int readExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readExternalPermission != PackageManager.PERMISSION_GRANTED)
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permList.size() > 0) {
            String[] perms = new String[permList.size()];
            ActivityCompat.requestPermissions(this, permList.toArray(perms), RESULT_LOAD_IMAGE);
        }

        // if we have all 3 permissions, open image gallery
        if (cameraPermission == PackageManager.PERMISSION_GRANTED &&
                writeExternalPermission == PackageManager.PERMISSION_GRANTED
                && readExternalPermission == PackageManager.PERMISSION_GRANTED) {

            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }
    }

    /**
     * Dispatch incoming result to the correct fragment.
     * @param requestCode The integer request code originally supplied to startActivityForResult(), allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data  An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            imageURI = data.getData();
            petImageView.setImageURI(imageURI);
        }
    }
}
