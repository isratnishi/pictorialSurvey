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
            Glide.with(context).load(question.getOption1().getValue()).into(holder.imageanswer1);
            Glide.with(context).load(question.getOption2().getValue()).into(holder.imageanswer2);
            holder.answer2.setVisibility(View.GONE);
            holder.answer1.setVisibility(View.GONE);
            holder.answer3.setVisibility(View.GONE);
            holder.answer4.setVisibility(View.GONE);

        } else {
            holder.imageanswer1.setVisibility(View.GONE);
            holder.imageanswer2.setVisibility(View.GONE);
            if (question.getOption1().getValue().equals("")||question.getOption1().getValue()==null) {
                holder.answer1.setVisibility(View.GONE);
            } if (question.getOption2().getValue().equals("")||question.getOption2().getValue()==null) {
                holder.answer2.setVisibility(View.GONE);
            }  if (question.getOption3().getValue().equals("")||question.getOption3().getValue()==null) {
                holder.answer3.setVisibility(View.GONE);
            } if (question.getOption4().getValue().equals("")||question.getOption4().getValue()==null) {
                holder.answer4.setVisibility(View.GONE);
            } /*else {
                holder.answer2.setVisibility(View.VISIBLE);
                holder.answer1.setVisibility(View.VISIBLE);
                holder.answer3.setVisibility(View.VISIBLE);
                holder.answer4.setVisibility(View.VISIBLE);
            }*/
        }


        //  holder.question.setText(question.getQuestion());

        holder.answer2.setText(itemList.get(position).getOption2().getValue());
        holder.answer1.setText(itemList.get(position).getOption1().getValue());
        holder.answer3.setText(itemList.get(position).getOption3().getValue());
        holder.answer4.setText(itemList.get(position).getOption4().getValue());
        holder.question.setText("Q. " + itemList.get(position).getQuestion());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView Counter1, Counter2;
        RadioButton answer1, answer2, answer3, answer4;
        ImageView imageanswer1, imageanswer2;
        CardView rootLayout;
        LinearLayout llradioAnswer, llImageAnswer;

        public ItemViewHolder(View view) {
            super(view);
            question = view.findViewById(R.id.teamone);
            answer1 = view.findViewById(R.id.answer1);
            answer2 = view.findViewById(R.id.answer2);
            answer3 = view.findViewById(R.id.answer3);
            answer4 = view.findViewById(R.id.answer4);
            imageanswer1 = view.findViewById(R.id.imageanswer1);
            imageanswer2 = view.findViewById(R.id.imageanswer2);
            rootLayout = view.findViewById(R.id.rootLayout);
            llradioAnswer = view.findViewById(R.id.llradioAnswer);
            llImageAnswer = view.findViewById(R.id.llImageAnswer);

        }
    }


}
