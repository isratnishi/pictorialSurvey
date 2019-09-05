package com.opus_bd.pictorialsurvey.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.opus_bd.pictorialsurvey.Activity.LoginActivity;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.Option1;
import com.opus_bd.pictorialsurvey.Model.Option2;
import com.opus_bd.pictorialsurvey.Model.Option3;
import com.opus_bd.pictorialsurvey.Model.Option4;
import com.opus_bd.pictorialsurvey.Model.Question;
import com.opus_bd.pictorialsurvey.Model.ServayQuestionModel;
import com.opus_bd.pictorialsurvey.Model.Survey;
import com.opus_bd.pictorialsurvey.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY_ID;

public class QuestionActivity extends AppCompatActivity {

    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    Button btnAdd;
    private static final int PICK_IMG = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int uploads = 0;
    Survey survey;
    int count = 0;
    //Firebase
    int dec=0;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    TextInputEditText etSetQuestion;
    TextInputEditText etSetQuestionAnswer1;
    TextInputEditText etSetQuestionAnswer2;
    TextInputEditText etSetQuestionAnswer3;
    TextInputEditText etSetQuestionAnswer4;
    ImageView imgSetQuestionAnswer1;
    ImageView imgSetQuestionAnswer2;
    LinearLayout llTextAnswer;
    LinearLayout llPictureAnswer;
    Uri resultUri;
    String FIRST_UPLOADED_IMAGE_LINK;
    String SECOND_UPLOADED_IMAGE_LINK;
    AppCompatSpinner spnGender;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference();
    private Uri filePath;
    String path1, path2;
    String path, survey1;
    Context context = this;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    int FIRST_IMAGE = 1;
    int SECOND_IMAGE = 2;
    int CURRENTLY_SELECTING_IMAGEVIEW;
    Uri FIRST_IMAGE_PATH;
    Uri SECOND_IMAGE_PATH;
    StorageReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*  ActivityQusetionBinding binding = DataBindingUtil. */
        //CURRENTLY_SHOWING_SURVEY_ID
        setContentView(R.layout.activity_question);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            survey = (Survey) getIntent().getSerializableExtra(Constant.EXTRA_ITEM);
            survey1 = bundle.getString(Constant.EXTRA_ITEM1);
        }
        initializeveriable();
        //requestMultiplePermissions();
        spnGender.setOnItemSelectedListener(new CustomOnItemSelectedListener());


    }

    private void askPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            openPicker();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    public void PickImage_1(View view) {
        CURRENTLY_SELECTING_IMAGEVIEW = FIRST_IMAGE;

        askPermission();

    }

    public void PickImage_2(View view) {
        CURRENTLY_SELECTING_IMAGEVIEW = SECOND_IMAGE;
        askPermission();

    }

    private void openPicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public void initializeveriable() {

        etSetQuestion = findViewById(R.id.etSetQuestion);
        etSetQuestionAnswer1 = findViewById(R.id.etSetQuestionAnswer1);
        etSetQuestionAnswer2 = findViewById(R.id.etSetQuestionAnswer2);
        etSetQuestionAnswer3 = findViewById(R.id.etSetQuestionAnswer3);
        etSetQuestionAnswer4 = findViewById(R.id.etSetQuestionAnswer4);
        imgSetQuestionAnswer1 = findViewById(R.id.imgSetQuestionAnswer1);
        imgSetQuestionAnswer2 = findViewById(R.id.imgSetQuestionAnswer2);
        llTextAnswer = findViewById(R.id.llTextAnswer);
        llPictureAnswer = findViewById(R.id.llPictureAnswer);
        spnGender = findViewById(R.id.spnGender);
        btnAdd = findViewById(R.id.btnAdd);

    }

    public void Submit(View view) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //final DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.QUESTION).child(survey.getSurveyName());
        //String key = databaseReference.push().getKey();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.SURVEY_LIST).child(CURRENTLY_SHOWING_SURVEY_ID).child("options");
        String key = databaseReference.push().getKey();
        ServayQuestionModel servayQuestionModel = new ServayQuestionModel();
        servayQuestionModel.setQuestion(etSetQuestion.getText().toString().trim());
        String option_1_key = databaseReference.child(key).push().getKey();
        servayQuestionModel.setOption1(new Option1(option_1_key, etSetQuestionAnswer1.getText().toString().trim()));
        servayQuestionModel.setQuestionKey(key);

        String option_2_key = databaseReference.child(key).push().getKey();
        servayQuestionModel.setOption2(new Option2(option_2_key, etSetQuestionAnswer2.getText().toString().trim()));
        String option_3_key = databaseReference.child(key).push().getKey();
        servayQuestionModel.setOption3(new Option3(option_3_key, etSetQuestionAnswer3.getText().toString().trim()));
        String option_4_key = databaseReference.child(key).push().getKey();
        servayQuestionModel.setOption4(new Option4(option_4_key, etSetQuestionAnswer4.getText().toString().trim()));
        servayQuestionModel.setOptionType("TEXT");
        databaseReference.child(key).setValue(servayQuestionModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(QuestionActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
        });


        Intent intent = new Intent(this, SurveyActivity.class);
        intent.putExtra(Constant.EXTRA_ITEM, survey);
        //  intent.putExtra(Constant.EXTRA_ITEM1, survey1);*/
        startActivity(intent);


    }


    public void SubmitPicture(View view) {
        if (FIRST_IMAGE_PATH != null && SECOND_IMAGE_PATH != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("1st image Uploading...");
            progressDialog.show();

            FIRST_UPLOADED_IMAGE_LINK = null;
            SECOND_UPLOADED_IMAGE_LINK = null;

            ref = storageReference.child("images/" + UUID.randomUUID().toString());

            ref.putFile(FIRST_IMAGE_PATH)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(context, ref.getPath(), Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FIRST_UPLOADED_IMAGE_LINK = uri.toString();
                                    //upload second  image
                                    ref = storageReference.child("images/" + UUID.randomUUID().toString());
                                    progressDialog.setTitle("2nd image Uploading...");

                                    ref.putFile(SECOND_IMAGE_PATH)
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(context, ref.getPath(), Toast.LENGTH_SHORT).show();
                                                    // Glide.with(context).load(resultUri).into(imgSetQuestionAnswer2);
                                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            progressDialog.dismiss();
                                                            SECOND_UPLOADED_IMAGE_LINK = uri.toString();
                                                            Toast.makeText(QuestionActivity.this, "Both file is uploaded", Toast.LENGTH_SHORT).show();

                                                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                                                            DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.SURVEY_LIST).child(CURRENTLY_SHOWING_SURVEY_ID).child("options");
                                                            String key = databaseReference.push().getKey();
                                                            ServayQuestionModel servayQuestionModel = new ServayQuestionModel();
                                                            servayQuestionModel.setQuestion(etSetQuestion.getText().toString().trim());
                                                            String option_1_key = databaseReference.child(key).push().getKey();
                                                            servayQuestionModel.setOption1(new Option1(option_1_key, FIRST_UPLOADED_IMAGE_LINK));
                                                            servayQuestionModel.setQuestionKey(key);

                                                            String option_2_key = databaseReference.child(key).push().getKey();
                                                            servayQuestionModel.setOption2(new Option2(option_2_key, SECOND_UPLOADED_IMAGE_LINK));
                                                            String option_3_key = databaseReference.child(key).push().getKey();
                                                            servayQuestionModel.setOption3(new Option3(option_3_key, ""));
                                                            String option_4_key = databaseReference.child(key).push().getKey();
                                                            servayQuestionModel.setOption4(new Option4(option_4_key, ""));
                                                            servayQuestionModel.setOptionType("PHOTO");
                                                            databaseReference.child(key).setValue(servayQuestionModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(QuestionActivity.this, "success", Toast.LENGTH_SHORT).show();
                                                                    onBackPressed();
                                                                }
                                                            });


                                                        }
                                                    });

                                                    //uploadData();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                                            .getTotalByteCount());
                                                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                                }
                                            });
                                }
                            });

                            //uploadData();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }


    }

    public void btnAdd(View view) {

        count++;
        dec++;
        if (count == 1) {
            etSetQuestionAnswer3.setVisibility(VISIBLE);

        }
        if (count == 2) {
            etSetQuestionAnswer4.setVisibility(VISIBLE);
            btnAdd.setText("Hide");


        }
        if (dec == 3) {
            etSetQuestionAnswer4.setVisibility(GONE);
            btnAdd.setText("Hide");
            count--;
        }
        if (dec == 4) {
            etSetQuestionAnswer3.setVisibility(GONE);
            btnAdd.setText("Add");
            count=0;
            dec=0;

        }

    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String item = parent.getItemAtPosition(pos).toString();
            if (item.equals("Text")) {
                llTextAnswer.setVisibility(VISIBLE);
                llPictureAnswer.setVisibility(GONE);
            }
            if (item.equals("Picture")) {
                llPictureAnswer.setVisibility(VISIBLE);
                llTextAnswer.setVisibility(GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                if (CURRENTLY_SELECTING_IMAGEVIEW == FIRST_IMAGE) {
                    FIRST_IMAGE_PATH = resultUri;
                    Glide.with(context).load(resultUri).into(imgSetQuestionAnswer1);


                } else if (CURRENTLY_SELECTING_IMAGEVIEW == SECOND_IMAGE) {
                    SECOND_IMAGE_PATH = resultUri;
                    Glide.with(context).load(resultUri).into(imgSetQuestionAnswer2);

                }

                // imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_IMG) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    int CurrentImageSelect = 0;


                    if (CURRENTLY_SELECTING_IMAGEVIEW == FIRST_IMAGE) {
                        imgSetQuestionAnswer1.setImageURI(data.getClipData().getItemAt(CurrentImageSelect).getUri());

                    } else if (CURRENTLY_SELECTING_IMAGEVIEW == SECOND_IMAGE) {
                        imgSetQuestionAnswer2.setImageURI(data.getClipData().getItemAt(CurrentImageSelect).getUri());

                    }


                }

            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            encodeBitmapAndSaveToFirebase(thumbnail);

            String path = saveImage(thumbnail);

            Glide.with(QuestionActivity.this).load(path).into(getImageView());

            if (getImageView() == imgSetQuestionAnswer1) {
                path1 = path;
            } else {
                path2 = path;
            }

            Toast.makeText(QuestionActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

        }
    }


    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference()
                .child("ImageFolder");
        ref.setValue(imageEncoded);
        StorageReference im = FirebaseStorage.getInstance().getReference().child("ImageFolder");
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QuestionActivity.this, SurveyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}
