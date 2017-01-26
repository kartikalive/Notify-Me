package kartik.notifyme;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String rmLogin = "http://tnp.dtu.ac.in/rm_2016-17/intern/intern_login";
    String rmStudent = "http://tnp.dtu.ac.in/rm_2016-17/intern/intern_student";
    WebView webview;
    ProgressDialog progressDialog;
    ArrayList<String> contentList = new ArrayList<>(100);
    ArrayList<String> timeList = new ArrayList<>(100);
    ArrayList<String> dateList = new ArrayList<>(100);
    public static final String PREF_NAME = "NotifyMe";
    SharedPreferences preferences;
    int flag;
    public static final int DEFAULT = 1;

//------------------------Unused--------------------------------------
    String str1 = "http://www.stackoverflow.com";
    String stackQues = "http://stackoverflow.com/questions";
    String str = "http://www.exam.dtu.ac.in/result.htm";
    String str2 = "http://www.tnp.dtu.ac.in/rm_2016-17/intern/intern_student";
    String str3 = "https://open-event.herokuapp.com/api/v2/events/4";
    String rmHandle = "http://tnp.dtu.ac.in/rm_2016-17/intern/intern_login/student_login_handle";
    final String USER_AGENT = "\"Mozilla/5.0 (Windows NT\" +\n" +
            "          \" 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2\"";
    HashMap<String, String> cookies = new HashMap<>();
    HashMap<String, String> formData = new HashMap<>();

    String yahooLogin = "https://in.yahoo.com/";
    String google = "https://www.google.co.in/";
    TextView status;
    DateFormat df;
    String lastString, currentString;
    String lastCheckSum, currentCheckSum;
    String info;
    String len;
    Button btn;
//------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //    df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        //   status = (TextView) findViewById(R.id.text_view);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        progressDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        progressDialog.setCancelable(false);
    //    CookieSyncManager.createInstance(this);
     //   CookieSyncManager.getInstance().sync();

        preferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        flag = preferences.getInt("flag",DEFAULT);
        if(flag==DEFAULT) {
            Log.d("TAG15","flag: "+flag+"");
            webScrapingFunc(rmLogin);
        }
        else if(flag==2){
            Log.d("TAG15","flag: "+flag+"");
            webScrapingFunc(rmStudent);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//       // CookieSyncManager.getInstance().stopSync();
//    }

    public void webScrapingFunc(String url){
        webview = (WebView) findViewById(R.id.web);
        WebSettings settings = webview.getSettings();

        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
      //  settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        webview.loadUrl(url);
        webview.addJavascriptInterface(new MyJavaScriptInterface(),"HtmlHandler");
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
                if(url.equals(rmStudent)) {
                    preferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("flag", 2);
                    editor.commit();
                    int temp = preferences.getInt("flag",0);
                    Log.d("TAG15","afterLogin: "+temp+"");
                    webview.loadUrl("javascript:window.HtmlHandler.handleHtml('<html>'+document.getElementsByTagName('body')[0].innerHTML+'</html>');");
                   // webview.addJavascriptInterface(new MyJavaScriptInterface2(),"HtmlApply");
                //    webview.loadUrl("javascript:window.HtmlApply.applyHtml('<html>'+document.getElementsByClassName('");
           //         webview.loadUrl("javascript:document.getElementsByClassName('timeline-header')[0].click();"‌​);
//                    <h4 class="timeline-header"><a href="http://tnp.dtu.ac.in/rm_2016-17/intern/intern_student/recruiter_profile/coe_rm_17_intern">COE INTERN ADMIN</a></h4>
                   // body > div > div > section.content > div > div > ul.timeline > li:nth-child(2) > div > h4
                   // body > div > div > section.content > div > div > ul.timeline > li:nth-child(4) > div > h4
                }

                if(url.equals(rmLogin)) {
                    preferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("flag", 1);
                    editor.commit();
                    int temp = preferences.getInt("flag",0);
                    Log.d("TAG15","afterLogout: "+temp+"");
                }
                progressDialog.dismiss();
                Log.d("TAG5",url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.d("TAG10","here");
                handler.proceed();
                error.getCertificate();
            }
        });

        webview.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



    public  void showNotification(View v){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Title");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentText("Text");
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(sound);

        Intent intent = new Intent(this, SecondActivity.class);
        android.support.v4.app.TaskStackBuilder stackBuilder = android.support.v4.app.TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SecondActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =  stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());


    }

    class MyJavaScriptInterface
    {

        String[] contentList = new String[102];
        String[] timeList = new String[102];
        String[] dateList = new String[102];
        String[] linkList = new String[102];

        @SuppressWarnings("unused")
        @JavascriptInterface
        public void handleHtml(String html) {
            Log.d("TAG56", html);
            Document doc = Jsoup.parse(html);
            int n = 40;
            for(int i=1;i<=n;i++) {
                String contentSelector = "body > div > div > section.content > div > div > ul.timeline > li:nth-child("+2*i+") > div > div > p:nth-child(1)";
                Elements contentElement = doc.select(contentSelector);
                String singleContent = Html.fromHtml(contentElement.toString()).toString();
                contentList[i] = singleContent;

                String timeSelector = "body > div > div > section.content > div > div > ul.timeline > li:nth-child("+(2*i-1)+") > span";
                Elements timeElement = doc.select(timeSelector);
                String singleTime = Html.fromHtml(timeElement.toString()).toString();
                timeList[i] = singleTime;

                String dateSelector = "body > div > div > section.content > div > div > ul.timeline > li:nth-child("+2*i+") > div > span";
                Elements dateElement = doc.select(dateSelector);
                String singleDate = Html.fromHtml(dateElement.toString()).toString();
                dateList[i] = singleDate;

                String linkSelector = "body > div > div > section.content > div > div > ul.timeline > li:nth-child("+2*i+") > div > h4 > a";
                Elements linkElement = doc.select(linkSelector);
                String singleLink = linkElement.attr("abs:href");
                //String singleLink = Html.fromHtml(linkElement.toString()).toString();
                linkList[i] = singleLink;

                Log.d("TAG7", "i = " + i + "    Element = "+ contentList[i]+ "    Time = "+ timeList[i] + "    Date =  "+ dateList[i] + "    Link =   " + linkList[i] + "\n\n");
            }

        }

    }



}



