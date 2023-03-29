package com.app.sushi.tei.fragment.FiveMenu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Account.AccountDetail;
import com.app.sushi.tei.Model.Account.SecondaryAddress;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.MyAccount.OtherAddressRecyclerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.sushi.tei.GlobalMembers.GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT;


public class MyAccountFragment extends Fragment {


    private View mView;
    private Toolbar toolbar;
    private Activity mContext;
    private TextView txtTitle;
    private LinearLayout imgBack;
    private LinearLayout kakiLayout, layoutOtherAddress;
    private CircleImageView imageView_profileview;
    private TextView noOfUse_textview, earned_points_textview, redeemed_textview, createdOnTextView,
            button_update;
    private TextView user_textview, memberIdTextView, staffTextView;

    private EditText firstname_editview, lastname_editview,
            preferredname_editview, preferrednamesecond_editview,
            mobilenumber_editview, emailaddress_editview,
            birthday_editview,
            gender_editview, postalCodeEditText, unitNumEditText, addressLineOneEditText,
            unitNumEditTextTwo;
    public static final int RESULT_OK = -1;
    int CAMERA_PIC_REQUEST = 0;

    private Date dobDate, customerCreatedDate;

    private String CommonImageurl = "";

    private boolean isFbLogin;
    private String from = "";

    public String[] genders = {"Male", "Female","Unspecified"};

    private TextView divingTextView, soccerTextView, basketBallTextView, runningTextView, fitnessTextView,
            cyclingTextView, dancingTextView, motorCyclingTextView, photographyTextView,
            martialArtsTextView, dogsAndPetsTextView, rollerBladeTextView, hobbyInfoLabelText;
    private String mCustomerId = "", mReferenceId = "";

    /*janaki code*/
    private RecyclerView recyclerviewOtherAddress;
    private RecyclerView.LayoutManager layoutManager;
    private OtherAddressRecyclerAdapter otherAddressRecyclerAdapter;
    private String userImgString = "";

    private TextView mess_redwards;
    private ArrayList<SecondaryAddress> secondaryAddressList;

    private RelativeLayout layoutMenmbershipId;
    private EditText dfault_mobile_code;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_my_account, container, false);
        txtTitle = (TextView) mView.findViewById(R.id.toolbartxtTitle);
        imgBack = mView.findViewById(R.id.toolbarBack);
        mess_redwards = (TextView) mView.findViewById(R.id.mess_redwards);
        divingTextView = (TextView) mView.findViewById(R.id.divingTextView);
        soccerTextView = (TextView) mView.findViewById(R.id.soccerTextView);
        basketBallTextView = (TextView) mView.findViewById(R.id.basketBallTextView);
        runningTextView = (TextView) mView.findViewById(R.id.runningTextView);
        fitnessTextView = (TextView) mView.findViewById(R.id.fitnessTextView);
        cyclingTextView = (TextView) mView.findViewById(R.id.cyclingTextView);
        dancingTextView = (TextView) mView.findViewById(R.id.dancingTextView);
        motorCyclingTextView = (TextView) mView.findViewById(R.id.motorCyclingTextView);
        photographyTextView = (TextView) mView.findViewById(R.id.photographyTextView);
        martialArtsTextView = (TextView) mView.findViewById(R.id.martialArtsTextView);
        dogsAndPetsTextView = (TextView) mView.findViewById(R.id.dogsAndPetsTextView);
        rollerBladeTextView = (TextView) mView.findViewById(R.id.rollerBladeTextView);
        hobbyInfoLabelText = (TextView) mView.findViewById(R.id.hobbyInfoLabelText);
        layoutMenmbershipId = mView.findViewById(R.id.layoutmembershipId);

        noOfUse_textview = (TextView) mView.findViewById(R.id.noOfUse_textview);
        earned_points_textview = (TextView) mView.findViewById(R.id.earned_points_textview);
        redeemed_textview = (TextView) mView.findViewById(R.id.redeemed_textview);
        user_textview = (TextView) mView.findViewById(R.id.user_textview);
        createdOnTextView = (TextView) mView.findViewById(R.id.createdOnTextView);

        memberIdTextView = (TextView) mView.findViewById(R.id.memberIdTextView);
        staffTextView = (TextView) mView.findViewById(R.id.staffTextView);

        imageView_profileview = (CircleImageView) mView.findViewById(R.id.imageView_profileview);

        button_update = (TextView) mView.findViewById(R.id.button_update);

        postalCodeEditText = (EditText) mView.findViewById(R.id.postalCodeEditText);
        addressLineOneEditText = (EditText) mView.findViewById(R.id.addressLineOneEditText);
        addressLineOneEditText.setEnabled(false);
        unitNumEditText = (EditText) mView.findViewById(R.id.unitNumEditText);
        unitNumEditTextTwo = (EditText) mView.findViewById(R.id.unitNumEditTextTwo);


        firstname_editview = (EditText) mView.findViewById(R.id.firstname_editview);
        lastname_editview = (EditText) mView.findViewById(R.id.lastname_editview);
        preferredname_editview = (EditText) mView.findViewById(R.id.preferredname_editview);
        preferrednamesecond_editview = (EditText) mView.findViewById(R.id.preferrednamesecond_editview);
        mobilenumber_editview = (EditText) mView.findViewById(R.id.mobilenumber_editview);
        emailaddress_editview = (EditText) mView.findViewById(R.id.emailaddress_editview);
        birthday_editview = (EditText) mView.findViewById(R.id.birthday_editview);
        gender_editview = (EditText) mView.findViewById(R.id.gender_editview);
        kakiLayout = (LinearLayout) mView.findViewById(R.id.kakiLayout);
        layoutOtherAddress = (LinearLayout) mView.findViewById(R.id.layoutOtherAddress);
        dfault_mobile_code = mView.findViewById(R.id.DefaultCode_myaccount);
        dfault_mobile_code.setClickable(false);
        dfault_mobile_code.setEnabled(false);
        recyclerviewOtherAddress = (RecyclerView) mView.findViewById(R.id.recyclerviewOtherAddress);

        layoutManager = new LinearLayoutManager(mContext);

        recyclerviewOtherAddress.setLayoutManager(layoutManager);


        //Hobbies
        createHobboySelections();
        initHobbyUI();

