package com.jeffreyruffolo.notevote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        final EditText room_name = (EditText) findViewById(R.id.room_name);

        Button playlistButton = (Button) findViewById(R.id.enter_button);
        playlistButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                RequestQueue queue = Volley.newRequestQueue(EnterActivity.this);
                StringRequest sr = new StringRequest(Request.Method.POST,"http://ec2-52-42-199-153.us-west-2.compute.amazonaws.com/api/getPlaylist",
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                Intent intent = new Intent(EnterActivity.this, PlaylistActivity.class);
                                intent.putExtra("room_name", room_name.getText().toString());

                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error

                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("roomID", room_name.getText().toString());

                        return params;
                    }
                };
                queue.add(sr);
            }
        });
    }


}
