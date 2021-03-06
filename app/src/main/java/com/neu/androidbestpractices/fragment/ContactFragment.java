package com.neu.androidbestpractices.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.neu.androidbestpractices.R;
import com.neu.androidbestpractices.fragment.base.BaseFragment;
import com.neu.contact.ContactFactory;
import com.neu.contact.contact.Contact;
import com.neu.contact.contact.ContactCallback;
import com.neu.contact.contact.PermissionResultCallback;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by neu on 16/2/3.
 */
public class ContactFragment extends BaseFragment{
    @Bind(R.id.contact)
    Button mContactButton;
    @Bind(R.id.text)
    TextView mText;
    @Bind(R.id.contactUI)
    Button mContactUI;
    @OnClick({R.id.contact,R.id.contactUI})
    void testContactModule(Button button){
        switch (button.getId()){
            case R.id.contact:

                mContact.getContacts();
                break;
            case R.id.contactUI:

                mContact.getContactsUI();
                break;
        }
    }

    private Contact mContact;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_contact;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mContact = ContactFactory.newContact(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mContact.onActivityResult(requestCode, resultCode, data, new ContactCallback() {
            @Override
            public void onSuccess(@NonNull String contactNumber, @NonNull String contactName) {
                mText.setText(contactName + contactNumber);
            }

            @Override
            public void onFailed(@NonNull int errCode, @NonNull String message) {
                mText.setText(errCode + message);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mContact.onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionResultCallback() {

            @Override
            public void denyPermission() {
                mText.setText("用户已经拒绝");
            }
        });
    }
}
