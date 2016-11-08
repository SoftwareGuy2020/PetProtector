package edu.orangecoastcollege.cs273.tmorrissey1.petprotector;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/** Adapter for PetListView
 * Created by Travis on 11/8/2016.
 */

public class PetListAdapter extends ArrayAdapter<Pet> {

    private Context mContext;
    private List<Pet> mPetList = new ArrayList<>();
    private int mResourceId;

    /**
     * Parametrized constructor
     * @param context The context
     * @param rId The resource id
     * @param pets the list of pets to be associated
     */
    public PetListAdapter(Context context, int rId, List<Pet> pets) {
        super(context, rId, pets);
        mContext = context;
        mResourceId = rId;
        mPetList = pets;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     * @param position Position of the item in the list that was selected
     * @param convertView the view
     * @param parent the parent of the view
     * @return the view
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Pet selectedPet = mPetList.get(position);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        LinearLayout petListLinearLayout = (LinearLayout) view.findViewById(R.id.petListLinearLayout);
        ImageView petListImageView = (ImageView) view.findViewById(R.id.petListImageView);
        TextView petListNameTextView = (TextView) view.findViewById(R.id.petListNameTextView);
        TextView petListDetailsTextView = (TextView) view.findViewById(R.id.petListDetailsTextView);

        petListLinearLayout.setTag(selectedPet);

        petListNameTextView.setText(selectedPet.getName());
        petListDetailsTextView.setText(selectedPet.getDetails());
        petListImageView.setImageURI(selectedPet.getImageURI());

        return view;
    }
}
