package com.example.android.calhacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    String MACID;
    String theclass;
    String classNo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        Intent intent = getIntent();
        MACID = intent.getStringExtra("MACID");
        TextView textView = (TextView)findViewById(R.id.classNo);
        if(MACID.equals("98:4F:EE:0F:38:99")){
            textView.setText("You have signed in to CS61A");
            theclass = "CS61A";
            classNo = "1";
        } else if (MACID.equals("98:4F:EE:0F:2F:87")){
            textView.setText("You have signed in to CS61B");
            theclass = "CS61B";
            classNo = "2";
        } else {
            textView.setText("You have signed in to CS70");
            theclass = "CS70";
            classNo = "3";
        }
    }
    public void login(View view){
        EditText id = (EditText)findViewById(R.id.id);
        EditText pw = (EditText)findViewById(R.id.pw);
        String idCode = id.getText().toString();
        String pwCode = pw.getText().toString();

        if (pwCode.equals("1234") && (idCode.equals("1") || idCode.equals("2") || idCode.equals("3"))){
            Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
            intent.putExtra("ID",idCode);
            intent.putExtra("theclass",theclass);
            intent.putExtra("classNo",classNo);
            startActivity(intent);
        }else{
            id.setText("");
            pw.setText("");
            Toast.makeText(getApplicationContext(),"wrong ID or password",Toast.LENGTH_SHORT).show();
        }
    }
}