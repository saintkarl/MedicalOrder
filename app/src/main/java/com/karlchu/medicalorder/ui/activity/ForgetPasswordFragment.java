package com.karlchu.medicalorder.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karlchu.medicalorder.R;
import com.karlchu.medicalorder.core.rest.RestClient;
import com.karlchu.medicalorder.core.utils.ELog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Linh Nguyen on 11/18/2016.
 */

public class ForgetPasswordFragment extends Activity {
    private final String TAG = "ForgetPasswordFragment";

    @BindView(R.id.edPhone)
    EditText edPhone;

    @BindView(R.id.login_msg)
    TextView tvErr;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forget_password);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage(getString(R.string.sending));
    }

//    @OnClick(R.id.btnLogin)
//    public void showLogin() {
//        onBackPressed();
//    }
//
//    @OnClick(R.id.btn_forget)
//    public void sendForgetPassword() {
//        String phone = edPhone.getText().toString().trim();
//
//        if (TextUtils.isEmpty(phone)) {
//            edPhone.setError(getString(R.string.phone_not_empty));
////            showResultDialog();
//        } else {
//            if (!progressDialog.isShowing()) {
//                progressDialog.show();
//            }
//            Call<Integer> call = RestClient.getInstance().getHomeService().findPassword(phone);
//            call.enqueue(new Callback<Integer>() {
//                @Override
//                public void onResponse(Call<Integer> call, Response<Integer> response) {
//                    if (response.isSuccessful() && response.body() != null) {
//                        int status = response.body();
//                        if (status == 1) {
//                            showResultDialog();
//                        } else if (status == 2) {
//                            showSmsDialog();
//                        } else {
//                            Toast.makeText(getBaseContext(), getString(R.string.send_fail), Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(getBaseContext(), getString(R.string.send_fail), Toast.LENGTH_LONG).show();
//                    }
//
//                    progressDialog.dismiss();
//                }
//
//                @Override
//                public void onFailure(Call<Integer> call, Throwable t) {
//                    ELog.e(t.getMessage(), t);
//                    progressDialog.dismiss();
//                    Toast.makeText(getBaseContext(), getString(R.string.send_fail), Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//
//    }

    private void showResultDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View bodyView = layoutInflater.inflate(R.layout.forgetpwd_result_item, null);

        builder.setView(bodyView);
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showSmsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View bodyView = layoutInflater.inflate(R.layout.forgetpwd_sms, null);

        builder.setView(bodyView);
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
