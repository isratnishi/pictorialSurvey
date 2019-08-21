package com.opus_bd.pictorialsurvey.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.stream.UrlLoader;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.opus_bd.pictorialsurvey.Activity.SurveyActivity;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.Question;
import com.opus_bd.pictorialsurvey.Model.Survey;
import com.opus_bd.pictorialsurvey.R;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class ViewItemsQuestionAdapter extends RecyclerView.Adapter<ViewItemsQuestionAdapter.ItemViewHolder> {
    private List<Question> itemList;
    private Context context;
    private int a1 = 0, a2 = 0, a3 = 0, a4 = 0;

    public ViewItemsQuestionAdapter(List<Question> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewItemsQuestionAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question_row, parent, false);
        return new ViewItemsQuestionAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewItemsQuestionAdapter.ItemViewHolder holder, final int position) {
        final Question question = itemList.get(position);
        try {
            if (question.getImageanswer1() != null) {
                holder.llradioAnswer.setVisibility(View.GONE);
                holder.imageanswer1.setVisibility(View.VISIBLE);
                StorageReference reference = FirebaseStorage.getInstance().getReference(itemList.get(position).getImageanswer1());
                Task<Uri> url = reference.getDownloadUrl();
                Glide.with(context).load(reference)
                        .into(holder.imageanswer1);
                Log.d("tag", "Image 1 " + url);

                holder.imageanswer1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.Counter1.setText(String.valueOf(a1));
                    }
                });
            } else {
                holder.imageanswer1.setVisibility(View.GONE);
            }
            if (question.getImageanswer2() != null) {
                holder.imageanswer2.setVisibility(View.VISIBLE);
                StorageReference reference = FirebaseStorage.getInstance().getReference(itemList.get(position).getImageanswer2());

                Glide.with(context).load(reference + ".jpg")
                        .into(holder.imageanswer2);
                Log.d("tag", " " + reference);
                holder.imageanswer2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        a2++;
                        holder.Counter2.setText(String.valueOf(a2));
                    }
                });
            } else {
                holder.imageanswer2.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.d("tag", " " + e.toString());
        }
        String s = "Q. " + itemList.get(position).getQuestion();
        holder.question.setText(s);
        holder.answer1.setText(itemList.get(position).getAnswer1());

        holder.answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a3++;
                holder.Counter1.setText(String.valueOf(a3));
            }
        });


        holder.answer2.setText(itemList.get(position).getAnswer2());

        holder.answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a4++;
                holder.Counter2.setText(String.valueOf(a4));
            }
        });
        Log.d("tag", " key " + itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView Counter1, Counter2;
        RadioButton answer1, answer2;
        ImageView imageanswer1, imageanswer2;
        CardView rootLayout;
        LinearLayout llradioAnswer, llImageAnswer;

        public ItemViewHolder(View view) {
            super(view);
            question = view.findViewById(R.id.teamone);
            answer1 = view.findViewById(R.id.answer1);
            answer2 = view.findViewById(R.id.answer2);
            imageanswer1 = view.findViewById(R.id.imageanswer1);
            imageanswer2 = view.findViewById(R.id.imageanswer2);
            Counter1 = view.findViewById(R.id.Counter1);
            Counter2 = view.findViewById(R.id.Counter2);
            rootLayout = view.findViewById(R.id.rootLayout);
            llradioAnswer = view.findViewById(R.id.llradioAnswer);
            llImageAnswer = view.findViewById(R.id.llImageAnswer);

        }
    }
}
