package com.suwfe.sm.teamcount3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {

    private  final  String TAG ="Rate";
    private  float dollarRate=0.1f;
    private  float euroRate=0.2f;
    private  float wonRate=0.3f;

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
        config.putExtra("wonrate_key", wonRate);


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
            config.putExtra("wonrate_key",wonRate);
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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
