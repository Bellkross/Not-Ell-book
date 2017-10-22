package ua.bellkross.bellnotebook.contactslist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.bellkross.bellnotebook.R;
import ua.bellkross.bellnotebook.database.DBHelper;
import ua.bellkross.bellnotebook.model.ArrayListOfContacts;
import ua.bellkross.bellnotebook.model.Contact;

import static ua.bellkross.bellnotebook.model.Constants.LOG_TAG;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private View.OnClickListener onClickItemListener;
    private static RecyclerAdapter recyclerAdapter;

    private RecyclerAdapter() {
        super();
        recyclerAdapter = this;
    }

    public void add(Contact contact){
        int posDB = DBHelper.getDBHelper().addDB(contact);
        contact.setPositionInDB(posDB);
        ArrayListOfContacts.getArrayList().add(contact);
        ArrayListOfContacts.sort();
        notifyItemRangeInserted(0, ArrayListOfContacts.getArrayList().size());
    }

    public void update(Contact contact, int posList, int posDB){
        DBHelper.getDBHelper().updateDB(""+posDB,contact);
        ArrayListOfContacts.getArrayList().set(posList,contact);
        ArrayListOfContacts.sort();
        notifyItemChanged(posList);
        notifyItemRangeChanged(0, ArrayListOfContacts.getArrayList().size());
        //notifyDataSetChanged();
    }

    public void remove(int posList, int posDB){
        DBHelper.getDBHelper().removeDB(""+posDB);
        ArrayListOfContacts.getArrayList().remove(posList);
        ArrayListOfContacts.sort();
        notifyItemRemoved(posList);
        notifyItemRangeInserted(0, ArrayListOfContacts.getArrayList().size());
//        notifyDataSetChanged();

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        view.setOnClickListener(onClickItemListener);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(ArrayListOfContacts.getArrayList().get(position));
    }

    @Override
    public int getItemCount() {
        return ArrayListOfContacts.getArrayList().size();
    }

    public static RecyclerAdapter getRecyclerAdapter(){
        if(recyclerAdapter == null){
            new RecyclerAdapter();
            return recyclerAdapter;
        }else
            return recyclerAdapter;
    }

    public void setOnClickItemListener(View.OnClickListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

}
