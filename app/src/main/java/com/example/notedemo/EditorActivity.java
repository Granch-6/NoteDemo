package com.example.notedemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.notepad.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class EditorActivity extends Activity implements View.OnClickListener{
    private Button btn_save;
    private Button btn_delete;
    private EditText editText;
    private static String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor);
        btn_save = findViewById(R.id.save);
        btn_delete = findViewById(R.id.delete);
        editText = findViewById(R.id.editText);
        Intent intent = getIntent();
        fileName = intent.getStringExtra("key");
        String content = intent.getStringExtra("content");
        System.out.println(content+"!!!!!!");
        if(content!=null){
            editText.setText(getIntent().getStringExtra("content"));
        }
        System.out.println(content+"!!!!!!");
        btn_save.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

    }
    public static String getRandomFileName(){
        if (fileName == null || fileName.equals("")){
            String string  = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Random random = new Random();
            int randomNumber = (int) (random.nextDouble()*(99999-10000+1))+10000;
            fileName = Environment.getExternalStorageDirectory() + "/untitled_"+randomNumber + "_" +string+".txt";
        }
        return fileName;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save:
                File file = new File(getRandomFileName());
                editText = findViewById(R.id.editText);
                String textString = editText.getText().toString();
                System.out.println(textString);
                try(FileOutputStream fileOutputStream =  new FileOutputStream(file)){
                    fileOutputStream.write(textString.getBytes());
                    Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
                    System.out.println("Sucess!!");
                    Intent intent = new Intent(this,SelectActivity.class);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.delete:
                try {
                    File file1 = new File(getIntent().getStringExtra("key"));
                    if(file1.delete()){
                        Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                        System.out.println(file1.getName() + " Delete!!!");
                    }else{
                        Toast.makeText(this,"Error!!!",Toast.LENGTH_SHORT).show();
                        System.out.println("Error!!!");
                    };
                    Intent intent = new Intent(this,SelectActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }
    }


}
