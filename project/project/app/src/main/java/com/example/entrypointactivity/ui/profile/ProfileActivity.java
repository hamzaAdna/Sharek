package com.example.entrypointactivity.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.entrypointactivity.R;
import com.example.entrypointactivity.adapter.RecyclerViewPostAdapter;
import com.example.entrypointactivity.adapter.RecyclerViewPostWithoutNameAdapter;
import com.example.entrypointactivity.model.post.ResponsePost;
import com.example.entrypointactivity.model.user.ResponseUser;
import com.example.entrypointactivity.model.user.User;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends FragmentActivity {
    private ProfileViewModel profileViewModel;
    private RecyclerView recyclerViewPost;
    private TextView textViewUserName;
    private TextView textViewPhone;
    private TextView textViewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerViewPost=findViewById(R.id.recyclerView_post);
        textViewEmail=findViewById(R.id.textView_email);
        textViewPhone=findViewById(R.id.textView_phone);
        textViewUserName=findViewById(R.id.textView_user_name);



        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        int userId=getIntent().getIntExtra("userId",0);



        profileViewModel.getUserByUserId(userId);

        profileViewModel.getUserByUserIdLiveData().observe(this, new Observer<ResponseUser>() {
            @Override
            public void onChanged(ResponseUser responseUser) {


                if(responseUser.getData().size()>0)
                {
                    User user = responseUser.getData().get(0);
                    textViewEmail.setText("  "+user.getEmail());
                    textViewPhone.setText("  "+user.getPhone());
                    textViewUserName.setText("  "+user.getName());
                }


            }
        });


        RecyclerViewPostWithoutNameAdapter adapter = new RecyclerViewPostWithoutNameAdapter(R.layout.post_item_without_name);
        recyclerViewPost.setHasFixedSize(true);
        recyclerViewPost.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPost.setAdapter(adapter);


        profileViewModel.getPostsByUserId(userId);
        profileViewModel.getPostsByUserIdLiveData().observe(this, new Observer<ResponsePost>() {
            @Override
            public void onChanged(ResponsePost responsePost) {
               adapter.setPosts(responsePost.getData());
            }
        });















        }
}