package com.karlchu.medicalorder.ui.utils;

import android.content.Context;
import android.content.Intent;

import com.karlchu.medicalorder.core.A;
import com.karlchu.medicalorder.core.db.Repo;
import com.karlchu.medicalorder.core.rest.RestClient;
import com.karlchu.medicalorder.core.utils.K;
import com.karlchu.medicalorder.ui.activity.LoginActivity;
import com.karlchu.medicalorder.ui.activity.MainActivity;
import com.karlchu.medicalorder.ui.adapter.AppConstant;

import java.util.List;

public class UserUtils {

    public static String getUserRole() {
        List<String> authorities = A.getPrincipal().getAuthorities();

        if (authorities.size() > 0) {
            if (authorities.contains(AppConstant.AUTH_CUSTOMER)) {
                return AppConstant.AUTH_CUSTOMER;
            }

            if (authorities.contains(AppConstant.AUTH_SM)) {
                return AppConstant.AUTH_SM;
            }

            if (authorities.contains(AppConstant.AUTH_SE)
                    || authorities.contains(AppConstant.AUTH_ASM)
                    || authorities.contains(AppConstant.AUTH_RSM)
                    || authorities.contains(AppConstant.AUTH_NW)
                    || authorities.contains(AppConstant.AUTH_CN)) {
                return AppConstant.AUTH_GENERAL;
            }
        }

        return "";
    }

    public static void logOut(Context context){
        A.delc(K.PRINCIPAL_JSON);
        A.setPrincipal(null);
        RestClient.getInstance().setAuthToken(null);

        Repo repo = new Repo(context);
       repo.release();

        A.prefEditor().clear().commit();

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((MainActivity)context).finish();

    }

}
