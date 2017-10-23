package ua.bellkross.bellnotebook.contactslist;

import android.content.Intent;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;

import ua.bellkross.bellnotebook.R;
import ua.bellkross.bellnotebook.database.DBHelper;
import ua.bellkross.bellnotebook.editActivity.EditActivity;
import ua.bellkross.bellnotebook.informationActivity.InformationActivity;
import ua.bellkross.bellnotebook.model.ArrayListOfContacts;
import ua.bellkross.bellnotebook.model.Constants;
import ua.bellkross.bellnotebook.model.Contact;

import static ua.bellkross.bellnotebook.model.Constants.ADD_TAG;
import static ua.bellkross.bellnotebook.model.Constants.EMPTY_URI;
import static ua.bellkross.bellnotebook.model.Constants.LOG_TAG;
import static ua.bellkross.bellnotebook.model.Constants.NEW_ELEMENT;

public class ContactsListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        initialize();
        if(!correctPositionInList(ArrayListOfContacts.getArrayList())){
            Log.d(LOG_TAG,"HYINYA S INDEXAMI");
        }

        RecyclerAdapter.getRecyclerAdapter().sort();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG,"onResume");
        DBHelper.getDBHelper().sortContacts();
        RecyclerAdapter.getRecyclerAdapter().notifyItemRangeInserted(0, ArrayListOfContacts.getArrayList().size());
    }

    private void initialize(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_contacts_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DBHelper.getDBHelper(this);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.rvContacts);
        recyclerView.setLayoutManager(linearLayoutManager);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
        recyclerView.setLayoutAnimation(animation);

        recyclerView.setAdapter(RecyclerAdapter.getRecyclerAdapter());

        RecyclerAdapter.getRecyclerAdapter().setOnClickItemListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int recyclerViewPosition = recyclerView.getChildAdapterPosition(v);
                Log.d(LOG_TAG, "RecyclerView position = " + recyclerViewPosition);
                Log.d(LOG_TAG, "RecyclerView position = " + ArrayListOfContacts.getArrayList()
                        .get(recyclerViewPosition).getPositionInList());
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                intent.putExtra(Constants.POSITION_IN_LIST_TAG,recyclerViewPosition);
                intent.putExtra(Constants.ID_TAG,ArrayListOfContacts.getArrayList()
                        .get(recyclerViewPosition).getPositionInDB());
                startActivity(intent);
            }
        });


    }

    private boolean correctPositionInList(ArrayList<Contact> arrayList){
        boolean result = true;

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getPositionInList() != i) {
                result = false;
            }
        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_add:
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(ADD_TAG, true);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return super.onCreateOptionsMenu(menu);
    }
}