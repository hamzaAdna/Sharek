package com.example.entrypointactivity.ui.user.post.showPost;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.entrypointactivity.R;
import com.example.entrypointactivity.adapter.RecyclerViewPostAdapter;
import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.address.Address;
import com.example.entrypointactivity.model.address.ResponseAddress;
import com.example.entrypointactivity.model.post.Post;
import com.example.entrypointactivity.model.post.ResponsePost;
import com.example.entrypointactivity.ui.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class showPostActivity extends FragmentActivity {
    Spinner spinnerAvaliableSeat;
    Spinner spinnerTo;
    Spinner spinnerFrom;
    EditText editTextPrice;
    EditText editTextNote;
    Button   buttonAddPost;
    RecyclerView recyclerViewPost;
    ShowPostViewModel showPostViewModel;
    ArrayList<Address>addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);
        showPostViewModel = new ViewModelProvider(this).get(ShowPostViewModel.class);
        spinnerFrom=findViewById(R.id.spinner_from);
        spinnerTo=findViewById(R.id.spinner_to);
        spinnerAvaliableSeat=findViewById(R.id.spinner_avaliable_seat);
        editTextNote=findViewById(R.id.edit_text_note);
        editTextPrice=findViewById(R.id.edit_text_price);
        buttonAddPost=findViewById(R.id.button_add_post);
        recyclerViewPost=findViewById(R.id.recyclerView_post);

        TextView textViewShowDate = findViewById(R.id.textView_show_date);
        Button changeDate=findViewById(R.id.change_date);
        TextView textViewShowTime = findViewById(R.id.textView_show_time);
        Button changeTime=findViewById(R.id.change_time);



        ArrayAdapter arrayAdapterTo=new ArrayAdapter(getBaseContext(),R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterFrom=new ArrayAdapter(getBaseContext(),R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterAvaliableSeat=new ArrayAdapter(getBaseContext(),R.layout.support_simple_spinner_dropdown_item);

        for (int i = 1; i <=50 ; i++) {
         arrayAdapterAvaliableSeat.add(i+"");
        }
        spinnerAvaliableSeat.setAdapter(arrayAdapterAvaliableSeat);
        spinnerTo.setAdapter(arrayAdapterTo);
        spinnerFrom.setAdapter(arrayAdapterFrom);
        showPostViewModel.getAddresses();
        showPostViewModel.getAddressesLiveData().observe(this, new Observer<ResponseAddress>() {
            @Override
            public void onChanged(ResponseAddress responseAddress) {
                addresses=responseAddress.getData();
                Object[] arrayList= responseAddress.getData().stream().map(address -> address.getPlace()).toArray();
                arrayAdapterTo.addAll(arrayList);
                arrayAdapterFrom.addAll(arrayList);
            }
        });


        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        showPostActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                textViewShowDate.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
        changeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(showPostActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                textViewShowTime.setText(hourOfDay + ":" + minute+":00");
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });

        buttonAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPref =getSharedPreferences("sharedParamedicApp",MODE_PRIVATE);
                int userId=sharedPref.getInt("userlogin_Id",0);

                Post post=new Post();
                post.setNote(editTextNote.getText().toString());
                post.setAvaliableSeat(Integer.parseInt(spinnerAvaliableSeat.getSelectedItem().toString()));
                post.setPrice(Integer.parseInt(editTextPrice.getText().toString()));
                post.setFrom(addresses.get(spinnerFrom.getSelectedItemPosition()).getAddressId());
                post.setTo(addresses.get(spinnerTo.getSelectedItemPosition()).getAddressId());
                post.setDepartureDate(textViewShowDate.getText().toString()+" "+textViewShowTime.getText().toString());
                post.setUserId(userId);
                showPostViewModel.insertPost(post);


            }
        });
        showPostViewModel.insertPostLiveData().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                Toast.makeText(getBaseContext(),"goal"+status.getStatus(),Toast.LENGTH_LONG);
                showPostViewModel.getPostsJoinAddressJoinUser();
            }
        });
        showPostViewModel.getPostsJoinAddressJoinUser();


        RecyclerViewPostAdapter adapter = new RecyclerViewPostAdapter(R.layout.post_item);
        adapter.setShowPostListner(new showPostActivity.IShowProfile() {
            @Override
            public void showProfileListner(int userId) {
//                Intent intent=new Intent(getBaseContext(), ProfileActivity.class);
//                intent.putExtra("userId",userId);
//                startActivity(intent);
            }
        });
        recyclerViewPost.setHasFixedSize(true);
        recyclerViewPost.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPost.setAdapter(adapter);
        showPostViewModel.getPostLiveData().observe(this, new Observer<ResponsePost>() {
            @Override
            public void onChanged(ResponsePost responsePost) {
                adapter.setPosts(responsePost.getData());


            }
        });













    }


    public interface IShowProfile{
        void showProfileListner(int userId);

    }

}