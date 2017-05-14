package org.pac4j.profile.wechat;
import com.github.scribejava.core.builder.api.DefaultApi20;

import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.utils.OAuthEncoder;
import com.github.scribejava.core.model.OAuthConfig;


/*
 * 
 * 
 */
public class WeiXinApi20 extends DefaultApi20 {

	/*
	 * https://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html#.E7.AC.AC.E4.B8.80.E6.AD.A5.EF.BC.9A.E7.94.A8.E6.88.B7.E5.90.8C.E6.84.8F.E6.8E.88.E6.9D.83.EF.BC.8C.E8.8E.B7.E5.8F.96code
	 * 
	 * scope:
	 * snsapi_base
	 * snsapi_userinfo
	 */
    private final static String AUTHORIZATION_URL = "%s?response_type=code&appid=%s&redirect_uri=%s";

    protected final String authUrl;
    protected final String tokenUrl;

    public WeiXinApi20(String authUrl, String tokenUrl) {
        this.authUrl = authUrl;
        this.tokenUrl = tokenUrl;
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    public String getAccessTokenEndpoint() {
        return tokenUrl;
    }

    @Override
    public String getAuthorizationUrl(final OAuthConfig config) {
    	System.out.println(config);
    	System.out.println(config.getCallback());
        String url = String.format(AUTHORIZATION_URL, authUrl, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
        url += "&scope=snsapi_base";
//        if (config.hasScope()) {
//            url += "&scope=" + OAuthEncoder.encode(config.getScope());
//        }
        if (config.getState() != null) {
            url += "&state=" + OAuthEncoder.encode(config.getState());
        }
        System.out.println("url:"+url);
        return url;
    }
}


///**
// * 用于定义获取微信返回的code与access_token
// * Created by Jeng on 16/2/21.
// */
//public class WeiXinApi20 extends DefaultApi20 {
//    private static final String WEIXIN_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login#wechat_redirect";
//
//    @Override
//    public Verb getAccessTokenVerb()
//    {
//        return Verb.POST;
//    }
//
//    @Override
//    public String getAccessTokenEndpoint() {
//        return "https://api.weixin.qq.com/sns/oauth2/access_token";
//    }
//
//    @Override
//    public String getAuthorizationUrl(OAuthConfig config) {
//        return String.format(WEIXIN_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
//    }
//}
