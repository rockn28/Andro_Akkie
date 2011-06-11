package com.akkie.testproj.tab_setting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.Toast;

import com.akkie.testproj.common.DebugUtils2;

public class SDCardview {
	/** FileName */
//	private String FILENAME_IN 	= "in_query.csv";
//	private String FILENAME_OUT = "out_query.csv";
	private String FILENAME;
	private String SDFILEPATH;
	/** 改行文字 */
	private final String BR = System.getProperty("line.separator");    

	private Activity mAct = null;
	private FileOutputStream fos = null;	// /data/data/書き込み
	private	BufferedWriter bufferedWriterObj = null; // /sdcard 配下書き込み
	private Handler mHandler = new Handler();
	private String resStr = "失敗しました";

	public SDCardview(Activity context) {
		DebugUtils2.d("", "SDCardview() Start");
		mAct = (Activity) context;
		//TODO ギャラリーデータ更新 これだと非同期なのでcsv作成と同期が取れない
		mAct.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse ("file://" + Environment.getExternalStorageDirectory())));
		mAct.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse ("file://" + Environment.getDataDirectory())));
		DebugUtils2.d("", "SDCardview() End");
	}
	
	public void loadFromSDCard(final Uri _uri, String fileName) throws Exception {   
		DebugUtils2.d("", "loadFromSDCard("+ _uri +") Start");
		FILENAME = fileName;  // /data/data/filename
		
//		if (_uri == MediaStore.Images.Media.INTERNAL_CONTENT_URI) {
//			FILENAME = FILENAME_IN;
//		} else if (_uri == MediaStore.Images.Media.EXTERNAL_CONTENT_URI){
//			FILENAME = FILENAME_OUT;
//		}
		//引数に、microSDのディレクトリ名と、保存用ディレクトリ名を指定。 
        File file_to_basedir = new File(Environment.getExternalStorageDirectory(), "/debugDir");
        //初回起動時にフォルダを作る
        if(file_to_basedir.mkdirs()){
            DebugUtils2.i("", "DirectoryHasCreated!");
        } 
        //ディレクトリの存在確認
        if (!file_to_basedir.exists()) {
            DebugUtils2.e("", "not exist "+ file_to_basedir);
            Toast.makeText(mAct, "書き出しフォルダがありません", Toast.LENGTH_SHORT).show();
            return;
        }
        SDFILEPATH = file_to_basedir.getAbsolutePath() +"/"+ FILENAME;
        //DebugUtils2.i("", "path ="+ SDFILEPATH);
		
		try{
			//ファイル出力ストリームの作成
			fos = mAct.openFileOutput(FILENAME, Context.MODE_PRIVATE);	// /data/dataに書き込み
			bufferedWriterObj = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SDFILEPATH)));

			Toast.makeText(mAct, "処理を開始します", Toast.LENGTH_SHORT).show();
			new Thread(new Runnable() {
				public void run() {
					
					Uri uri = _uri;
					Cursor c = mAct.managedQuery(uri, null, null, null, null);   
					DebugUtils2.i("", "DISPLAYING IMAGES COUNT= " + c.getCount());
					try {
						//title書き込み
						c.moveToFirst();
						for (String title : c.getColumnNames()) {
							fos.write((title + ", ").getBytes());
							bufferedWriterObj.write((title + ", "));
						}
						fos.write(BR.getBytes());
						bufferedWriterObj.write(BR);

						//レコード書き込み
						c.moveToFirst();   
						for (int k = 0; k < c.getCount(); k++) {   
							//DebugUtils2.i("", "ID = " + c.getString(c.getColumnIndexOrThrow("_id")));   
							for (String column : c.getColumnNames()) {
								String cell = c.getString(c.getColumnIndexOrThrow(column));
								//DebugUtils2.i("", column + "=" + cell);   
								fos.write((cell + ",").getBytes());
								bufferedWriterObj.write(cell + ",");
							}
							fos.write(BR.getBytes());
							bufferedWriterObj.write(BR);
							c.moveToNext();   
						}
						fos.flush();
						fos.close();
						bufferedWriterObj.flush();
						bufferedWriterObj.close();
						resStr = "CSVファイルの作成が完了しました！";
						DebugUtils2.i("", "make csv >>>"+ mAct.getApplication().getFilesDir().getPath()+ "/" +FILENAME);
						DebugUtils2.i("", "make csv >>>"+ SDFILEPATH);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					mHandler.post(new Runnable() {
						public void run() {
							Toast.makeText(mAct, resStr, Toast.LENGTH_SHORT).show();
						}
					});
				}
			}).start();

		}catch(Exception e){
			e.printStackTrace();
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			DebugUtils2.e("", "loadFromSDCard() Bad End");
			throw e;
		}
		DebugUtils2.d("", "loadFromSDCard() End");
	}
	
	public void makeImageDirList() {   
		DebugUtils2.d("", "makeImageDirList() Start");
		//同一ディレクトリ挿入済み確認
		ArrayList<String> directorysName = new ArrayList<String>();
		//sd指定
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		Cursor c = mAct.managedQuery(uri, null, null, null, null);   
		DebugUtils2.i("", "DISPLAYING IMAGES COUNT= " + c.getCount());
		try {   
			//行
			for (c.moveToFirst(); c.getPosition() < c.getCount(); c.moveToNext()) {   
				DebugUtils2.i("", "my ID = " + c.getString(c.getColumnIndexOrThrow("_id")) +"Position = "+ c.getPosition());
				String fileType = c.getString(c.getColumnIndexOrThrow("mime_type"));
				if (fileType.equals("image/jpeg") || fileType.equals("image/png")) {
					//DebugUtils2.i("", "image match id = "+ c.getString(c.getColumnIndexOrThrow("_id")));
					String dirName = c.getString(c.getColumnIndexOrThrow("bucket_display_name"));
					//既にフォルダ登録済みかチェック
					Boolean regFlag = true;
					for (int already = 0; already < directorysName.size(); already++) {
						if (directorysName.get(already).equals(dirName)) {
							//登録済み
							regFlag = false;
							break;
						}
					}
					if (!regFlag) {
						//フォルダ登録スキップ
						continue;
					}
					//未登録フォルダ
					directorysName.add(dirName);
				}
			}
			//登録ディレクトリ表示
			for (int i = 0; i < directorysName.size(); i++){
				DebugUtils2.i("", "dir["+ i +"] = "+ directorysName.get(i));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			DebugUtils2.e("", "makeImageDirList() Bad End");
		}
		DebugUtils2.d("", "makeImageDirList() End");
	}
}
