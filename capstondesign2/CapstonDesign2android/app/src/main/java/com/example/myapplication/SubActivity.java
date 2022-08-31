package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubActivity extends AppCompatActivity {
    private Button btn_next;
    String sex= "", region = ""; //성별, 지역
    String userName, userID, userPassword, userEmailAdr, userPhoneNumber;
    private EditText et_name, et_id, et_pass, et_emailAdr, et_phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        et_name = findViewById(R.id.name);
        et_id = findViewById(R.id.id);
        et_pass = findViewById(R.id.editTextTextPassword);
        et_emailAdr = findViewById(R.id.editTextTextEmailAddress);
        et_phoneNumber = findViewById(R.id.editTextPhone2);

        //spinner
        Spinner regionSpinner = (Spinner) findViewById(R.id.spinner_region);
        ArrayAdapter regionAdator = ArrayAdapter.createFromResource(this,R.array.region, android.R.layout.simple_spinner_item);
        regionAdator.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionAdator);


        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = regionSpinner.getSelectedItem().toString();
                Log.d("선택",region);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "선택되지 않음", Toast.LENGTH_LONG).show();
            }
        });

        final CheckBox man = (CheckBox)findViewById(R.id.checkbox_man);
        final CheckBox woman = (CheckBox)findViewById(R.id.checkbox_womean);


        btn_next = findViewById(R.id.btn_login);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = "";
                if(man.isChecked() == true){
                    sex += man.getText().toString();
                }
                if(woman.isChecked() == true) {
                    sex += woman.getText().toString();
                }
                userName = et_name.getText().toString();
                userID = et_id.getText().toString();
                userPassword = et_pass.getText().toString();
                userEmailAdr = et_emailAdr.getText().toString();
                userPhoneNumber = et_phoneNumber.getText().toString();

                registerRequest();
            }
        });

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public void registerRequest(){
        String url = "http://wlsdn3411.ivyro.net/AppRegisterTest.php";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObj = new JSONObject(response);
                            boolean  success = jsonObj.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"회원가입 성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },null
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userName", userName);
                params.put("userID", userID);
                params.put("userPassword", userPassword);
                params.put("userEmailAdr", userEmailAdr);
                params.put("userPhoneNumber", userPhoneNumber);
                params.put("userRegion", region);
                params.put("userSex", sex);
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }
}