package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText text;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.editTextTextMultiLine);
        saveBtn = (Button)  findViewById(R.id.button);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    writeToFile(text.getText().toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            if(readFromFile() != null){
                text.setText(readFromFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String message) throws FileNotFoundException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("todolist.txt", Context.MODE_PRIVATE));
        try {
            outputStreamWriter.write(message);
            outputStreamWriter.close(); // always close your streams
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile() throws IOException {
        String result = "";
        int count = 1;
        InputStream inputStreamReader = openFileInput("todolist.txt");
        if(inputStreamReader != null){
            InputStreamReader inputStreamReader1 = new InputStreamReader(inputStreamReader);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader1);
            String tempString = "";
            StringBuilder stringBuilder = new StringBuilder();
            while((tempString = bufferedReader.readLine()) != null){
                stringBuilder.append(count++ + ". " + tempString+"\n");
            }
            inputStreamReader.close();
            result = stringBuilder.toString();
        }
        return result;
    }
}