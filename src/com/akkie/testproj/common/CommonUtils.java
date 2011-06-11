package com.akkie.testproj.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;


public class CommonUtils {
    private CommonUtils() {
        /*　インスタンス化防止用のコンストラクタ */
    }
    /** システム共通改行文字 */
    public static final String BR = System.getProperty("line.separator");
    
	private static ProgressDialog Progressdialog = null;
	private static AlertDialog AlertDialog = null;
    
	//処理中ダイアログ表示用メソッド
	public static void showLoadingDialog(String str,Context context){
		DebugUtils2.d("", "showLoadingDialog("+ str +", ) Start");
		Progressdialog = new ProgressDialog(context);
		Progressdialog.setMessage(str);
		Progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		Progressdialog.show();
		DebugUtils2.d("", "showLoadingDialog() End");
	}
	
	//処理中ダイアログ削除用メソッド
	public static void dismissLoadingDialog(){
		DebugUtils2.d("", "dismissLoadingDialog() Start");
		if(Progressdialog != null){
			Progressdialog.dismiss();
			Progressdialog = null;
		}
		DebugUtils2.d("", "dismissLoadingDialog() End");
	}
	//警告ダイアログ表示用メソッド(削除はOKボタン押下のみ)
	public static void showAlertDialog(String str,Context context){
		AlertDialog = new AlertDialog.Builder(context)
			.setMessage(str)
			.setPositiveButton("OK", null).create();
		AlertDialog.show();
	}
	//警告ダイアログ表示用メソッド(削除はOKボタン押下のみ)
    public static void showAlertDialog(String title, String mes, Context context){
        AlertDialog = new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(mes)
            .setPositiveButton("OK", null).create();
        AlertDialog.show();
    }
	//外部ストレージマウント状態取得
	public static boolean isSdCardWriteable(){
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
}
