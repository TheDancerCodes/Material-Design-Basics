package com.thedancercodes.materialdesignbasics;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    public static final String PREF_FILE_NAME = "testpref";

    // You need a key when reading writing to SharedPreferences
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;

    // Variable that shows whether the user is aware of the drawers' existence or not.
    private boolean mUserLearnedDrawer;

    // Variable Shows indicates whether the fgament is being created for the first time or if it is coming
    // back from a rotation
    private boolean mFromSavedInstanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read value from the SharedPreferences
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),
                KEY_USER_LEARNED_DRAWER, "false"));

        // Coming back from a rotation
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        // Initialize the View
        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }

                // invalidateOptionsMenu() makes your Activity draw the Toolbar again.
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();


            }

            // Use the value of slideOffset to set the alpha
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // If statement to ensure that the Toolbar isn't completely blacked out
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };

        // If user has never see the drawer & if this is the 1st time the fragment is starting,
        // Display the Navigation Drawer
        if (mUserLearnedDrawer && mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        // Set the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // syncState - To ensure the hamburger icon appears on Toolbar.
        // Syncs the state of the drawer indicator/affordance with the linked DrawerLayout.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    // Methods to write and read the value of mUserLearnedDrawer to & from SharedPreferences

    /**
     * TODO: You can shorten the function below using Cascading Method Calls.
     */
    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    /**
     * TODO: You can shorten the function below using Cascading Method Calls.
     */
    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }


}
