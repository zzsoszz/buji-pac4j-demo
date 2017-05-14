package org.pac4j.oauth.client;

import com.fasterxml.jackson.databind.JsonNode;

import com.github.scribejava.core.builder.api.BaseApi;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.AttributesDefinition;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.profile.wechat.WeChatProfile;
import org.pac4j.profile.wechat.WeiXinApi20;
//import org.pac4j.scribe.builder.api.GenericApi20;
//SAML2Client
//FacebookClient
/*
 * http://jinnianshilongnian.iteye.com/blog/2018398
 */


public class WeChatClient extends BaseOAuth20StateClient<WeChatProfile> {

    protected String authUrl ="https://open.weixin.qq.com/connect/oauth2/authorize";//"https://open.weixin.qq.com/connect/qrconnect";// "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login#wechat_redirect" ;
    protected String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
    protected String profileUrl = "https://api.weixin.qq.com/sns/userinfo";
    protected AttributesDefinition attributesDefinition = null;
    
    protected String scope = null;

    public WeChatClient() {
    }

    /**
     * Convenience constructor. Uses {@link org.pac4j.oauth.profile.generic.DefaultGenericAttributesDefinition}
     * for the attributes definition
     *
     * @param key the key
     * @param secret the secret
     * @param authUrl the authorization url
     * @param tokenUrl the access token url
     * @param profileUrl the url to retrieve the profile
     * @param scope the OAuth scope
     */
    public WeChatClient(final String key,
                                final String secret,
                                final String authUrl,
                                final String tokenUrl,
                                final String profileUrl,
                                final String scope) {
        setKey(key);
        setSecret(secret);
        this.authUrl = authUrl;
        this.tokenUrl = tokenUrl;
        this.profileUrl = profileUrl;
        this.scope = scope;
    }

    /**
     * Convenience constructor. Allows for a user-defined AttributesDefinition to be passed in.
     *
     * @param key the key
     * @param secret the secret
     * @param authUrl the authorization url
     * @param tokenUrl the access token url
     * @param profileUrl the url to retrieve the profile
     * @param scope the OAuth scope
     * @param attributes the attributes definition
     */
    public WeChatClient(final String key,
                                final String secret,
                                final String authUrl,
                                final String tokenUrl,
                                final String profileUrl,
                                final String scope,
                                final AttributesDefinition attributes) {
        setKey(key);
        setSecret(secret);
        this.authUrl = authUrl;
        this.tokenUrl = tokenUrl;
        this.profileUrl = profileUrl;
        this.scope = scope;
        this.attributesDefinition = attributes;
    }

    @Override
    protected void internalInit(final WebContext context) {
        CommonHelper.assertNotBlank("authEndpoint", this.authUrl);
        CommonHelper.assertNotBlank("tokenEndpoint", this.tokenUrl);
        CommonHelper.assertNotBlank("profileEndpoint", this.profileUrl);
        super.internalInit(context);
    }

    @Override
    protected BaseApi<OAuth20Service> getApi() {
        return new WeiXinApi20(authUrl, tokenUrl);
    }

    @Override
    protected String getProfileUrl(final OAuth2AccessToken accessToken) {
        return profileUrl;
    }

    @Override
    protected WeChatProfile extractUserProfile(String body) {
        final WeChatProfile profile = new WeChatProfile();
        if (attributesDefinition != null) {
            profile.setAttributesDefinition(attributesDefinition);
        }
        final JsonNode json = JsonHelper.getFirstNode(body);
        if (json != null) {
            profile.setId(JsonHelper.getElement(json, "id"));
            for (final String attribute : profile.getAttributesDefinition().getPrimaryAttributes()) {
                profile.addAttribute(attribute, JsonHelper.getElement(json, attribute));
            }
        }
        return profile;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public AttributesDefinition getAttributesDefinition() {
        return attributesDefinition;
    }

    public void setAttributesDefinition(AttributesDefinition attributesDefinition) {
        this.attributesDefinition = attributesDefinition;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}


