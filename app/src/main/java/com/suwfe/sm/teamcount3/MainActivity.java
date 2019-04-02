package com.suwfe.sm.teamcount3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView count;
    TextView count2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count =(TextView) findViewById(R.id.count);
        count2 =(TextView) findViewById(R.id.count2);
    }

    public void Reset(View v) {
        count.setText("0");
        count2.setText("0");
    }

    public void Add3(View v) {
        if(v.getId()==R.id.add3b){
            showCount(3);
        }else{
            showCount2(3);
        }
    }

    public void Add2(View v) {
        if(v.getId()==R.id.add2b){
            showCount(2);
        }else{
            showCount2(2);
        }
    }

    public void Add1(View v) {
        if(v.getId()==R.id.add1b){
            showCount(1);
        }else{
            showCount2(1);
        }
}
    private void showCount(int inc){
        Log.i("show","inc="+inc);
        String oldCount =(String)count.getText();
        int newCount =Integer.parseInt(oldCount)+inc;
        count.setText(""+newCount);
    }
    private void showCount2(int inc){
        Log.i("show","inc="+inc);
        String oldCount =(String)count2.getText();
        int newCount =Integer.parseInt(oldCount)+inc;
        count2.setText(""+newCount);
    }

}
