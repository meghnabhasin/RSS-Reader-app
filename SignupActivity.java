package meghna.com.blogistic1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    EditText em, p;
    Button s;
//    FirebaseUser user;
    DialogConnectivity dialogConnectivity;
    CheckNetworkConnection checkNetworkConnection;
    //   private Calendar myCalendar;
   // private RadioButton f;
   // private RadioButton m;
   // private RadioGroup rg;
    private FirebaseAuth auth;
   // private String bdate;
    private String email;
   // private String first;
   // private String last;
    private String password;
    ProgressBar pb;
    private boolean isNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        em = (EditText) findViewById(R.id.em);
        p = (EditText) findViewById(R.id.p);
        s = (Button) findViewById(R.id.s);
        pb = (ProgressBar) findViewById(R.id.progressbar);
        dialogConnectivity = new DialogConnectivity(getApplicationContext());
        checkNetworkConnection = new CheckNetworkConnection(getApplicationContext());
        isNetwork = checkNetworkConnection.isNetworkAvailable(getApplicationContext());
        if (!isNetwork) {
           // dialogConnectivity.showAlertDialog(SignupActivity.this, "No Internet Connection",
             //       "You don't have internet connection", false);
            Toast.makeText(SignupActivity.this, "Please check your internet connection and try again later!!", Toast.LENGTH_SHORT).show();
        }
        /* fn = (EditText) findViewById(R.id.fn);
        ln = (EditText) findViewById(R.id.ln);
        bd = (EditText) findViewById(R.id.bd);
  myCalendar = Calendar.getInstance();
        bd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignupActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        rg = (RadioGroup) findViewById(R.id.rg);
        m = (RadioButton) findViewById(R.id.m);
        f = (RadioButton) findViewById(R.id.f);
        */
        else {
            s.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    em.setError(null);
                    p.setError(null);
                    // Store values at the time of the login attempt.
                    View focusView = null;
                    boolean cancel = false;
                    // bdate = bd.getText().toString();
                    email = em.getText().toString();
                    //first = fn.getText().toString();
                    //last = ln.getText().toString();
                    password = p.getText().toString();
                    if (TextUtils.isEmpty(email)) {
                        em.setError(getString(R.string.error_field_required));
                        focusView = em;
                        cancel = true;
                    } else if (!isEmailValid(email)) {
                        em.setError(getString(R.string.error_invalid_email));
                        focusView = em;
                        cancel = true;
                    }
                    if (TextUtils.isEmpty(password)) {
                        em.setError(getString(R.string.error_field_required));
                        focusView = em;
                        cancel = true;
                    } else if (!isPasswordValid(password)) {
                        em.setError("Password too short, enter minimum 6 characters!");
                        focusView = em;
                        cancel = true;
                    }
                /*if (TextUtils.isEmpty(bdate)) {
                    bd.setError(getString(R.string.error_field_required));
                    focusView = bd;
                    cancel = true;
                }
                if (TextUtils.isEmpty(first)) {
                    fn.setError(getString(R.string.error_field_required));
                    focusView = fn;
                    cancel = true;
                }
                if (TextUtils.isEmpty(last)) {
                    ln.setError(getString(R.string.error_field_required));
                    focusView = ln;
                    cancel= true;
                }*/
                    if (cancel) {
                        // There was an error; don't attempt login and focus the first
                        // form field with an error.
                        focusView.requestFocus();
                    } else if (!cancel) {
                        pb.setVisibility(View.VISIBLE);
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                        pb.setVisibility(View.GONE);
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                           // firebase.auth().onAuthStateChanged(function(user){
                                             //   user.sendEmailVerification();
                                           // });
                                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                      //                      finish();
                                        }
                                    }
                                });
                    }
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }
    /*final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
   private void updateLabel() {

        String myFormat = "dd/mm/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        bd.setText(sdf.format(myCalendar.getTime()));
    }*/
    @Override
    protected void onResume() {
        super.onResume();
        pb.setVisibility(View.GONE);
    }

}