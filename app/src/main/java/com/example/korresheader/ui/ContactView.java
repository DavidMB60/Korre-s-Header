package com.example.korresheader.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.korresheader.Contact;
import com.example.korresheader.R;
import com.example.korresheader.databinding.ActivityContactViewBinding;
import com.example.korresheader.viewmodel.ContactViewViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        Contact contact = getIntent().getParcelableExtra(MainActivity.SELECTED_QUICKSET);
        if (contact != null) {
            viewModel.setContact(contact);
            initViews(contact);
        }
    }

    /**
     * We establish the data of the different fields if a contact is given
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
        getMenuInflater().inflate(R.menu.menu_contact_view, menu);
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
                // TODO: Ask the user for confirmation and remove if necessary
                break;
            case R.id.createContact:
                if (!viewModel.saveContact()) {
                    // TODO: Create Alert
                }
                break;
            default:
                // TODO: Send an alert of invalid option
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}