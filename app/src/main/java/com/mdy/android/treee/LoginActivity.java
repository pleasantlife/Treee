package com.mdy.android.treee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mdy.android.treee.domain.UserProfile;
import com.mdy.android.treee.util.PermissionControl;
import com.mdy.android.treee.util.PreferenceUtil;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, PermissionControl.CallBack{

    private static final String TAG = "===FaceBook===";
    private static final int RC_SIGN_IN = 10;

    private SignInButton btnLoginGoogle;
    private LoginButton loginButton;
    private Button btnLoginEmail;
    private EditText editTextEmail, editTextPassword;
    private TextView textViewTitle;
    private ImageView imageViewLogo;


    private GoogleApiClient mGoogleApiClient;

    private FirebaseDatabase database;
    private DatabaseReference userRef, userProfileImageRef, userAlarmRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager mCallbackManager;


    // 프로그래스바 정의
    private ProgressDialog loginDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginDialog = new ProgressDialog(this);
        loginDialog.setTitle("로그인");
        loginDialog.setMessage("로그인 하고 있습니다.");
        loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        setViews();

        PermissionControl.checkVersion(this);


        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        userProfileImageRef = database.getReference("user");
        userAlarmRef = database.getReference("user");

        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        // 구글 로그인 버튼 리스너
        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(LoginActivity.this, "Google 로그인 버튼이 클릭되었습니다,", Toast.LENGTH_SHORT).show();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });



        // 이메일 로그인 버튼 리스너 -- 유효성 검사 추가
        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                boolean emailValidate = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (emailValidate == true) {
                    createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                } else {
                    Toast.makeText(getBaseContext(), "이메일 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 리스너 - 사용자의 로그인 상태 변화에 응답하는 AuthStateListener를 설정
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    PreferenceUtil.setUid(LoginActivity.this, user.getUid());
                    Log.w("======== Uid ========", user.getUid());

                    userProfileImageRef = database.getReference("user").child(user.getUid()).child("profile");
                    userProfileImageRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                                // 프로필 사진이 저장되어 있지 않으면
                                if(userProfile.profileFileUriString == null){
                                    setProfileImg("");
                                } else {
                                    // 프로필 사진이 저장되어 있으면
                                    Log.w("=========userProfile.profileFileUteriString============", userProfile.profileFileUriString);
                                    setProfileImg(userProfile.profileFileUriString);
                                }
                            } catch (Exception e){
                                Log.e("Firebase", e.getMessage());
                            }
                            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });


                    userAlarmRef = database.getReference("user").child(user.getUid()).child("profile");
                    userAlarmRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                                // 알람이 저장되어 있지 않으면

                                if(userProfile.alarmHour != 0) {
                                    setAlarmTime(userProfile.alarmHour, userProfile.alarmMinute);
                                    Log.w("22userProfile.alarmHour", "===============" + userProfile.alarmHour);
                                    Log.w("22userProfile.alarmMinute", "===============" + userProfile.alarmMinute);
                                }
                                /*
                                if(userProfile.alarmHour == 0 && userProfile.alarmMinute == 0){
                                    setAlarmTime(0, 0);
                                    Log.w("11userProfile.alarmHour", "===============" + userProfile.alarmHour);
                                    Log.w("11userProfile.alarmMinute", "===============" + userProfile.alarmMinute);
                                } else {
                                    // 알람이 저장되어 있으면
                                    setAlarmTime(userProfile.alarmHour, userProfile.alarmMinute);
                                    Log.w("22userProfile.alarmHour", "===============" + userProfile.alarmHour);
                                    Log.w("22userProfile.alarmMinute", "===============" + userProfile.alarmMinute);
                                }
                                */
                            } catch (Exception e) {
                                Log.e("Firebase", e.getMessage());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


//                    Log.w("===== DisplayName =====", mAuth.getCurrentUser().getDisplayName());

//                    String userEmail = mAuth.getCurrentUser().getEmail();
//                    userRef = database.getReference("user").child(user.getUid()).child("profile");
//                    userRef.child("userEmail").setValue(userEmail);

//                    PreferenceUtil.setProfileImageUri(LoginActivity.this, "");

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        mCallbackManager = CallbackManager.Factory.create();

        // 페이스북 로그인 버튼
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG1", "facebook:onSuccess:" + loginResult);
                AccessToken token = loginResult.getAccessToken();
                Log.d(TAG, "token expired:" + token.isExpired());
                Log.d(TAG, "token value:" + token.toString());
                Log.d(TAG, "token permissions:" + token.getDeclinedPermissions());
                Log.d(TAG, "token userid:" + token.getUserId());
                handleFacebookAccessToken(token);
            }

            @Override
            public void onCancel() {
                Log.d("facebook:onCancel", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG2", "facebook:onError", error);
            }
        });
    }

    public void setProfileImg(String url){
        PreferenceUtil.setProfileImageUri(this, url);
    }

    public void setAlarmTime(int hour, int minute){
        PreferenceUtil.setNotiAlarmHour(this, hour);
        PreferenceUtil.setNotiAlarmMinute(this, minute);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    // 페이스북 로그인
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        Log.d(TAG, "token:" + token.getApplicationId());
        Log.d(TAG, "token:" + token.getUserId());
        Log.d(TAG, "token:" + token.describeContents());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Log.d(TAG, "credential:" + credential.toString());
        loginDialog.show();

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("TAG", "signInWithCredential:onComplete:" + task.isSuccessful());
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "signInWithCredential", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "FaceBook 아이디 연동 성공", Toast.LENGTH_SHORT).show();
//                        PreferenceUtil.saveUidPreference(LoginActivity.this, mAuth);

                        // 사용자 이메일 데이터베이스 등록
                        String userEmail = mAuth.getCurrentUser().getEmail();
                        userRef = database.getReference("user").child(mAuth.getCurrentUser().getUid()).child("profile");
                        userRef.child("userEmail").setValue(userEmail);
                        // Toast.makeText(LoginActivity.this, "회원가입이 성공되었습니다.", Toast.LENGTH_SHORT).show();
                        loginDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
    }

    // 이메일 로그인 관련
    private void createUser(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // 기존 아이디로 로그인을 할 경우
                        if (!task.isSuccessful()) {
                            loginUser(email, password);
                            // Toast.makeText(LoginActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            // 회원 가입을 할 경우 실행
                            Toast.makeText(LoginActivity.this, "회원가입이 성공되었습니다.", Toast.LENGTH_SHORT).show();

                            // 사용자 이메일 데이터베이스 등록
                            String userEmail = mAuth.getCurrentUser().getEmail();
                            userRef = database.getReference("user").child(mAuth.getCurrentUser().getUid()).child("profile");
                            userRef.child("userEmail").setValue(userEmail);
                        }

                        // ...
                    }
                });
    }

    // 이메일 로그인 관련
    private void loginUser(String email, String password){
        loginDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "이메일 로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "이메일 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
//                            PreferenceUtil.saveUidPreference(LoginActivity.this, mAuth);
                            loginDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }



    private void setViews(){
        loginButton = (LoginButton) findViewById(R.id.facebook_login_button);   // 페이스북 로그인 버튼
        btnLoginGoogle = (SignInButton) findViewById(R.id.btnLoginGoogle);
        btnLoginEmail = (Button) findViewById(R.id.btnLoginEmail);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        imageViewLogo = (ImageView) findViewById(R.id.imageViewLogo);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        loginDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Google 아이디 인증이 성공하였습니다.", Toast.LENGTH_SHORT).show();
//                            PreferenceUtil.saveUidPreference(LoginActivity.this, mAuth);

                            // 사용자 이메일 데이터베이스 등록
                            String userEmail = mAuth.getCurrentUser().getEmail();
                            userRef = database.getReference("user").child(mAuth.getCurrentUser().getUid()).child("profile");
                            userRef.child("userEmail").setValue(userEmail);
                            // Toast.makeText(LoginActivity.this, "회원가입이 성공되었습니다.", Toast.LENGTH_SHORT).show();
                            loginDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    // implements GoogleApiClient.OnConnectionFailedListener
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void init() {

    }

    @Override
    public void cancel() {
        Toast.makeText(this, "권한을 요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
