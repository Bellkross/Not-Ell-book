package ua.bellkross.bellnotebook.editActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import ua.bellkross.bellnotebook.R;
import ua.bellkross.bellnotebook.contactslist.ContactsListActivity;
import ua.bellkross.bellnotebook.informationActivity.InformationActivity;
import ua.bellkross.bellnotebook.model.ArrayListOfContacts;
import ua.bellkross.bellnotebook.model.Constants;
import ua.bellkross.bellnotebook.model.Contact;

import static ua.bellkross.bellnotebook.model.Constants.ADD_TAG;
import static ua.bellkross.bellnotebook.model.Constants.ID_TAG;
import static ua.bellkross.bellnotebook.model.Constants.NEW_ELEMENT;
import static ua.bellkross.bellnotebook.model.Constants.POSITION_IN_LIST_TAG;

public class EditActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private EditActivityPresenter editActivityPresenter;
    private boolean add;
    private int positionInList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initToolbar();
        Intent intent = getIntent();
        add = intent.getBooleanExtra(ADD_TAG,false);
        positionInList = intent.getIntExtra(Constants.POSITION_IN_LIST_TAG, NEW_ELEMENT);
        Contact contact;
        if(!add) {
            contact = ArrayListOfContacts.getArrayList().
                    get(positionInList);
        }else{
            contact = new Contact();
        }

        editActivityPresenter = new EditActivityPresenter(this, contact,
                (ImageView) findViewById(R.id.ivPhoto), (EditText) findViewById(R.id.etName),
                (EditText) findViewById(R.id.etSurname), (EditText) findViewById(R.id.etNumber),
                (EditText) findViewById(R.id.etBirthday), (EditText) findViewById(R.id.etAdditionalInf),
                (Button) findViewById(R.id.btnOk), (Button) findViewById(R.id.btnAdd),
                getIntent().getBooleanExtra(ADD_TAG,false));
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_edit_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(imageReturnedIntent!=null) {
            String uri = imageReturnedIntent.getData().toString();
            editActivityPresenter.getContact().setPhotoUri(uri);
            editActivityPresenter.getIvPhoto().setImageURI(
                    Uri.parse(editActivityPresenter.getContact().getPhotoUri()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            if (add) {
                Intent intent = new Intent(this, ContactsListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
            } else {
                finishAndRemoveTask();
            }
        }

        return super.onOptionsItemSelected(item);
    }

}