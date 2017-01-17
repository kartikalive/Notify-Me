package kartik.notifyme;

import android.app.ProgressDialog;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    String str1 = "http://www.stackoverflow.com";
    String stackQues = "http://stackoverflow.com/questions";
    String str = "http://www.exam.dtu.ac.in/result.htm";
    String str2 = "http://www.tnp.dtu.ac.in/rm_2016-17/intern/intern_student";
    String str3 = "https://open-event.herokuapp.com/api/v2/events/4";
    String rmLogin = "http://tnp.dtu.ac.in/rm_2016-17/intern/intern_login";
    String yahooLogin = "https://in.yahoo.com/";
    String google = "https://www.google.co.in/";
    TextView status;
    DateFormat df;
    String date;
    String lastString, currentString;
    String lastCheckSum, currentCheckSum;
    String info;
    String len;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        status = (TextView) findViewById(R.id.text_view);
      //  btn = (Button) findViewById(R.id.button);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        progressDialog.setCancelable(false);


        final WebView webview = (WebView) findViewById(R.id.web);
        WebSettings settings = webview.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        webview.addJavascriptInterface(new MyJavaScriptInterface(),"HTMLOUT");
      //  webview.loadUrl(str1);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressDialog.show();
                view.loadUrl(url);
                Log.d("TAG3",url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webview.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                progressDialog.dismiss();
                Log.d("TAG5",url);
                clickHere(url);
            }
        });
        webview.loadUrl(rmLogin);


//        try {
//            lastString = str;
//            lastCheckSum = getCheckSum(lastString);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public String getCheckSum(String s) throws IOException {
        Log.d("TAG7", s + "");
        //String shaKey;
        //MessageDigest md = null;
        URL url = new URL(s);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestMethod("GET");
        //huc.setDoOutput(true);
        huc.connect();
        Log.d("TAG7","okay1");
        InputStream in = huc.getInputStream();
        Log.d("TAG7", "okay2");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        line = reader.readLine();
        StringBuilder output = new StringBuilder();
        Log.d("TAG7", "okay4");
        int count = 1;
        while (count!=10) {
            //output.append("\n\n--> ");
            output.append(line);
            line = reader.readLine();
            count++;

        }
        Log.d("TAG7", "okay3");
        in.close();
        String ans = output.toString();
        return ans;
        //   String input = huc.getInputStream().toString();
//        long input;
//
//            Log.d("TAG2","here: ");
//            Date date = new Date(huc.getLastModified());
//
//        //  input = huc.modi
//        Log.d("TAG1",date + " ");
////        try {
////            md = MessageDigest.getInstance("SHA-256");
////        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////        }
////        md.update(input.getBytes("UTF-8"));
////        byte[] digest = md.digest();
////        String output = digest.toString();
////        Log.d("TAG2",output);
////        return output;
//    //    return DigestUtils.sha256Hex(huc.getInputStream());
//        return date+"";
    }

    public void clickHere(final String x ){
        Log.d("TAG6",x);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try {
                    info = getCheckSum(x);
                    //                   len = getCheckLength(str);
                    Log.d("TAG1", "here str = " + x + "  info =  " + info);
                } catch (IOException e) {
                    Log.d("TAG1", "else Here  " + e);
                    e.printStackTrace();
                }
//                while (true){
//                    if(checkSumDB.get(str)!=null) {
//                        lastCheckSum = checkSumDB.get(str);
//                    }
//                    else{
//                        lastCheckSum = "";
//                    }
//                    currentCheckSum = "";
//                    try {
//                        currentCheckSum = getCheckSum(str);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Log.d("TAG1",lastCheckSum);
//                    Log.d("TAG2", currentCheckSum);
//                    if(lastCheckSum.equals(currentCheckSum)){
//                        date = df.format(Calendar.getInstance().getTime());




                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        status.setText(info + "");

                    }
                });
//
//                //  Toast.makeText(MainActivity.this, "Not updated yet", Toast.LENGTH_SHORT).show();
//            }
//
////                    else{
////                        date = df.format(Calendar.getInstance().getTime());
////                        checkSumDB.put(str,currentCheckSum);
////                        runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                status.setText("Changed at "+ date);
////
////                            }
////                        });
////                       // Toast.makeText(MainActivity.this, "Changed", Toast.LENGTH_SHORT).show();
////                    }
//
////                    try {
////                        Thread.sleep(1000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                        Log.d("TAG",""+e);
////                       // Toast.makeText(MainActivity.this, "Exception", Toast.LENGTH_SHORT).show();
////                    }
//
//            //}
                 }
        };
//
        Thread thread = new Thread(runnable);
        thread.start();
//
    }

}

class MyJavaScriptInterface
{
    @SuppressWarnings("unused")
   // @JavascriptInterface
    @JavascriptInterface
    public void processHTML(final String html)
    {
        Log.d("processed html",html);

        Thread OauthFetcher=new Thread(new Runnable() {

            @Override
            @JavascriptInterface
            public void run() {

                String oAuthDetails=null;
                oAuthDetails= Html.fromHtml(html).toString();
                Log.d("oAuthDetails",oAuthDetails);

            }
        });OauthFetcher.start();
    }
}