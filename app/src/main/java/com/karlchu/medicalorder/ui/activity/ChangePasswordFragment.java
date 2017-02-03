package com.karlchu.medicalorder.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karlchu.medicalorder.R;
import com.karlchu.medicalorder.core.A;
import com.karlchu.medicalorder.core.rest.RestClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends BaseFragment {
    private final String TAG = "ChangePasswordFragment";

    @BindView(R.id.edCurrentPassword)
    EditText edCurrent;

    @BindView(R.id.edNewPassword)
    EditText edNew;

    @BindView(R.id.edConfirmPassword)
    EditText edConfirm;

    @BindView(R.id.btnConfirm)
    Button submit;

    @BindView(R.id.changeNoti)
    TextView changeNoti;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(view.getContext());
        setHasOptionsMenu(true);

        requirePasswordView();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);

        bindEvents();
    }

    private void bindEvents() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = edCurrent.getText().toString();
                String newPassword = edNew.getText().toString();
                String confirmPassword = edConfirm.getText().toString();

                if (checkRequire(currentPassword, newPassword, confirmPassword)) {
                    boolean isValid = checkConfirmPassword(newPassword, confirmPassword);

                    if (isValid) {
                        progressDialog.show();
                        Call<Integer> call = RestClient.getInstance().getHomeService().changePassword(currentPassword, newPassword);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    int status = response.body();
                                    if (status == 1) {
                                        if (A.getPrincipal().getNeedChangePassword() != null &&
                                                A.getPrincipal().getNeedChangePassword()) {
                                            A.getPrincipal().setNeedChangePassword(false);
                                            parent.setDrawerState(true);
                                        }

                                        Intent intent = new Intent(parent, MainActivity.class);
                                        startActivity(intent);
                                        parent.finish();
                                        Toast.makeText(parent, R.string.change_password_success, Toast.LENGTH_LONG).show();
                                    } else if (status == -1) {
                                        edCurrent.setError(getString(R.string.error_cp_wrong));
                                        edCurrent.requestFocus();
                                    } else {
                                        Toast.makeText(parent, R.string.change_password_fail, Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(parent, R.string.change_password_fail, Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(parent, R.string.change_password_fail, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                }

            }
        });
    }

    private boolean checkConfirmPassword(String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            edConfirm.setError(getString(R.string.error_duplicated));
            edConfirm.requestFocus();

            return false;
        }

        return true;
    }

    private boolean checkRequire(String currentPassword, String newPassword, String confirmPassword) {
        if (TextUtils.isEmpty(currentPassword)) {
            edCurrent.setError(getString(R.string.error_cp_empty));
            edCurrent.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(newPassword)) {
            edNew.setError(getString(R.string.error_np_empty));
            edNew.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            edConfirm.setError(getString(R.string.error_cfp_empty));
            edConfirm.requestFocus();
            return false;
        }

        return true;
    }

    // Remove all navigation if require change password
    private void requirePasswordView() {
        if (A.getPrincipal().getNeedChangePassword() != null && A.getPrincipal().getNeedChangePassword()) {
            parent.setDrawerState(false);
            changeNoti.setVisibility(View.VISIBLE);
        }
    }

    // Remove all navigation if require change password
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // Hide home menu when require change password
        if (A.getPrincipal().getNeedChangePassword() != null && A.getPrincipal().getNeedChangePassword()) {
            menu.findItem(R.id.action_home).setVisible(false);
        }
    }


}
