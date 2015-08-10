package fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rancard.kudi.client.async.Callback;
import com.rancard.kudi.client.async.Kudi;
import com.rancard.kudi.domain.Account;

import rancard.com.kudi.R;


public class CreateAccount extends Fragment {

    static Kudi kudiInstance = Kudi.newInstance("http://10.42.0.1:8080/wallet/api/v1");
    static Kudi.Session session = kudiInstance.getSession("877");
    static TextView createAccount;
    private static final String ARG_SECTION_NUMBER = "section_number";
    String result;

    public static CreateAccount newInstance(int sectionNumber) {
        CreateAccount fragment = new CreateAccount();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public CreateAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        createAccount = (TextView)rootView.findViewById(R.id.accountName);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Account account = new Account();
        account.setAccountName("Thomas");
        session.createAccount(account, new Callback<Long>() {
            @Override
            public void onFailure(String s, int i) {
                Log.d("account error", s);

            }

            @Override
            public void onSuccess(Long aLong) {

                Log.d("Creating account", aLong.toString());
                result = aLong.toString();
                Log.d("Long account", aLong.toString());

                createAccount.setText(result);


            }

        });


    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
