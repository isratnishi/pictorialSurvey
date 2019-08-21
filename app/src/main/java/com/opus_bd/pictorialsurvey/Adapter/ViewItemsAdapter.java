package com.opus_bd.pictorialsurvey.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.opus_bd.pictorialsurvey.Activity.SurveyActivity;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.Question;
import com.opus_bd.pictorialsurvey.Model.Survey;
import com.opus_bd.pictorialsurvey.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ViewItemsAdapter extends RecyclerView.Adapter<ViewItemsAdapter.ItemViewHolder> {
    private List<Survey> itemList;
    private Context context;
    private String tableNumber;
    private HashMap<String, Survey> mData;

    public ViewItemsAdapter(List<Survey> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewItemsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewItemsAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewItemsAdapter.ItemViewHolder holder, final int position) {
        final Survey survey = itemList.get(position);
        holder.question.setText(itemList.get(position).getSurveyName());
        holder.answer1.setText(itemList.get(position).getDescription());
        Log.d("tag", " key " + itemList.get(position));
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SurveyActivity.class);
                intent.putExtra(Constant.EXTRA_ITEM, survey);


                context.startActivity(intent);
            }
        });

    /*    holder.priceTextView.setText(itemList.get(position).getPrice() + " Tk.");
        holder.itemDescriptionTextView.setText(itemList.get(position).getDescription());
        Glide.with(context).load(itemList.get(position).getImage())
                .into(holder.imageView);
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.putExtra(Constants.EXTRA_FOOD_ITEM, itemList.get(position));
                intent.putExtra(Constants.EXTRA_POSITION, position);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView answer1;
        TextView answer2;
        ImageView imageView;
        CardView rootLayout;

        public ItemViewHolder(View view) {
            super(view);
            question = view.findViewById(R.id.teamone);
            answer1 = view.findViewById(R.id.teamtwo);
            rootLayout = view.findViewById(R.id.rootLayout);
          /*  priceTextView = view.findViewById(R.id.itemPriceTextView);
            itemDescriptionTextView = view.findViewById(R.id.itemDescriptionTextView);
            imageView = view.findViewById(R.id.imageView);
            rootLayout = view.findViewById(R.id.rootLayout);*/
        }
    }
}
