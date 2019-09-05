package com.opus_bd.pictorialsurvey.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.opus_bd.pictorialsurvey.Activity.Admin.SurveyActivity;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.Survey;
import com.opus_bd.pictorialsurvey.R;

import java.util.HashMap;
import java.util.List;

import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY;
import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY_ID;
import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY_STATUS;

public class ViewItemsAdapter extends RecyclerView.Adapter<ViewItemsAdapter.ItemViewHolder> {
    private List<Survey> itemList;
    private Context context;
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
        holder.tvSurveyPassword.setText("Password:  "+itemList.get(position).getPassword());
        Log.d("tag", " key " + itemList.get(position));
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SurveyActivity.class);
                if (survey == null) {
                    Toast.makeText(context, "survey is null", Toast.LENGTH_SHORT).show();
                } else {
                    if (survey.getKey() != null) {
                        CURRENTLY_SHOWING_SURVEY_ID = survey.getKey();
                        CURRENTLY_SHOWING_SURVEY_STATUS= survey.getSurveyCondition();
                        CURRENTLY_SHOWING_SURVEY=survey;
                       context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "key is null", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView answer1;
        TextView tvSurveyPassword;
        CardView rootLayout;

        public ItemViewHolder(View view) {
            super(view);
            question = view.findViewById(R.id.teamone);
            answer1 = view.findViewById(R.id.teamtwo);
            tvSurveyPassword = view.findViewById(R.id.tvSurveyPassword);
            rootLayout = view.findViewById(R.id.rootLayout);
        }
    }
}
