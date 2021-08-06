package com.example.korresheader.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.korresheader.util.Contact;
import com.example.korresheader.R;
import com.example.korresheader.databinding.ActivityContactViewBinding;
import com.example.korresheader.viewmodel.ContactViewViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        binding.contactNameTextField.setPlaceholderText(contact.getName());
        binding.contactPhoneTextField.setPlaceholderText(Integer.toString(contact.getPhoneNumber()));
        binding.contactAgeTextField.setPlaceholderText(Integer.toString(contact.getAge()));
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
                new MaterialAlertDialogBuilder(getApplicationContext())
                        .setTitle(R.string.information_dialog)
                        .setMessage(R.string.information_dialog_text)
                        .setPositiveButton(R.string.ok, (dialog, which) -> viewModel.deleteContact())
                        .setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss())
                        .show();
                break;
            case R.id.createContact:
                viewModel.setContactName(
                        binding.contactNameTextField.getEditText().getText().toString());
                viewModel.setContactPhoneNumber(
                        Integer.parseInt(
                                binding.contactPhoneTextField.getEditText().getText().toString()));
                viewModel.setContactAge(
                        Integer.parseInt(
                                binding.contactAgeTextField.getEditText().getText().toString()));
                if (viewModel.saveContact()) {
                    finish();
                } else {
                    new MaterialAlertDialogBuilder(getApplicationContext())
                            .setTitle(R.string.information_dialog)
                            .setMessage(R.string.information_dialog_text)
                            .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                            .show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}