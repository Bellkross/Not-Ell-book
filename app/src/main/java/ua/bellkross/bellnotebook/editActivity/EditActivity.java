package ua.bellkross.bellnotebook.editActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import ua.bellkross.bellnotebook.R;
import ua.bellkross.bellnotebook.model.ArrayListOfContacts;
import ua.bellkross.bellnotebook.model.Constants;
import ua.bellkross.bellnotebook.model.Contact;

import static ua.bellkross.bellnotebook.model.Constants.ADD_TAG;

public class EditActivity extends AppCompatActivity{

    private EditActivityPresenter editActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        Contact contact;
        if(!intent.getBooleanExtra(ADD_TAG,false)) {
            contact = ArrayListOfContacts.getArrayList().
                    get(intent.getIntExtra(Constants.POSITION_IN_LIST_TAG, -1));
        }else{
            contact = new Contact();
        }

        editActivityPresenter = new EditActivityPresenter(this, contact,
                (ImageView) findViewById(R.id.ivPhoto), (EditText) findViewById(R.id.etName),
                (EditText) findViewById(R.id.etSurname), (EditText) findViewById(R.id.etNumber),
                (EditText) findViewById(R.id.etBirthday), (EditText) findViewById(R.id.etAdditionalInf),
                (Button) findViewById(R.id.btnOk), (Button) findViewById(R.id.btnAdd),
                (Button) findViewById(R.id.btnBack), getIntent().getBooleanExtra(ADD_TAG,false));
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

}