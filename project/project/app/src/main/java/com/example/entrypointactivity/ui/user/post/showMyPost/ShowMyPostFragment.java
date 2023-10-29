package com.example.entrypointactivity.ui.user.post.showMyPost;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entrypointactivity.R;
import com.example.entrypointactivity.adapter.RecyclerViewPostAdapter;
import com.example.entrypointactivity.adapter.RecyclerViewPostWithoutNameAdapter;
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
public class ShowMyPostFragment extends Fragment {


    Spinner spinnerAvaliableSeat;
    Spinner spinnerTo;
    Spinner spinnerFrom;
    EditText editTextPrice;
    EditText editTextNote;
    Button buttonAddPost;
    RecyclerView recyclerViewPost;
    ShowMyPostViewModel showPostViewModel;
    ArrayList<Address> addresses;

    private ShowMyPostFragment showMyPostFragment;

    public static ShowMyPostFragment newInstance(int userId) {
        ShowMyPostFragment showMyPostFragment =new ShowMyPostFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("userId",userId);
        showMyPostFragment.setArguments(bundle);
        return showMyPostFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        showPostViewModel= new ViewModelProvider(this).get(ShowMyPostViewModel.class);
        return inflater.inflate(R.layout.activity_show_post, container, false);
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

        TextView textViewShowDate = view.findViewById(R.id.textView_show_date);
        Button changeDate=view.findViewById(R.id.change_date);
        TextView textViewShowTime =view.findViewById(R.id.textView_show_time);
        Button changeTime=view.findViewById(R.id.change_time);



        ArrayAdapter arrayAdapterTo=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterFrom=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterAvaliableSeat=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item);

        for (int i = 1; i <=50 ; i++) {
            arrayAdapterAvaliableSeat.add(i+"");
        }
        spinnerAvaliableSeat.setAdapter(arrayAdapterAvaliableSeat);
        spinnerTo.setAdapter(arrayAdapterTo);
        spinnerFrom.setAdapter(arrayAdapterFrom);
        showPostViewModel.getAddresses();
        showPostViewModel.getAddressesLiveData().observe(getViewLifecycleOwner(), new Observer<ResponseAddress>() {
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
                        getContext(),
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
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


                Post post=new Post();
                post.setNote(editTextNote.getText().toString());
                post.setAvaliableSeat(Integer.parseInt(spinnerAvaliableSeat.getSelectedItem().toString()));
                post.setPrice(Integer.parseInt(editTextPrice.getText().toString()));
                post.setFrom(addresses.get(spinnerFrom.getSelectedItemPosition()).getAddressId());
                post.setTo(addresses.get(spinnerTo.getSelectedItemPosition()).getAddressId());
                post.setDepartureDate(textViewShowDate.getText().toString()+" "+textViewShowTime.getText().toString());
                post.setUserId(getArguments().getInt("userId"));
                showPostViewModel.insertPost(post);


            }
        });
        showPostViewModel.insertPostLiveData().observe(getViewLifecycleOwner(), new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                Toast.makeText(getContext(),"goal"+status.getStatus(),Toast.LENGTH_LONG);
                showPostViewModel.getPostsJoinAddressJoinUser(getArguments().getInt("userId"));
            }
        });
        showPostViewModel.getPostsJoinAddressJoinUser(getArguments().getInt("userId"));


        RecyclerViewPostWithoutNameAdapter adapter = new RecyclerViewPostWithoutNameAdapter(R.layout.post_item_without_name);
//        adapter.setShowPostListner(new showPostActivity.IShowProfile() {
//            @Override
//            public void showProfileListner(int userId) {
//                Intent intent=new Intent(getContext(), ProfileActivity.class);
//                intent.putExtra("userId",userId);
//                startActivity(intent);
//            }
//        });
        recyclerViewPost.setHasFixedSize(true);
        recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPost.setAdapter(adapter);
        showPostViewModel.getPostLiveData().observe(getViewLifecycleOwner(), new Observer<ResponsePost>() {
            @Override
            public void onChanged(ResponsePost responsePost) {
                adapter.setPosts(responsePost.getData());


            }
        });



    }






    }
