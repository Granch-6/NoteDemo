package com.example.notedemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notepad.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private Button btn_new;
    private Button btn_exit;
    private EditText editText;
    private byte[] buffer;
    private List<String> filenames ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_interface);
        listView=findViewById(R.id.listView);
        File file = Environment.getExternalStorageDirectory();
        File[] files = file.listFiles();
        if(files!=null){
            filenames =getFilesAllName(files);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.text, filenames);
            listView.setAdapter(adapter);
        }
        btn_exit=findViewById(R.id.exit);
        btn_new = findViewById(R.id.newFile);
        btn_exit.setOnClickListener(this);
        btn_new.setOnClickListener(this);
        listView.setOnItemClickListener(this);

    }

    public static List<String> getFilesAllName(File[] files){
        List<String> s =new ArrayList<>();
        int length = files.length;
        for(int i =0;i<length;i++){
            String name = files[i].getAbsolutePath();
            if (name.endsWith(".txt")){
                s.add(name);
            }
        }
        return s;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newFile:
                Intent intent = new Intent(SelectActivity.this,EditorActivity.class);
                intent.putExtra("key","");
                startActivity(intent);

            case R.id.exit:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        File file = Environment.getExternalStorageDirectory();
        File[] files = file.listFiles();
        List<String> filenames =getFilesAllName(files);

        Intent intent = new Intent(this,EditorActivity.class);
        intent.putExtra("key",filenames.get(i));
        editText=findViewById(R.id.editText);
        try(FileInputStream fileInputStream = new FileInputStream(filenames.get(i))) {
            buffer= new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            intent.putExtra("content",new String(buffer));
            System.out.println("Read finish");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        File file = Environment.getExternalStorageDirectory();
        File[] files = file.listFiles();
        filenames.clear();
        if(files!=null){
            filenames =getFilesAllName(files);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.text, filenames);
            listView.setAdapter(adapter);
        }

    }

}
