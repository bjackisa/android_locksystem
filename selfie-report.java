import android.content.Intent;

import android.provider.MediaStore;

import android.util.Log;

import android.view.View;

public class WrongPasswordActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wrong_password);

        // Add a click listener to the "Enter Password" button

        findViewById(R.id.enter_password_button).setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                // Get the password entered by the user

                EditText passwordEditText = findViewById(R.id.password_edit_text);

                String password = passwordEditText.getText().toString();

                // Check if the entered password is correct

                if (!password.equals("myPassword")) {

                    // If the password is incorrect, take a selfie and send it to info@jackisa.com

                    dispatchTakePictureIntent();

                    sendSelfieToEmail();

                } else {

                    // If the password is correct, proceed to the next activity

                    Intent intent = new Intent(WrongPasswordActivity.this, HomeActivity.class);

                    startActivity(intent);

                }

            }

        });

    }

    // Create an intent to take a picture

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }

    }

    // Send the selfie to info@jackisa.com

    private void sendSelfieToEmail() {

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("message/rfc822");

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@jackisa.com"});

