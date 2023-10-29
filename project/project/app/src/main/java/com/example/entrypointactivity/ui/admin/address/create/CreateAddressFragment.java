package com.example.entrypointactivity.ui.admin.address.create;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.entrypointactivity.R;
import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.address.Address;
import com.example.entrypointactivity.model.address.ResponseAddress;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateAddressFragment extends Fragment {


     Button buttonAddPlace;
     EditText editTextPlace;
    CreateAddressViewModel createAddressViewModel;
    ArrayList<Address> addressArrayList;

    private CreateAddressFragment createAddressFragment;

    public static CreateAddressFragment newInstance() {
        CreateAddressFragment createAddressFragment =new CreateAddressFragment();
        return createAddressFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        createAddressViewModel = new ViewModelProvider(this).get(CreateAddressViewModel.class);
        return inflater.inflate(R.layout.fragment_create_address, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        buttonAddPlace=view.findViewById(R.id.button_add_place);
        editTextPlace=view.findViewById(R.id.edit_text_place);

        buttonAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Address address=new Address();
                address.setPlace(editTextPlace.getText().toString());
                createAddressViewModel.createAddress(address);
            }
        });

        createAddressViewModel.getStatusCreateAddress().observe(getViewLifecycleOwner(), new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if(status.getStatus().equals("success"))
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
            }
        });

    }






    }
