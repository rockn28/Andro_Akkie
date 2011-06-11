package com.akkie.testproj.common;

import java.lang.reflect.Field;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * <情報取得クラス><br>
 */
public class DebugHelper extends Build {
    /** デバッグ出力用のタグに用いるクラス名文字列 */
    private static final String className = "DebugHelper";
    /** システム共通改行文字 */
    private static final String BR = System.getProperty("line.separator");

    public DebugHelper() {
        super();
    }
    /**
     * SDK_INTはver4からの為、SDK(String)で取得しint変換を行う
     * @param _sdk
     * @return
     */
    public static int getSdkVersion() {
        return Integer.valueOf(Build.VERSION.SDK);
    }

    /**
     * Build情報を返す
     * リフレクションを使う http://www.textdrop.net/soft/android-backword-compatibility/
     */
    public String getBuildInfo() {
        DebugUtils2.i("", "getBuildInfo() Start");
        String retInfo = "";
        int sdkVer = getSdkVersion();

        if (sdkVer >= 4) {
            DebugUtils2.i("", "CODENAME = "+ Build.VERSION.CODENAME);
            retInfo = retInfo.concat("CODENAME = "+ Build.VERSION.CODENAME + BR);
        }
        DebugUtils2.i("", "INCREMENTAL = "+ Build.VERSION.INCREMENTAL);
        retInfo = retInfo.concat("INCREMENTAL = "+ Build.VERSION.INCREMENTAL + BR);
        DebugUtils2.i("", "RELEASE = "+ Build.VERSION.RELEASE);
        retInfo = retInfo.concat("RELEASE = "+ Build.VERSION.RELEASE + BR);
        DebugUtils2.i("", "SDK = "+ Build.VERSION.SDK);
        retInfo = retInfo.concat("SDK = "+ Build.VERSION.SDK + BR);
        if (sdkVer >= 4) {
            DebugUtils2.i("", "SDK_INT = "+ Build.VERSION.SDK_INT);
            retInfo = retInfo.concat("SDK_INT = "+ Build.VERSION.SDK_INT + BR);
        }

        //Build情報 リフレクションを使用、全APIバージョンのフィールドを表示
        Field[] fields = this.getClass().getFields();
        //Field[] fields = new Build().getClass().getFields();
        DebugUtils2.i("", "fields size="+ fields.length);
        for (Field field : fields) {
            String fieldkey = field.getName();
            Object fieldValue;
            try {
                fieldValue = field.get(null);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                fieldValue = "???";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                fieldValue = "???";
            }
            String res = fieldkey +" = "+ fieldValue.toString();
            DebugUtils2.i("", res);
            retInfo = retInfo.concat(res + BR);
        }

        //Build情報 リフレクションを使用せず全APIバージョンのフィールドを表示
//        DebugUtils2.i("", "BOARD = "+ BOARD);
//        retInfo = retInfo.concat("BOARD = "+ BOARD + BR);
//        //      if (sdkVer >= 8) {
//        //          DebugUtils2.i("", "BOOTLOADER = "+ BOOTLOADER);
//        //          retInfo = retInfo.concat("BOOTLOADER = "+ BOOTLOADER + BR);
//        //      }
//        DebugUtils2.i("", "BRAND = "+ BRAND);
//        retInfo = retInfo.concat("BRAND = "+ BRAND + BR);
//        if (sdkVer >= 4) {
//            DebugUtils2.i("", "CPU_ABI = "+ CPU_ABI);
//            retInfo = retInfo.concat("CPU_ABI = "+ CPU_ABI + BR);
//        }
//        //      if (sdkVer >= 8) {
//        //          DebugUtils2.i("", "CPU_ABI2 = "+ CPU_ABI2);
//        //          retInfo = retInfo.concat("CPU_ABI2 = "+ CPU_ABI2 + BR);
//        //      }
//        DebugUtils2.i("", "DEVICE = "+ DEVICE);
//        retInfo = retInfo.concat("DEVICE = "+ DEVICE + BR);
//        if (sdkVer >= 3) {
//            DebugUtils2.i("", "DISPLAY = "+ DISPLAY);
//            retInfo = retInfo.concat("DISPLAY = "+ DISPLAY + BR);
//        }
//        DebugUtils2.i("", "FINGERPRINT = "+ FINGERPRINT);
//        retInfo = retInfo.concat("FINGERPRINT = "+ FINGERPRINT + BR);
//        //      if (sdkVer >= 8) {
//        //          DebugUtils2.i("", "HARDWARE = "+ HARDWARE);
//        //          retInfo = retInfo.concat("HARDWARE = "+ HARDWARE + BR);
//        //      }
//        DebugUtils2.i("", "HOST = "+ HOST);
//        retInfo = retInfo.concat("HOST = "+ HOST + BR);
//        DebugUtils2.i("", "ID = "+ ID);
//        retInfo = retInfo.concat("ID = "+ ID + BR);
//        if (sdkVer >= 4) {
//            DebugUtils2.i("", "MANUFACTURER = "+ MANUFACTURER);
//            retInfo = retInfo.concat("MANUFACTURER = "+ MANUFACTURER + BR);
//        }
//        DebugUtils2.i("", "MODEL = "+ MODEL);
//        retInfo = retInfo.concat("MODEL = "+ MODEL + BR);
//        DebugUtils2.i("", "PRODUCT = "+ PRODUCT);
//        retInfo = retInfo.concat("PRODUCT = "+ PRODUCT + BR);
//        //      if (sdkVer >= 8) {
//        //          DebugUtils2.i("", "RADIO = "+ RADIO);
//        //          retInfo = retInfo.concat("RADIO = "+ RADIO + BR);
//        //      }
//        //      if (sdkVer >= 9) {
//        //          DebugUtils2.i("", "SERIAL = "+ SERIAL);
//        //          retInfo = retInfo.concat("SERIAL = "+ SERIAL + BR);
//        //      }
//        DebugUtils2.i("", "TAGS = "+ TAGS);
//        retInfo = retInfo.concat("TAGS = "+ TAGS + BR);
//        DebugUtils2.i("", "TIME = "+ TIME);
//        retInfo = retInfo.concat("TIME = "+ TIME + BR);
//        DebugUtils2.i("", "TYPE = "+ TYPE);
//        retInfo = retInfo.concat("TYPE = "+ TYPE + BR);
//        //      if (sdkVer >= 8) {
//        //          DebugUtils2.i("", "UNKNOWN = "+ UNKNOWN);
//        //          retInfo = retInfo.concat("UNKNOWN = "+ UNKNOWN + BR);
//        //      }
//        DebugUtils2.i("", "USER = "+ USER);
//        retInfo = retInfo.concat("USER = "+ USER + BR);

        DebugUtils2.i("", "getBuildInfo() End");
        return retInfo;
    }

