package com.suwfe.sm.teamcount3;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RateListActivity extends ListActivity implements Runnable{
    String data[]={"wait ......"};
    Handler handler;
    private  final  String TAG ="Rate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_rate_list);
        List<String> list1 =new ArrayList<String>();
        for (int i=1;i<100;i++){
            list1.add("item"+i);
        }

        ListAdapter adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        setListAdapter(adapter);


        Thread t =new Thread(this);
        t.start();

        handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==7){
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter =new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
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
