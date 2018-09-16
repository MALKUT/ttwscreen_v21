package com.android.ttwscreen_v21;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ttwscreen_v21.Helper.Helper;
import com.android.ttwscreen_v21.CdContainer;
import com.android.ttwscreen_v21.Container.CodContainer;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Helper helper = new Helper();
    CodContainer codContainer=new CodContainer();

    Button bt_TakePhoto;
    Button bt_Finalizar;
    ImageView imageView;
    EditText edtCodContainer;
    TextView tvPatio;
    static public final String LOG_TAG = MainActivity.class.getSimpleName();
    static String mCurrentPhotoPath = "";
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID+".fileprovider";
    private String idContainer;
    String albumName;
    private Dialog alerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_TakePhoto = findViewById(R.id.btFotografar);
        bt_Finalizar=findViewById(R.id.btfinalizar);
        imageView = findViewById(R.id.ivPhoto);
        edtCodContainer=findViewById(R.id.edtCodContainer);
        tvPatio = findViewById(R.id.tvPatio);
        startBasic();

    }
    private void startBasic (){
        Intent intent = getIntent();
        String message = intent.getStringExtra(PrincipalActivity.LOG_TAG);
        tvPatio.setText(message);

    }

    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Diretorio nao criado");
        }
        return file;
    }//end getAlbumStorageDir
    public void btnFotogrfar(View view) {
        idContainer = edtCodContainer.getText().toString();
        if (!idContainer.equals("")){
            dispatchTakePictureIntent();
        }else {
            //msg"para o usuario"
            Toast.makeText(MainActivity.this,helper.PreenCampo,Toast.LENGTH_LONG).show();
            //identifica o campo zavio
            edtCodContainer.setBackground(helper.shapeDrawable());
        }

    }
    public void btnFinalizar(View view){
        setAlerta();
    }
    private File createImageFile() throws IOException {

        albumName = helper.idContainer;

        if (verificaContainer()){
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss",Locale.getDefault()).format(new Date());
            String imageFileName = albumName+"_" + timeStamp + "_";
            File storageDir = getAlbumStorageDir(albumName);
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);
            mCurrentPhotoPath = image.getAbsolutePath();
            return image;
        }else {
            Toast.makeText(MainActivity.this,helper.CamVazio,Toast.LENGTH_LONG).show();
            edtCodContainer.setBackground(helper.shapeDrawable());
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
        // idcontainer = albumname
        return codContainer.isContainerNumberValid (this,helper.idContainer);
    }
}//end

