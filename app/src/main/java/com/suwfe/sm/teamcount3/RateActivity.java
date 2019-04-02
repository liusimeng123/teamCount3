package com.suwfe.sm.teamcount3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {
    EditText rmb;
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb =(EditText)findViewById(R.id.rmb);
        show=(TextView)findViewById(R.id.showout);
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
             val = r * (1/6.7f);

        }else if(btn.getId()==R.id.euro){
            val = r *(1/11.0f);

        }else {
           val = r * 500;

        }
        show.setText(""+val);

        }
    public void openOne(View btn){
        Log.i("open","openOne");
        Intent hello =new Intent(this,MainActivity.class);
        Intent web =new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
        Intent intent =new Intent(Intent.ACTION_DIAL, Uri.parse("tel:87092173"));
        startActivity(hello);
    }
}
