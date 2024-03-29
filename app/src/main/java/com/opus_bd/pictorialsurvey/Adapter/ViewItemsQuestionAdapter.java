package com.opus_bd.pictorialsurvey.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.opus_bd.pictorialsurvey.Model.Question;
import com.opus_bd.pictorialsurvey.Model.ServayQuestionModel;
import com.opus_bd.pictorialsurvey.Model.VotingModel;
import com.opus_bd.pictorialsurvey.R;

import java.util.List;

import static com.opus_bd.pictorialsurvey.Activity.User.SurveyUserActivity.votingModels;

public class ViewItemsQuestionAdapter extends RecyclerView.Adapter<ViewItemsQuestionAdapter.ItemViewHolder> {
    private Context context;
    private int a1 = 0, a2 = 0, a3 = 0, a4 = 0;

    public ViewItemsQuestionAdapter(Context context) {
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
        final VotingModel question = votingModels.get(position);
        if (question.getOptionType().equals("PHOTO")) {
            holder.imageanswer1.setVisibility(View.VISIBLE);
            holder.imageanswer2.setVisibility(View.VISIBLE);
            //show image on glide
            Glide.with(context).load(question.getOptionOne()).into( holder.imageanswer1);
            Glide.with(context).load(question.getOptionTwo()).into( holder.imageanswer2);
            holder.answer2.setVisibility(View.VISIBLE);
            holder.answer1.setVisibility(View.VISIBLE);
            holder.answer2.setText("Picture 2");
            holder.answer1.setText("Picture 1");

        } else {
            holder.imageanswer1.setVisibility(View.GONE);
            holder.imageanswer2.setVisibility(View.GONE);
            holder.answer2.setVisibility(View.VISIBLE);
            holder.answer1.setVisibility(View.VISIBLE);
            holder.answer2.setText(votingModels.get(position).getOptionTwo());
            holder.answer1.setText(votingModels.get(position).getOptionOne());
        }



        holder.radioQuestionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Toast.makeText(context, "vote result changed", Toast.LENGTH_SHORT).show();
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.answer1: {
                        question.setSelectedOptionID(question.getOptionOneID());
                        question.setNonSelectedID(question.getOptionTwoID());
                        break;
                    }
                    case R.id.answer2: {
                        question.setSelectedOptionID(question.getOptionTwoID());
                        question.setNonSelectedID(question.getOptionOneID());

                        break;
                    }

                }
            }
        });



       // holder.question.setText(question.getQuestuionValue());


        holder.question.setText("Q. "+votingModels.get(position).getQuestuionValue());

    }

    @Override
    public int getItemCount() {
        return votingModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView Counter1, Counter2;
        RadioButton answer1, answer2;
        RadioGroup radioQuestionGroup;
        ImageView imageanswer1, imageanswer2;
        CardView rootLayout;
        LinearLayout llradioAnswer;
        LinearLayout llImageAnswer;

        public ItemViewHolder(View view) {
            super(view);
            question = view.findViewById(R.id.teamone);
            answer1 = view.findViewById(R.id.answer1);
            answer2 = view.findViewById(R.id.answer2);
            imageanswer1 = view.findViewById(R.id.imageanswer1);
            radioQuestionGroup = view.findViewById(R.id.radioQuestionGroup);
            imageanswer2 = view.findViewById(R.id.imageanswer2);
            rootLayout = view.findViewById(R.id.rootLayout);
            llradioAnswer = view.findViewById(R.id.llradioAnswer);
            llImageAnswer = view.findViewById(R.id.llImageAnswer);

        }
    }


}
