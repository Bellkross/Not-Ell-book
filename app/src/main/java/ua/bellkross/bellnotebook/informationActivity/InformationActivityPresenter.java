package ua.bellkross.bellnotebook.informationActivity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.jar.Attributes;

import ua.bellkross.bellnotebook.R;
import ua.bellkross.bellnotebook.contactslist.ContactsListActivity;
import ua.bellkross.bellnotebook.contactslist.RecyclerAdapter;
import ua.bellkross.bellnotebook.editActivity.EditActivity;
import ua.bellkross.bellnotebook.model.ArrayListOfContacts;
import ua.bellkross.bellnotebook.model.Constants;
import ua.bellkross.bellnotebook.model.Contact;

import static ua.bellkross.bellnotebook.model.Constants.ID_TAG;
import static ua.bellkross.bellnotebook.model.Constants.LOG_TAG;
import static ua.bellkross.bellnotebook.model.Constants.POSITION_IN_LIST_TAG;

public class InformationActivityPresenter {

    private Activity activity;
    private ImageView ivPhoto;
    private TextView tvName, tvSurname;
    private ListView lvContactInformation;
    private ListView lvContactActions;
    private View.OnClickListener onClickShare;
    private View.OnClickListener onClickEdit;
    private View.OnClickListener onClickRemove;
    private int positionInList;
    private int positionInDatabase;
    private String uri,name,surname,number,birthday, additionalInformation;

    public InformationActivityPresenter(Activity activity) {
        this.activity = activity;

        positionInList = activity.getIntent().getIntExtra(POSITION_IN_LIST_TAG, -1);
        positionInDatabase = activity.getIntent().getIntExtra(ID_TAG, -1);
        Log.d(LOG_TAG, "pos in list = " + positionInList + " pos in db = " + positionInDatabase);

        Contact contact = ArrayListOfContacts.getArrayList().get(positionInList);
        uri = contact.getPhotoUri();
        name = contact.getName();
        surname = contact.getSurname();
        number = contact.getNumber();
        birthday = contact.getBirthday();
        additionalInformation = contact.getAdditionalInformation();

        upload();
        initializeListViews();

    }

    private void upload(){
        ivPhoto = (ImageView) activity.findViewById(R.id.ivInformationPhoto);
        tvName = (TextView) activity.findViewById(R.id.tvName);
        tvSurname = (TextView) activity.findViewById(R.id.tvSurname);

        if(!uri.equals(Constants.EMPTY_URI))
            ivPhoto.setImageURI(Uri.parse(uri));
        else
            ivPhoto.setImageDrawable(activity.getDrawable(R.drawable.nophoto));
        tvName.setText(name);
        tvSurname.setText(surname);
    }

    private void initializeListViews(){
        initializeOnClickListeners();
        lvContactInformation = (ListView) activity.findViewById(R.id.lvContanctInformation);
        lvContactActions = (ListView) activity.findViewById(R.id.lvContactAction);

        final ArrayList<String> actions = new ArrayList<>();
        actions.add("share");
        actions.add("edit");
        actions.add("remove");
        lvContactActions.setAdapter(new ArrayAdapter<String>(activity.getApplicationContext(),
                android.R.layout.simple_list_item_1,actions){
            @NonNull
            @Override
            public View getView(int positionInList, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(positionInList, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setTextColor(Color.BLACK);
                text1.setText(actions.get(positionInList));
                text1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                switch (positionInList){
                    case 0:
                        view.setOnClickListener(onClickShare);
                        break;
                    case 1:
                        view.setOnClickListener(onClickEdit);
                        break;
                    case 2:
                        view.setOnClickListener(onClickRemove);
                        break;
                }

                return view;
            }
        });
        final ArrayList<String> information = new ArrayList<>();

        information.add(number);
        information.add(birthday);
        information.add(additionalInformation);
        final ArrayList<String> informationType = new ArrayList<>();
        informationType.add("Number");
        informationType.add("Birthday");
        informationType.add("Additional information");
        lvContactInformation.setAdapter(new ArrayAdapter<String>(activity.getApplicationContext(),
                android.R.layout.simple_list_item_2, android.R.id.text1, information){
            @NonNull
            @Override
            public View getView(int positionInList, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(positionInList, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setTextColor(Color.BLACK);
                text1.setText(information.get(positionInList));
                text2.setTextColor(Color.WHITE);
                text2.setText(informationType.get(positionInList));
                return view;
            }
        });
    }

    private void initializeOnClickListeners(){
        onClickShare = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = name + " " + surname + '\n' +
                        "number: " + number + '\n' +
                        "birthday: " + birthday;
                intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                activity.startActivity(Intent.createChooser(intent, "Bell sharing"));
            }
        };

        onClickEdit = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), EditActivity.class);
                intent.putExtra(POSITION_IN_LIST_TAG, positionInList);
                intent.putExtra(ID_TAG, positionInDatabase);
                intent.putExtra(Constants.ADD_TAG, false);
                activity.startActivity(intent);
            }
        };

        onClickRemove = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), ContactsListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                RecyclerAdapter.getRecyclerAdapter().remove(positionInList,positionInDatabase);
                activity.startActivity(intent);
            }
        };
    }

}