    public static void intentCheck(Intent i) {
        //Intent i = getIntent();
        //DEBUG
        DebugUtils2.i("",  "getAction = "+ i.getAction());
        DebugUtils2.i("",  "getCategories = "+ i.getCategories());
        DebugUtils2.i("",  "getComponent = "+ i.getComponent());
        DebugUtils2.i("",  "getData = "+ i.getData());
        DebugUtils2.i("",  "getDataString = "+ i.getDataString());
        DebugUtils2.i("",  "getExtras = "+ i.getExtras());
        DebugUtils2.i("",  "getFlags = "+ i.getFlags());
        DebugUtils2.i("",  "getPackage = "+ i.getPackage());
        DebugUtils2.i("",  "getScheme = "+ i.getScheme());
        //DebugUtils2.i("",  "getSourceBounds = "+ i.getSourceBounds());
        DebugUtils2.i("",  "getType = "+ i.getType());
    }
    public static String displayCheck(Activity act) {
        DebugUtils2.d("", "displayCheck() Start");
        String retInfo = "";
        //ディスプレイ情報
        WindowManager wm = (WindowManager) act.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Log.i(className, "width="+ String.valueOf(display.getWidth()));
        retInfo = retInfo.concat("width="+ String.valueOf(display.getWidth()) + BR);  
        Log.i(className, "height="+ String.valueOf(display.getHeight()));
        retInfo = retInfo.concat("height="+ String.valueOf(display.getHeight()) + BR);  
        Log.i(className, "orientation="+ String.valueOf(display.getOrientation()));
        retInfo = retInfo.concat("orientation="+ String.valueOf(display.getOrientation()) + BR);  
        Log.i(className, "refreshRate="+ String.valueOf(display.getRefreshRate()));
        retInfo = retInfo.concat("refreshRate="+ String.valueOf(display.getRefreshRate()) + BR);  
        Log.i(className, "pixelFormat="+ String.valueOf(display.getPixelFormat()));
        retInfo = retInfo.concat("pixelFormat="+ String.valueOf(display.getPixelFormat()) + BR);  
        retInfo = retInfo.concat(BR);  

        DisplayMetrics metrics = new DisplayMetrics(); 
        display.getMetrics(metrics);  
        Log.i(className, "density=" + metrics.density);
        retInfo = retInfo.concat("density=" + metrics.density + BR);  
        Log.i(className, "densityDpi=" + metrics.densityDpi);
        retInfo = retInfo.concat("densityDpi=" + metrics.densityDpi + BR);  //ver4
        Log.i(className, "scaledDensity=" + metrics.scaledDensity);
        retInfo = retInfo.concat("scaledDensity=" + metrics.scaledDensity + BR);  
        Log.i(className, "widthPixels=" + metrics.widthPixels);
        retInfo = retInfo.concat("widthPixels=" + metrics.widthPixels + BR);  
        Log.i(className, "heightPixels=" + metrics.heightPixels);
        retInfo = retInfo.concat("heightPixels=" + metrics.heightPixels + BR);  
        Log.i(className, "xDpi=" + metrics.xdpi);
        retInfo = retInfo.concat("xDpi=" + metrics.xdpi + BR);  
        Log.i(className, "yDpi=" + metrics.ydpi);
        retInfo = retInfo.concat("yDpi=" + metrics.ydpi + BR);     
        
        //act.getResources().getDisplayMetrics().
        
        DebugUtils2.d("", "displayCheck() End");
        return retInfo;
    }

