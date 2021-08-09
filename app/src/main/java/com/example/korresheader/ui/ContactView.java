package com.example.korresheader.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.korresheader.R;
import com.example.korresheader.databinding.ActivityContactViewBinding;
import com.example.korresheader.util.Contact;
import com.example.korresheader.viewmodel.ContactViewViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContactView extends AppCompatActivity {

    //View binding
    private ActivityContactViewBinding binding;

    //The ViewModel
    private ContactViewViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ContactViewViewModel.class);
        viewModel.getContactMutableLiveData().observe(this, this::initViews);
        Contact contact = getIntent().getParcelableExtra(HomeActivity.SELECTED_CONTACT);
        if (contact != null) {
            viewModel.setContact(contact);
        }
    }

    /**
     * We establish the data of the different fields if a contact is given and create the listeners
     * @param contact The contact to retrieve the data
     */
    private void initViews(Contact contact) {
        binding.contactNameTextField.getEditText().setText(contact.getName()); ;
        binding.contactPhoneTextField.getEditText().setText(Integer.toString(contact.getPhoneNumber()));
        binding.contactAgeTextField.getEditText().setText(Integer.toString(contact.getAge()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (getIntent().getParcelableExtra(HomeActivity.SELECTED_CONTACT) != null) {
            getMenuInflater().inflate(R.menu.menu_contact_view, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_contact_view_new_contact, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.deleteContact:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.delete_dialog)
                        .setMessage(R.string.delete_dialog_text);
                builder.setPositiveButton(R.string.ok, (dialog, which) -> deleteContact());
                builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.overwriteContact:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(R.string.overwrite_dialog)
                        .setMessage(R.string.overwrite_dialog_text);
                builder2.setPositiveButton(R.string.ok, (dialog2, which) -> saveContact());
                builder2.setNegativeButton(R.string.no, (dialog2, which) -> dialog2.dismiss());
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                break;
            case R.id.createContact:
                saveContact();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method calls the ViewModel to remove the current Contact and finish the Activity
     */
    private void deleteContact() {
        viewModel.deleteContact();
        finish();
    }

    /**
     * This method do the necessary operations to save the contact information
     */
    private void saveContact() {
        try {
            String name = binding.contactNameTextField.getEditText().getText().toString();
            int phone = Integer.parseInt(binding.contactPhoneTextField
                    .getEditText().getText().toString());
            int age = Integer.parseInt(binding.contactAgeTextField
                    .getEditText().getText().toString());
            viewModel.setContactName(name);
            viewModel.setContactPhoneNumber(phone);
            viewModel.setContactAge(age);
            if (viewModel.saveContact()) {
                finish();
            }
            //We catch this Exception because this would happen when the user didn't write
            // anything in the Phone or Age TextFields, so we use it to show an alert.
        }   catch (NumberFormatException e) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle(R.string.info_not_filled)
                    .setMessage(R.string.info_not_filled_text);
            builder2.setPositiveButton(R.string.ok, (dialog2, which) -> dialog2.dismiss());
            AlertDialog dialog2 = builder2.create();
            dialog2.show();
        }
    }

}