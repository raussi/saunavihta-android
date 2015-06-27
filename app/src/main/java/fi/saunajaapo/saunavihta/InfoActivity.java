package fi.saunajaapo.saunavihta;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class InfoActivity extends ActionBarActivity {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mTextView1 = (TextView) findViewById(R.id.textView1);
        mTextView2 = (TextView) findViewById(R.id.textView2);
        mTextView3 = (TextView) findViewById(R.id.textView3);

        String infoText1 = "Sauna-Jaapon saunavihta, löyly ja virvoke kätevässä pakkauksessa.";
        String infoText2 = "Heiluta puhelinta, niin kuulet saunavihdan ja löylynheiton äänet sekä virvoitusjuomapullon avausäänen";
        String infoText3 = "Halutessasi voit lisätä sovelluksen asetuksista saunomisen taustaäänet.";

        mTextView1.setText(infoText1);
        mTextView2.setText(infoText2);
        mTextView3.setText(infoText3);

        mBackButton = (Button) findViewById(R.id.button2);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
