package com.rsw.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RSW on 2017/9/15.
 * com.bulaitesi.bdhr.utils
 */

public class Util {
    public static String replaceAll(String src, String fnd, String rep) {
        int pos = 0;
        int prepos = 0;
        int fndlen = fnd.length();
        String newstr = "";
        pos = src.indexOf(fnd, pos);
        while (pos >= 0) {
            newstr += src.substring(prepos, pos);
            newstr += rep;
            prepos = pos + fndlen;
            pos = src.indexOf(fnd, pos + fndlen);
        }
        newstr += src.substring(prepos);
        return newstr;
    }

    public static void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }

    /**
     * 获取状态通知栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        System.out.println("statusBarHeight=============" + frame.top);
        return frame.top;
    }

    public static void fitPopupWindowOverStatusBar(PopupWindow mPopupWindow) {//Android 全屏PopWindow，盖住StatusBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(mPopupWindow, true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static String ellpiseText(String str) {
        String unitString = "";
        if (str.length() > 10) {
            unitString = str.substring(0, 9) + "...";
        } else {
            unitString = str;
        }
        return unitString;
    }

    public static String replaceAllDouhao(String str) {
        String unitString = "";
        unitString = str.replaceAll("，", "");
        unitString = unitString.replaceAll(",", "");
        return unitString;
    }

    public static String replaceStartAndEndDouhao(String str) {
        String unitString = "";
        if (str.startsWith(",")) {
            unitString = str.substring(1, str.length());
        } else if (str.endsWith(",")) {
            unitString = str.substring(0, str.length() - 1);
        } else if (str.startsWith(",") && str.endsWith(",")) {
            unitString = str.substring(1, str.length() - 1);
        } else {
            unitString = str;
        }
        return unitString;
    }

    public static boolean compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() >= dt2.getTime()) {
                return false;
            } else if (dt1.getTime() < dt2.getTime()) {
                return true;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean checkDateBefore(String time1, String time2) {//判断时间是不是有问题
        if (!"".equals(time1) && !"".equals(time2)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bt = null;
            Date et = null;
            try {
                bt = sdf.parse(time1);
                et = sdf.parse(time2);
                if (et.before(bt)) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static boolean checkTimeBefore(String time1, String time2) {//判断时间是不是有问题
        if (!"".equals(time1) && !"".equals(time2)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH-mm");
            Date bt = null;
            Date et = null;
            try {
                bt = sdf.parse(time1);
                et = sdf.parse(time2);
                if (et.before(bt)) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static void dotnotShowJianpan(Activity activity) {
        //让虚拟键盘一直不显示
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
    }

    public static void hidejianpan(Activity activity) {
        /* 隐藏软键盘 */
        InputMethodManager im = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    /**
     * 验证手机号码
     * <p>
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147、182
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189、177
     * <p>
     * 13(老)号段：130、131、132、133、134、135、136、137、138、139
     * 14(新)号段：1400、1410、1440、145、146、147、148,149
     * 15(新)号段：150、151、152、153、154、155、156、157、158、159
     * 16(新)号段：166
     * 17(新)号段：170、171、173、175、176、177、178、1740（0-5）、1740（6-9）、1740（10-12）
     * 18(3G)号段：180、181、182、183、184、185、186、187、188、189
     * 19(新)号段：198、199
     *
     * @param cellphone
     * @return
     */
    public static boolean isPhoneNumber(String cellphone) {
        String regex = "^((13[0-9])|(14[0,1,4-9])|(15[0-9])|(166)|(17[0,1,3-9])|(18[0-9])|(19[8-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellphone);
        return matcher.matches();
    }

    public static String readAssert(Context context, String fileName) {
        String resultString = "";
        try {
            InputStream inputStream = context.getResources().getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /*判断email格式*/
    public static boolean isEmail(String email) {
        String regex = "\\w+@\\w+(\\.\\w+)+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * @return 获取版本名称，一个浮点数版本名称，给人看的
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * @return 表示app更新的次数
     */
    public int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return packageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    public static void hideXunijianapan(Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
    }


    public static boolean isMIUI() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop" + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    public static String pingjieurl(String url) {
        StringBuffer buffer = new StringBuffer(url);
        if (url.contains("?")) {
            buffer.append("&");
            url = buffer.toString();
        } else {
            buffer.append("?");
            url = buffer.toString();
        }
        return url;
    }

    /*
 *  透明式状态栏
 * */
    public static void initStatusBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /*
    * 白底黑字
    * 透明式状态栏
    * */
    public static boolean initBlackStatusBar(Window window, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //状态栏颜色字体(白底黑字)修改 MIUI6+
        if (!setMiuiStatusBarDarkMode(window, true)) {
            //状态栏颜色字体(白底黑字)修改 Flyme4+
            if (!setMeizuStatusBarDarkIcon(window, true)) {
                if (!isHaveNavigationBar(activity)) {
                    return false;
                }
                return true;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean isHaveNavigationBar(Activity context) {

        boolean isHave = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            isHave = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                isHave = false;
            } else if ("0".equals(navBarOverride)) {
                isHave = true;
            }
        } catch (Exception e) {
        }
        return isHave;
    }

    //对于miui
    //设置成白色的背景，字体颜色为黑色。
    public static boolean setMiuiStatusBarDarkMode(Window window, boolean darkmode) {
        Class<? extends Window> clazz = window.getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //对于flyme:
    //设置成白色的背景，字体颜色为黑色。
    public static boolean setMeizuStatusBarDarkIcon(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }


    /*
*
* 判断是否有网络连接
*
* */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
*
*   判断GPS是否打开
*
* */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /*
*
*   判断WiFi是否打开
*
* */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /*
*
*   判断是否是3G网络
*
* */
    public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /*
*
*   判断是WiFi还是3g
*
* */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }


    /*
*
*   获取当前时间
*
* */
    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
    }


    /**
     * 返回中间删除线的文本
     */
    public static SpannableString getDeleteStr(String content) {
        SpannableString sps = new SpannableString(content);
        sps.setSpan(new StrikethroughSpan(), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sps;
    }

    /**
     * 设置文字的底部下划线文本
     *
     * @param tv
     * @param str
     */
    public static void setUnderlineStr(TextView tv, String str) {
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv.setText(str);
    }

    /**
     * 返回指定颜色的文本
     */
    public static SpannableString getColorStr(String content, int color) {

        SpannableString sps = new SpannableString(content); // 更换字体颜色
        sps.setSpan(new ForegroundColorSpan(color), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sps;
    }

    /**
     * 设置TextView的字体风格
     *
     * @param tv
     * @param tf
     */
    public static void setTextViewFont(TextView tv, Typeface tf) {
        tv.setTypeface(tf);
    }

    /**
     * 返回图片适配的高度
     *
     * @param picWidth
     * @param picHeight
     * @param desWidth
     * @return
     */
    public static int getRealHeight(int picWidth, int picHeight, int desWidth) {
        return (picHeight * desWidth) / picWidth;
    }


    // 正则表达式验证
    public static boolean checkString(String s, String regex) {
        return s.matches(regex);
    }

    /**
     * 弹吐司
     *
     * @param ctx
     * @param txt
     */
    public static void popTipOrWarn(Context ctx, String txt) {
        Toast.makeText(ctx, txt, Toast.LENGTH_SHORT).show();
    }

    public static void popTipOrWarn(Context ctx, int txt) {
        Toast.makeText(ctx, txt, Toast.LENGTH_SHORT).show();
    }


    // 将文件路径前加上"file://"
    public static String addFileBegin(String path) {
        if (path.startsWith("file://"))
            return path;
        return "file://" + path;
    }

    // 截取文件名称
    public static String getFileName(String path) {
        if (path == null) {
            return "";
        }
        int i = path.lastIndexOf("\\");
        if (i < 0) {
            i = path.lastIndexOf("/");
        }
        if (i < 0) {
            return path;
        }
        return path.substring(i + 1);
    }

    // 截取文件类型
    public static String getFileFormat(String path) {
        if (path == null) {
            return "";
        }
        int i = path.lastIndexOf(".");
        if (i < 0) {
            return path;
        }
        return path.substring(i + 1);
    }

    /**
     * 去掉小月份前面的0，譬如07月变成7月，12月不变。
     *
     * @param month
     * @return
     */
    public static String tranMonth(String month) {
        if ("0".equals(month.substring(0, 1))) {
            month = month.substring(1, 2);
        }
        return month;
    }

    /**
     * 产生唯一的ID号
     *
     * @return
     */
    public static String getUUId() {
        return UUID.randomUUID().toString();
    }


    // 显示整数金额
    public static final String getMoneyAmount(String payAmount) {
        Double douAmount = Double.valueOf(payAmount);
        int intAmount = douAmount.intValue();
        return ("¥" + intAmount / 100);
    }

    // 标准时间格式
    public static String getNowDatetimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        Date date = new Date();
        return sdf.format(date);
    }

    // 标准时间格式
    public static String getTomDatetimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return sdf.format(cal.getTime());
    }


    /**
     * type 0 代表 取奇数，1代表 取 偶数
     *
     * @param src
     * @param type
     * @return
     */
    public static String getOddOrEvenString(String src, int type) {
        if (src == null || src.length() <= 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int sbLength = src.length();
        for (int i = 0; i < sbLength; i++) {
            if (i % 2 == type) {
                sb.append(src.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 判断选择服务日期时间 是否早于手机当前时间 <一句话功能简述> <功能详细描述>
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean compare_date(String signtime) {
        System.out.println("signtime=========" + signtime);
        DateFormat df = new SimpleDateFormat("HH:mm");
        try {
            Date dt1 = df.parse(signtime);
            Date today = new Date();
            String time2 = df.format(today);
            System.out.println("time2=========" + time2);
            //19:42 18:00
            String[] signhour = signtime.split(":");
            String[] hour2 = time2.split(":");
            //先比较小时，在比较分钟
            if (Integer.parseInt(hour2[0]) > Integer.parseInt(signhour[0]) || (Integer.parseInt(hour2[0]) == Integer.parseInt(signhour[0]) && Integer.parseInt(hour2[1]) >= Integer.parseInt(signhour[1]))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }


    /**
     * 正则表达式 判断车牌号是否正确
     */
    public static boolean isNumCorrect(String num) {
        Pattern p = Pattern.compile("^(([\u4e00-\u9fa5])([a-z]|[A-Z])([a-z]|[A-Z]|[0-9]){5})$");
        Matcher m = p.matcher(num);
        return m.matches();
    }

    /**
     * 判断密码是否有中文 true代表无中文，false代表有中文
     */
    public static boolean isNotChin(String password) {
        return (password.getBytes().length == password.length()) ? true : false;

    }

    /**
     * 根据原始图，获取指定分辨率的小图
     *
     * @param Url
     * @param picType
     * @return
     */
    public static String getFitPicUrlPath(String Url, String picType) {
        String partOne = null;
        String partTow = null;
        if (TextUtils.isEmpty(Url)) {
            return Url;
        } else {
            int indexOf = Url.lastIndexOf(".");
            if (indexOf < 1) {
                return Url;
            }
            partOne = Url.substring(0, indexOf);
            partTow = Url.substring(indexOf);
        }

        if ("picType168X134".equals(picType)) {
            return partOne + "_168_134" + partTow;
        } else if ("picType218X174".equals(picType)) {
            return partOne + "_218_174" + partTow;
        } else if ("picType640X384".equals(picType)) {
            return partOne + "_640_384" + partTow;
        } else if ("picType168X134".equals(picType)) {
            return partOne + "_168_134" + partTow;
        } else if ("picType176X130".equals(picType)) {
            return partOne + "_176_130" + partTow;
        }
        return Url;
    }


    public static boolean isPassword(String str) {// 判断字符格式是否正确
        if (!TextUtils.isEmpty(str)) {
            for (char c : str.toCharArray()) {
                if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '#'
                        || c == '@' || c == '_')) {
                    return false;
                }
            }
        }
        return true;
    }

    // 根据评分计算出是否需要显示半星
    public static boolean isHalfStar(String scoreStr) {// 判断是否显示半星，限制小数点后只有一位小数的，012，false，34567，true，89false，但是星的个数要+1；
        float temp = 0f;
        try {
            temp = Float.parseFloat(getScoreBy1(scoreStr));
        } catch (Exception e) { // can't reach
            e.printStackTrace();
        }
        if (temp != 0) {
            temp = temp * 10;
            int intTemp = ((int) temp) % 10;
            if (intTemp > 2 && intTemp <= 7) {
                return true;
            }
        }
        return false;
    }

    // 根据评分计算出全星的分数
    public static int starCount(String scoreStr) {// 判断全星个数，限制小数点后只有一位小数的，01234567，个数不变，89，星的个数要+1；
        float score = 0f;
        try {
            score = Float.parseFloat(getScoreBy1(scoreStr));
        } catch (Exception e) { // can't reach
            e.printStackTrace();
        }
        if (score != 0) {
            score = score * 10;
            int intTemp = (int) score;

            int intCount = intTemp / 10;
            int intYu = intTemp % 10;
            if (intYu > 7) {
                return intCount + 1;
            } else
                return intCount;
        }
        return 0;
    }

    // 商家商品评分，可能会出现多位小数，进行四舍五入，取1位小数处理
    public static String getScoreBy1(String score) {
        String grade_score = "0";
        float f = Float.parseFloat(score);
        BigDecimal b = new BigDecimal(f);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();

        if ((int) f1 == f1)// 末尾是.0的，直接显示整数
            grade_score = String.valueOf((int) f1);
        else
            grade_score = String.valueOf(f1);
        return grade_score;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /*
     * 将诸如20140630085447时间格式的字符串转化为2014-06-30 08:54:47
     */
    public static String getTimeStrFromStr(String intime) {
        try {
            StringBuffer timeStr = new StringBuffer();
            timeStr.append(intime.subSequence(0, 4));
            timeStr.append("-");
            intime = intime.substring(4);

            timeStr.append(intime.subSequence(0, 2));
            timeStr.append("-");
            intime = intime.substring(2);

            timeStr.append(intime.subSequence(0, 2));
            timeStr.append("   ");
            intime = intime.substring(2);

            timeStr.append(intime.subSequence(0, 2));
            timeStr.append(":");
            intime = intime.substring(2);

            timeStr.append(intime.subSequence(0, 2));
            timeStr.append(":");
            intime = intime.substring(2);

            timeStr.append(intime.subSequence(0, 2));
            return timeStr.toString();
        } catch (Exception e) {
            return intime;
        }

    }

    /**
     * 将日期格式YYYYMMDD转换为YYYY-MM-DD
     *
     * @param str
     * @return
     */
    public static String formatDate(String str) {
        if (str == null || str.length() != 8) {
            return str;
        }
        String strs = "";
        strs = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        return strs;
    }

    /**
     * 将日期格式YYYY-MM-DD转换为YYYYMMDD
     *
     * @param str
     * @return
     */
    public static String formatDateStr(String str) {
        if (str == null || str.length() != 10) {
            return str;
        }
        String strs = "";
        strs = str.substring(0, 4) + str.substring(5, 7) + str.substring(8, 10);
        return strs;
    }

    /**
     * 将日期格式YYYY-MM-DD转换为YYYYMMDD
     *
     * @param str
     * @return
     */
    public static String formatDateStrChinese(String str) {
        if (str == null || str.length() != 10) {
            return str;
        }
        String strs = "";
        strs = str.substring(0, 4) + "年" + str.substring(5, 7) + "月" + str.substring(8, 10) + "日";
        return strs;
    }

    /**
     * 将日期格式YYYY-MM转换为YYYY年MM月
     *
     * @param str
     * @return
     */
    public static String formatDateStr_chinese(String str) {
        if (str == null || str.length() != 7) {
            return str;
        }
        String strs = str.replace("-", "年") + "月";
        return strs;
    }


    public static Bitmap getJieping(Activity activity) {// 获取截屏
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }


    /**
     * 获得网络连接是否可用
     *
     * @param context
     * @return
     */
    public static boolean hasNetwork(Context context) {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo workinfo = con.getActiveNetworkInfo();
        if (workinfo == null || !workinfo.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }


    public static boolean isEmpty(TextView et) {
        return et.getText().toString().length() == 0;
    }

    public static String formatSelectText(String textData) {
        if (TextUtils.isEmpty(textData)) {
            return "未选择";
        } else {
            return textData;
        }
    }

    /**
     * 判断字符串是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainsChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

}