//----------------------------------------------UNUSED CODE---------------------------------------------


//public void clickHere(final String x ){
//    Log.d("TAG6",x);
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//
//            try {
//                info = getCheckSum(x);
//                //                   len = getCheckLength(str);
//                Log.d("TAG1", "here str = " + x + "  info =  " + info);
//            } catch (IOException e) {
//                Log.d("TAG1", "else Here  " + e);
//                e.printStackTrace();
//            }
//            while (true){
//                if(checkSumDB.get(str)!=null) {
//                    lastCheckSum = checkSumDB.get(str);
//                }
//                else{
//                    lastCheckSum = "";
//                }
//                currentCheckSum = "";
//                try {
//                    currentCheckSum = getCheckSum(str);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Log.d("TAG1",lastCheckSum);
//                Log.d("TAG2", currentCheckSum);
//                if(lastCheckSum.equals(currentCheckSum)){
//                    date = df.format(Calendar.getInstance().getTime());
//
//
//
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            status.setText(info + "");
//
//                        }
//                    });
//
//                    Toast.makeText(MainActivity.this, "Not updated yet", Toast.LENGTH_SHORT).show();
//                }
//
//                else{
//                    date = df.format(Calendar.getInstance().getTime());
//                    checkSumDB.put(str,currentCheckSum);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            status.setText("Changed at "+ date);
//
//                        }
//                    });
//                    Toast.makeText(MainActivity.this, "Changed", Toast.LENGTH_SHORT).show();
//                }
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    Log.d("TAG",""+e);
//                    Toast.makeText(MainActivity.this, "Exception", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }
//    };
//
//    Thread thread = new Thread(runnable);
//    thread.start();
//
//}
//
//
//    public void scrapingFunc(final String url){
//
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Connection.Response loginForm =  Jsoup.connect(url).method(Connection.Method.GET).userAgent(USER_AGENT).execute();
//                    org.jsoup.nodes.Document loginDoc = loginForm.parse();
//                    Log.d("checkTag","1, cookies of login = " + loginForm.cookies());
//
//                    cookies.putAll(loginForm.cookies());
//                    cookies.put("PHPSESSID","0rettnm5ikcam3g6at6hqcp571");
//
//                    String authToken = loginDoc.select("#student > form > input[type=\"hidden\"]")
//                            .first()
//                            .attr("value");
//                    //Elements btnE = loginDoc.getElements
//                    //String btn = loginDoc.select("#student > form > button").first().attr("name");
//                    //  String btnS = btnE.toString();
//                    Log.d("checkTag", "2" + " authToken = " + authToken);
//                    formData.put("intern_student_username_rollnumber", username);
//                    //  formData.put("utf8", "e2 9c 93");
//                    formData.put("intern_student_password", password);
//                    formData.put("csrf_test_name",authToken);
//                    //  formData.put(btnS,"Log In");
//
//
//                    //CookieSyncManager.getInstance().sync();
//                    // android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
//                    // String c = cookieManager.getCookie(rmLogin);
//                    // Log.d("checkTag", "c : " + c);
//                    // String firstC = cookies.get("xyzsd");
//                    // String secondC = cookies.get("csrf_cookie_name");
////                    URL url = new URL(rmLogin);
////                    URLConnection urlConnection = url.openConnection();
//                    // Log.d("checkTag", "firstC = "+ firstC + " secondC = " + secondC + "status code = " + "                " + urlConnection.getHeaderField(4));
//                    Connection.Response homePage = Jsoup.connect(rmHandle)
//                            .cookies(cookies)
//                            .data(formData)
//                            .userAgent(USER_AGENT)
//                            .method(Connection.Method.POST)
//                            .execute();
//
//                    Log.d("checkTag", "3  cookies = " + cookies);
//
//                    //org.jsoup.nodes.Document studentDoc = homePage.parse();
//                    Log.d("checkTag",homePage.parse() + "");
////            Connection.Response login = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
////                    .data("intern_student_username_rollnumber","").data("intern_student_password","")
////                    .cookies(init.cookies()).method(Connection.Method.POST).execute();
////            Connection.Response login = Jsoup.connect(url)
////                    .data("intern_student_username_rollnumber",username).data("intern_student_password",password);
////            .cookies(init.cookies()).method(Connection.Method.POST).execute();
////            Log.d("checkTag", "2");
////            org.jsoup.nodes.Document doc = Jsoup.connect(rmStudent).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").cookies(login.cookies()).get();
////            Log.d("TAG9", doc + "");
//                } catch (IOException e) {
//                    Log.d("TAG9"," exception - "+ e );
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        Thread thread = new Thread(runnable);
//        thread.start();
//    }
//
//
//
//
//
//
//    public String getCheckSum(String s) throws IOException {
//        Log.d("TAG7", s + "");
//        //String shaKey;
//        //MessageDigest md = null;
//        URL url = new URL(s);
//        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
//        huc.setRequestMethod("GET");
//        //huc.setDoOutput(true);
//        huc.connect();
//        Log.d("TAG7","okay1");
//        InputStream in = huc.getInputStream();
//        Log.d("TAG7", "okay2");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        String line;
//        line = reader.readLine();
//        StringBuilder output = new StringBuilder();
//        Log.d("TAG7", "okay4");
//        int count = 1;
//        while (count!=10) {
//            //output.append("\n\n--> ");
//            output.append(line);
//            line = reader.readLine();
//            count++;
//
//        }
//        Log.d("TAG7", "okay3");
//        in.close();
//        String ans = output.toString();
//        return ans;
//
//
//
//        String input = huc.getInputStream().toString();
//        long input;
//
//        Log.d("TAG2","here: ");
//        Date date = new Date(huc.getLastModified());
//
//        input = huc.modi
//        Log.d("TAG1",date + " ");
//        try {
//            md = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        md.update(input.getBytes("UTF-8"));
//        byte[] digest = md.digest();
//        String output = digest.toString();
//        Log.d("TAG2",output);
//        return output;
//        return DigestUtils.sha256Hex(huc.getInputStream());
//        return date+"";
//    }