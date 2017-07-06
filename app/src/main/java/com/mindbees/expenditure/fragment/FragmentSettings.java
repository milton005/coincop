package com.mindbees.expenditure.fragment;

import org.apache.http.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.facebook.login.LoginManager;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindbees.expenditure.Activity_LoginType;
import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.ImageVisibility;
import com.mindbees.expenditure.model.StatusAccount;
import com.mindbees.expenditure.ui.CircularContactView;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;
import com.mindbees.expenditure.util.WebService;
import com.mindbees.expenditure.util.XMLParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class FragmentSettings extends Fragment{
	
	ImageVisibility mCallback;
	CircularContactView changePass;
	CircularContactView changeEmail;
	CircularContactView Logout;
	 AlertDialog passDialog, emailDialog;
	
	RelativeLayout cPass, cEmail, cLogout;
	
	Tools tools;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		try {
			mCallback = (ImageVisibility) activity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ClassCastException(activity.toString()
                    + " must implement Listener");
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_settings, null);
		
		mCallback.isVisible(Const.TAG_SETTINGS);
		
		tools = new Tools(getActivity());
		
		changePass = (CircularContactView) v.findViewById(R.id.imgLeftIconPassword);
		changeEmail = (CircularContactView) v.findViewById(R.id.imgLeftIconEmail);
		Logout = (CircularContactView) v.findViewById(R.id.imgLeftIconLogout);
		
		cPass = (RelativeLayout) v.findViewById(R.id.lauoutPAss);
		cEmail = (RelativeLayout) v.findViewById(R.id.lauoutEmail);
		cLogout = (RelativeLayout) v.findViewById(R.id.lauoutLogout);
		
		
		changePass.setImageResource(R.drawable.change_password, getResources().getColor(R.color.settings_icon_bg));
		changeEmail.setImageResource(R.drawable.email, getResources().getColor(R.color.settings_icon_bg));
//		Logout.setImageResource(R.drawable.logout, getResources().getColor(R.color.settings_icon_bg));
		
		if (!BaseActivity.getpreferenceBoolean(Const.TAG_LOGIN_FB, getActivity())) {
			cPass.setVisibility(View.VISIBLE);
			cEmail.setVisibility(View.VISIBLE);
		}
		
		changePasswordDialog();
		changeEmailDialog();
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		cLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logOut();
			}
		});
		
		cPass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!BaseActivity.getpreferenceBoolean(Const.TAG_LOGIN_FB, getActivity())) {
					passDialog.show();
				}else {
					tools.showToastMessage("You can't edit details");
				}
				
			}
		});
		
		cEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (!BaseActivity.getpreferenceBoolean(Const.TAG_LOGIN_FB, getActivity())) {
					emailDialog.show();
				}else {
					tools.showToastMessage("You can't edit details");
				}
				
			}
		});
	}
	
	
	public void logOut() {
		new AlertDialog.Builder(getActivity())
				.setTitle("Coin Cop")
				.setMessage("Do you want to log out ?")
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								clearCachedValues();
								
							}

				}).setNegativeButton("No", null).show();
	}

	protected void clearCachedValues() {
		// TODO Auto-generated method stub
         
		if (!BaseActivity.getpreferenceBoolean(Const.TAG_LOGIN_FB, getActivity())) {
			LoginManager.getInstance().logOut();
		}
		
         BaseActivity.setpreferenceBoolean(Const.TAG_LOGIN, false, getActivity());
         BaseActivity.setpreferenceBoolean(Const.TAG_LOGIN_FB, false, getActivity());
         BaseActivity.setpreference(Const.TAG_USERID, "", getActivity());
         BaseActivity.setpreference(Const.TAG_USERPASS, "", getActivity());
         BaseActivity.setpreference(Const.TAG_USEREMAIL, "", getActivity());
         BaseActivity.setpreference(Const.TAG_CURRENCY_ID, "", getActivity());
         
         Intent intent = new Intent(getActivity(), Activity_LoginType.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  
         startActivity(intent);
         getActivity().finish();
		
	}
	
	
	private void changePasswordDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.view_change_pass, null);
        
        final EditText oldpass = (EditText) dialoglayout.findViewById(R.id.oldPass);
        final EditText newpass = (EditText) dialoglayout.findViewById(R.id.newPass);
        dialoglayout.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passDialog.dismiss();
            }
        });
        dialoglayout.findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if (tools.isConnectingToInternet()) {
            		validateField();
				}else {
					tools.showToastMessage("Please check your connection !");
				}
                
            	
            	
            }

			private void validateField() {
				// TODO Auto-generated method stub
				
				String oldPass =  oldpass.getText().toString().trim();
				String newPass =  newpass.getText().toString().trim();
				
				oldpass.setError(null);
				newpass.setError(null);
				
				if (oldPass.isEmpty()) {
					
					oldpass.setError("need Old Password");
					
				}else if (!(oldPass.contentEquals(BaseActivity.getpreference(Const.TAG_USERPASS, getActivity())))) {
					oldpass.setError("Old Password mismatch !");
					
				}else if (newPass.isEmpty()) {
					newpass.setError("need new Password");
				}else {
					passDialog.dismiss();
					sumbmitChange(newPass, BaseActivity.getpreference(Const.TAG_USEREMAIL, getActivity()));
				}
				
			}
        });



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialoglayout);
        passDialog = builder.create();
    }
	
	
	
	private void changeEmailDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.view_change_email, null);
        
        final EditText email = (EditText) dialoglayout.findViewById(R.id.email);
        email.setText(BaseActivity.getpreference(Const.TAG_USEREMAIL, getActivity()));
        dialoglayout.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailDialog.dismiss();
            }
        });
        dialoglayout.findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            	if (tools.isConnectingToInternet()) {
            		validateField();
				}else {
					tools.showToastMessage("Please check your connection !");
				}
            	
            }

			private void validateField() {
				// TODO Auto-generated method stub
				
				String newEmail =  email.getText().toString().trim();
				
				email.setError(null);
				
				if (newEmail.isEmpty()) {
					
					email.setError("need email");
					
				}else if (!tools.isValidEmail(newEmail)) {
					email.setError("Required valid Email !");
					
				}else {
					sumbmitChange(BaseActivity.getpreference(Const.TAG_USERPASS, getActivity()), newEmail);
					
				}
				
			}
        });



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialoglayout);
        emailDialog = builder.create();
    }

	protected void sumbmitChange(final String password, final String newEmail) {
		// TODO Auto-generated method stub
		
		 	final ProgressDialog pd = new ProgressDialog(getActivity());
				 pd.setMessage("Updating");
				 pd.setCancelable(false);

				RequestParams params = new RequestParams();
				params.put("user_action", "1005");
				params.put("user_id", BaseActivity.getpreference(Const.TAG_USERID, getActivity()));
				params.put("user_email", newEmail);
				params.put("new_password", password);

				WebService.post(Const.URL, params, new AsyncHttpResponseHandler() {

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						 pd.show();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub

						parse(new String(arg2));
						tools.showLog(new String(arg2), 2);
						
						 if (pd.isShowing()) { pd.dismiss(); }
						 

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub

						try {
							Tools.debugResponse(new String(arg2));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						
						 if (pd.isShowing()) { pd.dismiss(); }
						 

						Tools.debugThrowable(arg3);

						if (arg2 != null) {
							Tools.debugResponse(new String(arg2));
						}
						tools.showToastMessage(getActivity().getResources().getString(R.string.connectivity));
					}

					private void parse(String string) {
						// TODO Auto-generated method stub
						String value = "";
						String message = "";
						
						XMLParser parser = new XMLParser();
						try {
							String xml = string;

							Document doc = parser.getDomElement(xml);
							NodeList nl = doc.getElementsByTagName("result");
							for (int i = 0; i < nl.getLength(); i++) {
								Element e = (Element) nl.item(i);

								value = parser.getValue(e, "value");
								message = parser.getValue(e, "message");

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						tools.showToastMessage(message);
						if (Integer.parseInt(value) == 1) {
							
							BaseActivity.setpreference(Const.TAG_USERPASS, password, getActivity());
							BaseActivity.setpreference(Const.TAG_USEREMAIL, newEmail, getActivity());
						}

					}
				});
		
	}


}
