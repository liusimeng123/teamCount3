package com.suwfe.sm.teamcount3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity implements Runnable{
    Handler handler;
    private  final  String TAG ="Rate";
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        String data[]={"111","222"};
        listView =(ListView)findViewById(R.id.mylist);

        ListAdapter adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);

        Thread t =new Thread(this);
        t.start();

        handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==7){
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter =new ArrayAdapter<String>(MyListActivity.this,android.R.layout.simple_list_item_1,list2);
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    public void run() {

        List<String> retList =new ArrayList<String>();
        Document doc = null;
        try {
            Thread.sleep(3000);
            doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj/").get();
            //doc =Jsoup.parse(html);
            Log.i(TAG,"RUN:"+doc.title());
            Elements tables =doc.getElementsByTag("table");
//            for(Element table:tables){
//                Log.i(TAG,"RUN:table["+i+"]"+table);
//                i++;
//            }
            Element table6 =tables.get(1);
            //Log.i(TAG,"RUN:table6="+table6);

            Elements tds =table6.getElementsByTag("td");
            for(Element td :tds){
                Log.i(TAG,"RUN:td="+td);
                for(int i=0;i<tds.size();i+=8){
                    Element td1 =tds.get(i);
                    Element td2 =tds.get(i+5);
                    Log.i(TAG,"RUN:"+td1.text()+">>"+td2.text());
                    String str1 =td1.text();
                    String val =td2.text();

                    retList.add(str1+"==>"+val);

                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message msg=handler.obtainMessage(7);
        msg.obj =retList;
        handler.sendMessage(msg);
    }
}
