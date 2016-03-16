package kawinpart.sorasak.bicbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText idCardEditText, passwordEditText;
    private String idCardString, passwordString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        bindwidget();



    } //Main Method

    private void bindwidget() {

        idCardEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);

    }

    public void clickSignUpSign(View view) {

        idCardString = idCardEditText.getText().toString().trim();
        passwordString = idCardEditText.getText().toString().trim();

        //Check Space
        if (idCardString.equals("") || passwordString.equals("")) {
            //Have Space
            myToast("กรุณากรอกให้ครบ ค่ะ");



        } else {
            //No Space
            //Log.d("test", "L = " + idCardString.length());
            checkIDcard();

        }

    } //clickSignUp

    private void checkIDcard() {
        if (idCardString.length() == 13) {
            //id card True



        } else {
            //id card False
            myToast("รหัสบัตรไม่ถูกต้อง ค่ะ");

        }
    } //checkIdCard

    private void myToast(String strToast) {
        Toast.makeText(SignUpActivity.this, strToast, Toast.LENGTH_SHORT).show();
    }

} //Main Class
