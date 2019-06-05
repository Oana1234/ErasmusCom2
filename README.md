<p align="center">A social application for Erasmus students or students that want to go abroad with this mobility</p>

## Features 

- Push Notifications
- News Feed
- Messages and Inbox
- Friend Request
- Profile Picture 

## Screenshots

<img src="https://github.com/Oana1234/ErasmusCom2/blob/master/p1.jpg" width="200px" height="380px"/>        <img src="https://github.com/Oana1234/ErasmusCom2/blob/master/p2.jpg"  width="200px" height="380px"  />        <img src="https://github.com/Oana1234/ErasmusCom2/blob/master/p3.jpg" width="200px" height="380px" />        <img src="https://github.com/Oana1234/ErasmusCom2/blob/master/p4.jpg" width="200px" height="380px"/>     
<img src="https://github.com/Oana1234/ErasmusCom2/blob/master/p6.jpg" width="200px" height="380px"/>        <img src="https://github.com/Oana1234/ErasmusCom2/blob/master/p5.jpg" width="200px" height="380px"/>        <img src="https://github.com/Oana1234/ErasmusCom2/blob/master/p7.jpg" width="200px" height="380px"/>        <img src="https://github.com/Oana1234/ErasmusCom2/blob/master/p8.jpg" width="200px" height="380px"/>


## Dependencies

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })

    compile 'com.squareup.okhttp3:logging-interceptor:3.4.0'
    compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
    compile 'com.squareup:otto:1.3.8'
    compile 'com.google.code.gson:gson:2.6.2'
    compile 'com.github.bumptech.glide:glide:3.7.0'
    compile 'com.android.support:design:25.3.1'
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    compile 'com.android.support:support-v4:25.3.1'
    compile 'com.google.firebase:firebase-messaging:11.0.2'
    compile 'com.google.firebase:firebase-database:11.0.2'
    compile 'com.google.firebase:firebase-auth:11.0.2'
    compile 'com.google.firebase:firebase-core:11.0.2'
    compile 'com.google.android.gms:play-services-auth:11.0.2'
    compile 'de.hdodenhof:circleimageview:2.1.0'
    compile 'com.google.firebase:firebase-storage:11.0.2'
    compile 'org.greenrobot:eventbus:3.0.0'
    compile 'com.squareup.okhttp3:okhttp:3.4.1'
    compile 'com.android.support:cardview-v7:25.3.1'
    compile 'com.android.support:recyclerview-v7:25.3.1'
    compile 'com.firebaseui:firebase-ui:2.0.1'
    testCompile 'junit:junit:4.12'
}
