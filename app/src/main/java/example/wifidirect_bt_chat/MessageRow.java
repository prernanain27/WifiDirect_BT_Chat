package example.wifidirect_bt_chat;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MessageRow implements Parcelable {
	private final static String TAG = "PTP_MSG";
	
	public String mSender;
	public String mMsg;
	public String mTime;
	public static final String mDel = "^&^";
	
	private MessageRow() { 
	    this.mSender = null;
		this.mTime = null;
		this.mMsg = null;
	}
	
	public MessageRow(String sender, String msg, String time){
		mTime = time;
		if( time == null ){
			Date now = new Date();
			//SimpleDateFormat timingFormat = new SimpleDateFormat("mm/dd hh:mm");
			//mTime = new SimpleDateFormat("dd/MM HH:mm").format(now);
			mTime = new SimpleDateFormat("h:mm a").format(now);
		} 
		mSender = sender;
		mMsg = msg;
	}
	
	public MessageRow(Parcel in) {
        readFromParcel(in);
    }
	
	public String toString() {
		return mSender + mDel + mMsg + mDel + mTime;
	}
	
	
	public static JSONObject getAsJSONObject(MessageRow msgrow) {
		JSONObject jsonobj = new JSONObject();
		try{
			jsonobj.put(Constants.MSG_SENDER, msgrow.mSender);
			jsonobj.put(Constants.MSG_TIME, msgrow.mTime);
			jsonobj.put(Constants.MSG_CONTENT, msgrow.mMsg);
		}catch(JSONException e){
			//Log.e(TAG, "getAsJSONObject : " + e.toString());
		}
		return jsonobj;
	}
	
	/**
	 * convert json object to message row.
	 */
	public static MessageRow parseMesssageRow(JSONObject jsonobj) {
		MessageRow row = null;
		if( jsonobj != null ){
			try{
				row = new MessageRow(jsonobj.getString(Constants.MSG_SENDER), jsonobj.getString(Constants.MSG_CONTENT), jsonobj.getString(Constants.MSG_TIME));
			}catch(JSONException e){
				Log.d(TAG, "parseMessageRow: " + e.toString());
			}
		}
		return row;
	}
	
	/**
	 * convert a json string representation of messagerow into messageRow object.
	 */
	public static MessageRow parseMessageRow(String jsonMsg){
		JSONObject jsonobj = Utils_JSON.getJsonObject(jsonMsg);
		Log.d(TAG, "parseMessageRow : " + jsonobj.toString());
		return parseMesssageRow(jsonobj);
	}

	public static final Creator<MessageRow> CREATOR = new Creator<MessageRow>() {
        public MessageRow createFromParcel(Parcel in) {
            return new MessageRow(in);
        }
 
        public MessageRow[] newArray(int size) {
            return new MessageRow[size];
        }
    };
    
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mSender);
		dest.writeString(mMsg);
		dest.writeString(mTime);
	}
	
	public void readFromParcel(Parcel in) {
		mSender = in.readString();
		mMsg = in.readString();
		mTime = in.readString();
    }
}