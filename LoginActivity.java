package meghna.com.blogistic1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity  implements
        GoogleApiClient.OnConnectionFailedListener{
    EditText us, pas;
    ImageView user,lock;
    Button b1,b2,b3;
    CheckBox cb;
    private FirebaseAuth auth;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private ProgressBar progressBar;
    private String password;
    private String email;
    DialogConnectivity dialogConnectivity;
    CheckNetworkConnection checkNetworkConnection;
    private boolean isNetwork;
    private GoogleApiClient mGoogleApiClient;
    private View gle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = (ImageView) findViewById(R.id.user);
        lock = (ImageView) findViewById(R.id.lock);
        us = (EditText) findViewById(R.id.us);
        pas = (EditText) findViewById(R.id.pas);
        b1 = (Button) findViewById(R.id.b);
        b2 = (Button) findViewById(R.id.rh);
        b3 = (Button) findViewById(R.id.reh);
        cb = (CheckBox) findViewById(R.id.cb);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        dialogConnectivity = new DialogConnectivity(getApplicationContext());
        checkNetworkConnection = new CheckNetworkConnection(getApplicationContext());
        isNetwork = checkNetworkConnection.isNetworkAvailable(getApplicationContext());
        if (!isNetwork) {
            //dialogConnectivity.showAlertDialog(LoginActivity.this, "No Internet Connection",
              //      "You don't have internet connection", false);
            Toast.makeText(LoginActivity.this, "Please check your internet connection and try again later!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        else {
            progressBar= (ProgressBar) findViewById(R.id.progressbar);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // checkbox status is changed from uncheck to checked.
                    if (!isChecked) {
                        // show password
                        pas.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        // hide password
                        pas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                }
            });
            auth = FirebaseAuth.getInstance();
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    us.setError(null);
                    pas.setError(null);
                    // Store values at the time of the login attempt.
                    email = us.getText().toString();
                    password = pas.getText().toString();
                    View focusView = null;
                    boolean cancel = false;
                    // Check for a valid password, if the user entered one.
                    // Check for a valid password, if the user entered one.
                    // Check for a valid email address.
                    if (TextUtils.isEmpty(email)) {
                        us.setError(getString(R.string.error_field_required));
                        focusView = us;
                        cancel = true;
                    }
                    if (TextUtils.isEmpty(password)) {
                        pas.setError(getString(R.string.error_field_required));
                        focusView = pas;
                        cancel = true;
                    } else if (!isEmailValid(email)) {
                        us.setError(getString(R.string.error_invalid_email));
                        focusView = us;
                        cancel = true;
                    }
                    if (cancel) {
                        // There was an error; don't attempt login and focus the first
                        // form field with an error.
                        focusView.requestFocus();
                    } else if (!cancel) {
                        progressBar.setVisibility(View.VISIBLE);
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        progressBar.setVisibility(View.GONE);
                                        if (!task.isSuccessful()) {
                                            // there was an error
                                            if (password.length() < 6) {
                                                pas.setError(getString(R.string.minimum_password));
                                            } else {
                                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            progressBar.setVisibility(View.VISIBLE);
                                            Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
                                            i.putExtra("meghna.com.blogistic1.gemp",2);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                });
                    }
                }

            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
            });
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(LoginActivity.this, forgotpassActivity.class);
                    startActivity(in);
                }
            });
            findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();   
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN && resultCode==RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            Intent in = new Intent(LoginActivity.this,HomePageActivity.class);
            startActivity(in);
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
  //          showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            Intent in = new Intent(LoginActivity.this,HomePageActivity.class);
            in.putExtra("meghna.com.blogistic1.gemp",4);
            startActivity(in);
            finish();
            //firebaseAuthWithGoogle(account);
            // Signed in successfully, show authenticated UI.
        } //else {
            //Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
        }
    /*private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());

                auth.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    }
                });
    }*/
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}
