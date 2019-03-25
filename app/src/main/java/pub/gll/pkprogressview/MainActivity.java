package pub.gll.pkprogressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import pub.gll.pkprogressview.widget.PKProgressView;

public class MainActivity extends AppCompatActivity {
    private PKProgressView pk_progress_view;


    private int leftNum = 0;
    private int rightNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pk_progress_view = findViewById(R.id.pk_progress_view);
        pk_progress_view.setRightNum(rightNum);
        pk_progress_view.setLeftNum(leftNum);
        pk_progress_view.setRadius(10);
        findViewById(R.id.bt_left_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftNum++;
                pk_progress_view.setLeftNum(leftNum);
            }
        });
        findViewById(R.id.bt_right_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightNum++;
                pk_progress_view.setRightNum(rightNum);
            }
        });

        findViewById(R.id.bt_left_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftNum==0){
                    Toast.makeText(MainActivity.this,"票数不能为负",Toast.LENGTH_SHORT).show();
                }else {
                    leftNum--;
                    pk_progress_view.setLeftNum(leftNum);
                }
            }
        });
        findViewById(R.id.bt_right_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightNum==0){
                    Toast.makeText(MainActivity.this,"票数不能为负",Toast.LENGTH_SHORT).show();
                }else {
                    rightNum--;
                    pk_progress_view.setRightNum(rightNum);
                }
            }
        });

    }
}
