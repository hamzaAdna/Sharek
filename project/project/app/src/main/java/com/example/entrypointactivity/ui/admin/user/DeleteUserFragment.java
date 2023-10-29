package com.example.entrypointactivity.ui.admin.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrypointactivity.R;
import com.example.entrypointactivity.adapter.RecyclerViewDeletePostAdapter;
import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.address.Address;
import com.example.entrypointactivity.model.post.ResponsePost;
import com.example.entrypointactivity.model.user.ResponseUser;
import com.example.entrypointactivity.model.user.User;
import com.example.entrypointactivity.ui.profile.ProfileActivity;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeleteUserFragment extends Fragment {


    Spinner spinnerReadUser;
    Button buttonDelete;
    DeleteUserViewModel deleteUserViewModel;
    ArrayList<User> userArrayList;

    private DeleteUserFragment deleteUserFragment;

    public static DeleteUserFragment newInstance() {
        DeleteUserFragment deleteUserFragment=new DeleteUserFragment();
        return deleteUserFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        deleteUserViewModel= new ViewModelProvider(this).get(DeleteUserViewModel.class);
        return inflater.inflate(R.layout.fragment_crud_user, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        spinnerReadUser=view.findViewById(R.id.spinner_read_user);
        buttonDelete=view.findViewById(R.id.button_delete);


        ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item);
        deleteUserViewModel.getUsers();

        spinnerReadUser.setAdapter(arrayAdapter);
        deleteUserViewModel.getUsersLiveData().observe(getViewLifecycleOwner(), new Observer<ResponseUser>() {
            @Override
            public void onChanged(ResponseUser responseUser) {


               userArrayList=responseUser.getData();
               Object [] userNames=userArrayList.stream().map(ele->ele.getName()).toArray();
                Log.d("TAG", "onChanged: "+userNames[0]);
                arrayAdapter.clear();
               arrayAdapter.addAll(userNames);
               arrayAdapter.notifyDataSetChanged();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId=userArrayList.get(spinnerReadUser.getSelectedItemPosition()).getUserId();
                deleteUserViewModel.deleteUser(userId);
            }
        });

        deleteUserViewModel.getStatusDeleteUserLogin().observe(getViewLifecycleOwner(), new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                deleteUserViewModel.getUsers();
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
            }
        });

    }






    }
