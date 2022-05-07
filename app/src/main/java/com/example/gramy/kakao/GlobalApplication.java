package com.example.gramy.kakao;

import android.app.Application;
import androidx.annotation.Nullable;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;

        KakaoSDK.init(new KakaoSDKAdapter());
    }

    //@Override
    public void OnTerminate(){
        super.onTerminate();
        instance = null;
    }

    public static Application getInstance(){
        if (instance == null){
            throw new IllegalStateException("this app illegal state");
        }
        return instance;
    }
    public class KakaoSDKAdapter extends KakaoAdapter{
        @Override
        public ISessionConfig getSessionConfig(){
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Nullable
                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig(){
            return GlobalApplication::getInstance;
        }

    }
}
