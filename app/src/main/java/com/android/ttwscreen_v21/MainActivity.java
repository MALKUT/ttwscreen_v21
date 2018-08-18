package com.android.ttwscreen_v21;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    Button bt_TakePhoto;
    ImageView imageView;
    static public final String LOG_TAG = MainActivity.class.getSimpleName();
    static String mCurrentPhotoPath = "";
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID+".fileprovider";
    String albumName = "abb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_TakePhoto = findViewById(R.id.btTakePhoto);
        imageView = findViewById(R.id.ivPhoto);
        dispatchTakePictureIntent();

    }
    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Diretorio nao criado");

        } else {

        }
        return file;
    }//end getAlbumStorageDir
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getAlbumStorageDir(albumName);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Log.e(LOG_TAG,"Error");
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, AUTHORITY, photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }//enddispatchTakePictureIntent
}//end

