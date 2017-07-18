# Treee App
## Firebase Project
#### 부제: DailyThreeThanks
![appIcon](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/appIcon.png)

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
<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/1_login.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/2_feedFirst.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/3_listFirst.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/4_read.png' width='210' height='350' />

<br>

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/5_modify.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/6_delete.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/7_feedFAB.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/8_listFAB.png' width='210' height='350' />

<br>

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/9_feedCheckBox.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/10_feedCheckBoxDelete.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/11_listCheckBox.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/12_listCheckBoxDelete.png' width='210' height='350' />

<br>

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/13_profileImage.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/14_profileNotiOn.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/15_profileNotiTimeSet.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/16_notiAlarm.png' width='210' height='350' />

<br>

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/17_logout.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/Treee/graphics/18_accountDelete.png' width='210' height='350' />


<br>

## Treee App 소스코드
- #### [전체소스코드](https://github.com/mdy0501/Study/tree/master/Android/Mini%20Project/Treee/app/src/main/java/com/mdy/android/treee)

<br>

## Treee App 영상
- #### [Treee App 시연 영상](https://www.youtube.com/watch?v=VR_NBMiT3pM&feature=youtu.be)
- #### [Treee App 설명 영상](https://youtu.be/igLezaQlNJw)

<br>

## Treee App에 사용된 개념들
### 1. SharedPreference
- #### 사용자 Uid 저장
- #### 로그인시 프로필 사진 fileUriString
- #### Notification 시간 설정

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

### 4. Firebase 데이터베이스
- #### Create (삽입)
```java
// 파이어베이스 데이터베이스
FirebaseDatabase database;
DatabaseReference memoRef, userRef;

...

// 데이터베이스 레퍼런스
database = FirebaseDatabase.getInstance();
memoRef = database.getReference("memo");
String userUid = auth.getCurrentUser().getUid();
userRef = database.getReference("user").child(userUid).child("memo");

...

// 데이터베이스 삽입
/* 입력할 데이터의 키 생성 */
String memoKey = memoRef.push().getKey(); // 자동생성된 키를 가져온다.
memo.memoKeyName = memoKey;

memoRef.child(memoKey).setValue(memo);
userRef = database.getReference("user").child(userUid).child("memo");
userRef.child(memoKey).setValue(memo);
```
<br>

- #### Read (읽기)
```java
// 파이어베이스 데이터베이스
FirebaseDatabase database;
DatabaseReference userRef;

...

String userUid = PreferenceUtil.getUid(this);
userRef = database.getReference("user").child(userUid).child("memo");

...

userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                Data.list.clear();
                for( DataSnapshot item : data.getChildren() ){
                    // json 데이터를 Memo 인스턴스로 변환오류 발생 가능성 있어서 예외처리 필요
                    try {
                        Memo memo = item.getValue(Memo.class);
                        if(memo.userUid.equals(auth.getCurrentUser().getUid()) ) {
                            Data.list.add(memo);
                        }
                    } catch (Exception e){
                        Log.e("FireBase", e.getMessage());
                    }
                }
                refreshFeed(Data.list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
```
<br>

- #### Update (수정)
```java
// update는 insert와 동일하게 동작한다.
String userUid = PreferenceUtil.getUid(this);
userRef = database.getReference("user").child(userUid).child("memo");

userRef.child(memo.memoKey).setValue(memo);
```
<br>

- #### Delete (삭제)
```java
// delete는 insert와 동일하게 동작한다.
String userUid = PreferenceUtil.getUid(this);
userRef = database.getReference("user").child(userUid).child("memo");

userRef.child(memo.memoKey).setValue(null);
```

<br>

