package com.example.administrator.testverticalviewpager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.testverticalviewpager.bean.User;
import com.example.administrator.testverticalviewpager.widget.LampView;
import com.example.administrator.testverticalviewpager.widget.LampViewRight;
import com.example.administrator.testverticalviewpager.widget.MyStackView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyVerticalViewPager verticalViewPager;
    private WebView mWebView;
    private VerticalViewPagerAdapter adapter;
    private final String TAG = MainActivity.class.getSimpleName();
    private Button btn_start;
    private Button btn_stop;
    private ImageView iv_test;
    String avatar = "https://images.qn.rgbvr.com/misc/1502894024145image_20170816223344";
    private Button mBtnNext;
    private Button mBtnSnack;
    private View mRootView;
    private Toast mTopToast;
    private Button mBtnSwipe;
    private MyStackView mMyStackView;
    private EditText mEtChange;
    private LampView mLampViewLeft;
    private LampViewRight mLampViewRight;
    private RelativeLayout mRlLampViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(mRootView);
        initView();
        Log.d("apollo", "onCreate");
//        testWebView();

    }

    private void testWebView() {
        mWebView.requestFocusFromTouch();//输入焦点
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//支持js
        //自适应屏幕
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.supportMultipleWindows();  //多窗口
        settings.setAllowFileAccess(true);  //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        //页面支持缩放
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        String myUa = getUA();
        String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua.replace("Android", myUa));
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {

            }
        });
        mWebView.setWebViewClient(new WebViewClient() {//打开网页时不调用系统浏览器， 而是在本WebView中显示
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }

                // Otherwise allow the OS to handle things like tel, mailto, etc.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        String codeUrl = "http://www.duiba.com.cn/autoLogin/autologin?redirect=http%3A%2F%2Fhome.m.duiba.com.c" +
                "n%2Fchome%2Findex%3Fpreview%3Dtrue%26from%3Dlogin%26spm%3D38595.1.1.1&uid=13&credits=16195&sign=784" +
                "ebfdf16c3a8b590b73a6e561897f4&appKey=uYSLYdB88LzNgW7aTAxJ48Z3YYk&timestamp=1512569846470&";
//        String codeUrl = "http://www.baidu.com.cn";
        mWebView.loadUrl(codeUrl);
    }

    private void initView() {
        verticalViewPager = findViewById(R.id.vertical_vp);
        mWebView = findViewById(R.id.webview);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        iv_test = findViewById(R.id.iv_test_glide);
        mBtnNext = findViewById(R.id.btn_next);
        mBtnSnack = findViewById(R.id.test_snack);
        mBtnSwipe = findViewById(R.id.btn_swipe);
        mMyStackView = findViewById(R.id.stack_view);

        Glide.with(getApplicationContext())
                .load(avatar)
                .placeholder(R.drawable.ic_launcher_background)
                .into(iv_test);

        String json = "[\n" +
                "        {\n" +
                "            \"avatar\": \"avatarDir/123456_20170811173550\",\n" +
                "            \"broadCastContent\": \"此抓一处，谁与争锋！asd又抓到一只BNH\",\n" +
                "            \"dollPic\": \"misc/1509179775761image_20171028163615\",\n" +
                "            \"roomId\": 14,\n" +
                "            \"parentRoomId\": 13\n" +
                "        },\n" +
                "        {\n" +
                "            \"avatar\": \"avatarDir/3521688_20170826212359\",\n" +
                "            \"broadCastContent\": \"此抓一处，谁与争锋！大鱼箬\uD83D\uDC14又抓到一只泰迪熊\",\n" +
                "            \"dollPic\": \"misc/1502520827632image_20170812145347\",\n" +
                "            \"roomId\": 4,\n" +
                "            \"parentRoomId\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"avatar\": \"avatarDir/123485_20170810165658\",\n" +
                "            \"broadCastContent\": \"小丑抓到了一只＂泰迪熊＂，为你撒花~\",\n" +
                "            \"dollPic\": \"misc/1502520827632image_20170812145347\",\n" +
                "            \"roomId\": 4,\n" +
                "            \"parentRoomId\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"avatar\": \"avatarDir/default3.png\",\n" +
                "            \"broadCastContent\": \"guoyx抓到了一只＂泰迪熊＂！献上一只膝盖\",\n" +
                "            \"dollPic\": \"misc/1502520827632image_20170812145347\",\n" +
                "            \"roomId\": 6,\n" +
                "            \"parentRoomId\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"avatar\": \"avatarDir/4981338_20170915174447\",\n" +
                "            \"broadCastContent\": \"此抓一处，谁与争锋！pr0-m又抓到一只泰迪熊\",\n" +
                "            \"dollPic\": \"misc/1502520827632image_20170812145347\",\n" +
                "            \"roomId\": 6,\n" +
                "            \"parentRoomId\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"avatar\": \"avatarDir/123470_20170808145223\",\n" +
                "            \"broadCastContent\": \"Admin抓到了一只＂泰迪熊＂！献上一只膝盖\",\n" +
                "            \"dollPic\": \"misc/1502520827632image_20170812145347\",\n" +
                "            \"roomId\": 6,\n" +
                "            \"parentRoomId\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"avatar\": \"avatarDir/11_20170901144857\",\n" +
                "            \"broadCastContent\": \"哇哦！AN抓到了一只＂泰迪熊＂\",\n" +
                "            \"dollPic\": \"misc/1502520827632image_20170812145347\",\n" +
                "            \"roomId\": 6,\n" +
                "            \"parentRoomId\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"avatar\": \"avatarDir/11_20170901144857\",\n" +
                "            \"broadCastContent\": \"哇哦！AN抓到了一只＂泰迪熊＂\",\n" +
                "            \"dollPic\": \"misc/1502520827632image_20170812145347\",\n" +
                "            \"roomId\": 6,\n" +
                "            \"parentRoomId\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"avatar\": \"avatarDir/11_20170901144857\",\n" +
                "            \"broadCastContent\": \"哇哦！AN抓到了一只＂泰迪熊＂\",\n" +
                "            \"dollPic\": \"misc/1502520827632image_20170812145347\",\n" +
                "            \"roomId\": 6,\n" +
                "            \"parentRoomId\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"avatar\": \"avatarDir/11_20170901144857\",\n" +
                "            \"broadCastContent\": \"此抓一处，谁与争锋！AN又抓到一只泰迪熊\",\n" +
                "            \"dollPic\": \"misc/1502520827632image_20170812145347\",\n" +
                "            \"roomId\": 6,\n" +
                "            \"parentRoomId\": 1\n" +
                "        }\n" +
                "    ]";

        Gson gson = new Gson();
        Type type = new TypeToken<List<BroadcustData>>() {
        }.getType();
        List<BroadcustData> dataList = gson.fromJson(json, type);
        adapter = new VerticalViewPagerAdapter(getApplicationContext(), dataList);
        verticalViewPager.setAdapter(adapter);
        verticalViewPager.setPageTransformer(true, new DefaultTransformer());
        verticalViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter.setOnPagerItemClickListner(new VerticalViewPagerAdapter.OnPagerItemClickListner() {
            @Override
            public void onPagerItemClick(int postion) {
                Log.d(TAG, "点击了" + postion);
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                verticalViewPager.startLoop();
                mLampViewLeft.startLight(200, LampView.LOOP_TYPE_ADD);
                mLampViewRight.startLight(100, LampViewRight.LOOP_TYPE_SINGLE);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                verticalViewPager.stopLoop();
                mLampViewLeft.stopLight();
                mLampViewRight.stopLight();
            }
        });
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        mBtnSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topToastShow();

            }
        });
        mBtnSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, QuickGrabActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_swipe_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SwipeActivity.class);
                startActivity(intent);
            }
        });


        final User user = new User();
        user.setAvatar(String.valueOf(R.mipmap.emoji_01));
        User user1 = new User();
        user1.setAvatar(String.valueOf(R.mipmap.emoji_02));
        User user2 = new User();
        user2.setAvatar(String.valueOf(R.mipmap.emoji_03));
        User user3 = new User();
        user3.setAvatar(String.valueOf(R.mipmap.emoji_04));
        User user4 = new User();
        user4.setAvatar(String.valueOf(R.mipmap.emoji_05));
        User user5 = new User();
        user5.setAvatar(String.valueOf(R.mipmap.emoji_06));
        final List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        mMyStackView.setmUsers(userList);
        mMyStackView.setOnItemClickListener(new MyStackView.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Log.d(TAG, user.getAvatar());
            }
        });
        mEtChange = findViewById(R.id.et_change);

        findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mEtChange.getText().toString();
                if (TextUtils.isEmpty(s))
                    return;
                int number = Integer.parseInt(s);
                List<User> users = new ArrayList<>();
                for (int i = 0; i < number; i++) {
                    User user = new User();
                    user.setAvatar(String.valueOf(R.mipmap.emoji_06));
                    users.add(user);
                }
                mMyStackView.setmUsers(users);
            }
        });

        final LinearLayout llAddView = findViewById(R.id.ll_add_test);

        mLampViewLeft = findViewById(R.id.lamp_view_left);
        mLampViewRight = findViewById(R.id.lamp_view_right);