//        other address
        getSecondaryAddress();


        imageView_profileview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPhotoPermission();
            }
        });

        postalCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String data = postalCodeEditText.getText().toString();
                int length = data.length();

                if (length == 6) {

                    new GetAddressTask(addressLineOneEditText, null).execute(data);

                } else {

                    addressLineOneEditText.setText("");

                }


            }
        });


        try {
           /* if (GlobalValues.currentAvailibilityId == null) {

                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
*/
            //FB login check for change password
            if (Utility.readFromSharedPreference(mContext, GlobalValues.FB_LOGIN).equalsIgnoreCase("0")) {
                isFbLogin = false;
            } else {
                isFbLogin = true;
            }

   /*         if (isFbLogin) {
                //gone
                changePasswordTextView.setVisibility(View.GONE);
            } else {
                changePasswordTextView.setVisibility(View.VISIBLE);
            }*/

        } catch (Exception e) {
            e.printStackTrace();

          /*  Intent intent = new Intent(mContext, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();*/


        }

        Intent mainIintent = null;//getIntent();
        if (mainIintent != null) {

            if (mainIintent.hasExtra("FROM")) {

                String fromExtra = mainIintent.getStringExtra("FROM");

                //if(fromExtra.equals("Checkout_Activity"))
                from = fromExtra;

            }


        }

        if (from.equals("Checkout_Activity")) {


           /* myDashboardExpandableLayout.collapse();
            accInfoExpandableLayout.expand();
            prevOrdersExpandableLayout.collapse();*/


        }

        birthday_editview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                hideKeyboard();


                if (dobDate == null) {


                    Calendar calendar = Calendar.getInstance();

//                    new DatePickerDialog(MyProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//
//

//
//
//                        }
//                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
//                            .show();


                    final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.YEAR, year);
                            cal.set(Calendar.MONTH, month);
                            cal.set(Calendar.DAY_OF_MONTH, day);

                            dobDate = cal.getTime();

                            SimpleDateFormat simpleDisplayDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                            birthday_editview.setText(simpleDisplayDateFormat.format(dobDate));


                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();

                } else {

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, dobDate.getYear() + 1900);
                    calendar.set(Calendar.MONTH, dobDate.getMonth());
                    calendar.set(Calendar.DAY_OF_MONTH, dobDate.getDate());


                    final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.YEAR, year);
                            cal.set(Calendar.MONTH, month);
                            cal.set(Calendar.DAY_OF_MONTH, day);

                            dobDate = cal.getTime();

                            SimpleDateFormat simpleDisplayDateFormat = new SimpleDateFormat("dd/MM/yyyy");



                            birthday_editview.setText(simpleDisplayDateFormat.format(dobDate));


                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();


                }


            }
        });


        final ArrayAdapter<String> spinner_genders = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, genders);

        gender_editview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Gender")
                        .setAdapter(spinner_genders, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();


                                gender_editview.setText(genders[which].toString());


                            }
                        }).create().show();
            }
        });

        new CustomerDetailsTask().execute();
        new PointsTask().execute();

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = firstname_editview.getText().toString();
                String lastName = lastname_editview.getText().toString();
                String mobileNumber = mobilenumber_editview.getText().toString();
                String nickName = preferredname_editview.getText().toString();
                String email = emailaddress_editview.getText().toString();

                String gender = gender_editview.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String birthday = "";
                if (dobDate != null) {
                    birthday = sdf.format(dobDate);        // birthDayEditText.getText().toString();   //change this
                } else {
                    birthday = "";
                }

                // String companyName = companyNameEditText.getText().toString();
                String addressLineOne = addressLineOneEditText.getText().toString();
                String postalCode = postalCodeEditText.getText().toString();
                String unitNumberOne = unitNumEditText.getText().toString();
                String unitNumberTwo = unitNumEditTextTwo.getText().toString();


                if (firstName.equals("")) {

                    Toast.makeText(mContext, "Please enter first name", Toast.LENGTH_SHORT).show();

                } else if (lastName.equals("")) {

                    Toast.makeText(mContext, "Please enter last name", Toast.LENGTH_SHORT).show();

                } else if (mobileNumber.equals("")) {

                    Toast.makeText(mContext, "Please enter mobile number", Toast.LENGTH_SHORT).show();

                } else if (mobileNumber.length() < 8) {

                    Toast.makeText(mContext, "Please enter a 8-digit Mobile Number", Toast.LENGTH_LONG).show();

                } else if (email.equals("")) {

                    Toast.makeText(mContext, "Please enter email", Toast.LENGTH_SHORT).show();

                } /*else if (birthday.equals("")) {

                    Toast.makeText(NewMyAccountActivity.this, "Please select birthday", Toast.LENGTH_SHORT).show();

                }*/
                /* else if (companyName.equals("")) {

                    Toast.makeText(NewMyAccountActivity.this, "Please enter company name", Toast.LENGTH_SHORT).show();

                }*/

        /*        else if (addressLineOne.equals("")) {

                    Toast.makeText(NewMyAccountActivity.this, "Please enter valid postal code", Toast.LENGTH_SHORT).show();

                } else if (postalCode.equals("")) {

                    Toast.makeText(NewMyAccountActivity.this, "Please enter postal code", Toast.LENGTH_SHORT).show();

                } */

                /*else if (unitNumber.equals("")) {

                    Toast.makeText(NewMyAccountActivity.this, "Please enter unit number", Toast.LENGTH_SHORT).show();

                }*/
                else {
                    //run update task
                    Map<String, String> mapData = new HashMap<>();
                    mapData.put("app_id", GlobalValues.APP_ID);
                    mapData.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                    mapData.put("customer_first_name", firstName);
                    mapData.put("customer_last_name", lastName);
                    mapData.put("customer_email", email);
                    mapData.put("customer_phone", mobileNumber);
                    mapData.put("customer_birthdate", birthday);
                    mapData.put("customer_nick_name", nickName);
                    mapData.put("customer_address_line1", addressLineOne);
                    mapData.put("customer_postal_code", postalCode);
                    mapData.put("customer_address_name", unitNumberOne);
                    mapData.put("customer_address_name2", unitNumberTwo);
                    mapData.put("customer_photo", userImgString);

                    mapData.put("customer_hobby", getHobbyPostString());

                    if (gender.equals(genders[0])) {
                        mapData.put("customer_gender", "M");
                    } else if (gender.equals(genders[1])) {
                        mapData.put("customer_gender", "F");
                    } else if (gender.equals(genders[2])) {
                        mapData.put("customer_gender", "O");
                    }

/*                    mapData.put("customer_address_line1", addressLineOne);
                    mapData.put("customer_postal_code", postalCode);
                    mapData.put("customer_company_name", companyName);

                    mapData.put("customer_address_name", unitNumberOne);
                    mapData.put("customer_address_name2", unitNumberTwo);*/
                    mapData.put("type", "App");


                    try {

                        //run(formBody);

                        new UpdateTask(mapData).execute();

                        hideKeyboard();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }
        });


        return mView;/**/
    }

    private void getSecondaryAddress() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) != null) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
        }

        String url = GlobalUrl.OTHER_ADDRESS_URL + "?app_id=" + GlobalValues.APP_ID + "&status=A" + "&refrence=" + mCustomerId;

        new OtherAddressTask().execute(url);
    }


    public void requestPhotoPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, GlobalValues.REQUEST_WRITE_PERMISSION);
        } else {
            selectImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GlobalValues.REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        }
    }

    public void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera_intent, CAMERA_PIC_REQUEST);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {

                    Bitmap profilebitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    profilebitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] datanew = byteArrayOutputStream.toByteArray();
                    userImgString = Base64.encodeToString(datanew, Base64.DEFAULT);
                    imageView_profileview.setImageBitmap(profilebitmap);

                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = mContext.getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap bitmap = (BitmapFactory.decodeFile(picturePath));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] prodata = byteArrayOutputStream.toByteArray();
                    userImgString = Base64.encodeToString(prodata, Base64.DEFAULT);
                    imageView_profileview.setImageBitmap(bitmap);

                }
                break;
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }


    private String getHobbyPostString() {

        if (hobbySelection != null && hobbySelection.size() > 0) {

            StringBuilder stringBuilder = new StringBuilder();

            int size = hobbySelection.keySet().size();
            Set<String> keys = hobbySelection.keySet();

            for (String hobbyKey : keys) {

                if (hobbySelection.get(hobbyKey))
                    stringBuilder.append(hobbyKey + ";");

            }



            /*for (int i = 0; i < size; i++) {
                String key = keys.keys[i];

                if ((i + 1) == hobbySelection.keySet().size())
                    stringBuilder.append(hobbySelection.get(key));

            }*/

            /*for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
                String hobbyKey = it.next();

                stringBuilder.append(hobbySelection.get(hobbyKey) + ";");


            }*/


            return stringBuilder.toString();

        } else {

            return "";
        }


    }

    private void initHobbyUI() {

        divingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hobbySelection.get(divingTextView.getText().toString()) == true) {
                    hobbySelection.put(divingTextView.getText().toString(), false);
                    hobbyUnSelected(divingTextView);
                } else {
                    hobbySelection.put(divingTextView.getText().toString(), true);
                    hobbySelected(divingTextView);
                }

                showSelectedHobbiesOnInfo();

            }
        });

        soccerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hobbySelection.get(soccerTextView.getText().toString()) == true) {
                    hobbySelection.put(soccerTextView.getText().toString(), false);
                    hobbyUnSelected(soccerTextView);
                } else {
                    hobbySelection.put(soccerTextView.getText().toString(), true);
                    hobbySelected(soccerTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });

        basketBallTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hobbySelection.get(basketBallTextView.getText().toString()) == true) {
                    hobbySelection.put(basketBallTextView.getText().toString(), false);
                    hobbyUnSelected(basketBallTextView);
                } else {
                    hobbySelection.put(basketBallTextView.getText().toString(), true);
                    hobbySelected(basketBallTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });

        runningTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobbySelection.get(runningTextView.getText().toString()) == true) {
                    hobbySelection.put(runningTextView.getText().toString(), false);
                    hobbyUnSelected(runningTextView);
                } else {
                    hobbySelection.put(runningTextView.getText().toString(), true);
                    hobbySelected(runningTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });
        fitnessTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobbySelection.get(fitnessTextView.getText().toString()) == true) {
                    hobbySelection.put(fitnessTextView.getText().toString(), false);
                    hobbyUnSelected(fitnessTextView);
                } else {
                    hobbySelection.put(fitnessTextView.getText().toString(), true);
                    hobbySelected(fitnessTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });
        cyclingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobbySelection.get(cyclingTextView.getText().toString()) == true) {
                    hobbySelection.put(cyclingTextView.getText().toString(), false);
                    hobbyUnSelected(cyclingTextView);
                } else {
                    hobbySelection.put(cyclingTextView.getText().toString(), true);
                    hobbySelected(cyclingTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });
        dancingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobbySelection.get(dancingTextView.getText().toString()) == true) {
                    hobbySelection.put(dancingTextView.getText().toString(), false);
                    hobbyUnSelected(dancingTextView);
                } else {
                    hobbySelection.put(dancingTextView.getText().toString(), true);
                    hobbySelected(dancingTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });
        motorCyclingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobbySelection.get(motorCyclingTextView.getText().toString()) == true) {
                    hobbySelection.put(motorCyclingTextView.getText().toString(), false);
                    hobbyUnSelected(motorCyclingTextView);
                } else {
                    hobbySelection.put(motorCyclingTextView.getText().toString(), true);
                    hobbySelected(motorCyclingTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });
        photographyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobbySelection.get(photographyTextView.getText().toString()) == true) {
                    hobbySelection.put(photographyTextView.getText().toString(), false);
                    hobbyUnSelected(photographyTextView);
                } else {
                    hobbySelection.put(photographyTextView.getText().toString(), true);
                    hobbySelected(photographyTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });
        martialArtsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobbySelection.get(martialArtsTextView.getText().toString()) == true) {
                    hobbySelection.put(martialArtsTextView.getText().toString(), false);
                    hobbyUnSelected(martialArtsTextView);
                } else {
                    hobbySelection.put(martialArtsTextView.getText().toString(), true);
                    hobbySelected(martialArtsTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });
        dogsAndPetsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobbySelection.get(dogsAndPetsTextView.getText().toString()) == true) {
                    hobbySelection.put(dogsAndPetsTextView.getText().toString(), false);
                    hobbyUnSelected(dogsAndPetsTextView);
                } else {
                    hobbySelection.put(dogsAndPetsTextView.getText().toString(), true);
                    hobbySelected(dogsAndPetsTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });
        rollerBladeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hobbySelection.get(rollerBladeTextView.getText().toString()) == true) {
                    hobbySelection.put(rollerBladeTextView.getText().toString(), false);
                    hobbyUnSelected(rollerBladeTextView);
                } else {
                    hobbySelection.put(rollerBladeTextView.getText().toString(), true);
                    hobbySelected(rollerBladeTextView);
                }
                showSelectedHobbiesOnInfo();
            }
        });
    }


    public boolean networkCheck() {

        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }


    //Default Address
    public class CustomerDetailsTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog availibilityDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            availibilityDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            availibilityDialog.setMessage("Loading...");
            availibilityDialog.setCancelable(false);
            availibilityDialog.show();


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                //Promotion count and Reawrd points

                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                    mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                    mReferenceId = "";

                } else {
                    mReferenceId = Utility.getReferenceId(mContext);
                    mCustomerId = "";
                }

                String cartDetailURL = GlobalUrl.DEFAULT_BILLING_URL + "?app_id=" + GlobalValues.APP_ID
                        + "&refrence=" + mCustomerId;



                response = WebserviceAssessor.GetRequest(cartDetailURL);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            availibilityDialog.dismiss();


            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);


