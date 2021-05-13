package com.example.reminderapp;

import android.media.ImageReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.viewholder> {
  ArrayList<String> notes;
  ItemClickedListener listener;
  RvAdapter (ArrayList<String> notes) {
      this.notes=notes;
  }

  public void itemClicked(ItemClickedListener mlistener) {
      listener = mlistener;
  }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v= inflater.inflate(R.layout.itemview,parent, false);
        return new viewholder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
     holder.tv.setText(notes.get(position));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvnote);
            iv = itemView.findViewById(R.id.ivdelete);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClicked(getAdapterPosition());
                }
            });
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.deleteClicked(getAdapterPosition());
                }
            });
        }
    }
    interface ItemClickedListener {
      void itemClicked(int position);
      void deleteClicked(int position);
    }
}
