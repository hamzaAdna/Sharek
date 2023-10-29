package com.example.entrypointactivity.ui.admin.address.delete;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.example.entrypointactivity.model.user.ResponseUser;
import com.example.entrypointactivity.model.user.User;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeleteAddressFragment extends Fragment {


    Spinner spinnerReadAddress;
    Button buttonDelete;
    DeleteAddressViewModel deleteAddressViewModel;
    ArrayList<Address> addressArrayList;

    private DeleteAddressFragment deleteAddressFragment;

    public static DeleteAddressFragment newInstance() {
        DeleteAddressFragment deleteAddressFragment =new DeleteAddressFragment();
        return deleteAddressFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        deleteAddressViewModel = new ViewModelProvider(this).get(DeleteAddressViewModel.class);
        return inflater.inflate(R.layout.fragment_delete_address, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        spinnerReadAddress=view.findViewById(R.id.spinner_read_address);
        buttonDelete=view.findViewById(R.id.button_delete);


        ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item);
        deleteAddressViewModel.getAddresses();

        spinnerReadAddress.setAdapter(arrayAdapter);
        deleteAddressViewModel.getAddressesLiveData().observe(getViewLifecycleOwner(), new Observer<ResponseAddress>() {
            @Override
            public void onChanged(ResponseAddress responseAddress) {


               addressArrayList=responseAddress.getData();
               Object [] userNames=addressArrayList.stream().map(ele->ele.getPlace()).toArray();
                Log.d("TAG", "onChanged: "+userNames[0]);
                arrayAdapter.clear();
               arrayAdapter.addAll(userNames);
               arrayAdapter.notifyDataSetChanged();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int addressId=addressArrayList.get(spinnerReadAddress.getSelectedItemPosition()).getAddressId();
                deleteAddressViewModel.deleteAddress(addressId);
            }
        });

        deleteAddressViewModel.getStatusDeleteUserLogin().observe(getViewLifecycleOwner(), new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                deleteAddressViewModel.getAddresses();
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
            }
        });

    }






    }
