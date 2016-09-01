package com.dream.example.data.support;

import android.net.Uri;
import android.text.TextUtils;

import com.dream.example.data.support.cookie.CookieJarImpl;
import com.dream.example.data.support.cookie.store.MemoryCookieStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.NetworkPolicy;

import org.yapp.ex.RequestException;
import org.yapp.utils.FileUtil;
import org.yapp.utils.Log;
import org.yapp.utils.SignalUtil;
import org.yapp.utils.Toast;
import org.yapp.y;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

/**
 * Description: 网络工厂. <br>
 * Date: 2016/3/17 9:59 <br>
 * Author: ysj
 */
public class HttpFactory {
    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";

    public static String HOST = AppConsts.ServerConfig.MAIN_HOST;

    public static OkHttpClient mClient;

    private static DataSupports mMainDataSupports;

    private static DefaultInterceptor mInterceptor; // 拦截器

    private static CookieJarImpl mCookieJar;

    static {
        System.setProperty("http.keepAlive", "false"); // 特别注意
        mInterceptor = DefaultInterceptor.getInstance();
        mCookieJar = new CookieJarImpl(new MemoryCookieStore());
        mClient = new OkHttpClient.Builder()
                .cookieJar(mCookieJar)
                .cache(new Cache(FileUtil.getCacheDir(AppConsts.AppConfig.CACHE_IMG), 1024 * 1024 * 100))// 开启图片缓存功能
                .addNetworkInterceptor(mInterceptor)
                .build();
    }

    /**
     * 获取MAIN_HOST请求支持类
     *
     * @return
     */
    public static DataSupports getMainDataSupports() {
        if (null == mMainDataSupports) {
            synchronized (HttpFactory.class) {
                mMainDataSupports = getDataSupports(HOST, DataSupports.class);
            }
        }
        return mMainDataSupports;
    }

    public static <T> T getDataSupports(String host, Class<?> supports) {
        return (T) new HttpFactory.HttpRetrofit(host, supports).getService();
    }

    public static void resetMainDataSupports() {
        synchronized (HttpFactory.class) {
            mMainDataSupports = null;
            HOST = AppConsts.ServerConfig.MAIN_HOST;
            mMainDataSupports = getDataSupports(HOST, DataSupports.class);
        }
    }

    public static DefaultDownLoader getDefaultDownLoader() {
        return DefaultDownLoader.getInstance();
    }

    /**
     * Description: 服务转换. <br>
     * Date: 2016/3/17 9:59 <br>
     * Author: ysj
     */
    private static class HttpRetrofit<T> {

        private final T mService;

        public HttpRetrofit(String host, Class<T> service) {
            // 适配器
            Retrofit serviceAdapter = new Retrofit.Builder()
                    .baseUrl(host)
                    .addConverterFactory(GsonConverterFactory.create(GSON))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mClient)
                    .build();
            mService = serviceAdapter.create(service);
        }

