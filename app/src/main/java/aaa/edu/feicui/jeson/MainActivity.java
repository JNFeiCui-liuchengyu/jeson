package aaa.edu.feicui.jeson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button   btn;
    TextView tvname, tvage;
    public final         String jsonUrl = "http://192.168.1.147:8080/index2.jsp";
    private static final String TAG     = "MainActivity";
private Handler mHandler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 1:
                tvname.setText();
        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        Log.d(TAG, "onCreate:111 ");
        tvname = (TextView) findViewById(R.id.tv_name);
        tvage = (TextView) findViewById(R.id.tv_age);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                }).start();
        }
    }

    private void loadData() {
        String jsonString;
        HttpURLConnection connection;
        BufferedReader reader;
        try {
            URL url = new URL(jsonUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //connection.setConnectTimeout(5000);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            Log.d(TAG, "loadData:232323 ");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                Log.d(TAG, "loadData:2222 ");
                return;

            }
            jsonString = buffer.toString();
            Log.d(TAG, "loadData:22666 " + jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("students");
            Log.d(TAG, "jsonArray: " + jsonArray);
            //String string=jsonArray.getString(0);
            for (int i = 0; i < jsonArray.length(); i++) {
                String name;
                int age;

                JSONObject object = jsonArray.getJSONObject(i);

                name = object.getString("name");
                age = object.getInt("age");
                Log.d(TAG, "name: " + name + "age=" + age);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
