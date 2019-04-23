package com.suwfe.sm.teamcount3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RateActivity extends AppCompatActivity implements Runnable{

    private  final  String TAG ="Rate";
    private  float dollarRate=0.1f;
    private  float euroRate=0.2f;
    private  float wonRate=0.3f;

    EditText rmb;
    TextView show;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb =(EditText)findViewById(R.id.rmb);
        show=(TextView)findViewById(R.id.showout);

        SharedPreferences sharedPreferences =getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate = sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate = sharedPreferences.getFloat("won_rate",0.0f);

        Log.i(TAG,"onCreate:sp dollarRate="+ dollarRate);
        Log.i(TAG,"onCreate:sp euroRate="+ euroRate);
        Log.i(TAG,"onCreate:sp wonRate="+ wonRate);

        Thread t =new Thread(this);
        t.start();

        handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==5){
                   Bundle bdl =(Bundle)msg.obj;
                    dollarRate=bdl.getFloat("dollar_rate");
                    euroRate=bdl.getFloat("euro_rate");
                    wonRate=bdl.getFloat("won_rate");
                    Log.i(TAG,"HANDLE:DR"+dollarRate);
                    Log.i(TAG,"HANDLE:ER"+euroRate);
                    Log.i(TAG,"HANDLE:WR"+wonRate);

                    Toast.makeText(RateActivity.this,"汇率已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };

    }
    public void  onClick(View btn){
        String str =rmb.getText().toString();
        float r=0;
        float val=0;
        if(str.length()>0){
            r =Float.parseFloat(str);
        }else{
            Toast.makeText(this,"plasea input",Toast.LENGTH_SHORT).show();
        }
        if(btn.getId()==R.id.dollar){
             show.setText(String.format("%.2f",r*dollarRate));
        }else if(btn.getId()==R.id.euro){
            show.setText(String.format("%.2f",r*euroRate));
        }else {
            show.setText(String.format("%.2f",r*wonRate));
        }
    }
    public void openOne(View btn){
        openConfig();
    }

    private void openConfig() {
        Intent config = new Intent(this, Rate2Activity.class);
        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("euro_rate_key", euroRate);
        config.putExtra("won_rate_key", wonRate);


        //startActivity(config);
        startActivityForResult(config, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            Intent config=new Intent(this,Rate2Activity.class);
            config.putExtra("dollar_rate_key",dollarRate);
            config.putExtra("euro_rate_key",euroRate);
            config.putExtra("won_rate_key",wonRate);
            startActivityForResult(config,1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&&resultCode==2){
            Bundle bundle =data.getExtras();
            dollarRate =bundle.getFloat("key_dollar",0.1f);
            euroRate =bundle.getFloat("key_euro",0.2f);
            wonRate =bundle.getFloat("key_won",0.3f);

            SharedPreferences sharedPreferences =getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);
            editor.commit();
            Log.i(TAG,"onActivityResult:数据nn sharedPreferences");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        Log.i(TAG,"run:run()....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();



//        URL url = null;
//        try {
//            url = new URL("http://www.usd-cny.com/bankofchina.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in =http.getInputStream();
//
//            String html  = inputStream2String(in);
//            Log.i(TAG,"run:html="+html);
//            Document doc =Jsoup.parse(html);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            //doc =Jsoup.parse(html);
            Log.i(TAG,"RUN:"+doc.title());
            Elements tables =doc.getElementsByTag("table");
//            for(Element table:tables){
//                Log.i(TAG,"RUN:table["+i+"]"+table);
//                i++;
//            }
            Element table6 =tables.get(0);
            //Log.i(TAG,"RUN:table6="+table6);

            Elements tds =table6.getElementsByTag("td");
            for(Element td :tds){
                Log.i(TAG,"RUN:td="+td);
                for(int i=0;i<tds.size();i+=6){
                    Element td1 =tds.get(i);
                    Element td2 =tds.get(i+5);
                    Log.i(TAG,"RUN:"+td1.text()+">>"+td2.text());
                    String str1 =td1.text();
                    String val =td2.text();

                    if("美元".equals(str1)){
                          bundle.putFloat("dollar_rate",100f/Float.parseFloat(val));
                    }else if("欧元".equals(str1)){
                        bundle.putFloat("euro_rate",100f/Float.parseFloat(val));
                    }if("韩元".equals(str1)){
                        bundle.putFloat("won_rate",100f/Float.parseFloat(val));
                    }

                }


        }

        } catch (IOException e) {
            e.printStackTrace();
        }

            Message msg=handler.obtainMessage(5);
            // msg.what =5;
           // msg.obj ="HEllo from run()";
            msg.obj =bundle;
            handler.sendMessage(msg);



    }
    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}
