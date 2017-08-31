# Firebase Project : Treee Application 제작

#### 부제: DailyThreeThanks
![appIcon]<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/appIcon.png' width='60%' height='60% />

<br>

## Treee App 소개
#### ***Treee*** 는 하루에 3가지씩 감사한 것을 기록하는 어플리케이션 입니다.
#### Treee 라는 단어는 숫자 3의 영어인 Three와 나무 Tree의 합성어 입니다.
#### 감사한 것들을 적은 기록들 하나 하나를 나무 한 그루로 생각했습니다.
#### 나무 한그루는 작고, 보잘 것 없어 보이지만 그 나무들이 수십개, 수백개 모였을 때는 세상에 여러가지로 좋은 영향을 줄 수 있습니다.
#### 우리가 하루를 정신없이 보내다보면 깨닫지 못하고 지나가는 감사한 일들이 참 많습니다.
#### 그 감사한 수많은 일들을 Treee 어플리케이션으로 기록해봤으면 좋겠습니다.
#### 이렇게 하루하루 감사하는 훈련들을 하다보면 사소한 것에서도 감사할 수 있게 되고,
#### 작은 것에서도 기쁨을 느끼는 삶을 살 수 있을 것이라 생각합니다.

<br>
<br>

## Treee App 화면
<img src = 'https://github.com/pleasantlife/Treee/blob/master/graphics/1_login.png' width='210' height='350'>로그인화면 </img> <img src = 'https://github.com/pleasantlife/Treee/blob/master/graphics/2_feedFirst.png' width='210' height='350' /> <img src = 'https://github.com/pleasantlife/Treee/blob/master/graphics/3_listFirst.png' width='210' height='350' /> <img src = 'https://github.com/pleasantlife/Treee/blob/master/graphics/4_read.png' width='210' height='350' />

<br>

<img src = 'https://github.com/pleasantlife/Treee/blob/master/graphics/5_modify.png' width='210' height='350' /> <img src = 'https://github.com/pleasantlife/Treee/blob/master/graphics/6_delete.png' width='210' height='350' /> <img src = 'https://github.com/pleasantlife/Treee/blob/master/graphics/7_feedFAB.png' width='210' height='350' /> <img src = 'https://github.com/pleasantlife/Treee/blob/master/graphics/8_listFAB.png' width='210' height='350' />

<br>

<img src = 'https://github.com/pleasantlife/Treee/blob/master/graphics/9_feedCheckBox.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/10_feedCheckBoxDelete.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/11_listCheckBox.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/12_listCheckBoxDelete.png' width='210' height='350' />

<br>

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/13_profileImage.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/14_profileNotiOn.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/15_profileNotiTimeSet.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/16_notiAlarm.png' width='210' height='350' />

<br>

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/17_logout.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/18_accountDelete.png' width='210' height='350' />

<br>

## Treee App 영상
- #### [Treee App 시연 영상](https://www.youtube.com/watch?v=VR_NBMiT3pM&feature=youtu.be)
- #### [Treee App 설명 영상](https://youtu.be/igLezaQlNJw)

<br>

## Treee App 제작시 활용한 안드로이드 기능

### 0. 활용 기능 요약
- #### SharedPreference : 사용자의 고유데이터 저장(알림 활성 시간, 로그인 처리용 토큰 등)
- #### Firebase : 이메일, 페이스북 계정 또는 구글 계정을 이용한 로그인 및 사용자 등록
- #### Time Pick & Save : TimePicker를 통해 사용자가 특정 시간대를 선택, Calendar와 SharedPreference를 이용하여 선택한 시간대를 저장 
- #### Notification : Service와 BroadCast Receiver를 이용하여 지정된 시간에 Notification 활성화

### 1. SharedPreference
- #### 사용자 Uid 저장
- #### 로그인시 프로필 사진 fileUriString(페이스북 프로필 사진 포함)
- #### Notification 시간 설정(사용자가 설정한 시간에 맞춰 Notification을 활성화 시킴)