//        mLampViewLeft.post(new Runnable() {
//            @Override
//            public void run() {
//                int measuredHeight = mLampViewLeft.getHeight();
//                mLampViewLeft.initView(measuredHeight);
//            }
//        });

        findViewById(R.id.btn_add_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(R.mipmap.emoji_01);
                llAddView.addView(imageView);
            }
        });
        findViewById(R.id.btn_delete_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llAddView.removeAllViews();
            }
        });

    }


    /**
     * 顶部toast
     */
    private void topToastShow() {
//        mTopToast = TopToast.getToast(this, R.layout.snack_layout, 1000);
//        mTopToast.show();

//        显示5秒的Toast
//        ExToast exToast = ExToast.makeText(this,"message",5);
//        exToast.setAnimations(R.style.anim_top_toast);
//        exToast.show();
        testPop();
    }

    private void testPop() {
        TopBarUtil.show(this, R.layout.activity_main, 1000);
    }

    //按home键再返回activity会走
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("apollo", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("apollo", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        verticalViewPager.startLoop();
        Log.d("apollo", "onResume");
    }

    //若跳转的activity主题是透明，则本activity失去焦点但可见，走onPause
    //返回本activity走onResume
    //若跳转的activity主题不是透明，则本activity失去焦点，不可见，走onPause，onStop
    //返回本activity走onRestart，onStart，onResume
    @Override
    protected void onPause() {
        super.onPause();
        verticalViewPager.stopLoop();
        Log.d("apollo", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("apollo", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("apollo", "onDestroy");
    }

    /**
     * 手机信息
     *
     * @return
     */
    public static String getUA() {
        String handSetInfo =
                "手机型号:" + android.os.Build.MODEL +
                        ",SDK版本:" + android.os.Build.VERSION.SDK +
                        ",系统版本:Android" + android.os.Build.VERSION.RELEASE;
        return handSetInfo;
    }

    private class ViewPagerAdapter extends PagerAdapter {
        List<View> lists;

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {

            super.onBackPressed();
        }
    }
}
