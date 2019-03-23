package com.example.slider;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.firebase.FirebaseApp;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import static android.Manifest.permission.SEND_SMS;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SOSActivity extends AppCompatActivity implements ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    ArrayList<String> list;
    private static final int REQUEST_PHONE_CALL = 1;
    private static final String TAG = "Check Contacts";
    EditText etPhone, etMessage, etMail;
    Button btnSendSMS;
    String email;
    DatabaseReference database;
    List<String> nums;

    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
//gps
//    public static final String TAG = "Tag";
    private static final  int REQUEST_CODE = 1000;
    private GoogleApiClient googleApiClient;
    private Location location;
    double latitude;
    double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        MultiDex.install(this);
        nums = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference();
        getIncomingIntent();

//        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);


        sentPI = PendingIntent.getBroadcast(SOSActivity.this, 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(SOSActivity.this, 0, new Intent(DELIVERED), 0);

        //gps
        googleApiClient = new GoogleApiClient.Builder(SOSActivity.this)
                .addConnectionCallbacks(SOSActivity.this)
                .addOnConnectionFailedListener(SOSActivity.this)
                .addApi(LocationServices.API).build();



    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG,"onconnected gps ");
        showuserlocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG,"on connection suspendeed");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG,"on connection failed");
        if(connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(SOSActivity.this,REQUEST_CODE);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
//            Toast.makeText(activity, "on connection falied", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    private void showuserlocation(){
        int permissioncheck = ContextCompat.checkSelfPermission(SOSActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissioncheck == PackageManager.PERMISSION_GRANTED){
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(SOSActivity.this);
//            location = fusedLocationProviderClient.getLastLocation(googleApiClient);

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location locationn) {
                            // Got last known location. In some rare situations this can be null.
                            location = locationn;
                            if(location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

//                                Toast.makeText(activity, "lat long", Toast.LENGTH_SHORT).show();
                                System.out.println(latitude + "................." + longitude);
//                                SharedPreferences.Editor edit = sharedPreferences.edit();
//                                edit.putString("lat",latitude + "");
//                                edit.putString("longi",longitude+"");
//                                edit.apply();
                            }
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        } else {
            System.out.println("permission denied");
            ActivityCompat.requestPermissions(SOSActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},10);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onactivityresult..............................");

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            googleApiClient.connect();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    public void sendSMS(View v) {

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(SOSActivity.this, "IN DATA CHANGE", Toast.LENGTH_LONG).show();
                if(dataSnapshot.child("userContacts").hasChild(email)) {
                    Toast.makeText(SOSActivity.this, "IN IF", Toast.LENGTH_LONG).show();
                    for (DataSnapshot ds : dataSnapshot.child("userContacts").child(email).getChildren()) {
                        nums.add((String)ds.getValue());
                    }
                }
                if (!nums.isEmpty()) {
                    Toast.makeText(SOSActivity.this, "IN 2nd IF", Toast.LENGTH_LONG).show();
                    if (ContextCompat.checkSelfPermission(SOSActivity.this, Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(SOSActivity.this, new String [] {Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }
                    else
                    {
                        SmsManager sms = SmsManager.getDefault();
                        Geocoder geocoder;
                        String message="Emergency Need Help " + "\n";
                        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        List<Address> addresses;
                        try {
                            addresses = geocoder.getFromLocation(latitude,longitude,1);
                            String address  = addresses.get(0).getAddressLine(0);
                            message += address;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //phone - Recipient's phone number
                        //address - Service Center Address (null for default)
                        //message - SMS message to be sent
                        //piSent - Pending intent to be invoked when the message is sent
                        //piDelivered - Pending intent to be invoked when the message is delivered to the recipient
                        System.out.println(nums);
                        for (int i= 0; i < nums.size(); i++) {
                            Toast.makeText(SOSActivity.this, "IN FOR LOOP", Toast.LENGTH_LONG).show();
                            sms.sendTextMessage(nums.get(i), null, message, sentPI, deliveredPI);
                        }

                        Vibrator vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vi.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            vi.vibrate(500);
                        }
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+nums.get(0)));
                        if (ContextCompat.checkSelfPermission(SOSActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(SOSActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                        }
                        else
                        {
                            try {
                                startActivity(callIntent);
                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    Toast.makeText(SOSActivity.this, "You hven't added any contacts", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        String message = etMessage.getText().toString();
//        String telNr = etPhone.getText().toString();
//        String mail = etPhone.getText().toString();


//                new SendMail().execute("");

    }

    public void getIncomingIntent() {
        if (getIntent().hasExtra("email")) {
            email = getIntent().getStringExtra("email");
        }
    }

    public void importContact(View v) {
        new importContacts().execute((Void)null);
    }

    public class importContacts extends AsyncTask<Void, Void, Void> {
        public importContacts() {

        }
        @Override
        protected Void doInBackground(Void... voids) {
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Log.i(TAG, "Name: " + name);
                            Log.i(TAG, "Phone Number: " + phoneNo);
                            Contact cont = new Contact(name, phoneNo);
                            list.add(cont.getName() + " -- " + cont.getPhnNO());
                        }
                        pCur.close();
                    }
                }
                if (cur != null) cur.close();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            list = new ArrayList<>();

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println(list);
            Intent intent = new Intent(SOSActivity.this, ContactsActivity.class);
            Bundle bundle = new Bundle();
            intent.putStringArrayListExtra("myList",  list);
            intent.putExtra("email", email);
//            bundle.putParcelableArrayList("mylist", (ArrayList<? extends Parcelable>) list);
//            intent.putExtras(bundle);
//            intent.
            startActivity(intent);
        }
    }


//    private class SendMail extends AsyncTask<String, Integer, Void> {
//
//        private ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = ProgressDialog.show(SOSActivity.this, "Please wait", "Sending mail", true, false);
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            progressDialog.dismiss();
//        }
//
//        protected Void doInBackground(String... params) {
//            Mail m = new Mail("nehruperumalla@gmail", "Gtrp!8j8");
//
//            String[] toArr = {"nehruperumalla1@gmail", "nehruperumalla@gmail"};
//            m.setTo(toArr);
//            m.setFrom("nehruperumalla@gmail");
//            m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
//            m.setBody("Email body.");
//
//            try {
//                if(m.send()) {
//                    Toast.makeText(MainActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
//                }
//            } catch(Exception e) {
//                Log.e("MailApp", "Could not send email", e);
//            }
//            return null;
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();

        //The deliveredPI PendingIntent does not fire in the Android emulator.
        //You have to test the application on a real device to view it.
        //However, the sentPI PendingIntent works on both, the emulator as well as on a real device.

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS sent successfully!", Toast.LENGTH_SHORT).show();
                        break;

                    //Something went wrong and there's no way to tell what, why or how.
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure!", Toast.LENGTH_SHORT).show();
                        break;

                    //Your device simply has no cell reception. You're probably in the middle of
                    //nowhere, somewhere inside, underground, or up in space.
                    //Certainly away from any cell phone tower.
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "No service!", Toast.LENGTH_SHORT).show();
                        break;

                    //Something went wrong in the SMS stack, while doing something with a protocol
                    //description unit (PDU) (most likely putting it together for transmission).
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "Null PDU!", Toast.LENGTH_SHORT).show();
                        break;

                    //You switched your device into airplane mode, which tells your device exactly
                    //"turn all radios off" (cell, wifi, Bluetooth, NFC, ...).
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio off!", Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS delivered!", Toast.LENGTH_SHORT).show();
                        break;

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "SMS not delivered!", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };

        //register the BroadCastReceivers to listen for a specific broadcast
        //if they "hear" that broadcast, it will activate their onReceive() method
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
    }
}