```java
public class PreferenceUtil {
    private static SharedPreferences sharedPreferences = null;

    private static void setSharedPreferences(Context context){
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("settings", MODE_PRIVATE); // MODE_PRIVATE - 다른 앱이 접근하지 못하게
    }

    private static void setString(Context context, String key, String value){
        setSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static void setInt(Context context, String key, int value){
        setSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static String getString(Context context, String key){
        setSharedPreferences(context);
        return sharedPreferences.getString(key, "해당 데이터 없음");
    }

    private static int getInt(Context context, String key){
        setSharedPreferences(context);
        return sharedPreferences.getInt(key, -1);
    }


    // Uid 세팅
    public static void setUid(Context context, String value){
        setString(context,"userUid",value);
    }
    public static String getUid(Context context) {
        return getString(context, "userUid");
    }

    // Profile 사진 세팅
    public static void setProfileImageUri(Context context, String value){
        setString(context, "userProfileImage", value);
    }
    public static String getProfileImageUri(Context context){
        return getString(context, "userProfileImage");
    }

    // Noti Alarm 시간(Hour) 설정
    public static void setNotiAlarmHour(Context context, int valueOfAlarmHour){
        setInt(context, "alarmHour", valueOfAlarmHour);
    }
    public static int getNotiAlarmHour(Context context){
        return getInt(context, "alarmHour");
    }

    // Noti Alarm 분(Minute) 설정
    public static void setNotiAlarmMinute(Context context, int valueOfAlarmMinute){
        setInt(context, "alarmMinute", valueOfAlarmMinute);
    }
    public static int getNotiAlarmMinute(Context context){
        return getInt(context, "alarmMinute");
    }
}
```

<br>

### 2. Firebase 로그인
- #### 이메일 회원가입 로그인
- #### 페이스북 연동 로그인
- #### 구글 연동 로그인
```java
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
                                if(userProfile.alarmHour == 0 && userProfile.alarmMinute == 0){
                                    setAlarmTime(0, 0);
                                } else {
                                    // 알람이 저장되어 있으면
                                    setAlarmTime(userProfile.alarmHour, userProfile.alarmMinute);
                                }
                            } catch (Exception e) {
                                Log.e("Firebase", e.getMessage());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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

                        // 사용자 이메일 데이터베이스 등록
                        String userEmail = mAuth.getCurrentUser().getEmail();
                        userRef = database.getReference("user").child(mAuth.getCurrentUser().getUid()).child("profile");
                        userRef.child("userEmail").setValue(userEmail);
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
                            Toast.makeText(LoginActivity.this, "이메일 로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "이메일 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
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

                            // 사용자 이메일 데이터베이스 등록
                            String userEmail = mAuth.getCurrentUser().getEmail();
                            userRef = database.getReference("user").child(mAuth.getCurrentUser().getUid()).child("profile");
                            userRef.child("userEmail").setValue(userEmail);
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
```

<br>

### 3. Firebase 계정 삭제
```java
// 전역변수 선언
private FirebaseUser deleteUser;

...

// onCreate()에서
deleteUser = mAuth.getInstance().getCurrentUser();

...

// 사용자 계정 삭제하는 메소드 호출
deleteUserAccount();

...

// 사용자 계정 삭제하는 메소드
private void deleteUserAccount(){
    deleteUser.delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        // 계정 삭제에 성공하면
                        finish();
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(ProfileActivity.this, "계정이 삭제되었습니다. \n\n이용해 주셔서 감사합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
}
```
<br>

