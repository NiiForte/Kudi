package rancard.com.kudi;

import java.util.Locale;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rancard.kudi.client.async.Callback;
import com.rancard.kudi.client.async.Kudi;
import com.rancard.kudi.domain.Account;
import com.rancard.kudi.domain.User;

import fragment.CreateAccount;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    static Kudi kudiInstance = Kudi.newInstance("http://10.42.0.1:8080/wallet/api/v1");
    static Kudi.Session session = kudiInstance.getSession("877");

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = UserProfile.newInstance(position + 1);
                    break;
                case 1:
                    fragment = CreateAccount.newInstance(position + 1);
                    break;
               /* default:
                    fragment=CreateAccount.newInstance(position + 1);*/


            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class UserProfile extends Fragment {
        String name = "";
        public TextView textView;
        String userProfile;
        View bar;
        TextView action_bar_title;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static UserProfile newInstance(int sectionNumber) {
            UserProfile fragment = new UserProfile();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public UserProfile() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);

            textView = (TextView) rootView.findViewById(R.id.usertext);

            return rootView;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            Log.d("Debug", "session: " + session);
            session.userProfile(new Callback<User>() {
                @Override
                public void onFailure(String s, int i) {
                    Log.d("error", s + " " + i);

                }

                @Override
                public void onSuccess(final com.rancard.kudi.domain.User user) {

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            userProfile = "First Name: " + user.getFirstName() + "/n";
                            userProfile = userProfile + "Last Name: " + user.getLastName() + "/n";
                            userProfile = userProfile + "Email: " + user.getEmail() + "/n";
                            userProfile = userProfile + "Country" + user.getCountry() + "/n";
                            userProfile = userProfile + "Mobile Number: " + user.getMobileNumber();
                            name = user.getFirstName();
                            textView.setText(userProfile);
                            Log.d("User Name", "*********================" + name);

                        }
                    });

                }
            });

        }

        @Override
        public void onResume() {
            super.onResume();
        }
    }

    public static class CreateAccount extends Fragment {

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
                public void onSuccess(final Long aLong) {


                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Creating account", aLong.toString());
                            result = aLong.toString();
                            Log.d("Long account", aLong.toString());

                            createAccount.setText(result);


                        }
                    });



                }

            });


        }


        @Override
        public void onResume() {
            super.onResume();
        }
    }
}

