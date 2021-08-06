package com.example.korresheader.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.korresheader.R;
import com.example.korresheader.databinding.ActivityMainBinding;
import com.example.korresheader.viewmodel.HomeActivityViewModel;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import android.view.Menu;
import android.view.MenuItem;

import io.reactivex.rxjava3.disposables.Disposable;

public class HomeActivity extends AppCompatActivity {

    //View binding
    private ActivityMainBinding binding;

    //Static value for the Intent when opening a contact
    public static final String SELECTED_QUICKSET = "selected_quickset";

    //ViewModel
    private HomeActivityViewModel viewModel;

    //The adapter for the RecyclerView
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        initList();
        viewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);
        viewModel.setContext(getApplicationContext());
        observeModel();
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.addNewContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ContactView.class));
            }
        });
    }

    /**
     * This method initialize the RecyclerView and all it's necessary components
     */
    private void initList() {
        contactAdapter = new ContactAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.contactList.setLayoutManager(linearLayoutManager);
        binding.contactList.setAdapter(contactAdapter);
        binding.contactList.addItemDecoration(new StickyRecyclerHeadersDecoration(contactAdapter));
    }

    /**
     * This method calls for observer the model
     */
    private void observeModel() {
        viewModel.getContacts().observe(this, contactAdapter::setContactList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addNewContactMenu) {
            startActivity(new Intent(getApplicationContext(), ContactView.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // FIXME: This is stupid and shouldn't be done :/ but it won't update unless I do this
        observeModel();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // FIXME: This is stupid and shouldn't be done :/ but it won't update unless I do this
        observeModel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}