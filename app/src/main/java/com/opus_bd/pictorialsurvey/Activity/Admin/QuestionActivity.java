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

import static android.view.View.VISIBLE;
import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY_ID;

public class QuestionActivity extends AppCompatActivity {

    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    private static final int PICK_IMG = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int uploads = 0;
    Survey survey;
    int count = 0;
    //Firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    TextInputEditText etSetQuestion;
    TextInputEditText etSetQuestionAnswer1;
    TextInputEditText etSetQuestionAnswer2;
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
   /*     imgSetQuestionAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog(imgSetQuestionAnswer1);
                setImageView(imgSetQuestionAnswer1);
                CURRENTLY_SELECTING_IMAGEVIEW=FIRST_IMAGE;
                //path1=getPath();
            }
        });
        imgSetQuestionAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog(imgSetQuestionAnswer2);
                setImageView(imgSetQuestionAnswer2);
                CURRENTLY_SELECTING_IMAGEVIEW=SECOND_IMAGE;


                //path2=getPath();
            }
        });
        */


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
        imgSetQuestionAnswer1 = findViewById(R.id.imgSetQuestionAnswer1);
        imgSetQuestionAnswer2 = findViewById(R.id.imgSetQuestionAnswer2);
        llTextAnswer = findViewById(R.id.llTextAnswer);
        llPictureAnswer = findViewById(R.id.llPictureAnswer);
        spnGender = findViewById(R.id.spnGender);

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
        servayQuestionModel.setOptionType("TEXT");
        databaseReference.child(key).setValue(servayQuestionModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(QuestionActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        Map<String, String> firebaseDataMap = new HashMap<>();
        firebaseDataMap.put(Constant.SETQUESTION, etSetQuestion.getText().toString());
        firebaseDataMap.put(Constant.ANSWER1, etSetQuestionAnswer1.getText().toString());
        firebaseDataMap.put(Constant.ANSWER2, etSetQuestionAnswer2.getText().toString());


        Question question = new Question();
        question.setQuestion(etSetQuestion.getText().toString());
        question.setAnswer1(etSetQuestionAnswer1.getText().toString());
        question.setAnswer2(etSetQuestionAnswer2.getText().toString());
        databaseReference.push().setValue(question);



        Query query = databaseReference.orderByChild(Constant.SETSURVEY).equalTo(survey.getSurveyName());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


      /*  mDatabaseReference = mDatabase.getReference().child("question");
        mDatabaseReference.setValue(question);*/
/*
        Map<String, String> firebaseDataMap = new HashMap<>();

        Question question = new Question( );
        question.setAnswer1(etSetQuestionAnswer1.getText().toString());
        question.setAnswer2(etSetQuestionAnswer2.getText().toString());
        .push().setValue(scheduleModel);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("question");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.child(etSetQuestion.getText().toString()).getRef().setValue(question);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        usersRef.addListenerForSingleValueEvent(eventListener);*/
        Intent intent = new Intent(this, SurveyActivity.class);
        intent.putExtra(Constant.EXTRA_ITEM, survey);
        //  intent.putExtra(Constant.EXTRA_ITEM1, survey1);*/
        startActivity(intent);


    }

    public void SubmittoDb(String a, String b) {
        count = count + 1;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.QUESTION).child(survey.getSurveyName());
        //String key = databaseReference.push().getKey();

        Map<String, String> firebaseDataMap = new HashMap<>();
        firebaseDataMap.put(Constant.SETQUESTION, etSetQuestion.getText().toString());
        firebaseDataMap.put(Constant.IMAGE_ANSWER1, a);
        firebaseDataMap.put(Constant.IMAGE_ANSWER2, b);


        Question question = new Question();
        question.setQuestion(etSetQuestion.getText().toString());
        question.setImageanswer1(a);
        question.setImageanswer2(b);
        databaseReference.push().setValue(question);

        Query query = databaseReference.orderByChild(Constant.SETSURVEY).equalTo(survey.getSurveyName());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

   /*     final Question question = new Question();
        question.setQuestion(etSetQuestion.getText().toString());
        question.setAnswer1(a);
        question.setAnswer2(b);
        question.setUid(String.valueOf(count));
      *//*  mDatabaseReference = mDatabase.getReference().child("question");
        mDatabaseReference.setValue(question);*//*


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("question");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.child(etSetQuestion.getText().toString()).getRef().setValue(question);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        usersRef.addListenerForSingleValueEvent(eventListener);*/

    }

