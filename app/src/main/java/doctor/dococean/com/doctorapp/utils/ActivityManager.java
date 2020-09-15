package doctor.dococean.com.doctorapp.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import doctor.dococean.com.doctorapp.views.activities.AboutUsActivity;
import doctor.dococean.com.doctorapp.views.activities.AcceptBookingActivity;
import doctor.dococean.com.doctorapp.views.activities.BookingHistoryActivity;
import doctor.dococean.com.doctorapp.views.activities.BookingHistoryPatientDetailsActivity;
import doctor.dococean.com.doctorapp.views.activities.ConfirmedPatientDetailsActivity;
import doctor.dococean.com.doctorapp.views.activities.ForgotPasswordActivity;
import doctor.dococean.com.doctorapp.views.activities.HomeActivity;
import doctor.dococean.com.doctorapp.views.activities.LoginActivity;
import doctor.dococean.com.doctorapp.views.activities.PaymentActivity;
import doctor.dococean.com.doctorapp.views.activities.ProfileActivity;
import doctor.dococean.com.doctorapp.views.activities.ReferFriendActivity;
import doctor.dococean.com.doctorapp.views.activities.ScheduledPatientDetailsActivity;
import doctor.dococean.com.doctorapp.views.activities.SignupActivity;
import doctor.dococean.com.doctorapp.views.activities.TermsAndConditionsActivity;

/**
 * Created by nagendrasrivastava on 10/07/16.
 */
public class ActivityManager {

    public static void startHomeActivity(Context context, Bundle extras) {
        Intent homeIntent = new Intent(context, HomeActivity.class);
        if (extras != null) {
            homeIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, homeIntent);
    }


    public static void startLoginActivity(Context context) {
        Intent loginIntent = new Intent(context, LoginActivity.class);
        startActivity(context, loginIntent);
    }


    public static void startForgotPasswordActivity(Context context, Bundle extras) {
        Intent forgotpasswordIntent = new Intent(context, ForgotPasswordActivity.class);
        if (extras != null) {
            forgotpasswordIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, forgotpasswordIntent);
    }


    public static void startSignupActivity(Context context, Bundle extras) {
        Intent signupActivityIntent = new Intent(context, SignupActivity.class);
        if (extras != null) {
            signupActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, signupActivityIntent);
    }


    public static void startSchedulePatinetDetailsActivity(Context context, Bundle extras) {
        Intent scheduleActivityIntent = new Intent(context, ScheduledPatientDetailsActivity.class);
        if (extras != null) {
            scheduleActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, scheduleActivityIntent);
    }

    public static void startConfirmPatinetDetailsActivity(Context context, Bundle extras) {
        Intent confirmActivityIntent = new Intent(context, ConfirmedPatientDetailsActivity.class);
        if (extras != null) {
            confirmActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, confirmActivityIntent);
    }


    public static void startBookingHistoryDetailsActivity(Context context, Bundle extras) {
        Intent confirmActivityIntent = new Intent(context, BookingHistoryPatientDetailsActivity.class);
        if (extras != null) {
            confirmActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, confirmActivityIntent);
    }


    public static void startProfileActivity(Context context, Bundle extras) {
        Intent profileActivityIntent = new Intent(context, ProfileActivity.class);
        if (extras != null) {
            profileActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, profileActivityIntent);
    }

    public static void startPaymentActivity(Context context, Bundle extras) {
        Intent profileActivityIntent = new Intent(context, PaymentActivity.class);
        if (extras != null) {
            profileActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, profileActivityIntent);
    }


    public static void startReferFriendActivity(Context context, Bundle extras) {
        Intent profileActivityIntent = new Intent(context, ReferFriendActivity.class);
        if (extras != null) {
            profileActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, profileActivityIntent);
    }

    public static void startAboutUsActivity(Context context, Bundle extras) {
        Intent aboutUsActivityIntent = new Intent(context, AboutUsActivity.class);
        if (extras != null) {
            aboutUsActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, aboutUsActivityIntent);
    }


    public static void startTermsAndConditionsActivity(Context context, Bundle extras) {
        Intent aboutUsActivityIntent = new Intent(context, TermsAndConditionsActivity.class);
        if (extras != null) {
            aboutUsActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, aboutUsActivityIntent);
    }

    public static void startBookingHistoryActivity(Context context, Bundle extras) {
        Intent bookingHistoryActivityIntent = new Intent(context, BookingHistoryActivity.class);
        if (extras != null) {
            bookingHistoryActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, bookingHistoryActivityIntent);
    }


    private static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }


    public static void startOnDemandAcceptBookingActivity(Context context, Bundle extras) {
        Intent acceptActvityIntent = new Intent(context, AcceptBookingActivity.class);
        if (extras != null) {
            acceptActvityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivityWithNewTask(context, acceptActvityIntent);
    }

    public static void startHomeActivityFromBackground(Context context, Bundle extras) {
        Intent homeIntent = new Intent(context, HomeActivity.class);
        if (extras != null) {
            homeIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, homeIntent);
        startActivityWithNewTask(context, homeIntent);
    }

    private static void startActivityWithNewTask(Context context, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
