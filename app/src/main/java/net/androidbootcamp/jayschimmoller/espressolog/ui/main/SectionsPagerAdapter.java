package net.androidbootcamp.jayschimmoller.espressolog.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import net.androidbootcamp.jayschimmoller.espressolog.Frag1;
import net.androidbootcamp.jayschimmoller.espressolog.Frag2;
import net.androidbootcamp.jayschimmoller.espressolog.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.mainScreenTab, R.string.saveScreenTab};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment = new Frag1();
                break;
            case 1:
                fragment = new Frag2();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount()
    {
        // Show 2 total pages.
        return 2;
    }
}