        public T getService() {
            return mService;
        }
    }

    /**
     * 默认图片下载器(注:用于Picasso)
     */
    private static class DefaultDownLoader implements Downloader {
        private static DefaultDownLoader instance;

        /**
         * getInstance:(获取实例).<br>
         *
         * @return
         */
        public static DefaultDownLoader getInstance() {
            if (instance == null) {
                synchronized (DefaultDownLoader.class) {
                    if (instance == null) {
                        instance = new DefaultDownLoader();
                    }
                }
            }
            return instance;
        }

        @Override
        public Response load(Uri uri, int networkPolicy) throws IOException {
            CacheControl.Builder builder = new CacheControl.Builder();
            if (networkPolicy != 0) {
                if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                    builder.onlyIfCached();
                } else {
                    if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                        builder.noCache();
                    }
                    if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                        builder.noStore();
                    }
                }
            }
            Request request = new Request.Builder()
                    .cacheControl(builder.build())
                    .url(uri.toString())
                    .build();
            okhttp3.Response response = mClient.newCall(request).execute();
            return new Response(response.body().byteStream(), false, response.body().contentLength());
        }

        @Override
        public void shutdown() {
        }

        private DefaultDownLoader() {
            if (mClient == null) {
                throw new IllegalStateException("OkHttpClient not yet initialized.");
            }
        }
    }

    /**
     * 默认拦截器
     */
    private static class DefaultInterceptor implements Interceptor {
        private static final Charset UTF8 = Charset.forName("UTF-8");
        private static DefaultInterceptor instance;

        /**
         * getInstance:(获取实例).<br>
         *
         * @return
         */
        public static DefaultInterceptor getInstance() {
            if (instance == null) {
                synchronized (DefaultInterceptor.class) {
                    if (instance == null) {
                        instance = new DefaultInterceptor();
                    }
                }
            }
            return instance;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            if (SignalUtil.isNetworkConnected() == false) {
                Toast.showMessageForCenterShort("当前网络状态不佳");
                throw new RequestException("当前网络状态不佳");
            }
            Request request = chain.request()
                    .newBuilder()
                    .header("User-Agent",USER_AGENT)
                    .build();
            logForRequest(request);
            Response originalResponse = chain.proceed(request);
            return logForResponse(originalResponse);
        }

        private Response logForResponse(Response response) throws IOException {
            if (!y.isDebug()) {
                return response;
            }
            //===>response log
            Log.d("========Response Log=======start");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Headers headers = clone.headers();
            Log.d("Url : " + clone.request().url());
            Log.d("Code : " + clone.code());
            if (headers != null && headers.size() > 0) {
                Log.d("Headers : " + clone.headers().toString());
            }
            Log.d("Protocol : " + clone.protocol());
            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            if (!TextUtils.isEmpty(clone.message()))
                Log.d("Message : " + clone.message());
            if (!hasBody(response)) {
                Log.d("========Response Log=======end");
            } else if (bodyEncoded(response.headers())) {
                Log.d("========Response Log=======end (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType mediaType = responseBody.contentType();
                if (mediaType != null) {
                    Log.d("ResponseBody ContentType : " + mediaType.toString());
                    try {
                        charset = mediaType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        Log.d("");
                        Log.d("Couldn't decode the response body; charset is likely malformed.");
                        Log.d("========Response Log=======end");
                        return response;
                    }
                }

                if (contentLength != 0) {
                    Log.d("");
                    Log.d("ResponseBody Content : " + buffer.clone().readString(charset));
                }
                Log.d("========Response Log=======end (" + buffer.size() + "-byte body)");
            }
            return response;
        }

        private void logForRequest(Request request) {
            if (!y.isDebug()) {
                return;
            }
            Headers headers = request.headers();
            Log.d("========Request Log=======start");
            Log.d("Method : " + request.method());
            Log.d("Url : " + request.url().toString());
            if (headers != null && headers.size() > 0) {
                Log.d("Headers : " + request.headers().toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.d("RequestBody ContentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.d("RequestBody Content : " + bodyToString(request));
                    } else {
                        Log.d("RequestBody Content : " + " maybe [file part] or too large too print , ignored!");
                    }
                }
            }
            Log.d("========Request Log=======end");
        }

        private boolean isText(MediaType mediaType) {
            if (mediaType.type() != null && mediaType.type().equals("text")) {
                return true;
            }
            if (mediaType.subtype() != null) {
                if (mediaType.subtype().equals("json") ||
                        mediaType.subtype().equals("xml") ||
                        mediaType.subtype().equals("html") ||
                        mediaType.subtype().equals("webviewhtml")
                        )
                    return true;
            }
            return false;
        }

        private String bodyToString(final Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "something error when show requestBody.";
            }
        }

        private boolean bodyEncoded(Headers headers) {
            String contentEncoding = headers.get("Content-Encoding");
            return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
        }

        /**
         * Returns true if the response must have a (possibly 0-length) body. See RFC 2616 section 4.3.
         */
        private boolean hasBody(Response response) {
            // HEAD requests never yield a body regardless of the response headers.
            if (response.request().method().equals("HEAD")) {
                return false;
            }

            int responseCode = response.code();
            if ((responseCode < 100 || responseCode >= 200)
                    && responseCode != HTTP_NO_CONTENT
                    && responseCode != HTTP_NOT_MODIFIED) {
                return true;
            }

            // If the Content-Length or Transfer-Encoding headers disagree with the
            // response code, the response is malformed. For best compatibility, we
            // honor the headers.
            if (contentLength(response) != -1
                    || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
                return true;
            }

            return false;
        }

        /**
         * 获取内容长度
         *
         * @param response
         * @return
         */
        private long contentLength(Response response) {
            Headers headers = response.headers();
            String s = headers.get("Content-Length");
            if (s == null) return -1;
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        private DefaultInterceptor() {
        }
    }
}
