package kawinpart.sorasak.bicbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    // Explicit
    private EditText idcardEditText, passwordEditText;
    private String idcardString, passwordString;
    private MyManage myManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Widget
        bindWidget();

        //Request database
        myManage = new MyManage(this);

        // Test AddValue
        // testaddValue();

        // Delete All SQLite
        deleteAllSQLite();


        //  Syn JSON to SQLite
        synJSONtoSQLite();

    } //Main Method

    private void synJSONtoSQLite() {

        // Connect Http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        int intTABLE = 0;
        while (intTABLE <= 0) {

            //1 Create InputStream
            InputStream inputStream = null;
            String[] urlJSON = {"http://www.swiftcodingthai.com/bic/php_get_user.php"};

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlJSON[intTABLE]);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            } catch (Exception e) {
                Log.d("bic", "Input ==> " + e.toString());
            }

            //2 Create JSON String
            String strJSON = null;

            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = bufferedReader.readLine()) !=null) {
                    stringBuilder.append(strLine);
                }
                inputStream.close();
                strJSON = stringBuilder.toString();

            } catch (Exception e) {
                Log.d("bic", "JSON ==> " + e.toString());
            }


            //3 Create To SQLite
            try {

                JSONArray jsonArray = new JSONArray(strJSON);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    switch (intTABLE) {
                        case 0:

                            String strIDcard = jsonObject.getString(MyManage.column_id_card);
                            String strPassword = jsonObject.getString(MyManage.column_password);
                            myManage.addUser(strIDcard, strPassword);

                            break;
                    }

                }

            } catch (Exception e) {
                Log.d("bic", "UPDATE ==> " + e.toString());
            }


            intTABLE += 1;
        } // while
    } // synJSoN

    private void deleteAllSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);
    }

    private void testaddValue() {
        myManage.addUser("test ID card", "test Password");
    }

    private void bindWidget() {
        idcardEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);
    }

    public void clickSingInMain(View view) {
        idcardString = idcardEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // Check Space
        if (idcardString.equals("") || passwordString.equals("")) {
            // Have Space
            myAlert("โปรดกรอกให้ ครบทุกช่อง ครับ");
        } else if (idcardString.length() != 13) {
            // กรอก id card ไม่เท่ากัน 13 หลัก
            myAlert("กรอก id card ไม่เท่ากัน 13 หลัก");

        }

    } // clickSingInMain

    private void myAlert(String strAlert) {
        Toast.makeText(MainActivity.this, strAlert, Toast.LENGTH_SHORT).show();
    }

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

} //Main Class
