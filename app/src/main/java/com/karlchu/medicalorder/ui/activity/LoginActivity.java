package com.karlchu.medicalorder.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karlchu.medicalorder.R;
import com.karlchu.medicalorder.core.A;
import com.karlchu.medicalorder.core.UserPrincipal;
import com.karlchu.medicalorder.core.db.Repo;
import com.karlchu.medicalorder.core.rest.RestClient;
import com.karlchu.medicalorder.core.utils.CheckNetworkConnection;
import com.karlchu.medicalorder.core.utils.DroidUtils;
import com.karlchu.medicalorder.core.utils.ELog;
import com.karlchu.medicalorder.core.utils.K;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Linh Nguyen on 6/28/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private Repo repo;

    @BindView(R.id.edUsername)
    EditText editUsername;

    @BindView(R.id.edPassword)
    EditText editPassword;

    @BindView(R.id.btn_login)
    Button loginBtn;

    @BindView(R.id.login_msg)
    TextView txtLoginMsg;

    @BindView(R.id.version)
    TextView version;

    private ProgressDialog progressDialog;
    private String versionName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        repo = new Repo(this);

        bindViews();

        if (A.getPrincipal() != null) {
            RestClient.getInstance().setAuthToken(A.getPrincipal().getJwt());
            startHomeActivity();
        } else {
            bindEvents();
        }
    }

    private void bindViews() {
        ButterKnife.bind(this);

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage(A.s(R.string.loading));
            progressDialog.setCancelable(false);
        }

        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            version.setText(getString(R.string.version) + ": " + versionName);
            RestClient.getInstance().setVersion(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            ELog.w(e.getMessage());
        }
    }


    private void bindEvents() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckNetworkConnection.isConnectionAvailable(A.app())) {
                    setServerFail();
                    return;
                }

                txtLoginMsg.setVisibility(View.GONE);
                boolean isValid = true;
                if (TextUtils.isEmpty(editUsername.getText().toString().trim())) {
                    editUsername.setError(A.s(R.string.login_error_msg_empty_username));
                    isValid = false;
                }
                if (TextUtils.isEmpty(editPassword.getText())) {
                    editPassword.setError(A.s(R.string.login_error_msg_empty_password));
                    isValid = false;
                }

                if (isValid) {
                    progressDialog.show();
                    Call<UserPrincipal> loginCall = RestClient.getInstance().getHomeService().login(editUsername.getText().toString().trim(), editPassword.getText().toString());
                    loginCall.enqueue(new Callback<UserPrincipal>() {
                        @Override
                        public void onResponse(Call<UserPrincipal> call, Response<UserPrincipal> response) {
                            if (response.code() == 505) {
                                setWrongVersionMsg();
                            } else {
                                UserPrincipal userPrincipal = response.body();
                                if (userPrincipal != null && !TextUtils.isEmpty(userPrincipal.getJwt())) {

                                    RestClient.getInstance().setAuthToken(userPrincipal.getJwt());
                                    A.setPrincipal(userPrincipal);
                                    A.putc(K.PRINCIPAL_JSON, userPrincipal.toJsonString());

                                    startHomeActivity();

                                } else {
                                    setLoginFailedMsg();
                                }

                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserPrincipal> call, Throwable t) {
                            ELog.e(t.getMessage(), t);
                            setLoginFailedMsg();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });

        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(editPassword.getText().toString())) {
                    if (s.toString().length() > 0) {
                        loginBtn.setClickable(true);
                        loginBtn.setEnabled(true);
                        loginBtn.setBackgroundResource(R.drawable.button_rounded_enabled);
                    } else {
                        loginBtn.setClickable(false);
                        loginBtn.setEnabled(false);
                        loginBtn.setBackgroundResource(R.drawable.button_rounded);
                    }
                } else {
                    loginBtn.setClickable(false);
                    loginBtn.setEnabled(false);
                    loginBtn.setBackgroundResource(R.drawable.button_rounded);
                }
            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(editUsername.getText().toString())) {
                    if (s.toString().length() > 0) {
                        loginBtn.setClickable(true);
                        loginBtn.setEnabled(true);
                        loginBtn.setBackgroundResource(R.drawable.button_rounded_enabled);
                    } else {
                        loginBtn.setClickable(false);
                        loginBtn.setEnabled(false);
                        loginBtn.setBackgroundResource(R.drawable.button_rounded);
                    }
                } else {
                    loginBtn.setClickable(false);
                    loginBtn.setEnabled(false);
                    loginBtn.setBackgroundResource(R.drawable.button_rounded);
                }
            }
        });
    }


    @OnClick(R.id.btn_register)
    public void registerProgram() {
        AlertDialog dialog = initRegisterDialog();
        dialog.show();
    }

    private AlertDialog initRegisterDialog() {
        AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppThemeLogin));
        } else {
            builder  = new AlertDialog.Builder(LoginActivity.this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        }
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        View headerLayout = inflater.inflate(R.layout.register_header_dialog, null);
        final View layout = inflater.inflate(R.layout.register_body_dialog, null);

//        final TextView readTerm = (TextView) layout.findViewById(R.id.readTerm);
//        final EditText birthday = (EditText) layout.findViewById(R.id.edRegisterBirthday);
        final TextView registerSubmit = (TextView) layout.findViewById(R.id.register_submit);
        final CheckBox cbTerm = (CheckBox) layout.findViewById(R.id.cbTerm);
        TextView dialogTitle = (TextView) headerLayout.findViewById(R.id.dialogTitle);
        ImageView cancelBtn = (ImageView) headerLayout.findViewById(R.id.cancel);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cbTerm.setText(Html.fromHtml(getString(R.string.criteria), Html.FROM_HTML_MODE_LEGACY));
        } else {
            cbTerm.setText(Html.fromHtml(getString(R.string.criteria)));
        }
        cbTerm.setMovementMethod(LinkMovementMethod.getInstance());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        dialogTitle.setText(getString(R.string.register_program));
        builder.setCustomTitle(headerLayout);
        builder.setView(layout);

        AlertDialog dialog = builder.create();

