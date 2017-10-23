package ua.bellkross.bellnotebook.contactslist;


import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ua.bellkross.bellnotebook.R;
import ua.bellkross.bellnotebook.model.Contact;

import static ua.bellkross.bellnotebook.model.Constants.EMPTY_URI;
import static ua.bellkross.bellnotebook.model.Constants.LOG_TAG;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivContactIcon;
    private TextView tvName;
    private TextView tvSurname;
    private TextView tvAbout;

    public RecyclerViewHolder(final View itemView) {
        super(itemView);

        ivContactIcon = (ImageView) itemView.findViewById(R.id.ivContactIcon);
        tvName = (TextView) itemView.findViewById(R.id.tvContactName);
        tvSurname = (TextView) itemView.findViewById(R.id.tvContactSurname);
        tvAbout = (TextView) itemView.findViewById(R.id.tvContactAbout);
    }

    public void bind(Contact contact) {
        if (!contact.getPhotoUri().equals(EMPTY_URI)) {
            ivContactIcon.setImageURI(Uri.parse(contact.getPhotoUri()));
        }
        tvName.setText(contact.getName());
        tvSurname.setText(contact.getSurname());
        tvAbout.setText(contact.getAdditionalInformation());
    }
}