    public void SubmitPicture(View view) {
      /* SubmittoDb(path1, path2);
        upload();
        Intent intent = new Intent(this, SurveyActivity.class);
       /* intent.putExtra(Constant.EXTRA_ITEM, survey);
        intent.putExtra(Constant.EXTRA_ITEM1, survey1);*/
        //startActivity(intent);
        if (FIRST_IMAGE_PATH != null && SECOND_IMAGE_PATH != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("1st image Uploading...");
            progressDialog.show();

            FIRST_UPLOADED_IMAGE_LINK = null;
            SECOND_UPLOADED_IMAGE_LINK = null;

            ref = storageReference.child("images/" + UUID.randomUUID().toString());

            //upload first image
            ref.putFile(FIRST_IMAGE_PATH)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(context, ref.getPath(), Toast.LENGTH_SHORT).show();
                            // Glide.with(context).load(resultUri).into(imgSetQuestionAnswer2);
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
                                                            //final DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.QUESTION).child(survey.getSurveyName());
                                                            //String key = databaseReference.push().getKey();
                                                            DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.SURVEY_LIST).child(CURRENTLY_SHOWING_SURVEY_ID).child("options");
                                                            String key = databaseReference.push().getKey();
                                                            ServayQuestionModel servayQuestionModel = new ServayQuestionModel();
                                                            servayQuestionModel.setQuestion(etSetQuestion.getText().toString().trim());
                                                            String option_1_key = databaseReference.child(key).push().getKey();
                                                            servayQuestionModel.setOption1(new Option1(option_1_key, FIRST_UPLOADED_IMAGE_LINK));
                                                            servayQuestionModel.setQuestionKey(key);

                                                            String option_2_key = databaseReference.child(key).push().getKey();
                                                            servayQuestionModel.setOption2(new Option2(option_2_key, SECOND_UPLOADED_IMAGE_LINK));
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

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String item = parent.getItemAtPosition(pos).toString();
            if (item.equals("Text")) {
                llTextAnswer.setVisibility(VISIBLE);
                llPictureAnswer.setVisibility(View.GONE);
            }
            if (item.equals("Picture")) {
                llPictureAnswer.setVisibility(VISIBLE);
                llTextAnswer.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    private void showPictureDialog(final View view) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
      /*  Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(galleryIntent, GALLERY);*/

        //we will pick images
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMG);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
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


                 /*   path1 = ImageList.get(0).toString();
                    uploadImage(ImageList.get(0));
                    path2 = ImageList.get(1).toString();
                    uploadImage(ImageList.get(1));
                   /* textView.setVisibility(View.VISIBLE);
                    textView.setText("You Have Selected "+ ImageList.size() +" Pictures" );
                    choose.setVisibility(View.GONE);*/
                }

            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            encodeBitmapAndSaveToFirebase(thumbnail);
            // imgSetQuestionAnswer1.setImageBitmap(thumbnail);
            // saveImage(thumbnail);

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

    public void upload() {

        final StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        for (uploads = 0; uploads < ImageList.size(); uploads++) {
            Uri Image = ImageList.get(uploads);
            final StorageReference imagename = ImageFolder.child("image/" + Image.getLastPathSegment());

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = String.valueOf(uri);
                            SendLink(url);
                        }
                    });

                }
            });


        }


    }

    private void SendLink(String url) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("link", url);
        mDatabaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(QuestionActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                ImageList.clear();
            }
        });


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

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {

                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void uploadImage(Uri filePath) {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(QuestionActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(QuestionActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QuestionActivity.this, SurveyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}
