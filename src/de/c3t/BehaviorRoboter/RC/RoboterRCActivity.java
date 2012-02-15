package de.c3t.BehaviorRoboter.RC;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RoboterRCActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button bforward = (Button)findViewById(R.id.bforward);
        bforward.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						System.out.println("hello world");
					}
				});
    }
}