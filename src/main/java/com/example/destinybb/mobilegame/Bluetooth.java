package com.example.destinybb.mobilegame;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.destinybb.bluetoothchat.BluetoothChatService;
import com.example.destinybb.bluetoothchat.Constants;
import com.example.destinybb.common.logger.Log;
import com.example.destinybb.sql.MyDataBase;

import java.util.Arrays;

public class Bluetooth extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    private Activity activity;
    private MyDataBase db;

    // Layout Views
   // private ListView mConversationView;
  //  private EditText mOutEditText;
    private Button mSendButton;
    private Button mConnectButton;
    private Button mDiscoverButton;

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Array adapter for the conversation thread
     */
    private ArrayAdapter<String> mConversationArrayAdapter;

    /**
     * String buffer for outgoing messages
     */
    private StringBuffer mOutStringBuffer;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * Member object for the chat services
     */
    private BluetoothChatService mChatService = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
db = new MyDataBase(this);
        setContentView(R.layout.activity_bluetooth);
        activity = this;
        // Get local Bluetooth adapter BluetoothAdapter是區域藍芽接口(藍芽廣播)。
        // BluetoothAdapter也是所有藍芽交易互動的啟始點。用這個接口，我們可以偵 測區域內有哪些其它的藍芽裝置、查詢已配對過的藍芽列表、
        // 用已知的MAC地址建立一個BluetoothDevice實體、建立一個 BluetoothServerSocket來監聽是否有其它藍芽裝置傳來的通訊…等。
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {

            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            this.finish();
        }

      //  mConversationView = (ListView) findViewById(R.id.in);
      //  mOutEditText = (EditText) findViewById(R.id.edit_text_out);
        mSendButton = (Button) findViewById(R.id.button_send);
        mConnectButton = (Button) findViewById(R.id.button_connect);
        mDiscoverButton = (Button) findViewById(R.id.button_dis);
mSendButton.setEnabled(false);

        mConnectButton.setOnClickListener(new Button.OnClickListener() {

                                              @Override

                                              public void onClick(View v) {

                                                  Intent serverIntent = new Intent(activity, DeviceListActivity.class);
                                                  startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                                              }
                                          }

        );
        mDiscoverButton.setOnClickListener(new Button.OnClickListener() {

                                               @Override

                                               public void onClick(View v) {

                                                   ensureDiscoverable();
                                               }
                                           }

        );
    }


    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        // 得到一個BluetoothAdapter實體之後，
        // 在onStart()裡，如果沒有啟動藍芽，則要求使用者開啟藍芽。
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupChat();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // 在onResume()裡，也做一樣的事，如果檢查沒有開啟藍芽BluetoothChatService背景服務，則再次開始該服務。

        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }


    /**
     * Set up the UI and background operations for chat.
     */
    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);

     //   mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
    //    mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                sendMessage(Arrays.toString(db.randIntArray(10)));

            }
        });

        // 透過setupChat()建立起基本的對話視窗和BluetoothChatService背景服務，並把主Thread的Handler傳給Service以供日後傳回message。
        mChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");


    }

    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        //    mOutEditText.setText(mOutStringBuffer);
        }
    }

    /**
     * The action listener for the EditText widget, to listen for the return key
     */
    private TextView.OnEditorActionListener mWriteListener
            = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            return true;
        }
    };

    public void setSendEnabled(){
        mSendButton.setEnabled(true);
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:

                            mConversationArrayAdapter.clear();

                            if(mChatService.isItServer()==true){
                                setSendEnabled();
                            }
                            break;
                        case BluetoothChatService.STATE_CONNECTING:

                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:

                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add( writeMessage);
                    System.out.println(writeMessage);

                    String[] messageNumber = writeMessage.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
                    int[] numbers2 = new int[messageNumber.length];
                    for(int i = 0;i < messageNumber.length;i++)
                    {
                        numbers2[i] = Integer.parseInt(messageNumber[i]);
                    }


                    Intent intent = new Intent(Bluetooth.this, GamePlay.class);
                    Bundle bundle = new Bundle();
                    intent.putExtra("questArray", numbers2);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Bluetooth.this.finish();
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(readMessage);
                    System.out.println(readMessage);

                    String[] numberStrs = readMessage.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
                    int[] numbers = new int[numberStrs.length];
                    for(int i = 0;i < numberStrs.length;i++)
                    {
                        numbers[i] = Integer.parseInt(numberStrs[i]);
                    }

                    //test
                    Intent intent2 = new Intent(Bluetooth.this, GamePlay.class);
                    Bundle bundle2 = new Bundle();
                    intent2.putExtra("questArray", numbers);
                    intent2.putExtras(bundle2);
                    startActivity(intent2);
                    Bluetooth.this.finish();

                    //end test
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);

                    System.out.println("i am running secure connect");
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(activity, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
        }
    }

    /**
     * Establish connection with other divice
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }


}



