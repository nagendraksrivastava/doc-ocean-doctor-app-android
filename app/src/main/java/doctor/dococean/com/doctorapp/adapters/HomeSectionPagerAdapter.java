package doctor.dococean.com.doctorapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import doctor.dococean.com.doctorapp.views.fragments.ConfirmedBookingFragment;
import doctor.dococean.com.doctorapp.views.fragments.ScheduledBookingFragment;

/**
 * Created by nagendrasrivastava on 12/09/16.
 */
public class HomeSectionPagerAdapter extends FragmentStatePagerAdapter {


    public HomeSectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ConfirmedBookingFragment.newInstance();
            case 1:
                return ScheduledBookingFragment.newInstance();
            default:
                return ConfirmedBookingFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Confirmed";
            case 1:
                return "Scheduled";
        }
        return super.getPageTitle(position);
    }
}
