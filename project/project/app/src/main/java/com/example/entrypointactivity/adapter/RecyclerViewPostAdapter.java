package com.example.entrypointactivity.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.entrypointactivity.R;
import com.example.entrypointactivity.model.post.Post;
import com.example.entrypointactivity.ui.user.post.showPost.showPostActivity;

import java.util.ArrayList;

public class RecyclerViewPostAdapter extends RecyclerView.Adapter<RecyclerViewPostAdapter.ViewHolder>  {


    ArrayList<Post>posts=new ArrayList<>();
    showPostActivity.IShowProfile showPost;
    private int resource;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNote;
        private final TextView textViewDate;
        private final TextView textViewFrom;
        private final TextView textViewTo;
        private final TextView textViewPrice;
        private final TextView textViewAvailableSeat;
        private final TextView textViewUserName;
        public ViewHolder(View view) {
            super(view);
            textViewNote = (TextView) view.findViewById(R.id.textView_note);
            textViewDate = (TextView) view.findViewById(R.id.textView_date);
            textViewFrom = (TextView) view.findViewById(R.id.textView_from);
            textViewTo = (TextView) view.findViewById(R.id.textView_to);
            textViewPrice = (TextView) view.findViewById(R.id.textView_price);
            textViewAvailableSeat = (TextView) view.findViewById(R.id.textView_available_seat);
            textViewUserName = (TextView) view.findViewById(R.id.textView_user_name);

            textViewUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int userId= (int) textViewUserName.getTag(R.string.tag_userName);
                    RecyclerViewPostAdapter.this.showPost.showProfileListner(userId);


                }
            });

        }


        public TextView getTextViewUserName() {
            return textViewUserName;
        }
        public TextView getTextViewNote() {
            return textViewNote;
        }
        public TextView getTextViewDate() {
            return textViewDate;
        }
        public TextView getTextViewFrom() {
            return textViewFrom;
        }
        public TextView getTextViewTo() {
            return textViewTo;
        }
        public TextView getTextViewPrice() {
            return textViewPrice;
        }
        public TextView getTextViewAvaliableSeat() {
            return textViewAvailableSeat;
        }
    }
    public RecyclerViewPostAdapter(int resource) {
    // this.showPost=showPost;
        this.resource=resource;
    }

    public void setPosts(ArrayList<Post>posts)
    {
        this.posts=posts;

        notifyDataSetChanged();
    }

    public void setShowPostListner(showPostActivity.IShowProfile showPost)
    {

        this.showPost=showPost;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(resource, viewGroup, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Log.d("TAG", "onBindViewHolder: "+this.posts.get(position).getNote());

        viewHolder.getTextViewNote().setText("note : "+this.posts.get(position).getNote()+"");
        viewHolder.getTextViewDate().setText("date : "+this.posts.get(position).getDepartureDate()+"");
        viewHolder.getTextViewPrice().setText("price : "+this.posts.get(position).getPrice()+"");
        viewHolder.getTextViewTo().setText("to : "+this.posts.get(position).getToPlace()+"");
        viewHolder.getTextViewFrom().setText("  from : "+this.posts.get(position).getFromPlace()+"");
        viewHolder.getTextViewAvaliableSeat().setText("available seat : "+this.posts.get(position).getAvaliableSeat()+"");
        viewHolder.getTextViewUserName().setText(" user name : "+this.posts.get(position).getUserName()+"");
        viewHolder.getTextViewUserName().setTag(R.string.tag_userName,this.posts.get(position).getUserId());




    }
    @Override
    public int getItemCount() {
        return posts.size();
    }





}