    public static void uriCheck(Uri uri) {
        DebugUtils2.i("", "getAuthority = "+ uri.getAuthority());
        DebugUtils2.i("", "getEncodedAuthority = "+ uri.getEncodedAuthority());
        DebugUtils2.i("", "getEncodedFragment = "+ uri.getEncodedFragment());
        DebugUtils2.i("", "getEncodedPath = "+ uri.getEncodedPath());
        DebugUtils2.i("", "getEncodedQuery = "+ uri.getEncodedQuery());
        DebugUtils2.i("", "getEncodedSchemeSpecificPart = "+ uri.getEncodedSchemeSpecificPart());
        DebugUtils2.i("", "getEncodedUserInfo = "+ uri.getEncodedUserInfo());
        DebugUtils2.i("", "getFragment = "+ uri.getFragment());
        DebugUtils2.i("", "getHost = "+ uri.getHost());
        DebugUtils2.i("", "getLastPathSegment = "+ uri.getLastPathSegment());
        DebugUtils2.i("", "getPath = "+ uri.getPath());
        DebugUtils2.i("", "getPathSegments = "+ uri.getPathSegments());
        DebugUtils2.i("", "getPort = "+ uri.getPort());
        DebugUtils2.i("", "getQuery = "+ uri.getQuery());
        DebugUtils2.i("", "getScheme = "+ uri.getScheme());
        DebugUtils2.i("", "getSchemeSpecificPart = "+ uri.getSchemeSpecificPart());
        DebugUtils2.i("", "getUserInfo = "+ uri.getUserInfo());
        DebugUtils2.i("", "isAbsolute = "+ uri.isAbsolute());
        DebugUtils2.i("", "isHierarchical = "+ uri.isHierarchical());
        DebugUtils2.i("", "isOpaque = "+ uri.isOpaque());
        DebugUtils2.i("", "isRelative = "+ uri.isRelative());
    }
    
    //内蔵キーボード状態
    public static void keyCheck(InputMethodManager inputMethodManager) {
    	DebugUtils2.i("", "isAcceptingText = "+ inputMethodManager.isAcceptingText());
    	//DebugUtils2.i("", "isActive = "+ inputMethodManager.isActive(currentFocus));
    	DebugUtils2.i("", "imeActive = "+ inputMethodManager.isActive());
    	DebugUtils2.i("", "isFullscreenMode = "+ inputMethodManager.isFullscreenMode());
    	//DebugUtils2.i("", "isWatchingCursor = "+ inputMethodManager.isWatchingCursor(currentFocus));
    }
    
    //xmlタグ解析
    public static void xmlParserCheck(XmlPullParser parser) {
    	DebugUtils2.i("", "parser.getAttributeCount() = "+ parser.getAttributeCount());
    	DebugUtils2.i("", "parser.getColumnNumber() = "+ parser.getColumnNumber());
    	DebugUtils2.i("", "parser.getDepth() = "+ parser.getDepth());
    	try {
			DebugUtils2.i("", "parser.getEventType() = "+ parser.getEventType());
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
    	DebugUtils2.i("", "parser.getInputEncoding() = "+ parser.getInputEncoding());
    	DebugUtils2.i("", "parser.getLineNumber() = "+ parser.getLineNumber());
    	DebugUtils2.i("", "parser.getName() = "+ parser.getName());
    	DebugUtils2.i("", "parser.getNamespace() = "+ parser.getNamespace());
    	DebugUtils2.i("", "parser.getPositionDescription() = "+ parser.getPositionDescription());
    	//DebugUtils2.i("", "parser.getPrefix() = "+ parser.getPrefix());
    	DebugUtils2.i("", "parser.getText() = "+ parser.getText());
    	try {
			DebugUtils2.i("", "parser.isEmptyElementTag() = "+ parser.isEmptyElementTag());
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
    	//DebugUtils2.i("", "parser.isWhitespace() = "+ parser.isWhitespace());
    }
}