### 5. Notification
- #### Service
```java
public class NotiService extends Service {

    private NotificationManager notificationManager;
    private PendingIntent servicePendingIntent;

    public NotiService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Context context = this.getApplicationContext();


        Intent mIntent = new Intent(this, LoginActivity.class);
        servicePendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("하루에 감사한 일 세 가지. Treee");
        builder.setContentText("오늘 하루도 감사한 일이 참 많습니다.");
        builder.setSmallIcon(R.mipmap.ic_launcher_app_round);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_app_round);
        builder.setLargeIcon(largeIcon);
        builder.setContentIntent(servicePendingIntent);

        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(600, builder.build());


        return super.onStartCommand(intent, flags, startId);
    }
}
```
- #### Broadcast Receiver
```java
public class NotiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent receiverIntent = new Intent(context, NotiService.class);
        context.startService(receiverIntent);
    }
}
```
- #### TimePickerDialog.OnTimeSetListener
```java
// 알람 시간 사용자 설정 (TimePicker)
private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // 설정 버튼을 눌렀을 때
        Toast.makeText(getApplicationContext(), "알림 시간이 " + hourOfDay + "시" + minute + "분 으로 설정되었습니다.", Toast.LENGTH_SHORT).show();

        // SharedPreference에 알람 시간(Hour), 분(Minute) 저장
        PreferenceUtil.setNotiAlarmHour(ProfileActivity.this, hourOfDay);
        userAlarmHour = hourOfDay;

        PreferenceUtil.setNotiAlarmMinute(ProfileActivity.this, minute);
        userAlarmMinute = minute;

        // 데이터베이스 Profile에 시간과 분 저장
        UserProfile userProfile = new UserProfile();

        userProfile.alarmHour = hourOfDay;
        userProfile.alarmMinute = minute;

        userRef = database.getReference("user").child(mAuth.getCurrentUser().getUid()).child("profile");
        userRef.child("alarmHour").setValue(userProfile.alarmHour);
        userRef.child("alarmMinute").setValue(userProfile.alarmMinute);

        textViewSetAlarmTime.setText(userAlarmHour + "시 " + userAlarmMinute + "분");

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, userAlarmHour);
        calendar.set(Calendar.MINUTE, userAlarmMinute);
        calendar.set(Calendar.SECOND, 0);

        if(initCount == 12345) {
            mAlarmManager = (AlarmManager) getBaseContext().getSystemService(ALARM_SERVICE);
            Intent alarmIntent = new Intent(ProfileActivity.this, NotiReceiver.class);

            calendar = Calendar.getInstance();
            long startTime = calendar.getTimeInMillis();
            long allDayMill = 86400000;

            PendingIntent mPendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, allDayMill, mPendingIntent);

        } if(initCount == 678910){
            Intent cancelIntent = new Intent();
            PendingIntent delAlarmIntent = PendingIntent.getBroadcast(getBaseContext(), 0, cancelIntent, 0);
            mAlarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
            mAlarmManager.cancel(delAlarmIntent);
        }
    }
};
```

<br>

### 6. FloatingActionButton
- 화면을 일정구간까지 스크롤하면, FloatingActionButton이 화면에 나타나도록 로직 
- #### ListActivity
- #### FeedActivity
```java
// 전역변수 선언 - FloatingActionButton을 스크롤 하는 정도에 따라 나타나게 하기 위한
FloatingActionButton fabList;
CardView cardviewList;
NestedScrollView nestedList;
int firstListcardHeight;

...

// onCreate()에
setFabList();
setNestedList();

...

@Override
public void onWindowFocusChanged(boolean hasFocus) {
    cardviewList = (CardView) findViewById(R.id.cardviewList);
    firstListcardHeight = cardviewList.getHeight();
}

// FloatingActionButton 생성 및 onClick
public void setFabList(){
    //FloatingActionButton fabList로 전역변수 설정하였습니다.
    fabList = (FloatingActionButton) findViewById(R.id.fabList);
    fabList.setVisibility(View.GONE);
    fabList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ListActivity.this, WriteActivity.class);
            startActivity(intent);
        }
    });
}

public void setNestedList(){
    nestedList = (NestedScrollView) findViewById(R.id.nestedList);
    nestedList.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            // scrollY값 =현재 스크롤된 값
            if(scrollY >= firstListcardHeight){
                fabList.setVisibility(View.VISIBLE);
            } else {
                fabList.setVisibility(View.GONE);
            }
        }
    });
}

```
<br>

