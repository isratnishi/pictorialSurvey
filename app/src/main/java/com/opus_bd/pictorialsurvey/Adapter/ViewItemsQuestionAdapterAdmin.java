package com.opus_bd.pictorialsurvey.Adapter;

import android.content.Context;
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
import com.opus_bd.pictorialsurvey.Model.ServayQuestionModel;
import com.opus_bd.pictorialsurvey.Model.VotingModel;
import com.opus_bd.pictorialsurvey.R;

import java.util.List;

public class ViewItemsQuestionAdapterAdmin extends RecyclerView.Adapter<ViewItemsQuestionAdapterAdmin.ItemViewHolder> {
    private List<ServayQuestionModel> itemList;
    private Context context;
    private int a1 = 0, a2 = 0, a3 = 0, a4 = 0;

    public ViewItemsQuestionAdapterAdmin(List<ServayQuestionModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewItemsQuestionAdapterAdmin.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question_row, parent, false);
        return new ViewItemsQuestionAdapterAdmin.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewItemsQuestionAdapterAdmin.ItemViewHolder holder, final int position) {
        final ServayQuestionModel question = itemList.get(position);
        if (question.getOptionType().equals("PHOTO")) {
            holder.imageanswer1.setVisibility(View.VISIBLE);
            holder.imageanswer2.setVisibility(View.VISIBLE);
            //show image on glide
            Glide.with(context).load(question.getOption1().getValue()).into( holder.imageanswer1);
            Glide.with(context).load(question.getOption2().getValue()).into( holder.imageanswer2);
            holder.answer2.setVisibility(View.GONE);
            holder.answer1.setVisibility(View.GONE);

        } else {
            holder.imageanswer1.setVisibility(View.GONE);
            holder.imageanswer2.setVisibility(View.GONE);
            holder.answer2.setVisibility(View.VISIBLE);
            holder.answer1.setVisibility(View.VISIBLE);
        }



        holder.answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a3++;
                holder.Counter1.setText(String.valueOf(a3));
            }
        });



        holder.answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a4++;
                holder.Counter2.setText(String.valueOf(a4));
            }
        });
        Log.d("tag", " key " + itemList.get(position));

  //  holder.question.setText(question.getQuestion());

        holder.answer2.setText(itemList.get(position).getOption2().getValue());
        holder.answer1.setText(itemList.get(position).getOption1().getValue());
        holder.question.setText("Q. "+itemList.get(position).getQuestion());

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
            rootLayout = view.findViewById(R.id.rootLayout);
            llradioAnswer = view.findViewById(R.id.llradioAnswer);
            llImageAnswer = view.findViewById(R.id.llImageAnswer);

        }
    }


}
