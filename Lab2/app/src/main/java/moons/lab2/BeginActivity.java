package moons.lab2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class BeginActivity extends AppCompatActivity {

    static  final  String default_user_name = "Android";
    static  final  String default_user_password = "123456";
    static  final  String EmptyString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_layout);

        final AlertDialog.Builder alert_dialog = new AlertDialog.Builder(this);
        alert_dialog.setTitle(getString(R.string.alert_title))
                .setPositiveButton(getString(R.string.alert_confirm),
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                String toast_message = getString(R.string.alert_confirm_hint);
                                Toast.makeText(BeginActivity.this, toast_message, Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton(getString(R.string.alert_cancel),
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                String toast_message = getString(R.string.alert_cancel_hint);
                                Toast.makeText(BeginActivity.this, toast_message, Toast.LENGTH_SHORT).show();
                            }
                        }).create();


        RadioGroup rg = (RadioGroup) findViewById(R.id.parent);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID) {
                RadioButton rb = (RadioButton) group.findViewById(checkedID);
                if (rb.isChecked()) {
                    String toast_message = rb.getText() + getString(R.string.change_type_hint);
                    Toast.makeText(BeginActivity.this, toast_message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button signup_button = (Button) findViewById(R.id.signup);
        signup_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup rg = (RadioGroup) findViewById(R.id.parent);
                RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                if (rb != null) {
                    String toast_message = rb.getText() + getString(R.string.signup_message_hint);
                    Toast.makeText(BeginActivity.this, toast_message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button signin_button = (Button) findViewById(R.id.signin);
        signin_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String user_name = ((EditText) findViewById(R.id.user_name)).getText().toString();
                String user_password = ((EditText) findViewById(R.id.user_password)).getText().toString();

                String toast_message =  user_name.isEmpty() ? getString(R.string.user_name_empty) : (
                                        user_password.isEmpty() ? getString(R.string.user_password_empty) : EmptyString );

                if (!toast_message.isEmpty()) {
                    Toast.makeText(BeginActivity.this, toast_message, Toast.LENGTH_SHORT).show();
                } else {
                    boolean signin_success = user_name.equals(default_user_name) && user_password.equals(default_user_password);

                    alert_dialog.setMessage(signin_success  ? getString(R.string.signin_success)
                                                            : getString(R.string.signin_fail));

                    alert_dialog.show();
                }
            }
        });

    }
}
