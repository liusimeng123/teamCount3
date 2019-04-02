package com.suwfe.sm.teamcount3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TepActivity extends AppCompatActivity implements View.OnClickListener {

    TextView out;
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tep);

        out =(TextView) findViewById(R.id.txtout);
        out.setText("转换为的华氏温度为");
        edit =(EditText) findViewById(R.id.inp);
        String str =edit.getText().toString();

        Button btn =findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }
    public void onClick(View v) {
        Log.i("main","onclick msg");
        String str = edit.getText().toString();
        float c=Float.parseFloat(str);
        float f=c*9/5+32;
        out.setText("转换的华氏温度为"+f+"℉");
    }
}
