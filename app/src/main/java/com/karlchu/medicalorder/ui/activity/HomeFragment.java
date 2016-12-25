package com.karlchu.medicalorder.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.karlchu.medicalorder.R;
import com.karlchu.medicalorder.core.rest.RestClient;
import com.karlchu.medicalorder.core.utils.ELog;
import com.karlchu.medicalorder.core.utils.SnackbarUtils;
import com.karlchu.medicalorder.ui.utils.UserUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hieu on 4/11/2016.
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.circleButton)
    LinearLayout circleButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        parent.setToolbarTitle(getString(R.string.home));

//        getCustomerInfo();

    }


    private boolean checkErrorServer() {
        boolean isError;
        isError = false;
        return isError;
    }


    /*
    * If user don't have Internet, toast message
    * */
    private void setServerFail() {
        Snackbar snackbar = SnackbarUtils.buildSnackbar(
                getView(), getString(R.string.server_error), getString(R.string.retry), new Runnable() {
                    @Override
                    public void run() {
//                        getCustomerInfo();
                    }
                });
        snackbar.show();
    }


}
