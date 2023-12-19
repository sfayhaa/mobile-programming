package com.example.crudapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText NameMhs;
    private String NRPMhs;
    private ListView listView;
    private Button addButton, showButton, deleteButton, updateButton, showallButton;

    private final DBHelper DB = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NameMhs = (EditText) findViewById(R.id.etName);

        addButton = (Button) findViewById(R.id.btnSave);
        addButton.setOnClickListener(this);

        updateButton = (Button) findViewById(R.id.btnUpdate);
        updateButton.setOnClickListener(this);

        deleteButton = (Button) findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(this);

        showButton = (Button) findViewById(R.id.btnShow);
        showButton.setOnClickListener(this);

        showallButton = (Button) findViewById(R.id.btnShowAll);
        showallButton.setOnClickListener(this);

        listView = findViewById(R.id.listView);

        Button inputNRPButton = findViewById(R.id.btnInputNRP);
        inputNRPButton.setOnClickListener(v -> showInputDialog());
    }

    public void insert(){
        Boolean insert = this.DB.insertData(NameMhs.getText().toString(),this.NRPMhs);
        try {
            this.checkResult(insert);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("Range")
    public void getData(){
        String name = NameMhs.getText().toString();
        String nrp = this.NRPMhs;

        try {
            if (name != null || (name != null && nrp !=null)){
                String[] result = this.DB.checkName(name);
                Toast.makeText(this, result[0] + " " + result[1], Toast.LENGTH_LONG).show();
            }
            else if (nrp!=null) {
                String[] result = this.DB.checkNrp(nrp);
                Toast.makeText(this, result[0] + " " + result[1], Toast.LENGTH_LONG).show();
            }
            else { Toast.makeText(this, "Data NULL", Toast.LENGTH_LONG).show();}
        }
        catch(Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void update() {
        Boolean update = this.DB.updateName(NameMhs.getText().toString(), this.NRPMhs);
        try     {this.checkResult(update);}
        catch(Exception e)  {Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();}
    }

    public void delete() {
        Boolean delete = this.DB.deleteNrpByName(NameMhs.getText().toString());
        try     { this.checkResult(delete);}
        catch(Exception e)  { Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();}
    }

    public void showAll(){
        String[] data = this.DB.showAll();
        if (data != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_user, R.id.tvName, data);
            listView.setAdapter(adapter);
        }
    }
    private void checkResult(Boolean result) {
        String[] output;
        if(result) {
            output = this.DB.checkName(NameMhs.getText().toString());
            Toast.makeText(this, output[0] + " " + output[1], Toast.LENGTH_LONG).show();
        }
        else { Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();}
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.input_dialog, null);
        builder.setView(dialogView);
        final EditText inputEditText = dialogView.findViewById(R.id.etInputText);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String nrp = inputEditText.getText().toString();
            NRPMhs=nrp;
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSave) {
            this.insert();
        }
        else if (view.getId() == R.id.btnShow) {
            this.getData();
        }
        else if (view.getId() == R.id.btnDelete){
            this.delete();
        }
        else if (view.getId() == R.id.btnUpdate){
            this.update();
        }
        else if (view.getId() == R.id.btnShowAll){
            this.showAll();
        }
    }
}