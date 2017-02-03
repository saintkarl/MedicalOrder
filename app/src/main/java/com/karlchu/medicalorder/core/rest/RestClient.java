package com.karlchu.medicalorder.core.rest;

import com.karlchu.medicalorder.core.db.NullOnEmptyConverterFactory;
import com.karlchu.medicalorder.core.rest.service.HomeService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Created by hieu on 3/8/2016.
 */
public final class RestClient {
//    public static final String API_BASE_URL = "http://karlchu.medicalorder.com/";
    public static final String API_BASE_URL = "http://192.168.110.1:8090/";
    private static RestClient instance = new RestClient();

    private Retrofit retrofit;
    private HomeService homeService;
    private OkHttpClient httpClient;
    private String authToken, version;


    private RestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.addInterceptor(logging);
        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
                if (authToken != null) {
                    requestBuilder.header("Authorization", authToken);
                }
                if (version != null) {
                    requestBuilder.header("appVersion", version);
                }
                String contentType = original.header("Content-Type");
                if (contentType == null) {
                    requestBuilder.header("Content-Type", "application/json")
                            .method(original.method(), original.body());
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        httpBuilder.addInterceptor(requestInterceptor);
        httpBuilder.connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES);

        httpClient = httpBuilder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory()) // Catch if repsonse return body empty
                .addConverterFactory(JacksonConverterFactory.create(createObjectMapper()))
                .client(httpClient)
                .build();
    }

    public static RestClient getInstance() {
        return instance;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static String getApiBaseUrl() {
        return API_BASE_URL;
    }

    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objectMapper;
    }

    public HomeService getHomeService() {
        if (homeService == null) {
            homeService = createService(HomeService.class);
        }
        return homeService;
    }

    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }


}
