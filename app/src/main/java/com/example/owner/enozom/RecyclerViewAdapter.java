package com.example.owner.enozom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by owner on 5/12/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<ListItem> listItems;
    private Context context;

    public RecyclerViewAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);
        holder.nameText.setText(listItem.getName());
        holder.descText.setText(listItem.getDesc());
        Picasso.with(context).load(listItem.getImageUrl()).placeholder(R.drawable.placeholder).error(R.drawable.error).into(holder.image);
        //Log.i("adapte",listItem.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView descText;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.name);
            descText = (TextView) itemView.findViewById(R.id.description);
            image = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
