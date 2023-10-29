package com.example.entrypointactivity.ui.admin.showPostWIthDelete;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import com.example.entrypointactivity.ui.profile.ProfileActivity;


import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShowPostWithDeleteFragment extends Fragment {


    Spinner spinnerAvaliableSeat;
    Spinner spinnerTo;
    Spinner spinnerFrom;
    EditText editTextPrice;
    EditText editTextNote;
    Button buttonAddPost;
    RecyclerView recyclerViewPost;
    ShowPostWithDeleteViewModel showPostWithDeletetViewModel;
    ArrayList<Address> addresses;

    private ShowPostWithDeleteFragment ShowPostWithDeleteFragment;

    public static ShowPostWithDeleteFragment newInstance() {
        ShowPostWithDeleteFragment showPostWithDeleteFragment=new ShowPostWithDeleteFragment();
        //Bundle bundle=new Bundle();
        //bundle.putString("role_name",roleName);
        //showPostWithDeleteFragment.setArguments(bundle);
        return showPostWithDeleteFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        showPostWithDeletetViewModel= new ViewModelProvider(this).get(ShowPostWithDeleteViewModel.class);
        return inflater.inflate(R.layout.fragment_delete_post, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        spinnerFrom=view.findViewById(R.id.spinner_from);
        spinnerTo=view.findViewById(R.id.spinner_to);
        spinnerAvaliableSeat=view.findViewById(R.id.spinner_avaliable_seat);
        editTextNote=view.findViewById(R.id.edit_text_note);
        editTextPrice=view.findViewById(R.id.edit_text_price);
        buttonAddPost=view.findViewById(R.id.button_add_post);
        recyclerViewPost=view.findViewById(R.id.recyclerView_post);



        showPostWithDeletetViewModel.getPostsJoinAddressJoinUser();


        RecyclerViewDeletePostAdapter adapter = new RecyclerViewDeletePostAdapter(R.layout.post_item_delete);
        adapter.setShowPostListner(new ShowPostWithDeleteFragment.IRecyclerViewPostDeleteItemListner() {
            @Override
            public void showProfile(int userId) {
                Intent intent=new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }

            @Override
            public void deletePostItem(int postItem) {
                showPostWithDeletetViewModel.deletePost(postItem);

            }
        });

        recyclerViewPost.setHasFixedSize(true);
        recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPost.setAdapter(adapter);
        showPostWithDeletetViewModel.getPostLiveData().observe(getViewLifecycleOwner(), new Observer<ResponsePost>() {
            @Override
            public void onChanged(ResponsePost responsePost) {
                adapter.setPosts(responsePost.getData());


            }
        });
        showPostWithDeletetViewModel.deletePostLiveData().observe(getViewLifecycleOwner(), new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if(status.getStatus().equals("success")){
                    showPostWithDeletetViewModel.getPostsJoinAddressJoinUser();
                }}
        });



    }

    public interface IRecyclerViewPostDeleteItemListner{
        void showProfile(int userId);
        void deletePostItem(int postItem);
    }




    }
