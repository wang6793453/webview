package com.sd.lib.webview;

import android.text.TextUtils;
import android.webkit.WebView;

import com.sd.lib.webview.cookie.FWebViewCookie;

import java.net.HttpCookie;
import java.util.List;

public class FWebViewManager
{
    private static FWebViewManager sInstance;

    private FWebViewHandler mWebViewHandler;

    private FWebViewManager()
    {
    }

    public static FWebViewManager getInstance()
    {
        if (sInstance == null)
        {
            synchronized (FWebViewManager.class)
            {
                if (sInstance == null)
                    sInstance = new FWebViewManager();
            }
        }
        return sInstance;
    }

    public void setWebViewHandler(FWebViewHandler webViewHandler)
    {
        mWebViewHandler = webViewHandler;
    }

    private FWebViewHandler getWebViewHandler()
    {
        if (mWebViewHandler == null)
            mWebViewHandler = FWebViewHandler.DEFAULT;
        return mWebViewHandler;
    }

    public void notifyWebViewInit(WebView webView)
    {
        getWebViewHandler().onInitWebView(webView);
    }

    /**
     * 把url对应的http的cookie同步到webview
     *
     * @param url
     */
    public void synchronizeHttpCookieToWebView(String url)
    {
        if (TextUtils.isEmpty(url))
            return;

        final List<HttpCookie> listHttpCookie = getWebViewHandler().getHttpCookieForUrl(url);
        FWebViewCookie.setCookie(url, listHttpCookie);
    }

    /**
     * 同步url对应的webview的cookie到http
     *
     * @param url
     */
    public void synchronizeWebViewCookieToHttp(String url)
    {
        if (TextUtils.isEmpty(url))
            return;

        final String cookie = FWebViewCookie.getCookie(url);
        final List<HttpCookie> listCookie = FWebViewCookie.getCookieAsList(url);
        getWebViewHandler().synchronizeWebViewCookieToHttp(cookie, listCookie, url);
    }
}
