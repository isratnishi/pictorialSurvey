package com.opus_bd.pictorialsurvey.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.opus_bd.pictorialsurvey.Model.QuestionAndVoteCount;
import com.opus_bd.pictorialsurvey.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mukul on 3/10/2019.
 */


public class QuestionsLitsAdapter extends RecyclerView.Adapter<QuestionsLitsAdapter.MyViewHolder> {
    List<QuestionAndVoteCount> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_questionBody, tv_option_1, tv_option_1_vote, tv_option_2, tv_option_2_vote;
        RelativeLayout relative_container;
        ProgressBar first, second;
        ImageView image_one, image_two;
        PieChart pieChart;

        PieData pieData;
        PieDataSet pieDataSet;
        ArrayList pieEntries;
        ArrayList PieEntryLabels;

        RelativeLayout rvpic;
        public MyViewHolder(View view) {
            super(view);
            tv_questionBody = (TextView) view.findViewById(R.id.tv_questionBody);
            tv_option_1 = (TextView) view.findViewById(R.id.tv_option_1);
            tv_option_1_vote = (TextView) view.findViewById(R.id.tv_option_1_vote);
            tv_option_2 = (TextView) view.findViewById(R.id.tv_option_2);
            tv_option_2_vote = (TextView) view.findViewById(R.id.tv_option_2_vote);
            first = (ProgressBar) view.findViewById(R.id.first);
            second = (ProgressBar) view.findViewById(R.id.second);

            image_one = (ImageView) view.findViewById(R.id.image_one);
            image_two = (ImageView) view.findViewById(R.id.image_two);
            pieChart = view.findViewById(R.id.pieChart);
            rvpic = view.findViewById(R.id.rvpic);


        }



    }


    /*private void getEntries() {

    }*/
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
        holder.tv_questionBody.setText("Q. "+movie.getQuestionBody());
        holder.tv_option_1.setText(movie.getOptionOneValue());
        holder.tv_option_2.setText(movie.getOptionTwoValue());
        holder.tv_option_1_vote.setText(movie.getOptionOneCount() + " votes (" + movie.getFirstoption() + ")%");
        holder.tv_option_2_vote.setText(movie.getOptionTwoCount() + " votes (" + movie.getSecondoption() + ")%");
        holder.first.setProgress(movie.getFirstoption());
        holder.second.setProgress(movie.getSecondoption());


        if (movie.getType().equals("TEXT")) {
            holder.image_one.setVisibility(View.GONE);
            holder.image_two.setVisibility(View.GONE);
            holder.rvpic.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(movie.getOptionOneValue()).into(holder.image_one);
            Glide.with(context).load(movie.getOptionTwoValue()).into(holder.image_two);
            holder.tv_option_1.setVisibility(View.GONE);
            holder.tv_option_2.setVisibility(View.GONE);
        }
        holder.pieEntries = new ArrayList<>();

        if (movie.getType().equals("TEXT")) {
            holder.pieEntries.add(new PieEntry(movie.getFirstoption(), movie.getOptionOneValue()));
            holder.pieEntries.add(new PieEntry(movie.getSecondoption(), movie.getOptionTwoValue()));
        } else {
            holder.pieEntries.add(new PieEntry(movie.getFirstoption(), "Picture 1"));
            holder.pieEntries.add(new PieEntry(movie.getSecondoption(), "Picture 2"));
        }



        holder.pieDataSet = new PieDataSet(holder.pieEntries, "");
        holder.pieData = new PieData(holder.pieDataSet);
        holder.pieChart.setData(holder.pieData);
        holder.pieChart.setDescription(new Description());

        holder.pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        holder.pieDataSet.setSliceSpace(1f);
        holder.pieDataSet.setValueTextColor(Color.WHITE);
        //holder.pieDataSet.setValueTextSize(10f);
       // holder.pieDataSet.setSliceSpace(1f);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}