//        setEvent(dialog, birthday, cancelBtn, readTerm);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
//                final EditText edCodeDistributor = (EditText) layout.findViewById(R.id.edCodeDistributor);
//                final EditText edCodeOutlet = (EditText) layout.findViewById(R.id.edCodeOutlet);
                final EditText edName = (EditText) layout.findViewById(R.id.edUsername);
//                final EditText edPhone = (EditText) layout.findViewById(R.id.edPhone);
//                final EditText birthday = (EditText) layout.findViewById(R.id.edRegisterBirthday);


                registerSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isValid = true;
//                        if (TextUtils.isEmpty(edCodeDistributor.getText().toString().trim())) {
//                            edCodeDistributor.setError(A.s(R.string.distributor_not_empty));
//                            isValid = false;
//                        }
//                        if (TextUtils.isEmpty(edCodeOutlet.getText().toString().trim())) {
//                            edCodeOutlet.setError(A.s(R.string.outlet_not_empty));
//                            isValid = false;
//                        }

//                        if (TextUtils.isEmpty(birthday.getText())) {
//                            birthday.setError(A.s(R.string.date_not_empty));
//                            isValid = false;
//                            birthday.requestFocus();
//                        }
//                        if (TextUtils.isEmpty(edPhone.getText())) {
//                            edPhone.setError(A.s(R.string.phone_not_empty));
//                            isValid = false;
//                            edPhone.requestFocus();
//                        }
                        if (TextUtils.isEmpty(edName.getText().toString().trim())) {
                            edName.setError(A.s(R.string.name_not_empty));
                            isValid = false;
                            edName.requestFocus();
                        }


                        if (isValid) {
                            if (!cbTerm.isChecked()) {
                                Toast.makeText(getBaseContext(),
                                        getString(R.string.not_choose_privacy), Toast.LENGTH_LONG).show();
                                return;
                            }
                            // Submit info to server
                            progressDialog.show();
//                            Call<Integer> call = RestClient.getInstance()
//                                    .getHomeService()
//                                    .registerCustomer(!TextUtils.isEmpty(edCodeDistributor.getText().toString().trim()) ? edCodeDistributor.getText().toString().trim(): null,
//                                            !TextUtils.isEmpty(edCodeOutlet.getText().toString().trim()) ? edCodeOutlet.getText().toString().trim() : null,
//                                            edName.getText().toString().trim(),
//                                            edPhone.getText().toString(),
//                                            birthday.getText().toString());
//
//                            call.enqueue(new Callback<Integer>() {
//                                @Override
//                                public void onResponse(Call<Integer> call, Response<Integer> response) {
//                                    if (response.isSuccessful() && response.body() != null) {
//                                        Integer isSuccess = response.body();
//
//                                        if (isSuccess == 1) {
//                                            Toast.makeText(getBaseContext(), getString(R.string.register_success), Toast.LENGTH_LONG).show();
//
//                                        } else if (isSuccess == 0) {
//                                            Toast.makeText(getBaseContext(), getString(R.string.registered_fail), Toast.LENGTH_LONG).show();
//                                            dialog.dismiss();
//                                        } else {
//                                            Toast.makeText(getBaseContext(), getString(R.string.info_notvalid), Toast.LENGTH_LONG).show();
//                                        }
//                                    } else {
//                                        Toast.makeText(getBaseContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
//                                    }
//                                    progressDialog.dismiss();
//                                }
//
//                                @Override
//                                public void onFailure(Call<Integer> call, Throwable t) {
//                                    Toast.makeText(getBaseContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
//                                    progressDialog.dismiss();
//                                    ELog.e(t.getMessage(), t);
//                                }
//                            });
                        }

                    }

                });

            }

        });


        return dialog;
    }

    private void setEvent(final AlertDialog dialog, final EditText birthday, ImageView cancelBtn, TextView readmore) {

        // Show calendar picker
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DATE, dayOfMonth);

                        String s = DroidUtils.date2String(calendar.getTime(), "dd/MM/yyyy");
                        birthday.setError(null);
                        birthday.setText(s);

                    }
                };

                Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(LoginActivity.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Click x icon, dismiss this register form
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Show term and condition
        readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder termDialogBuilder = new AlertDialog.Builder(LoginActivity.this);

                termDialogBuilder.setTitle(getString(R.string.term));
                termDialogBuilder.setMessage(getString(R.string.term_content));
                termDialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog termDialog = termDialogBuilder.create();
                termDialog.show();
            }
        });
    }

    @OnClick(R.id.btn_forgetPassword)
    public void showForgetPassword() {
        Intent intent = new Intent(this, ForgetPasswordFragment.class);
        startActivity(intent);

    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startSaleHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLoginFailedMsg() {
        txtLoginMsg.setText(R.string.login_failed_msg);
        txtLoginMsg.setVisibility(View.VISIBLE);
    }

    private void setWrongVersionMsg() {
        txtLoginMsg.setText(R.string.login_wrongversion_msg);
        txtLoginMsg.setVisibility(View.VISIBLE);
    }

    private void setServerFail() {
        txtLoginMsg.setText(R.string.server_error);
        txtLoginMsg.setVisibility(View.VISIBLE);
    }

    private void setInernetFail() {
        txtLoginMsg.setText(R.string.not_internet);
        txtLoginMsg.setVisibility(View.VISIBLE);
    }
}
