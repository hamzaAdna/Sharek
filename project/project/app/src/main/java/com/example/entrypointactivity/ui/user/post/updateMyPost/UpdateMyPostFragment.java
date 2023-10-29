package com.example.entrypointactivity.ui.user.post.updateMyPost;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.entrypointactivity.adapter.RecyclerViewPostWithoutNameAdapter;
import com.example.entrypointactivity.adapter.RecyclerViewUpdatePostWithoutNameAdapter;
import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.address.Address;
import com.example.entrypointactivity.model.address.ResponseAddress;
import com.example.entrypointactivity.model.post.Post;
import com.example.entrypointactivity.model.post.ResponsePost;

import java.util.ArrayList;
import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UpdateMyPostFragment extends Fragment {

    int postId=0;
    Spinner spinnerAvaliableSeat;
    Spinner spinnerTo;
    Spinner spinnerFrom;
    EditText editTextPrice;
    EditText editTextNote;
    Button buttonAddPost;
    RecyclerView recyclerViewPost;
    UpdateMyPostViewModel showPostViewModel;
    ArrayList<Address> addresses;
    ArrayList<Post> posts;
    private UpdateMyPostFragment updateMyPostFragment;

    public static UpdateMyPostFragment newInstance(int userId) {
        UpdateMyPostFragment updateMyPostFragment =new UpdateMyPostFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("userId",userId);
        updateMyPostFragment.setArguments(bundle);
        return updateMyPostFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        showPostViewModel= new ViewModelProvider(this).get(UpdateMyPostViewModel.class);
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
        buttonAddPost.setText("update");
        buttonAddPost.setAllCaps(false);

        recyclerViewPost=view.findViewById(R.id.recyclerView_post);

        TextView textViewShowDate = view.findViewById(R.id.textView_show_date);
        Button changeDate=view.findViewById(R.id.change_date);
        TextView textViewShowTime =view.findViewById(R.id.textView_show_time);
        Button changeTime=view.findViewById(R.id.change_time);



        ArrayAdapter arrayAdapterTo=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterFrom=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterAvaliableSeat=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item);

        for (int i = 0; i <=50 ; i++) {
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
                post.setPostId(postId);
                post.setAvaliableSeat(Integer.parseInt(spinnerAvaliableSeat.getSelectedItem().toString()));
                post.setPrice(Double.parseDouble(editTextPrice.getText().toString()));
                post.setFrom(addresses.get(spinnerFrom.getSelectedItemPosition()).getAddressId());
                post.setTo(addresses.get(spinnerTo.getSelectedItemPosition()).getAddressId());
                post.setDepartureDate(textViewShowDate.getText().toString()+" "+textViewShowTime.getText().toString());
                //post.setUserId(getArguments().getInt("userId"));
                showPostViewModel.UpdatePost(post);


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


        RecyclerViewUpdatePostWithoutNameAdapter adapter = new RecyclerViewUpdatePostWithoutNameAdapter(R.layout.post_update);

          adapter.setShowPostListner(new IRecyclerViewUpdatePostItemListner() {
              @Override
              public void updatePostItem(int postItem) {
                  postId=postItem;
                  String from="";
                  String to="";
                  for (int i = 0; i <posts.size() ; i++) {
                      if(posts.get(i).getPostId()==postId){

                          spinnerAvaliableSeat.setSelection(posts.get(i).getAvaliableSeat());
                          editTextNote.setText(posts.get(i).getNote());
                          editTextPrice.setText(String.valueOf(posts.get(i).getPrice()));
                          from=posts.get(i).getFromPlace();
                          to=posts.get(i).getToPlace();
                        String[] str=  posts.get(i).getDepartureDate().split(" ");
                        textViewShowDate.setText(str[0]);
                          textViewShowTime.setText(str[1]);

                      }
                  }

                  for (int addre=0;addre<addresses.size();addre++){
                      if(addresses.get(addre).getPlace().equals(from))
                      {

                          spinnerFrom.setSelection(addre);
                      }
                      if(addresses.get(addre).getPlace().equals(to))
                      {
                          spinnerTo.setSelection(addre);
                      }
                  }
              }
          });
        recyclerViewPost.setHasFixedSize(true);
        recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPost.setAdapter(adapter);
        showPostViewModel.getPostLiveData().observe(getViewLifecycleOwner(), new Observer<ResponsePost>() {
            @Override
            public void onChanged(ResponsePost responsePost) {
                posts=responsePost.getData();
                adapter.setPosts(posts);



            }
        });



    }

    public interface IRecyclerViewUpdatePostItemListner{

        void updatePostItem(int postItem);
    }





    }
