package com.pies.platform.admin;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pies.platform.Login;
import com.pies.platform.ProgressDialogFragment;
import com.pies.platform.R;
import com.pies.platform.admin.model.Admin_data;
import com.pies.platform.admin.model.imageData;
import com.pies.platform.custom.RoundFormation;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Admin_profile extends AppCompatActivity implements  EasyPermissions.PermissionCallbacks {
    public static final String TAG = "NewPost";

    private static final int THUMBNAIL_MAX_DIMENSION = 240;
    private static final int FULL_SIZE_MAX_DIMENSION = 500;
    private static final String TAG_DIALOG_FRAGMENT = "DIALOG_FRAGMENT";
    private Button mSubmitButton;

    private ImageView mImageView;
    private Uri mFileUri;
    private Bitmap mResizedBitmap;
    private Bitmap mThumbnail;
    private Bitmap selectedBitmap;
    private Bitmap thumbnail;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
private  ProgressDialog progressDialog;
    private static final int TC_PICK_IMAGE = 101;
    private static final int RC_CAMERA_PERMISSIONS = 102;
    FirebaseUser currentUser ;
    private TextView email, name;
    CollapsingToolbarLayout collapsingToolbarLayout;

    private static final String[] cameraPerms = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private DatabaseReference mDatabase;
    private String title;
private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(Admin_profile.this);
       progressDialog.setMessage("Updating Profile    m...");
        progressDialog.setCancelable(false);


        email = (TextView) findViewById(R.id.user_email);
        name = (TextView) findViewById(R.id.username);
        mImageView = (ImageView) findViewById(R.id.profileicon);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [START auth_state_listener]

mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               currentUser = firebaseAuth.getCurrentUser();

                if (currentUser == null) {
                    Intent goback = new Intent(Admin_profile.this, Login.class);
                    startActivity(goback);
                    finish();
                } else {
                    checkUser(currentUser.getUid());
                    if (currentUser.getPhotoUrl() != null) {
                        String url = currentUser.getPhotoUrl().toString();
                        setprofileImage(url);
                        if (currentUser.getEmail() != null) {
                            email.setText(currentUser.getEmail());
                        }
                        if (currentUser.getDisplayName() != null) {
                            name.setText(currentUser.getDisplayName());
                        }
                    }
                }
            }};
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showImagePicker();


            }
        });

       /* Bitmap selectedBitmap = getSelectedBitmap();
        Bitmap thumbnail = getThumbnail();
        if (selectedBitmap != null) {

            mImageView.setImageBitmap(selectedBitmap);
            mResizedBitmap = selectedBitmap;



        }
        if (thumbnail != null) {
            mThumbnail = thumbnail;

        }*/
    }

    @AfterPermissionGranted(RC_CAMERA_PERMISSIONS)
    private void showImagePicker() {
        // Check for camera permissions
        if (!EasyPermissions.hasPermissions(this, cameraPerms)) {
            EasyPermissions.requestPermissions(this,
                    "This sample will upload a picture from your Camera",
                    RC_CAMERA_PERMISSIONS, cameraPerms);
            return;
        }

        // Choose file storage location
        File file = new File(getExternalCacheDir(), UUID.randomUUID().toString());
        mFileUri = Uri.fromFile(file);

        // Camera
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam){
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
            cameraIntents.add(intent);
        }

        // Image Picker
        Intent pickerIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent chooserIntent = Intent.createChooser(pickerIntent,
                getString(R.string.picture_chooser_title));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new
                Parcelable[cameraIntents.size()]));
        startActivityForResult(chooserIntent, TC_PICK_IMAGE);
    }

    public void onPostUploaded(final String error) {
        Admin_profile.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              //  mSubmitButton.setEnabled(true);
                dismissProgressDialog();
                if (error == null) {
                    Toast.makeText(Admin_profile.this, "Post created!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Admin_profile.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TC_PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    isCamera = MediaStore.ACTION_IMAGE_CAPTURE.equals(data.getAction());
                    String u =currentUser.getUid().toString();
                    upLoad(u);
                }
                if (!isCamera) {
                    mFileUri = data.getData();
                    String u =currentUser.getUid().toString();
                    upLoad(u);
                }
                Log.d(TAG, "Received file uri: " + mFileUri.getPath());
                String u =currentUser.getUid().toString();
                upLoad(u);
                resizeBitmap(mFileUri, THUMBNAIL_MAX_DIMENSION);
                resizeBitmap(mFileUri, FULL_SIZE_MAX_DIMENSION);




            }
        }
    }

    @Override
    protected void onResume() {
        if(mResizedBitmap != null){
            mResizedBitmap = null;
        }
        if(mThumbnail != null){
            mThumbnail = null;
        }
        super.onResume();

    }

    @Override
    public void onDestroy() {
        // store the data in the fragment
        if (mResizedBitmap != null) {

            setSelectedBitmap(mResizedBitmap);


        }
        if (mThumbnail != null) {

            setThumbnail(mThumbnail);
        }
        super.onDestroy();
    }
    public void onBitmapResized(Bitmap resizedBitmap, int mMaxDimension) {
        if (resizedBitmap == null) {
            Log.e(TAG, "Couldn't resize bitmap in background task.");
            Toast.makeText(getApplicationContext(), "Couldn't resize bitmap.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (mMaxDimension == THUMBNAIL_MAX_DIMENSION) {
            mThumbnail = resizedBitmap;
        } else if (mMaxDimension == FULL_SIZE_MAX_DIMENSION) {
            mResizedBitmap = resizedBitmap;
          //  mImageView.setImageBitmap(mResizedBitmap);
        }

        if (mThumbnail != null && mResizedBitmap != null) {
//            mSubmitButton.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }





    // Upload Task and Resize tast
    public void setSelectedBitmap(Bitmap bitmap) {
        this.selectedBitmap = bitmap;
    }

    public Bitmap getSelectedBitmap() {
        return selectedBitmap;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void resizeBitmap(Uri uri, int maxDimension) {
        LoadResizedBitmapTask task = new LoadResizedBitmapTask(maxDimension);
        task.execute(uri);
    }

    public void uploadPost(Bitmap bitmap, String inBitmapPath, Bitmap thumbnail, String inThumbnailPath,
                           String inFileName, String inPostText) {
        UploadPostTask uploadTask = new UploadPostTask(bitmap, inBitmapPath, thumbnail, inThumbnailPath, inFileName, inPostText);
        uploadTask.execute();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    class UploadPostTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Bitmap> bitmapReference;
        private WeakReference<Bitmap> thumbnailReference;
        private String postText;
        private String fileName;
        private String bitmapPath;
        private String thumbnailPath;

        public UploadPostTask(Bitmap bitmap, String inBitmapPath, Bitmap thumbnail, String inThumbnailPath,
                              String inFileName, String inPostText) {
            bitmapReference = new WeakReference<Bitmap>(bitmap);
            thumbnailReference = new WeakReference<Bitmap>(thumbnail);
            postText = inPostText;
            fileName = inFileName;
            bitmapPath = inBitmapPath;
            thumbnailPath = inThumbnailPath;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            Bitmap fullSize = bitmapReference.get();
            final Bitmap thumbnail = thumbnailReference.get();
            if (fullSize == null || thumbnail == null) {
                return null;
            }
            FirebaseStorage storageRef = FirebaseStorage.getInstance();
            StorageReference photoRef = storageRef.getReferenceFromUrl("gs://" + getString(R.string.google_storage_bucket));


            Long timestamp = System.currentTimeMillis();
            final StorageReference fullSizeRef = photoRef.child("admin-images").child("full").child(currentUser.getUid()).child(timestamp.toString()).child(fileName + ".jpg");
            final StorageReference thumbnailRef = photoRef.child("admin-images").child("thumb").child(currentUser.getUid()).child(timestamp.toString()).child(fileName + ".jpg");
            Log.d(TAG, fullSizeRef.toString());
            Log.d(TAG, thumbnailRef.toString());

            ByteArrayOutputStream fullSizeStream = new ByteArrayOutputStream();
            fullSize.compress(Bitmap.CompressFormat.JPEG, 90, fullSizeStream);
            byte[] bytes = fullSizeStream.toByteArray();
            fullSizeRef.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri fullSizeUrl = taskSnapshot.getDownloadUrl();

                    ByteArrayOutputStream thumbnailStream = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 70, thumbnailStream);
                    thumbnailRef.putBytes(thumbnailStream.toByteArray())
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    DatabaseReference postsRef = ref.child("images");
                                    final String newPostKey = postsRef.push().getKey();
                                    final Uri thumbnailUrl = taskSnapshot.getDownloadUrl();

                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setPhotoUri(thumbnailUrl)
                                            .build();
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task != null){

                                                Log.e(TAG, "Profile updated " );
                                            }
                                            else{
                                                Log.e(TAG, "Profile update failed");
                                            }
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.e(TAG, "profile update sucess");
                                        }
                                    });
                                    imageData newPost = new imageData(currentUser.getUid(), fullSizeUrl.toString(),
                                            thumbnailUrl.toString());

                                    Map<String,Object> newPostMap = newPost.toAdminImage() ;

                                    Map<String, Object> updatedUserData = new HashMap<>();

                                    updatedUserData.put("/images/"
                                            + newPostKey, newPostMap);
                                   // updatedUserData.put("/admin-images/" + currentUser.getUid(),newPostMap);

                                    ref.updateChildren(updatedUserData, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError firebaseError, DatabaseReference databaseReference) {
                                            if (firebaseError == null) {
                                                onPostUploaded(null);

                                            } else {

                                                Toast.makeText(Admin_profile.this, "Unable to upload", Toast.LENGTH_SHORT).show();
                                                Log.e(TAG, "Unable to create new post: " + firebaseError.getMessage());
                                                FirebaseCrash.report(firebaseError.toException());
                                                onPostUploaded(getApplicationContext().getString(
                                                        R.string.error_upload_task_create));
                                            }
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            FirebaseCrash.logcat(Log.ERROR, TAG, "Failed to upload post to database.");
                            FirebaseCrash.report(e);
                            onPostUploaded(getApplicationContext().getString(
                                    R.string.error_upload_task_create));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseCrash.logcat(Log.ERROR, TAG, "Failed to upload post to database.");
                    FirebaseCrash.report(e);
                    onPostUploaded(getApplicationContext().getString(
                            R.string.error_upload_task_create));
                }
            });
            // TODO: Refactor t nested callbacks.
            return null;
        }
    }

    class LoadResizedBitmapTask extends AsyncTask<Uri, Void, Bitmap> {
        private int mMaxDimension;

        public LoadResizedBitmapTask(int maxDimension) {
            mMaxDimension = maxDimension;
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Uri... params) {
            Uri uri = params[0];
            if (uri != null) {

                // Implement thumbnail + fullsize later.
                Bitmap bitmap = null;
                try {
                    bitmap = decodeSampledBitmapFromUri(uri, mMaxDimension, mMaxDimension);
                } catch (FileNotFoundException e) {
                    Log.e(TAG, "Can't find file to resize: " + e.getMessage());
                    FirebaseCrash.report(e);
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred during resize: " + e.getMessage());
                    FirebaseCrash.report(e);
                }
                return bitmap;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            onBitmapResized(bitmap, mMaxDimension);
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromUri(Uri fileUri, int reqWidth, int reqHeight)
            throws IOException {
        InputStream stream = new BufferedInputStream(
                getApplicationContext().getContentResolver().openInputStream(fileUri));
        stream.mark(stream.available());
        BitmapFactory.Options options = new BitmapFactory.Options();
        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, options);
        stream.reset();
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        BitmapFactory.decodeStream(stream, null, options);
        // Decode bitmap with inSampleSize set
        stream.reset();
        return BitmapFactory.decodeStream(stream, null, options);
    }


    protected void showProgressDialog(String message) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getExistingDialogFragment();
        if (prev == null) {
            ProgressDialogFragment fragment = ProgressDialogFragment.newInstance(message);
            fragment.show(ft, TAG_DIALOG_FRAGMENT);
        }
    }

    protected void dismissProgressDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getExistingDialogFragment();
        if (prev != null) {
            ft.remove(prev).commit();
        }
    }
    private Fragment getExistingDialogFragment() {
        return getSupportFragmentManager().findFragmentByTag(TAG_DIALOG_FRAGMENT);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    public void upLoad(String uid){
        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        Long timestamp = System.currentTimeMillis();

        String bitmapPath = "/" + "admin-images" + "/full/" + "/" + uid+ "/" + timestamp.toString() + "/";
        String thumbnailPath = "/" + "admin-images" + "/thumb/" + "/" + uid + "/" + timestamp.toString() + "/";
        uploadPost(mResizedBitmap, bitmapPath, mThumbnail, thumbnailPath, mFileUri.getLastPathSegment(),""
        );
    }

    public  void setprofileImage(String imageurl){

        Picasso.with(getApplicationContext())
                .load(imageurl)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .error(R.drawable.ic_account_circle_black_24dp)
                .transform(new RoundFormation(50, 4))
                //.resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .into(mImageView);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void checkUser(String uid) {

        mDatabase.child("admin-Profile").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Admin_data item = dataSnapshot.getValue(Admin_data.class);
                if(item.getName() != null){
                    String name1 = item.getName();
                  //  Toast.makeText(getActivity(), aNam, Toast.LENGTH_SHORT).show();
                    name.setText(name1);
                }
                if(item.getEmail() != null){
                    title = item.getName();
                    email.setText(item.getEmail().toString());
                    collapsingToolbarLayout.setTitle(title);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "url not founded", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
