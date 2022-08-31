package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private Button naverBtn;
    private Button naver_logout_btn;
    private EditText Login, password_toggle;
    String userID, userPassword;

    OAuthLogin mOAuthLoginModule;

    @Override
    protected void onDestroy() {
        if (mOAuthLoginModule!=null){
            mOAuthLoginModule.logout(getApplicationContext());
            Toast.makeText(getApplicationContext(), "네이버 로그아웃 하셨습니다. 자동으로 앱이 나가집니다" , Toast.LENGTH_LONG).show();

        }
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = findViewById(R.id.Login);
        password_toggle = findViewById(R.id.password_toggle);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"회원가입페이지 입니다!", Toast.LENGTH_SHORT).show();
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = Login.getText().toString();
                userPassword = password_toggle.getText().toString();
                sendRequest();
            }
        });

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        naverBtn = findViewById(R.id.naver_login_btn);
        naverBtn.setOnClickListener(view -> {
            mOAuthLoginModule = OAuthLogin.getInstance();
            mOAuthLoginModule.init(
                    getApplicationContext(),
                    getString(R.string.naver_login_client_id),
                    getString(R.string.naver_login_client_secret),
                    getString(R.string.naver_client_name)
            );

            @SuppressLint("HandlerLeak")
            OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
                @Override
                public void run(boolean success) {
                    if (success) {
                        String accessToken = mOAuthLoginModule.getAccessToken(getApplicationContext());
                        String refreshToken = mOAuthLoginModule.getRefreshToken(getApplicationContext());
                        long expiresAt = mOAuthLoginModule.getExpiresAt(getApplicationContext());
                        String tokenType = mOAuthLoginModule.getTokenType(getApplicationContext());

                        Log.i("LoginData","accessToken : "+ accessToken);
                        Log.i("LoginData","refreshToken : "+ refreshToken);
                        Log.i("LoginData","expiresAt : "+ expiresAt);
                        Log.i("LoginData","tokenType : "+ tokenType);

                        Intent intent = new Intent(getApplicationContext(), SubActivity3.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"네이버 아이디로 로그인 되었습니다!", Toast.LENGTH_SHORT).show();
                    } else {
                        String errorCode = mOAuthLoginModule.getLastErrorCode(getApplicationContext()).getCode();
                        String errorDesc = mOAuthLoginModule.getLastErrorDesc(getApplicationContext());
                        Log.e("LoginData","errorCode : "+ errorCode);
                        Log.e("LoginData","errorDesc : "+ errorDesc);
                        Toast.makeText(getApplicationContext(), "errorCode : " + errorCode + "errorDesc : " + errorDesc, Toast.LENGTH_SHORT).show();
                    }
                }
            };
            mOAuthLoginModule.startOauthLoginActivity(MainActivity.this, mOAuthLoginHandler);
        });



    }

    public void sendRequest(){
        String url = "http://wlsdn3411.ivyro.net/AppLoginTest2.php";

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
                                Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, SubActivity3.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPass", userPassword);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인 실패,회원가입을 해주세요!", Toast.LENGTH_SHORT).show();
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
                params.put("userID", userID);
                params.put("userPassword", userPassword);
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }
}
