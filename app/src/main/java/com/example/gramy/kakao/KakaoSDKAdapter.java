package com.example.gramy.kakao;

import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.sdk.common.model.ApprovalType;

public class KakaoSDKAdapter extends KakaoAdapter {

    @Override
    public IApplicationConfig getApplicationConfig() {
        return null;
    }

    public ISessionConfig getSessionConfig() {
        return new ISessionConfig(){
            @Override
            public AuthType[] getAuthTypes(){
                return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
            }
            @Override
            public boolean isUsingWebviewTimer(){
                return false;
            }

            @Override
            public boolean isSecureMode() {
                return false;
            }

            @Override
            public boolean inSecureMode(){
                return false;
            }
            @Override
            public ApprovalType getApprovalType(){
                return ApprovalType.INDIVIDUAL;
            }

            @Override
            public boolean isSaveFormData() {
                return false;
            }
        }
    }

}
