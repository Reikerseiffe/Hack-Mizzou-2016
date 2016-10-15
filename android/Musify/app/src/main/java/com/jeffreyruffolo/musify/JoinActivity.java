package com.jeffreyruffolo.musify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        Button joinButton = (Button) findViewById(R.id.join_button);
        joinButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(JoinActivity.this, EnterActivity.class));
            }
        });

        Button createButton = (Button) findViewById(R.id.host_button);
        createButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(JoinActivity.this, CreateActivity.class));
            }
        });
    }
}
