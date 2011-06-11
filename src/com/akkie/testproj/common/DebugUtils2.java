/**
 * @author Akkie 
 */
package com.akkie.testproj.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;

/**　android.util.Logををラップしたクラス <br>
 * TAGに スレッドID, メソッド名、クラスファイル名、行番号を自動出力する
 * */
public class DebugUtils2 {
	/** インスタンス化防止用のコンストラクタ */
	private DebugUtils2() {}

	/** ログ出力制御 リリース時はfalse, 又は呼び出し元をコメントアウト */
	private static boolean LOG_ENABLE = true;
	
    /** マニフェストファイルのデバッグモードから自動設定 */
	public static void setDebuggable(Context ctx) {
	    PackageManager manager = ctx.getPackageManager();
	    ApplicationInfo appInfo = null;
	    try {
	        appInfo = manager.getApplicationInfo(ctx.getPackageName(), 0);
	    } catch (NameNotFoundException e) {
	        return;
	    }
	    if ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE) {
	        LOG_ENABLE = true;
	    } else {
	        LOG_ENABLE = false;
	    }
	}
//    /**
//     * マニフェストファイルからバージョン名を取得する
//     * @param ctx
//     * @return
//     */
//    public static String getVersionName(Context ctx) {
//        String versionName = "";
//        PackageManager pm = ctx.getPackageManager();
//        try {
//            PackageInfo info = null;
//            info = pm.getPackageInfo(ctx.getPackageName(), 0);
//            versionName = info.versionName;
//        } catch (NameNotFoundException e) {
//        }
//        return versionName;
//    }
	
	
	/**
	 * デバッグ用の文字列をLogcatへ出力します。 <br>
	 *      ログレベル Verbose
	 * @param tag Tag表示は呼び出し元を情報表示する為、使用しません。
	 * @param message
	 *            LogCatへの出力文字列
	 */
	public static void v(String tag, String message) {
		if (LOG_ENABLE) {
			//タグ生成
			StackTraceElement mySte = (new Throwable()).getStackTrace()[1];
			String tagName = 
				"("+ android.os.Process.myTid() + ") " +
				mySte.getMethodName()
				+ " ("
				+ mySte.getFileName() + ":" + mySte.getLineNumber()
				+ ")";
			Log.v(tagName, "" + message);
		}
	}
	/**
	 * デバッグ用の文字列をLogcatへ出力します。<br>
	 *      ログレベル debug
	 * @param tag Tag表示は呼び出し元を情報表示する為、使用しません。
	 * @param message
	 *            LogCatへの出力文字列
	 */
	public static void d(String tag, String message) {
		if (LOG_ENABLE) {
			//タグ生成
			StackTraceElement mySte = (new Throwable()).getStackTrace()[1];
			String tagName = 
				"("+ android.os.Process.myTid() + ") " +
				mySte.getMethodName()
				+ " ("
				+ mySte.getFileName() + ":" + mySte.getLineNumber()
				+ ")";
			Log.d(tagName, "" + message);
		}
	}
	/**
	 * デバッグ用の文字列をLogcatへ出力します。 <br>
	 *      ログレベル Info
	 * @param tag Tag表示は呼び出し元を情報表示する為、使用しません。
	 * @param message
	 *            LogCatへの出力文字列
	 */
	public static void i(String tag, String message) {
		if (LOG_ENABLE) {
			//タグ生成
			StackTraceElement mySte = (new Throwable()).getStackTrace()[1];
			String tagName = 
				"("+ android.os.Process.myTid() + ") " +
				mySte.getMethodName()
				+ " ("
				+ mySte.getFileName() + ":" + mySte.getLineNumber()
				+ ")";
			Log.i(tagName, "" + message);
		}
	}
	/**
	 * デバッグ用の文字列をLogcatへ出力します。 <br>
	 *       ログレベル Warn
	 * @param tag Tag表示は呼び出し元を情報表示する為、使用しません。
	 * @param message
	 *            LogCatへの出力文字列
	 */
	public static void w(String tag, String message) {
		if (LOG_ENABLE) {
			//タグ生成
			StackTraceElement mySte = (new Throwable()).getStackTrace()[1];
			String tagName = 
				"("+ android.os.Process.myTid() + ") " +
				mySte.getMethodName()
				+ " ("
				+ mySte.getFileName() + ":" + mySte.getLineNumber()
				+ ")";
			Log.w(tagName, "" + message);
		}
	}
	/**
	 * デバッグ用の文字列をLogcatへ出力します。 <br>
	 *      ログレベル Error
	 * @param tag Tag表示は呼び出し元を情報表示する為、使用しません。
	 * @param message
	 *            LogCatへの出力文字列
	 */
	public static void e(String tag, String message) {
		if (LOG_ENABLE) {
			//タグ生成
			StackTraceElement mySte = (new Throwable()).getStackTrace()[1];
			String tagName = 
				"("+ android.os.Process.myTid() + ") " +
				mySte.getMethodName()
				+ " ("
				+ mySte.getFileName() + ":" + mySte.getLineNumber()
				+ ")";
			Log.e(tagName, "" + message);
		}
	}
	/**
	 * ログレベル Error スタックトレース付き表示
	 * @param tag Tag表示は呼び出し元を情報表示する為、使用しません。
	 * @param message
	 * @param tr Exception/Error など
	 */
	public static void e(String tag, String message, Throwable tr) {
        if (LOG_ENABLE) {
            //タグ生成
            StackTraceElement mySte = (new Throwable()).getStackTrace()[1];
            String tagName = 
                "("+ android.os.Process.myTid() + ") " +
                mySte.getMethodName()
                + " ("
                + mySte.getFileName() + ":" + mySte.getLineNumber()
                + ")";
            Log.e(tagName, "" + message, tr);
        }
    }
	
	/**
	 * ArrayList内容を表示
	 * @author Akkie 
	 * @param tag Tag表示は呼び出し元を情報表示する為、使用しません。
	 * @param arrList
	 */
	public static void viewArrayList(String tag, ArrayList<String> arrList) {
		if (LOG_ENABLE) {
			//タグ生成
			StackTraceElement mySte = (new Throwable()).getStackTrace()[1];
			String tagName = 
				"("+ android.os.Process.myTid() + ") " +
				mySte.getMethodName()
				+ " ("
				+ mySte.getFileName() + ":" + mySte.getLineNumber()
				+ ")";
			if(arrList == null ) {
				Log.w(tagName, "ArrayList == null");
				return;
			}
			Log.i(tagName, "list size = "+ arrList.size());
			Iterator<String> iterator = arrList.iterator(); 
			//ループ 
			while(iterator.hasNext()){ 
				//値を表示 
				Log.i(tagName, "" + (String) iterator.next());
			} 
		}
	}

	/**
	 * Map内容を表示
	 * @author Akkie 
	 * @param tag Tag表示は呼び出し元を情報表示する為、使用しません。
	 * @param map
	 */
	public static void viewHashMap(String tag, Map<String ,Object> map) {
		if (LOG_ENABLE) {
			//タグ生成
			StackTraceElement mySte = (new Throwable()).getStackTrace()[1];
			String tagName = 
				"("+ android.os.Process.myTid() + ") " +
				mySte.getMethodName()
				+ " ("
				+ mySte.getFileName() + ":" + mySte.getLineNumber()
				+ ")";
			
			if(map == null ) {
				Log.w(tagName, "Map == null");
				return;
			}
			Log.i(tagName, "Map size = "+ map.size());
			Set<String> set = map.keySet();
			//イテレータ取得
			Iterator<String> iterator = set.iterator();
			Object object;
			//オブジェクト内のデータを全て取得
			while(iterator.hasNext()){
				object = iterator.next();
				Log.i(tagName, object + " = " + map.get(object));
			}
		}
	}

	/**
	 * SharedPreferenceファイル全内容出力
	 * @param context
	 * @param fileName
	 */
	@SuppressWarnings("unchecked")
	public static void viewSharedPreferencesFile(Context context, String fileName) {
		if (LOG_ENABLE) {
			//タグ生成
			StackTraceElement mySte = (new Throwable()).getStackTrace()[1];
			String tagName = 
				"("+ android.os.Process.myTid() + ") " +
				mySte.getMethodName()
				+ " ("
				+ mySte.getFileName() + ":" + mySte.getLineNumber()
				+ ")";

			SharedPreferences pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
			Map<String, Object> allMap = (Map<String, Object>) pref.getAll();
			int size = allMap.size();
			Log.i(tagName, ""+ fileName +" content size = "+ size);
			if (size > 0) {
				for (String key : allMap.keySet()) {
					Object obj = allMap.get(key);
					if (!(obj instanceof String) && !(obj instanceof Number)
							&& !(obj instanceof Boolean)) {
						Log.w(tagName, key + " = ???");
						continue;
					}
					Log.i(tagName, key +" = "+ obj);
				}
			}
		}
	}
	
	/** ファイル書き込みパス */
	private static final String LOGDIR = Environment.getExternalStorageDirectory().getPath() + "/debugDir/";
	/** システム共通改行文字 */
	private static final String BR = System.getProperty("line.separator");
	
	/**
	 * 外部メモリにString文字列⇒ファイル保存 (sd書き込みパーミッション必要)<br>
	 * @param fileName　書き込みファイル名
	 * @param sOutdata　ファイル出力する文字列データ
	 * @param sEnctype　文字エンコード 未使用
	 */
	public static boolean writeSdLog(String fileName, String sOutdata, String sEnctype) {
		if (LOG_ENABLE) {
			/** 書き込みファイル */
			String SDFILE = LOGDIR + fileName;
			Date now = new Date();
			BufferedWriter bw = null;
			
			//タグ生成
			StackTraceElement mySte = (new Throwable()).getStackTrace()[1];
			String tagName = 
				"("+ android.os.Process.myTid() + ") " +
				mySte.getMethodName()
				+ " ("
				+ mySte.getFileName() + ":" + mySte.getLineNumber()
				+ ")";
			//ファイル書き込み文字列生成
			sOutdata = tagName + BR + sOutdata;

			try {
				try {
					mkdir_p(LOGDIR);
				} catch (IOException e) {
					Log.e(tagName, "writeSdLog() Failure 1");
					return false;
				}
				FileOutputStream file = new FileOutputStream(SDFILE, true);
				bw = new BufferedWriter(new OutputStreamWriter(file, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				Log.e(tagName, "writeSdLog() Failure 2");
				return false;
			} catch (FileNotFoundException e) {
				Log.e(tagName, "writeSdLog() Failure 3");
				return false;
			}
			try {
				//ファイルに追記書き込み
				bw.append((now.getYear()+1900)+"/"+(now.getMonth()+1)+"/"+now.getDate()
						+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds()+"\t"+sOutdata+"\n");
				//    			Log.e("log",(now.getYear()+1900)+"/"+(now.getMonth()+1)+"/"+now.getDate()
				//    					+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds()+"\t"+sOutdata);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bw = null;
			Log.i(tagName, "writeSdLog() SUCCESS    file > "+ SDFILE);
			return true;
		}
		return false;
	}
	private static boolean mkdir_p(File dir) throws IOException {
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				throw new IOException("File.mkdirs() failed.");
			}
			return true;
		} else if (!dir.isDirectory()) {
			throw new IOException("Cannot create path. " + dir.toString() + " already exists and is not a directory.");
		} else {
			return false;
		}
	}
	private static boolean mkdir_p(String dir) throws IOException {
		return mkdir_p(new File(dir));
	}
}
