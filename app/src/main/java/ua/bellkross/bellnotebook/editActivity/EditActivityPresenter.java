package ua.bellkross.bellnotebook.editActivity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import ua.bellkross.bellnotebook.R;
import ua.bellkross.bellnotebook.contactslist.ContactsListActivity;
import ua.bellkross.bellnotebook.contactslist.RecyclerAdapter;
import ua.bellkross.bellnotebook.informationActivity.InformationActivity;
import ua.bellkross.bellnotebook.model.Contact;

import static ua.bellkross.bellnotebook.model.Constants.ID_TAG;
import static ua.bellkross.bellnotebook.model.Constants.POSITION_IN_LIST_TAG;

public class EditActivityPresenter implements View.OnClickListener {

    private static final int GALLERY_REQUEST = 1;
    private int positionInDatabase;
    private int positionInList;
    private Activity activity;
    private Contact contact;
    private ImageView ivPhoto;
    private EditText etName;
    private EditText etSurname;
    private EditText etNumber;
    private EditText etBirthday;
    private EditText etAdditionalInformation;
    private Button btnOk;
    private Button btnAdd;
    private Button btnBack;
    private boolean add;

    public EditActivityPresenter(Activity activity, Contact contact, ImageView ivPhoto,
                                 EditText etName, EditText etSurname, EditText etNumber,
                                 EditText etBirthday, EditText etAdditionalInformation,
                                 Button btnOk, Button btnAdd, Button btnBack, boolean add) {
        this.activity = activity;
        this.positionInDatabase = activity.getIntent().getIntExtra(ID_TAG,-1);
        this.positionInList = activity.getIntent().getIntExtra(POSITION_IN_LIST_TAG,-1);
        this.contact = contact;
        this.ivPhoto = ivPhoto;
        this.etName = etName;
        this.etSurname = etSurname;
        this.etNumber = etNumber;
        this.etBirthday = etBirthday;
        this.etAdditionalInformation = etAdditionalInformation;
        this.btnOk = btnOk;
        this.btnAdd = btnAdd;
        this.btnBack = btnBack;
        this.add = add;
        initializeViews();
    }

    private void initializeViews() {
        ivPhoto.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        if (!add&&!contact.getPhotoUri().equalsIgnoreCase("nouri"))
            ivPhoto.setImageURI(Uri.parse(contact.getPhotoUri()));
        else
            ivPhoto.setImageDrawable(activity.getDrawable(R.drawable.nophoto));
        etName.setText(contact.getName());
        etSurname.setText(contact.getSurname());
        etNumber.setText(contact.getNumber());
        etBirthday.setText(contact.getBirthday());
        etAdditionalInformation.setText(contact.getAdditionalInformation());
        if (add) {
            btnAdd.setVisibility(View.VISIBLE);
            btnOk.setVisibility(View.INVISIBLE);
        } else {
            btnAdd.setVisibility(View.INVISIBLE);
            btnOk.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPhoto:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                activity.startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                break;
            case R.id.btnAdd:
                contact.setName(etName.getText().toString());
                contact.setSurname(etSurname.getText().toString());
                contact.setNumber(etNumber.getText().toString());
                contact.setBirthday(etBirthday.getText().toString());
                contact.setAdditionalInformation(etAdditionalInformation.getText().toString());

                RecyclerAdapter.getRecyclerAdapter().add(contact);

                Intent intentAdd = new Intent(activity, ContactsListActivity.class);
                intentAdd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intentAdd);
                break;
            case R.id.btnBack:
                if (add) {
                    Intent intent = new Intent(activity, ContactsListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                } else {
                    Intent intent = new Intent(activity, InformationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(POSITION_IN_LIST_TAG, positionInList);
                    intent.putExtra(ID_TAG, positionInDatabase);
                    activity.startActivity(intent);
                }
                break;
            case R.id.btnOk:
                contact.setName(etName.getText().toString());
                contact.setSurname(etSurname.getText().toString());
                contact.setNumber(etNumber.getText().toString());
                contact.setBirthday(etBirthday.getText().toString());
                contact.setAdditionalInformation(etAdditionalInformation.getText().toString());

                RecyclerAdapter.getRecyclerAdapter().update(contact, positionInList, positionInDatabase);

                Intent intent = new Intent(activity, InformationActivity.class);
                intent.putExtra(POSITION_IN_LIST_TAG, positionInList);
                intent.putExtra(ID_TAG, positionInDatabase);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                break;
        }
    }

    public Contact getContact() {
        return contact;
    }

    public ImageView getIvPhoto() {
        return ivPhoto;
    }
}
