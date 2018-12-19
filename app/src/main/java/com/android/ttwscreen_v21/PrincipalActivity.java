package com.android.ttwscreen_v21;
import com.android.ttwscreen_v21.Helper.Helper;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PrincipalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Helper helper= new Helper();
   private String idContainer;
    Spinner spinner;
    Button button;
    static public final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        button = findViewById(R.id.btFotografar);
        spinner = findViewById(R.id.spinPatio);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.patio, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /**
         * Recebe o o valor selecionado do spinner
         */
        idContainer = parent.getItemAtPosition(position).toString();
        /**
         * Envia o valor recebido na variável "item" para a classe Helper
         */
        helper.idPatio=idContainer;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void avancar(View view) {
        Intent intent = new Intent(PrincipalActivity.this,MainActivity.class);
        intent.putExtra(LOG_TAG,idContainer);
        startActivity(intent);
    }
}
