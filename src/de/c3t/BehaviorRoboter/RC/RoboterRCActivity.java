package de.c3t.BehaviorRoboter.RC;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RoboterRCActivity extends Activity {
	BluetoothAdapter localAdapter;

	BluetoothSocket socket_nxt1;

	boolean connected = false;

	final String nxt1 = "00:16:53:07:5D:F2";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button bconnect = (Button) findViewById(R.id.bconnect);
		bconnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (connected) {
					writeMessage(ComunicationConstants.exit);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						socket_nxt1.close();
					} catch (IOException e) {
					}
				} else {
					System.out.println("enable BT");
					enableBT();
					System.out.println("connect");
					connectToNXT();
					System.out.println("connected");
				}
			}
		});

		Button btakeControl = (Button) findViewById(R.id.btakeControl);
		btakeControl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				writeMessage((byte) ComunicationConstants.takeControl);
			}
		});

		Button bforward = (Button) findViewById(R.id.bforward);
		bforward.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				writeMessage((byte) ComunicationConstants.forward);
			}
		});

		Button bbackward = (Button) findViewById(R.id.bbackwards);
		bbackward.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				writeMessage((byte) ComunicationConstants.backward);
			}
		});

		Button bleft = (Button) findViewById(R.id.bleft);
		bleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				writeMessage((byte) ComunicationConstants.turnLeft);
			}
		});

		Button bright = (Button) findViewById(R.id.bright);
		bright.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				writeMessage((byte) ComunicationConstants.turnRight);
			}
		});
		
		Button bstop = (Button) findViewById(R.id.bstop);
		bstop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				writeMessage((byte) ComunicationConstants.stop);
			}
		});
		
		Button bforwadLeft = (Button) findViewById(R.id.bforwadLeft);
		bforwadLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				writeMessage((byte) ComunicationConstants.forwadLeft);
			}
		});
		
		Button bforwardRight = (Button) findViewById(R.id.bforwadRight);
		bforwardRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				writeMessage((byte) ComunicationConstants.forwadRight);
			}
		});
		
		Button bbackwardsLeft = (Button) findViewById(R.id.bbackwardsLeft);
		bbackwardsLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				writeMessage((byte) ComunicationConstants.backwardLeft);
			}
		});
		
		Button bbackwardsRight = (Button) findViewById(R.id.bbackwardsRight);
		bbackwardsRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				writeMessage((byte) ComunicationConstants.backwardRight);
			}
		});
	}

	// Enables Bluetooth if not enabled
	public void enableBT() {
		localAdapter = BluetoothAdapter.getDefaultAdapter();
		// If Bluetooth not enable then do it
		if (localAdapter.isEnabled() == false) {
			localAdapter.enable();
			while (!(localAdapter.isEnabled())) {
				Thread.yield();
			}
		}
	}

	// connect to both NXTs
	public boolean connectToNXT() {
		// get the BluetoothDevice of the NXT
		BluetoothDevice nxt_1 = localAdapter.getRemoteDevice(nxt1);
		// try to connect to the nxt
		try {
			socket_nxt1 = nxt_1.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			socket_nxt1.connect();
			connected = true;
		} catch (IOException e) {
			Log.d("Bluetooth", "Err: Device not found or cannot connect");
			connected = false;
		}
		return connected;
	}

	public void writeMessage(byte msg) {
		if (socket_nxt1 != null) {
			try {
				OutputStreamWriter out = new OutputStreamWriter(socket_nxt1.getOutputStream());
				out.write(msg);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Error
		}
	}
}