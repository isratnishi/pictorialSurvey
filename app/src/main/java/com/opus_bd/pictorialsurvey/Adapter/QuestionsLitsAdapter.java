package com.opus_bd.pictorialsurvey.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.opus_bd.pictorialsurvey.Model.QuestionAndVoteCount;
import com.opus_bd.pictorialsurvey.R;

import java.util.ArrayList;
import java.util.List;




public class QuestionsLitsAdapter extends RecyclerView.Adapter<QuestionsLitsAdapter.MyViewHolder> {
    List<QuestionAndVoteCount> list = new ArrayList<>();

    Context context;
    int total;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_questionBody, tv_option_1, tv_option_1_vote, tv_option_2, tv_option_2_vote,tv_total;
        RelativeLayout relative_container;
        ProgressBar first, second;


        public MyViewHolder(View view) {
            super(view);
            tv_questionBody = (TextView) view.findViewById(R.id.tv_questionBody);
            tv_option_1 = (TextView) view.findViewById(R.id.tv_option_1);
            tv_total = (TextView) view.findViewById(R.id.tv_total);
            tv_option_1_vote = (TextView) view.findViewById(R.id.tv_option_1_vote);
            tv_option_2 = (TextView) view.findViewById(R.id.tv_option_2);
            tv_option_2_vote = (TextView) view.findViewById(R.id.tv_option_2_vote);
            first = (ProgressBar) view.findViewById(R.id.first);
            second = (ProgressBar) view.findViewById(R.id.second);


        }
    }


    public QuestionsLitsAdapter(List<QuestionAndVoteCount> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_vote_result_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final QuestionAndVoteCount movie = list.get(position);
        context = holder.tv_questionBody.getContext();
        total=Integer.parseInt(movie.getOptionOneCount())+Integer.parseInt(movie.getOptionTwoCount());
        holder.tv_questionBody.setText("Q. "+movie.getQuestionBody());
        holder.tv_total.setText("Total participate "+total);
        holder.tv_option_1.setText(movie.getOptionOneValue());
        holder.tv_option_2.setText(movie.getOptionTwoValue());
        holder.tv_option_1_vote.setText(movie.getOptionOneCount()+" votes ("+movie.getFirstoption()+")%");
        holder.tv_option_2_vote.setText(movie.getOptionTwoCount()+" votes ("+movie.getSecondoption()+")%");
        holder.first.setProgress(movie.getFirstoption());
        holder.second.setProgress(movie.getSecondoption());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}