package com.example.entrypointactivity.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.entrypointactivity.R;
import com.example.entrypointactivity.model.post.Post;
import com.example.entrypointactivity.ui.user.post.deleteMyPost.ShowPostWithDeleteFragment;

import java.util.ArrayList;

public class RecyclerViewDeleteUserPostAdapter extends RecyclerView.Adapter<RecyclerViewDeleteUserPostAdapter.ViewHolder>  {


    ArrayList<Post>posts=new ArrayList<>();
    ShowPostWithDeleteFragment.IRecyclerViewPostDeleteItemListner itemListner;
    private int resource;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNote;
        private final TextView textViewDate;
        private final TextView textViewFrom;
        private final TextView textViewTo;
        private final TextView textViewPrice;
        private final TextView textViewAvailableSeat;
        private final Button buttonDeletePost;
        public ViewHolder(View view) {
            super(view);
            textViewNote = (TextView) view.findViewById(R.id.textView_note);
            textViewDate = (TextView) view.findViewById(R.id.textView_date);
            textViewFrom = (TextView) view.findViewById(R.id.textView_from);
            textViewTo = (TextView) view.findViewById(R.id.textView_to);
            textViewPrice = (TextView) view.findViewById(R.id.textView_price);
            textViewAvailableSeat = (TextView) view.findViewById(R.id.textView_available_seat);
            buttonDeletePost=(Button)view.findViewById(R.id.button_delete_post);


            buttonDeletePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int postId= (int) buttonDeletePost.getTag(R.string.tag_button_delete_post_item);
                    RecyclerViewDeleteUserPostAdapter.this.itemListner.deletePostItem(postId);

                }
            });

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
        public TextView getButtonDeletePostItem() {
            return buttonDeletePost;
        }
    }
    public RecyclerViewDeleteUserPostAdapter(int resource) {
    // this.showPost=showPost;
        this.resource=resource;
    }

    public void setPosts(ArrayList<Post>posts)
    {
        this.posts=posts;

        notifyDataSetChanged();
    }

    public void setShowPostListner(ShowPostWithDeleteFragment.IRecyclerViewPostDeleteItemListner itemListner)
    {

        this.itemListner=itemListner;
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
         viewHolder.getButtonDeletePostItem().setTag(R.string.tag_button_delete_post_item,this.posts.get(position).getPostId());




    }
    @Override
    public int getItemCount() {
        return posts.size();
    }





}
