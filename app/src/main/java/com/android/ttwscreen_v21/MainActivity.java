package com.android.ttwscreen_v21;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Helper helper;
    CdContainer container= new CdContainer();
    Button bt_TakePhoto;
    Button bt_Finalizar;
    ImageView imageView;
    EditText edtCodContainer;
    TextView tvPatio;
    static public final String LOG_TAG = MainActivity.class.getSimpleName();
    static String mCurrentPhotoPath = "";
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID+".fileprovider";

    String albumName;
    private Dialog alerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_TakePhoto = findViewById(R.id.bt_Fotografar);
        bt_Finalizar=findViewById(R.id.btfinalizar);
        imageView = findViewById(R.id.ivPhoto);
        edtCodContainer=findViewById(R.id.edtCodContainer);
        tvPatio = findViewById(R.id.tvPatio);

        Intent intent = getIntent();
        String message = intent.getStringExtra(PrincipalActivity.LOG_TAG);
        tvPatio.setText(message);

    }
    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Diretorio nao criado");

        } else {

        }
        return file;
    }//end getAlbumStorageDir
    public void btnFotogrfar(View view) {
        dispatchTakePictureIntent();
    }
    public void btnFinalizar(View view){
        setAlerta();
    }
    private File createImageFile() throws IOException {
        albumName = edtCodContainer.getText().toString();
        if (!albumName.equals("")&& !verificaContainer()==false){
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
            String imageFileName = albumName+"_" + timeStamp + "_";
            File storageDir = getAlbumStorageDir(albumName);
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);
            mCurrentPhotoPath = image.getAbsolutePath();
            return image;
        }else {
            Toast.makeText(MainActivity.this,"Necessário Preencher campo Código do Container ou Número Container Errado !",Toast.LENGTH_LONG).show();
        }
        return null;
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


    private void setAlerta(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Container: "+albumName);
        builder.setMessage("Deseja Finalizar? ");
        builder.setPositiveButton("Confirmar ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"confirmar="+which, Toast.LENGTH_SHORT).show();
                edtCodContainer.getText().clear();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"cancelar=" + which, Toast.LENGTH_SHORT).show();
            }
        });
        alerta=builder.create();
        alerta.show();
    }
    public Boolean verificaContainer(){

        return container.isContainerNumberValid(albumName);
    }
}//end

