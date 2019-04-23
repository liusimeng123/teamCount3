package com.suwfe.sm.teamcount3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private  final  String TAG ="second";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        String scorea =((TextView) findViewById(R.id.count)).getText().toString();
        String scoreb =((TextView) findViewById(R.id.count2)).getText().toString();

        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea= savedInstanceState.getString("teama_score");
        String scoreb= savedInstanceState.getString("teamb_score");

        ((TextView) findViewById(R.id.count)).setText(scorea);
        ((TextView) findViewById(R.id.count2)).setText(scoreb);
    }

    public void Reset(View v) {
        ((TextView) findViewById(R.id.count)).setText("0");
        ((TextView) findViewById(R.id.count2)).setText("0");
    }

    public void Add3(View v) {
        if(v.getId()==R.id.add3){
            showCount(3);
        }else{
            showCount2(3);
        }
    }

    public void Add2(View v) {
        if(v.getId()==R.id.add2){
            showCount(2);
        }else{
            showCount2(2);
        }
    }

    public void Add1(View v) {
        if(v.getId()==R.id.add1){
            showCount(1);
        }else{
            showCount2(1);
        }
}
    private void showCount(int inc){
        Log.i("show","inc="+inc);
        TextView out=(TextView)findViewById(R.id.count);
        String oldCount =(String)out.getText();
        int newCount =Integer.parseInt(oldCount)+inc;
       out.setText(""+newCount);
    }
    private void showCount2(int inc){
        Log.i("show","inc="+inc);
        TextView out2=(TextView)findViewById(R.id.count2);
        String oldCount =(String)out2.getText();
        int newCount =Integer.parseInt(oldCount)+inc;
        out2.setText(""+newCount);
    }

}
