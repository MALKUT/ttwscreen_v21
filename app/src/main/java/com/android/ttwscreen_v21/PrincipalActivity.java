package com.android.ttwscreen_v21;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
public class PrincipalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String item;
    Spinner spinner;
    Button button;
    static public final String LOG_TAG = MainActivity.class.getSimpleName();
    Helper helper = new Helper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        button = findViewById(R.id.bt_Fotografar);
        spinner = findViewById(R.id.spinPatio);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.patio, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        item = parent.getItemAtPosition(position).toString();

        //helper.setIdPatio(item);
        //mostrar item selecionado spinner
        //Toast.makeText(parent.getContext(),"Selecionado: "+ item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void avancar(View view) {
        Intent intent = new Intent(PrincipalActivity.this,MainActivity.class);
        intent.putExtra(LOG_TAG,item);

        startActivity(intent);
    }
}