### 5. 파일 업로드 (Firebase 스토리지)
```java
// 파이어베이스 스토리지
private StorageReference mStorageRef;

...

// 스토리지 레퍼런스
mStorageRef = FirebaseStorage.getInstance().getReference("images");

...

public void uploadFile(String filePath){
    // 스마트폰에 있는 파일의 경로
    File file = new File(filePath);
    Uri uri = Uri.fromFile(file);

    // 파이어베이스에 있는 파일 경로
    String fileName = file.getName(); // + 시간값 or UUID 추가해서 만들어준다.

    // 데이터베이스의 키가 값과 동일한 구조 ( 키 = 값 )
    StorageReference fileRef = mStorageRef.child(fileName);

    fileRef.putFile(uri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // 파이어베이스 스토리지에 방금 업로드한 파일의 경로
                    @SuppressWarnings("VisibleForTests")
                    Uri uploadedUri = taskSnapshot.getDownloadUrl();
                    afterUploadFile(uploadedUri);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.i("FBStorage", "Upload Fail : " + exception.getMessage());
                }
            });
}


public void afterUploadFile(Uri imageUri){

    String content1 = editTextContent1.getText().toString();
    String content2 = editTextContent2.getText().toString();
    String content3 = editTextContent3.getText().toString();

    // 파이어베이스 데이터베이스에 데이터 넣기
    // 1. 데이터 객체 생성
    Memo memo = new Memo(content1, content2, content3);
    memo.date = inputCurrentDate();
    memo.userUid = auth.getCurrentUser().getUid();
    memo.userEmail = auth.getCurrentUser().getEmail();

    if(imageUri != null){
        memo.fileUriString =imageUri.toString();
    }

    // 2. 입력할 데이터의 키 생성
    String memoKey = memoRef.push().getKey(); // 자동생성된 키를 가져온다.
    memo.memoKeyName = memoKey;

    // 3. 생성된 키를 레퍼런스로 데이터를 입력
    //   insert와 update, delete 는 동일하게 동작
    memoRef.child(memoKey).setValue(memo);

    String userUid = auth.getCurrentUser().getUid();
    userRef = database.getReference("user").child(userUid).child("memo");

    userRef.child(memoKey).setValue(memo);

    // 데이터 입력 후 창 닫기
    dialog.dismiss();
    finish();
}


// 시간 생성하는 함수
public String inputCurrentDate(){
    Date currentTime = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일, hh:mm a");
    return sdf.format(currentTime);
}


// startActivityForResult()가 끝나면 호출되는 메소드
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode == RESULT_OK){
        switch (requestCode) {
            // 나. 이미지 선택창에서 선택된 이미지의 경로 추출
            case 100:
                Uri imageUri = data.getData();
                String filePath = getPathFromUri(this, imageUri);
                txtImage.setText(filePath);
                Glide.with(this).load(imageUri).centerCrop().into(imageViewGallery);
                Toast.makeText(this, "사진이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}


// Uri에서 실제 경로 꺼내는 함수
public String getPathFromUri(Context context, Uri uri){
    String realPath = "";
    Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
    if( cursor.moveToNext() ){
        realPath = cursor.getString(cursor.getColumnIndex("_data"));
    }
    cursor.close();

    return realPath;
}
```
<br>

### 6. Notification
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

### 7. FloatingActionButton
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


### 8. ProgressDialog / AlertDialog
- #### ProgressDialog (로그인시)
```java
// 프로그래스바 정의
private ProgressDialog loginDialog;

...

// onCreate()에
loginDialog = new ProgressDialog(this);
loginDialog.setTitle("로그인");
loginDialog.setMessage("로그인 하고 있습니다.");
loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

...

// 로그인 시작하는 부분에서
loginDialog.show();

...

// 로그인 끝나는 부분에서
loginDialog.dismiss();

```
- #### AlertDialog (로그아웃시)
```java
setAlertDialogLogoutUserAccount();

...

// AlertDialog - ProfileActivity에서 사용자 계정 로그아웃
public void setAlertDialogLogoutUserAccount(Context context){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

    // 제목 setting
    alertDialogBuilder.setTitle("로그아웃");

    // AlertDialog setting
    alertDialogBuilder
            .setMessage("로그아웃 하시겠습니까?")
            .setCancelable(false)
            .setNegativeButton("예",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 로그아웃을 한다.
                            logoutUserAccount();
                        }
                    })
            .setPositiveButton("아니오",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 로그아웃을 하지 않는다.
                            dialog.cancel();
                        }
                    });

    // 다이얼로그 생성
    AlertDialog alertDialog = alertDialogBuilder.create();

    // 다이얼로그 보여주기
    alertDialog.show();
}
```
