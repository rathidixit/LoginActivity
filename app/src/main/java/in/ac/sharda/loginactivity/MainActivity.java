package in.ac.sharda.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
EditText editTextUserName, textPassword;
Button btnSubmit;
AsyncHttpClient client;
RequestParams params;
ListView list;
ArrayList listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        textPassword = (EditText)findViewById(R.id.editTextPassword);
        list = (ListView)findViewById(R.id.list);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        listdata = new ArrayList();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editTextUserName.getText().toString())){
                    editTextUserName.setError("Enter Name");
                }
                else if(TextUtils.isEmpty(editTextUserName.getText().toString())){
                    textPassword.setError("Enter Name");

                }
                else{
                     client = new AsyncHttpClient();
                     params = new RequestParams();
                     params.put("userName","abc");
                     params.put("password","abc");

                     client.get("http://10.0.2.2:8080/product/list",
                             new AsyncHttpResponseHandler() {
                         @Override
                         public void onSuccess(int statusCode, Header[] headers,
                                               byte[] responseBody) {
                             String data =  new String(responseBody);
                             try {
                                 JSONArray array = new JSONArray(data);
                                 for(int i = 0;i<array.length();i++){
                                     JSONObject jsnobj=array.getJSONObject(i);
                                     int id = jsnobj.getInt("id");
                                     int calories = jsnobj.getInt("calories");
                                     int price = jsnobj.getInt("price");
                                     String name = jsnobj.getString("name");
                                     String desc = jsnobj.getString("desc");

                                     listdata.add(id+"\n"+calories+"\n"+price+"\n"+name);

                                     ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,listdata);
                                     list.setAdapter(adapter);
                                 }
                             }
                             catch (JSONException je){
                                 je.printStackTrace();
                             }

                         }
                         public void onFailure(int statusCode,
                                               Header[] headers, byte[] responseBody,
                                               Throwable error) {
                             Toast.makeText(MainActivity.this,error.toString(),
                                     Toast.LENGTH_SHORT).show();
                             Log.d("Shivani", error.toString());

                         }
                     });
                }
            }
        });
    }
}