/*
                    "common":{"image_source":"http:\/\/ccpl.ninjaos.com\/media\/dev_team\/profile-pictures\/customers\/"}}
*/


                    JSONObject jsonObjectCommonImgurl = responseJSONObject.getJSONObject("common");

                    CommonImageurl = jsonObjectCommonImgurl.optString("image_source");


                    status = responseJSONObject.getString("status");

                    if (status.equals("ok")) {    //Success


                        Utility.writeToSharedPreference(mContext, GlobalValues.CUSTOMER_INFO, responseJSONObject.toString());

                        AccountDetail accountDetail = new AccountDetail();

                        JSONObject resultJSON = responseJSONObject.getJSONObject("result_set");

                        String customer_id = resultJSON.getString("customer_id");
                        String customer_company_id = resultJSON.getString("customer_company_id");
                        String customer_first_name = resultJSON.getString("customer_first_name").replace("\\", "");
                        String customer_last_name = resultJSON.getString("customer_last_name").replace("\\", "");
                        String customer_email = resultJSON.getString("customer_email");
                        String customer_phone = resultJSON.getString("customer_phone");
                        String customer_birthdate = resultJSON.getString("customer_birthdate");
                        String customer_company_name = resultJSON.getString("customer_company_name").replace("\\", "");
                        String customer_address_name = resultJSON.getString("customer_address_name");
                        String customer_address_name2 = resultJSON.getString("customer_address_name2");
                        String customer_address_line1 = resultJSON.getString("customer_address_line1").replace("\\", "");
                        String customer_address_line2 = resultJSON.getString("customer_address_line2").replace("\\", "");
                        String customer_city = resultJSON.getString("customer_city");
                        String customer_state = resultJSON.getString("customer_state");
                        String customer_country = resultJSON.getString("customer_country");
                        String customer_postal_code = resultJSON.getString("customer_postal_code");
                        String customer_photo = resultJSON.getString("customer_photo");
                        String customer_notes = resultJSON.getString("customer_notes");
                        String customer_status = resultJSON.getString("customer_status");
                        String customer_company_address = resultJSON.getString("customer_company_address");
                        String customer_company_phone = resultJSON.getString("customer_company_phone");

                        String customer_reward_point = resultJSON.getString("customer_reward_point");
                        String client_reward_point = resultJSON.getString("client_reward_point");

                        String customer_nick_name = resultJSON.getString("customer_nick_name");
                        String customer_gender = resultJSON.getString("customer_gender");
                        String customer_hobby = resultJSON.getString("customer_hobby");
                        String customer_created_on = resultJSON.getString("customer_created_on");
                        String customer_membership_type = resultJSON.getString("customer_membership_type");
                        String customer_type = resultJSON.getString("customer_type");

                        // String customer_hobby = resultJSON.getString("customer_hobby");

                        String customer_unique_id = "";
                        if (resultJSON.has("customer_unique_id")) {
                            customer_unique_id = resultJSON.getString("customer_unique_id");
                        }

                        String customer_pasta_stripe_id_dev = "";
                        if (resultJSON.has("customer_stripe_id_dev")) {
                            customer_pasta_stripe_id_dev = resultJSON.getString("customer_stripe_id_dev");
                        }

                      /*  String customer_porto_stripe_id_dev = "";
                        if (resultJSON.has("customer_porto_stripe_id_dev")) {
                            customer_porto_stripe_id_dev = resultJSON.getString("customer_porto_stripe_id_dev");
                        } else {

                        }

                        String customer_kraft_stripe_id_dev = "";
                        if (resultJSON.has("customer_kraft_stripe_id_dev")) {
                            customer_kraft_stripe_id_dev = resultJSON.getString("customer_kraft_stripe_id_dev");
                        } else {

                        }*/

                        //Live
                        String customer_pasta_stripe_id_live = "";
                        if (resultJSON.has("customer_stripe_id_live")) {

                            customer_pasta_stripe_id_live = resultJSON.getString("customer_stripe_id_live");
                        }
                     /*   String customer_porto_stripe_id_live = "";
                        if (resultJSON.has("customer_porto_stripe_id_live")) {
                            customer_porto_stripe_id_live = resultJSON.getString("customer_porto_stripe_id_live");
                        } else {

                        }
                        String customer_kraft_stripe_id_live = "";
                        if (resultJSON.has("customer_kraft_stripe_id_live")) {
                            customer_kraft_stripe_id_live = resultJSON.getString("customer_kraft_stripe_id_live");

                        } else {

                        }*/

                        accountDetail.setCustomer_id(customer_id);
                        accountDetail.setCustomer_company_id(customer_company_id);
                        accountDetail.setCustomer_first_name(customer_first_name);
                        accountDetail.setCustomer_last_name(customer_last_name);
                        accountDetail.setCustomer_email(customer_email);
                        accountDetail.setCustomer_phone(customer_phone);
                        accountDetail.setCustomer_birthdate(customer_birthdate);
                        accountDetail.setCustomer_company_name(customer_company_name);
                        accountDetail.setCustomer_address_name(customer_address_name);
                        accountDetail.setCustomer_address_name2(customer_address_name2);
                        accountDetail.setCustomer_address_line1(customer_address_line1);
                        accountDetail.setCustomer_address_line2(customer_address_line2);
                        accountDetail.setCustomer_city(customer_city);
                        accountDetail.setCustomer_state(customer_state);
                        accountDetail.setCustomer_country(customer_country);
                        accountDetail.setCustomer_postal_code(customer_postal_code);
                        accountDetail.setCustomer_photo(customer_photo);
                        accountDetail.setCustomer_notes(customer_notes);
                        accountDetail.setCustomer_status(customer_status);
                        accountDetail.setCustomer_company_address(customer_company_address);
                        accountDetail.setCustomer_company_phone(customer_company_phone);
                        accountDetail.setCustomer_reward_point(customer_reward_point);
                        accountDetail.setClient_reward_point(client_reward_point);

                        accountDetail.setIsFbLogin(false);

                        accountDetail.setcustomer_pasta_stripe_id_dev(customer_pasta_stripe_id_dev);
              /*          accountDetail.setcustomer_porto_stripe_id_dev(customer_porto_stripe_id_dev);
                        accountDetail.setcustomer_kraft_stripe_id_dev(customer_kraft_stripe_id_dev);*/

                        accountDetail.setCustomer_pasta_stripe_id_live(customer_pasta_stripe_id_live);
                    /*    accountDetail.setCustomer_porto_stripe_id_live(customer_porto_stripe_id_live);
                        accountDetail.setCustomer_kraft_stripe_id_live(customer_kraft_stripe_id_live);*/

                        accountDetail.setCustomer_nick_name(customer_nick_name);
                        accountDetail.setCustomer_gender(customer_gender);
                        accountDetail.setCustomer_hobby(customer_hobby);
                        accountDetail.setCustomer_created_on(customer_created_on);
                        accountDetail.setCustomer_membership_type(customer_membership_type);
                        accountDetail.setCustomer_type(customer_type);

                        accountDetail.setCustomer_unique_id(customer_unique_id);


                        GlobalValues.ACCOUNT_DETAIL = accountDetail;


                        //Update data to my account section after retrived data


                        updateUserDetails();


                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

            }
        }

    }


    // Get Address for postal code Task
    private class GetAddressTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog progressDialog;

        EditText addressEditText;
        AlertDialog alertDialog;

        GetAddressTask(EditText addressEditText, AlertDialog alertDialog) {
            this.addressEditText = addressEditText;
            this.alertDialog = alertDialog;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String app_id = "?app_id=" + GlobalValues.APP_ID;
                String zipCode = "&zip_code=" + strings[0];

                String url = GlobalUrl.GETADDRESS + app_id + zipCode;

                response = WebserviceAssessor.GetRequest(url);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {
                    responseJSONObject = new JSONObject(response);

                    status = responseJSONObject.getString("status");

                   /* JSONObject jsonObjectCommonImgurl = responseJSONObject.getJSONObject("common");

                    CommonImageurl = jsonObjectCommonImgurl.optString("image_source");*/


                    if (status.equals("ok")) {    //Success

                        JSONObject resultJSONObject = responseJSONObject.getJSONObject("result_set");

                        String zip_id = resultJSONObject.getString("zip_id");
                        String zip_code = resultJSONObject.getString("zip_code");
                        String zip_addtype = resultJSONObject.getString("zip_addtype");
                        String zip_buno = resultJSONObject.getString("zip_buno");
                        String zip_sname = resultJSONObject.getString("zip_sname");
                        String zip_buname = resultJSONObject.getString("zip_buname");

                        //     postalAddressTextView.setText(zip_buno + ", " + zip_sname);


                        addressEditText.setText(zip_buno + " " + zip_sname);


                    } else {

                        addressEditText.setText("");

                      /*  message = responseJSONObject.getString("form_error");
                        Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();*/
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                //    Toast.makeText(getActivity(), "Please check your internet connection.", Toast.LENGTH_LONG).show();

            }


        }
    }


    public void updateUserDetails() {
//TODO
        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_photo() != null
                && GlobalValues.ACCOUNT_DETAIL.getCustomer_photo().length() > 0) {
            Picasso.with(mContext)
                    .load(CommonImageurl + GlobalValues.ACCOUNT_DETAIL.getCustomer_photo()).
                    error(R.drawable.icon_myprofile).into(imageView_profileview);

        } else {

            Picasso.with(mContext)
                    .load(R.drawable.icon_myprofile).
                    placeholder(R.drawable.icon_myprofile).
                    into(imageView_profileview);

        }

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name().equals("")
                && !GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name().equals("null")) {

            user_textview.setText("Hello, " + GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name() + "!");

        } else {

            if (GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name().equals("null")) {


                if (GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name().equals("null")) {
                    user_textview.setText("Hello, " + GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() + " " + GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name() + "!");
                    // firstname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() + " " + GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name());
                } else {

                    user_textview.setText("Hello, " + GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() + "!");

                }
            } else {
                user_textview.setText("Hello,");
            }

        }

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name().equals("null")) {

            firstname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name());
        }


        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name().equals("null")) {
            lastname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name());

            lastname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name());

        }

                            /*if (GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name().equals("null")) {
                                user_textview.setText("Hello, " + GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() + "!");
                                contactNameTextView.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name());
                            } else {
                                user_textview.setText("Hello,");
                                contactNameTextView.setVisibility(View.GONE);
                            }*/

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_email() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_email().equals("null")) {
            emailaddress_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_email());
        }

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_phone() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_phone().equals("null")) {
            mobilenumber_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_phone());
        }

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name().equals("null")) {

            preferredname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name());
        }

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_address_line1() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_address_line1().equals("null"))
            addressLineOneEditText.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_address_line1());

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_postal_code() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_postal_code().equals("null"))
            postalCodeEditText.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_postal_code());

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name().equals("null"))
            unitNumEditText.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name());

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name().equals("null"))
            unitNumEditText.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name());

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name2() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name2().equals("null"))
            unitNumEditTextTwo.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name2());


        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_created_on() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_created_on().equals("null")) {

            try {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cal.setTime(sdf.parse(GlobalValues.ACCOUNT_DETAIL.getCustomer_created_on()));// all done

                customerCreatedDate = cal.getTime();


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                createdOnTextView.setText(simpleDateFormat.format(customerCreatedDate));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate().equals("0000-00-00")
        ) {

            try {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cal.setTime(sdf.parse(GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate()));// all done

                dobDate = cal.getTime();

            } catch (Exception e) {
                e.printStackTrace();
            }


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

            if (dobDate != null && !dobDate.equals("0000-00-00")) {
                birthday_editview.setText(simpleDateFormat.format(dobDate));
            } else {
                birthday_editview.setText("");
            }

            //    birthDayEditText.setText( new StringBuilder(GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate()).reverse().t  );

        }


      /*  if (GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point() != null &&
                !GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point().equals("")) {
            try {

                redeemed_textview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point());

            } catch (Exception e) {
                e.printStackTrace();
                redeemed_textview.setText("0");
            }

        } else {
            redeemed_textview.setText("0");
        }*/


        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_gender() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_gender().equals("null")) {

            String gender = GlobalValues.ACCOUNT_DETAIL.getCustomer_gender();
            if (gender.equals("M")) {
                gender_editview.setText(genders[0]);
            } else if (gender.equals("F")) {
                gender_editview.setText(genders[1]);
            }else if (gender.equals("O")) {
                gender_editview.setText(genders[2]);
            }

        }

        //Staff
        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_type() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_type().equals("null")) {
            if (GlobalValues.ACCOUNT_DETAIL.getCustomer_type().equals("Staff")) {
                staffTextView.setVisibility(View.VISIBLE);
            } else {
                staffTextView.setVisibility(View.GONE);
            }
        }


        //Kakis
        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_membership_type() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_membership_type().equals("null")) {
            if (GlobalValues.ACCOUNT_DETAIL.getCustomer_membership_type().equals("Kakis")) {
                kakiLayout.setVisibility(View.VISIBLE);
            } else {
                kakiLayout.setVisibility(View.GONE);

            }
        }


        try {
           // layoutMenmbershipId.setVisibility(View.VISIBLE);
            memberIdTextView.setVisibility(View.VISIBLE);
            if (GlobalValues.ACCOUNT_DETAIL.getCustomer_unique_id() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_unique_id().equals("null")) {
                String memberId = GlobalValues.ACCOUNT_DETAIL.getCustomer_unique_id();

              //  layoutMenmbershipId.setVisibility(View.VISIBLE);
                memberIdTextView.setText(memberId);

            } else {
               // layoutMenmbershipId.setVisibility(View.VISIBLE);
                memberIdTextView.setText("N/A");

            }

        } catch (Exception e) {
            e.printStackTrace();
            memberIdTextView.setText("null");
        }


        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_hobby() != null) {
            setHobbies(GlobalValues.ACCOUNT_DETAIL.getCustomer_hobby());
        } else {
            unselectAll();
        }


       /* if (GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point() != null &&
                !GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point().equals("")) {
            try {

                redeemed_textview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point());

            } catch (Exception e) {
                e.printStackTrace();
                redeemed_textview.setText("0");
            }

        } else {
            redeemed_textview.setText("0");
        }*/

    }


    public void hideKeyboard() {
        View view = mContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }


    private class UpdateTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog progressDialog;

        Map<String, String> mapData;

        UpdateTask(Map<String, String> mapData) {
            this.mapData = mapData;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                response = WebserviceAssessor.postRequest(mContext, GlobalUrl.UPDATEPROFILE_URL, mapData);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);


                    status = responseJSONObject.getString("status");


                    if (status.equals("ok")) {    //Success

                        message = responseJSONObject.getString("message");

                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();


                        try {
                            JSONObject jsonObjectCommonImgurl = responseJSONObject.getJSONObject("common");
                            CommonImageurl = jsonObjectCommonImgurl.optString("image_source");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //updating new account details
                        AccountDetail accountDetail = new AccountDetail();

                        JSONObject resultJSON = responseJSONObject.getJSONObject("result_set");

                        String customer_id = resultJSON.getString("customer_id");
                        String customer_company_id = resultJSON.getString("customer_company_id");
                        String customer_first_name = resultJSON.getString("customer_first_name").replace("\\", "");
                        String customer_last_name = resultJSON.getString("customer_last_name").replace("\\", "");
                        String customer_email = resultJSON.getString("customer_email");
                        String customer_phone = resultJSON.getString("customer_phone");
                        String customer_birthdate = resultJSON.getString("customer_birthdate");
                        String customer_company_name = resultJSON.getString("customer_company_name").replace("\\", "");
                        String customer_address_name = resultJSON.getString("customer_address_name");
                        String customer_address_name2 = resultJSON.getString("customer_address_name2");
                        String customer_address_line1 = resultJSON.getString("customer_address_line1").replace("\\", "");
                        String customer_address_line2 = resultJSON.getString("customer_address_line2").replace("\\", "");
                        String customer_city = resultJSON.getString("customer_city");
                        String customer_state = resultJSON.getString("customer_state");
                        String customer_country = resultJSON.getString("customer_country");
                        String customer_postal_code = resultJSON.getString("customer_postal_code");
                        String customer_photo = resultJSON.getString("customer_photo");
                        String customer_notes = resultJSON.getString("customer_notes");
                        String customer_status = resultJSON.getString("customer_status");
                        String customer_company_address = resultJSON.getString("customer_company_address");
                        String customer_company_phone = resultJSON.getString("customer_company_phone");

                        String customer_reward_point = resultJSON.getString("customer_reward_point");
                        String client_reward_point = resultJSON.getString("client_reward_point");

                        String customer_nick_name = resultJSON.getString("customer_nick_name");
                        String customer_gender = resultJSON.getString("customer_gender");
                        String customer_hobby = resultJSON.getString("customer_hobby");
                        String customer_created_on = resultJSON.getString("customer_created_on");
                        String customer_membership_type = resultJSON.getString("customer_membership_type");
                        String customer_type = resultJSON.getString("customer_type");
                        String customer_unique_id = "";
                        if (resultJSON.has("customer_unique_id")) {
                            customer_unique_id = resultJSON.getString("customer_unique_id");
                        }


                        Utility.writeToSharedPreference(mContext, GlobalValues.CUSTOMERID, resultJSON.optString("customer_id"));
                        Utility.writeToSharedPreference(mContext, GlobalValues.FIRSTNAME, resultJSON.optString("customer_first_name"));
                        Utility.writeToSharedPreference(mContext, GlobalValues.LASTNAME, resultJSON.optString("customer_last_name"));
                        Utility.writeToSharedPreference(mContext, GlobalValues.EMAIL, resultJSON.optString("customer_email"));
                        Utility.writeToSharedPreference(mContext, GlobalValues.CUSTOMERPHONE, resultJSON.optString("customer_phone"));
                        Utility.writeToSharedPreference(mContext, GlobalValues.POSTALCODE, resultJSON.optString("customer_postal_code"));


                        String customer_pasta_stripe_id_dev = "";
                        if (resultJSON.has("customer_stripe_id_dev")) {
                            customer_pasta_stripe_id_dev = resultJSON.getString("customer_stripe_id_dev");
                        }
/*
                        String customer_porto_stripe_id_dev = "";
                        if (resultJSON.has("customer_porto_stripe_id_dev")) {
                            customer_porto_stripe_id_dev = resultJSON.getString("customer_porto_stripe_id_dev");
                        } else {

                        }

                        String customer_kraft_stripe_id_dev = "";
                        if (resultJSON.has("customer_kraft_stripe_id_dev")) {
                            customer_kraft_stripe_id_dev = resultJSON.getString("customer_kraft_stripe_id_dev");
                        } else {

                        }*/

                        //Live
                        String customer_pasta_stripe_id_live = "";
                        if (resultJSON.has("customer_stripe_id_live")) {
                            customer_pasta_stripe_id_live = resultJSON.getString("customer_stripe_id_live");
                        }
                      /*  String customer_porto_stripe_id_live = "";
                        if (resultJSON.has("customer_porto_stripe_id_live")) {
                            customer_porto_stripe_id_live = resultJSON.getString("customer_porto_stripe_id_live");
                        } else {

                        }
                        String customer_kraft_stripe_id_live = "";
                        if (resultJSON.has("customer_kraft_stripe_id_live")) {
                            customer_kraft_stripe_id_live = resultJSON.getString("customer_kraft_stripe_id_live");

                        } else {

                        }*/


                        accountDetail.setCustomer_id(customer_id);
                        accountDetail.setCustomer_company_id(customer_company_id);
                        accountDetail.setCustomer_first_name(customer_first_name);
                        accountDetail.setCustomer_last_name(customer_last_name);
                        accountDetail.setCustomer_email(customer_email);
                        accountDetail.setCustomer_phone(customer_phone);
                        accountDetail.setCustomer_birthdate(customer_birthdate);
                        accountDetail.setCustomer_company_name(customer_company_name);
                        accountDetail.setCustomer_address_name(customer_address_name);
                        accountDetail.setCustomer_address_name2(customer_address_name2);
                        accountDetail.setCustomer_address_line1(customer_address_line1);
                        accountDetail.setCustomer_address_line2(customer_address_line2);
                        accountDetail.setCustomer_city(customer_city);
                        accountDetail.setCustomer_state(customer_state);
                        accountDetail.setCustomer_country(customer_country);
                        accountDetail.setCustomer_postal_code(customer_postal_code);
                        accountDetail.setCustomer_photo(customer_photo);
                        accountDetail.setCustomer_notes(customer_notes);
                        accountDetail.setCustomer_status(customer_status);
                        accountDetail.setCustomer_company_address(customer_company_address);
                        accountDetail.setCustomer_company_phone(customer_company_phone);
                        accountDetail.setCustomer_reward_point(customer_reward_point);
                        accountDetail.setClient_reward_point(client_reward_point);

                        accountDetail.setIsFbLogin(isFbLogin);

                        accountDetail.setCustomer_nick_name(customer_nick_name);
                        accountDetail.setCustomer_gender(customer_gender);
                        accountDetail.setCustomer_hobby(customer_hobby);
                        accountDetail.setCustomer_created_on(customer_created_on);
                        accountDetail.setCustomer_membership_type(customer_membership_type);
                        accountDetail.setCustomer_type(customer_type);
                        accountDetail.setCustomer_unique_id(customer_unique_id);


                        accountDetail.setcustomer_pasta_stripe_id_dev(customer_pasta_stripe_id_dev);
                  /*      accountDetail.setcustomer_porto_stripe_id_dev(customer_porto_stripe_id_dev);
                        accountDetail.setcustomer_kraft_stripe_id_dev(customer_kraft_stripe_id_dev);*/

                        accountDetail.setCustomer_pasta_stripe_id_live(customer_pasta_stripe_id_live);
                       /* accountDetail.setCustomer_porto_stripe_id_live(customer_porto_stripe_id_live);
                        accountDetail.setCustomer_kraft_stripe_id_live(customer_kraft_stripe_id_live);
*/

                        accountDetail.setCustomer_nick_name(customer_nick_name);

                        GlobalValues.ACCOUNT_DETAIL = accountDetail;


                        //storing new details in shared preference
                        SharedPreferences loginSharedPref = mContext.getSharedPreferences(
                                "Login_Credentials", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = loginSharedPref.edit();
                        editor.putString("LoginDetails", responseJSONObject.toString());
                        editor.commit();

                        if (from.equals("Checkout_Activity")) {

                            /*onBackPressed();
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();*/

                        } else if (from.equals("Order_Process")) {
                            /*onBackPressed();
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();*/

                        } else if (from.equals("Order_Reservation")) {

                            /*onBackPressed();
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();*/

                            /*Intent intent = new Intent(mContext, ReservationActivity.class);
                            startActivity(intent);*/


                        } else {
                            //dashboard

                            updateUserDetails();


                        }


                    } else {

                        message = responseJSONObject.getString("form_error");


                        String data = "";

                        if (message.length() > 2) {
                            data = message.substring(0, message.length() - 1);

                        } else {
                            data = message;
                        }

                        Toast.makeText(mContext, data.replace("<p>", "").replace("</p>", ""), Toast.LENGTH_LONG).show();


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_LONG).show();

            }


        }
    }


    // Points Display Task
    private class PointsTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String app_id = "?app_id=" + GlobalValues.APP_ID;
                String customer_id = "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                String url = GlobalUrl.REDEEMPOINTS_URL + app_id + customer_id;

                response = WebserviceAssessor.GetRequest(url);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);

                    status = responseJSONObject.getString("status");


                    if (status.equals("ok")) {    //Success

                        JSONObject resultJSONObject = responseJSONObject.getJSONObject("result_set");

                        String promocode_used = resultJSONObject.getString("promocode_used");
                        String earned_points = resultJSONObject.getString("earned_points");
                        String reedem_points = resultJSONObject.getString("reedem_points");


                        try {
                            double x_reedempoints = Double.parseDouble(reedem_points);
                            //  int reedemPoints = (int) x_reedempoints;


                            double x_earnedpoints = Double.parseDouble(earned_points);

                            //   int earnedPoints = (int) x_earnedpoints;

                            redeemed_textview.setText(String.valueOf(reedem_points));

                            String value = String.format("%.2f", new BigDecimal((GlobalValues.CUSTOMER_REWARD_POINT)));

                            SpannableStringBuilder cs = new SpannableStringBuilder(value);
//                            cs.setSpan(new SuperscriptSpan(), value.length()-3, value.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.6f), value.length() - 2, value.length(), 0);


                            earned_points_textview.setText(cs);

                            if (CUSTOMER_MONTHLY_REWARD_POINT > 0) {

                                mess_redwards.setText(String.valueOf(CUSTOMER_MONTHLY_REWARD_POINT) + " Points Expiring In 30 Days");
                            } else {
                                mess_redwards.setText("0.00 Points Expiring In 30 Days");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        noOfUse_textview.setText(promocode_used);

                    } else {

                      /*  message = responseJSONObject.getString("form_error");
                        Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();*/
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {

            }


        }
    }

    String TAG = "MyProfile";

    public void setHobbies(String hobbies) {

        try {

            String[] selectedHobbies = hobbies.split(";");
            if (selectedHobbies != null && selectedHobbies.length > 0) {

//                unselectAll();

                for (int i = 0; i < selectedHobbies.length; i++) {

                    String hobby = selectedHobbies[i];

                    if (hobby.equalsIgnoreCase(divingTextView.getText().toString())) {
                        hobbySelected(divingTextView);
                        hobbySelection.put(divingTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(rollerBladeTextView.getText().toString())) {
                        hobbySelected(rollerBladeTextView);
                        hobbySelection.put(rollerBladeTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(soccerTextView.getText().toString())) {
                        hobbySelected(soccerTextView);
                        hobbySelection.put(soccerTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(basketBallTextView.getText().toString())) {

                        hobbySelected(basketBallTextView);
                        hobbySelection.put(basketBallTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(runningTextView.getText().toString())) {
                        hobbySelected(runningTextView);
                        hobbySelection.put(runningTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(fitnessTextView.getText().toString())) {
                        hobbySelected(fitnessTextView);
                        hobbySelection.put(fitnessTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(cyclingTextView.getText().toString())) {
                        hobbySelected(cyclingTextView);
                        hobbySelection.put(cyclingTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(dancingTextView.getText().toString())) {
                        hobbySelected(dancingTextView);
                        hobbySelection.put(dancingTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(photographyTextView.getText().toString())) {
                        hobbySelected(photographyTextView);
                        hobbySelection.put(photographyTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(martialArtsTextView.getText().toString())) {
                        hobbySelected(martialArtsTextView);
                        hobbySelection.put(martialArtsTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(motorCyclingTextView.getText().toString())) {
                        hobbySelected(motorCyclingTextView);
                        hobbySelection.put(motorCyclingTextView.getText().toString(), true);

                    } else if (hobby.equalsIgnoreCase(dogsAndPetsTextView.getText().toString())) {
                        hobbySelected(dogsAndPetsTextView);
                        hobbySelection.put(dogsAndPetsTextView.getText().toString(), true);

                    }


                }


                showSelectedHobbiesOnInfo();


            } else {

                unselectAll();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    HashMap<String, Boolean> hobbySelection;

    public void createHobboySelections() {

        hobbySelection = new HashMap<>();
        hobbySelection.put(divingTextView.getText().toString(), false);
        hobbySelection.put(rollerBladeTextView.getText().toString(), false);
        hobbySelection.put(soccerTextView.getText().toString(), false);
        hobbySelection.put(basketBallTextView.getText().toString(), false);
        hobbySelection.put(runningTextView.getText().toString(), false);
        hobbySelection.put(fitnessTextView.getText().toString(), false);
        hobbySelection.put(cyclingTextView.getText().toString(), false);
        hobbySelection.put(dancingTextView.getText().toString(), false);
        hobbySelection.put(photographyTextView.getText().toString(), false);
        hobbySelection.put(martialArtsTextView.getText().toString(), false);
        hobbySelection.put(motorCyclingTextView.getText().toString(), false);
        hobbySelection.put(dogsAndPetsTextView.getText().toString(), false);


    }


    private void unselectAll() {

        hobbySelection.put(divingTextView.getText().toString(), false);
        hobbySelection.put(rollerBladeTextView.getText().toString(), false);
        hobbySelection.put(soccerTextView.getText().toString(), false);
        hobbySelection.put(basketBallTextView.getText().toString(), false);
        hobbySelection.put(runningTextView.getText().toString(), false);
        hobbySelection.put(fitnessTextView.getText().toString(), false);
        hobbySelection.put(cyclingTextView.getText().toString(), false);
        hobbySelection.put(dancingTextView.getText().toString(), false);
        hobbySelection.put(photographyTextView.getText().toString(), false);
        hobbySelection.put(martialArtsTextView.getText().toString(), false);
        hobbySelection.put(motorCyclingTextView.getText().toString(), false);
        hobbySelection.put(dogsAndPetsTextView.getText().toString(), false);

        hobbyUnSelected(divingTextView);
        hobbyUnSelected(rollerBladeTextView);
        hobbyUnSelected(soccerTextView);
        hobbyUnSelected(basketBallTextView);
        hobbyUnSelected(runningTextView);
        hobbyUnSelected(fitnessTextView);
        hobbyUnSelected(cyclingTextView);
        hobbyUnSelected(dancingTextView);
        hobbyUnSelected(photographyTextView);
        hobbyUnSelected(martialArtsTextView);
        hobbyUnSelected(motorCyclingTextView);
        hobbyUnSelected(dogsAndPetsTextView);


    }

    public void hobbyUnSelected(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.colorProfile_hobby_text_unselected));
        textView.setBackgroundColor(getResources().getColor(R.color.colorProfile_hobby_bg_unselected));
    }

    public void hobbySelected(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.colorProfile_hobby_text_selected));
        textView.setBackgroundColor(getResources().getColor(R.color.colorProfile_hobby_bg_selected));

    }


    public void showSelectedHobbiesOnInfo() {

        StringBuilder stringBuilder = new StringBuilder();

        for (String key : hobbySelection.keySet()) {
            if (hobbySelection.get(key)) {
                stringBuilder.append(key + ",");
            }
        }

        hobbyInfoLabelText.setText(stringBuilder.toString());

    }

    private class OtherAddressTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    layoutOtherAddress.setVisibility(View.VISIBLE);

                    secondaryAddressList = new ArrayList<>();
                    layoutOtherAddress.setVisibility(View.VISIBLE);
                    JSONArray jsonArray = jsonObject.getJSONArray("result_set");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject resultObj = jsonArray.getJSONObject(i);
                        SecondaryAddress secondaryAddress = new SecondaryAddress();

                        secondaryAddress.setSecondary_address_id(resultObj.getString("secondary_address_id"));
                        secondaryAddress.setAddress(resultObj.getString("address"));
                        secondaryAddress.setPostal_code(resultObj.getString("postal_code"));
                        secondaryAddressList.add(secondaryAddress);

                    }

                    otherAddressRecyclerAdapter = new OtherAddressRecyclerAdapter(mContext, secondaryAddressList);
                    recyclerviewOtherAddress.setAdapter(otherAddressRecyclerAdapter);
                    recyclerviewOtherAddress.setItemAnimator(new DefaultItemAnimator());
                    recyclerviewOtherAddress.setHasFixedSize(true);
                    recyclerviewOtherAddress.setNestedScrollingEnabled(false);
                    otherAddressRecyclerAdapter.notifyAdapter();


                    otherAddressRecyclerAdapter.setOnItemClick(new IOnItemClick() {
                        @Override
                        public void onItemClick(View v, int position) {

                            if (Utility.networkCheck(mContext)) {

                                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) != null) {

                                    mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);


                                    String url = GlobalUrl.DELETE_SECONDARY_ADDRESS_URL;

                                    Map<String, String> params = new HashMap<String, String>();

                                    params.put("app_id", GlobalValues.APP_ID);
                                    params.put("refrence", mCustomerId);
                                    params.put("secondary_address_id", secondaryAddressList.get(position).getSecondary_address_id());

                                    new DeleteAddressTask(params).execute(url);

                                }


                            } else {
                                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                } else {

                    layoutOtherAddress.setVisibility(View.GONE);

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }

        }
    }

    private class DeleteAddressTask extends AsyncTask<String, Void, String> {


        private ProgressDialog progressDialog;
        private Map<String, String> deleteParams;

        public DeleteAddressTask(Map<String, String> params) {

            deleteParams = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], deleteParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    getSecondaryAddress();
                    progressDialog.dismiss();

                } else {
                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }

        }
    }
}
