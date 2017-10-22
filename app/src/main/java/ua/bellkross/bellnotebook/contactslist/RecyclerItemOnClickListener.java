package ua.bellkross.bellnotebook.contactslist;


import android.app.Activity;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;
import android.view.View;

import ua.bellkross.bellnotebook.informationActivity.InformationActivity;
import ua.bellkross.bellnotebook.model.Constants;

import static ua.bellkross.bellnotebook.model.Constants.LOG_TAG;

public class RecyclerItemOnClickListener {

    private static View.OnClickListener onClickListener;
    private static Activity activity;

    private RecyclerItemOnClickListener(){
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(activity.getApplicationContext(), InformationActivity.class);
//                intent.putExtra(Constants.POSITION_IN_LIST_TAG, posDB);
//                intent.putExtra(Constants.ID_TAG, posList);
//                activity.startActivity(intent);
            }
        };
    }

    public static void setOnClickListener(View.OnClickListener onClickListener) {
        RecyclerItemOnClickListener.onClickListener = onClickListener;
    }

    public static View.OnClickListener getOnClickListener(){
        if(onClickListener == null){
            new RecyclerItemOnClickListener();
            return onClickListener;
        }else
            return onClickListener;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        RecyclerItemOnClickListener.activity = activity;
    }
}
