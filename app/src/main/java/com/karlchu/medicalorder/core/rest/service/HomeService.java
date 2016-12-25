package com.karlchu.medicalorder.core.rest.service;

import com.karlchu.medicalorder.core.UserPrincipal;
import com.karlchu.medicalorder.ui.adapter.AppConstant;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hieu on 3/10/2016.
 */
public interface HomeService {


    @POST(AppConstant.PREFIX_URL + "/mobile/retailer/auth.html")
    Call<UserPrincipal> login(@Query("userName") String userName, @Query("password") String password);


    @POST(AppConstant.PREFIX_URL + "/mobile/retailer/user/changePassword.html")
    Call<Integer> changePassword(@Query(value = "merchandiserId") Long merchandiserId,
                                 @Query(value = "oldPassword") String oldPassword,
                                 @Query(value = "newPassword") String newPass);

    @POST(AppConstant.PREFIX_URL + "/mobile/app/version.html")
    Call<Integer> checkVersion(@Query(value = "version") String merchandiserId);

    @FormUrlEncoded
    @POST(AppConstant.PREFIX_URL + "/mobile/retailer/updateCustomer.html")
    Call<Integer> updateCustomerInfo(@Field("name") String name,
                                     @Query("dateOfBirth") String dateOfBirth,
                                     @Query("phoneNumber") String phoneNumber);


    @POST(AppConstant.PREFIX_URL + "/mobile/retailer/sendGiftExchangeRequest.html")
    Call<Map<String, Object>> sendGiftExchangeRequest(@Query("quantity") Integer quantity,
                                                      @Query("cycleGiftId") Long cycleGiftId,
                                                      @Query("beneficiaryIds") List<Long> beneficiaryIds);

    @GET(AppConstant.PREFIX_URL + "/mobile/retailer/registerCustomer.html")
    Call<Integer> registerCustomer(@Query("distributorCode") String distributorCode,
                                   @Query("customerCode") String customerCode,
                                   @Query("name") String name,
                                   @Query("phoneNumber") String phoneNumber,
                                   @Query("dateOfBirth") String dateOfBirth);

    @FormUrlEncoded
    @POST(AppConstant.PREFIX_URL + "/mobile/retailer/changePassword.html")
    Call<Integer> changePassword(@Field("oldPassword") String oldPassword,
                                 @Field("newPassword") String newPassword);
}
