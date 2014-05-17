package com.phonegap.plugins.fileopener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class FileOpener extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        try {
            if (action.equals("openFile")) {
//                openFile(args.getString(0));
            	CopyReadAssets();
                callbackContext.success();
                return true;
            }
        } 
        catch (RuntimeException e) {  // KLUDGE for Activity Not Found
            e.printStackTrace();
            callbackContext.error(e.getMessage());
        }
        return false;
    }


    private void CopyReadAssets()
    {
        AssetManager assetManager = this.cordova.getActivity().getAssets();

        InputStream in = null;
        OutputStream out = null;
//        long time=Calendar.getInstance().getTimeInMillis();
        
        File file = new File(this.cordova.getActivity().getFilesDir()+"/pdftemp", "git.pdf");
        
        
        Toast.makeText(webView.getContext(), "this.cordova.getActivity().getFilesDir() :"+this.cordova.getActivity().getFilesDir(), Toast.LENGTH_LONG).show();
        
        Toast.makeText(webView.getContext(), "getAbsolutePath :"+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        try {
			Toast.makeText(webView.getContext(), "getCanonicalPath() :"+file.getCanonicalPath(), Toast.LENGTH_LONG).show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Toast.makeText(webView.getContext(), "getPath() :"+file.getPath(), Toast.LENGTH_LONG).show();
        Toast.makeText(webView.getContext(), "getParent :"+file.getParent(), Toast.LENGTH_LONG).show();
        
        Toast.makeText(webView.getContext(), "this.cordova.getActivity().getFilesDir() again :"+this.cordova.getActivity().getFilesDir(), Toast.LENGTH_LONG).show();;
        try
        {
            in = assetManager.open("www/pdf/reliance.pdf");
            out = this.cordova.getActivity().openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + this.cordova.getActivity().getFilesDir() + "/git.pdf"),"application/pdf");

        this.cordova.getActivity().startActivity(intent);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }
    
    
}
