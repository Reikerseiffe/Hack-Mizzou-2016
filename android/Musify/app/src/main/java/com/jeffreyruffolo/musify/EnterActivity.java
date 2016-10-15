package com.jeffreyruffolo.musify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        final EditText room_name = (EditText) findViewById(R.id.room_name);

        Button playlistButton = (Button) findViewById(R.id.enter_button);
        playlistButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(EnterActivity.this, PlaylistActivity.class);
                intent.putExtra("room_name", room_name.getText().toString());

                startActivity(intent);
            }
        });
    }


}
