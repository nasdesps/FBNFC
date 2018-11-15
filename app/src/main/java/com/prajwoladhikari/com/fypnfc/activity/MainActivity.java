package com.prajwoladhikari.com.fypnfc.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.prajwoladhikari.com.fypnfc.R;
import com.prajwoladhikari.com.fypnfc.utils.NFCUtils;

import java.util.List;

public class MainActivity extends Activity
{
	private NfcAdapter _nfcAdapter;
	private PendingIntent _pendingIntent;
	private IntentFilter[] _intentFilters;

	private final String _MIME_TYPE = "text/plain";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_init();
	}
	
	private void _init()
	{
		_nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
		if (_nfcAdapter == null)
		{
			Toast.makeText(this, "This device does not support NFC.", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (_nfcAdapter.isEnabled())
		{
			_pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
			
			IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
			try
			{
				ndefDetected.addDataType(_MIME_TYPE);
			} catch (MalformedMimeTypeException e)
			{
				Log.e(this.toString(), e.getMessage());
			}
			
			_intentFilters = new IntentFilter[] { ndefDetected };
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		_enableNdefExchangeMode();
	}
	
	private void _enableNdefExchangeMode()
	{
		EditText messageTextField = (EditText) findViewById(R.id.message_text_field);
		String stringMessage = " " + messageTextField.getText().toString();
		
		NdefMessage message = NFCUtils.getNewMessage(_MIME_TYPE, stringMessage.getBytes());
			
		_nfcAdapter.setNdefPushMessage(message, this);
		_nfcAdapter.enableForegroundDispatch(this, _pendingIntent, _intentFilters, null);
	}
	
	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);

		if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()))
		{
			List<String> msgs = NFCUtils.getStringsFromNfcIntent(intent);

			
			Toast.makeText(this, "Message received : "+msgs.get(0), Toast.LENGTH_LONG).show();
		}
	}

//	private void sendRequestDialog()
//	{
//		Bundle params = new Bundle();
//		params.putString("title", "Add Friend");
//		params.putString("message", "has requested you to add");
//
//		WebDialog requestsDialog = new WebDialog.RequestsDialogBuilder(this(),
//                PackageInstaller.Session.getActiveSession(),
//                params)
//				.setOnCompleteListener(new com.facebook.internal.WebDialog.OnCompleteListener()
//				{
//					@Override
//					public void onComplete(Bundle values, FacebookException error)
//					{
//						if (error != null)
//						{
//							if (error instanceof FacebookOperationCanceledException)
//							{
//								Toast.makeText(this().getApplicationContext(),
//										"Request cancelled",
//										Toast.LENGTH_SHORT).show();
//							}
//							else
//							{
//								Toast.makeText(this().getApplicationContext(),
//										"Network Error",
//										Toast.LENGTH_SHORT).show();
//							}
//						}
//						else
//						{
//							final String requestId = values.getString("request");
//
//							if (requestId != null)
//							{
//								Toast.makeText(this().getApplicationContext(),
//										"Request sent",
//										Toast.LENGTH_SHORT).show();
//							}
//							else
//							{
//								Toast.makeText(this().getApplicationContext(),
//										"Request cancelled",
//										Toast.LENGTH_SHORT).show();
//							}
//						}
//					}
//
//				}).setTo("XXXXXXXXX").build();
//
//		requestsDialog.show();
//	}
}
