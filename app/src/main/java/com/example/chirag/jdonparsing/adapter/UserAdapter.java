package com.example.chirag.jdonparsing.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.chirag.jdonparsing.R;
import com.example.chirag.jdonparsing.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by chirag on 27-Apr-17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private Context context;
    private ArrayList<User> userArrayList;
    public UserAdapter(Context context,ArrayList<User> userArrayList){
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final User user = userArrayList.get(position);
        holder.mTvFirstName.setText(user.getFirstName());
        holder.mTvLastName.setText(user.getLastName());
        Picasso.with(context).load(user.getThumb()).into(holder.mIvUser);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvFirstName,mTvLastName;
        private ImageView mIvUser;
        public UserViewHolder(View itemView) {
            super(itemView);
            mTvFirstName = (AppCompatTextView) itemView.findViewById(R.id.tv_first_name);
            mTvLastName = (AppCompatTextView) itemView.findViewById(R.id.tv_last_name);
            mIvUser = (ImageView) itemView.findViewById(R.id.iv_user_image);
        }
    }
}
