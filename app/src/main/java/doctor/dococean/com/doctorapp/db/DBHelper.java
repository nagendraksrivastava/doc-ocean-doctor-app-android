package doctor.dococean.com.doctorapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import doctor.dococean.com.doctorapp.utils.DocOceanConstants;
import doctor.dococean.com.doctorapp.utils.DocOceanLogger;

/**
 * Created by nagendrasrivastava on 30/06/16.
 */
public class DBHelper {

    private static final String TAG = DBHelper.class.getSimpleName();
    private static final String[] PROJECTION = new String[]{Tables.ExpertUser.AUTH_KEY};


    /**
     * It will insert data into Account table of DB
     */
    public static void saveToExpertUserTable(Context context, String userName, String userEmail,
                                             String contactNumber, String authKey, String userImageUrl) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.ExpertUser.USER_NAME, userName);
        cv.put(Tables.ExpertUser.USER_EMAIL, userEmail);
        cv.put(Tables.ExpertUser.CONTACT_NO, contactNumber);
        cv.put(Tables.ExpertUser.AUTH_KEY, authKey);
        cv.put(Tables.ExpertUser.USER_IMAGE_URL, userImageUrl);
        context.getContentResolver().insert(Tables.ExpertUser.CONTENT_URI, cv);
        DocOceanLogger.d(TAG, " data inserted into Normal User table ");
    }


    /**
     * It will insert data into Account table of DB
     */
    public static void insertToProfessionTable(Context context,
                                               int profId,
                                               String professionName) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.Profession.PROFFESION_ID, profId);
        cv.put(Tables.Profession.PROFESSION_NAME, professionName);
        context.getContentResolver().insert(Tables.Profession.CONTENT_URI, cv);
    }


    /**
     * It will insert data into Account table of DB
     */
    public static void insertToBookingTable(Context context,
                                            int notificationId,
                                            String bookingId,
                                            String bookingData) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.Booking.NOTIFICATION_ID, notificationId);
        cv.put(Tables.Booking.BOOKING_ID, bookingId);
        cv.put(Tables.Booking.BOOKING_DATA, bookingData);
        context.getContentResolver().insert(Tables.Booking.CONTENT_URI, cv);
    }


    public static void saveToRegistry(Context context, String key, boolean value) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.Registry.KEY, key);
        cv.put(Tables.Registry.VALUE, value);
        context.getContentResolver().insert(Tables.Registry.CONTENT_URI, cv);
    }


    public static void clearCompleteDB(Context context) {
        context.getContentResolver().delete(Tables.ExpertUser.CONTENT_URI, null, null);
        context.getContentResolver().delete(Tables.Registry.CONTENT_URI, null, null);
        context.getContentResolver().delete(Tables.Profession.CONTENT_URI, null, null);
    }

    public static void clearSingleAppointmentTable(Context context) {
        context.getContentResolver().delete(Tables.Booking.CONTENT_URI, null, null);
    }


    public static String getUserAuthKey(Context context) {
        String authKey = null;
        try {
            Cursor cursor = context.getContentResolver().query(Tables.ExpertUser.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        authKey = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return authKey;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return authKey;
        } catch (NullPointerException nullEx) {
            nullEx.printStackTrace();
            return authKey;
        }
    }


    public static String getUserName(Context context) {
        String userName = null;
        String[] PROJECTION = new String[]{Tables.ExpertUser.USER_NAME};
        try {
            Cursor cursor = context.getContentResolver().query(Tables.ExpertUser.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        userName = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return userName;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return userName;
        } catch (NullPointerException nullEx) {
            nullEx.printStackTrace();
            return userName;
        }
    }


    public static String getUserEmail(Context context) {
        String userEmail = null;
        String[] PROJECTION = new String[]{Tables.ExpertUser.USER_EMAIL};
        try {
            Cursor cursor = context.getContentResolver().query(Tables.ExpertUser.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        userEmail = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return userEmail;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return userEmail;
        } catch (NullPointerException nullEx) {
            nullEx.printStackTrace();
            return userEmail;
        }
    }

    public static String getUserImageUrl(Context context) {
        String userImageUrl = null;
        String[] PROJECTION = new String[]{Tables.ExpertUser.USER_IMAGE_URL};
        try {
            Cursor cursor = context.getContentResolver().query(Tables.ExpertUser.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        userImageUrl = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return userImageUrl;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return userImageUrl;
        } catch (NullPointerException nullEx) {
            nullEx.printStackTrace();
            return userImageUrl;
        }
    }


    public static String getBookingData(Context context) {
        String[] PROJECTION = new String[]{Tables.Booking.BOOKING_DATA};
        Cursor cursor = context.getContentResolver().query(Tables.Booking.CONTENT_URI, PROJECTION, null, null, null);
        String bookingDetails = null;
        try {
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        bookingDetails = cursor.getString(0);
                    }
                }
                cursor.close();
            }
            return bookingDetails;
        } catch (IndexOutOfBoundsException ex) {
            cursor.close();
            ex.printStackTrace();
            return bookingDetails;
        } catch (NullPointerException nullEx) {
            cursor.close();
            nullEx.printStackTrace();
            return bookingDetails;
        }
    }

    public static int getNotificationId(Context context, String bookingId) {
        String[] PROJECTION = new String[]{Tables.Booking.NOTIFICATION_ID};
        Cursor cursor = context.getContentResolver().query(Tables.Booking.CONTENT_URI, PROJECTION, null, null, null);
        int notificationId = DocOceanConstants.DEFAULT_NOTIFICATION_ID;
        try {
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        notificationId = cursor.getInt(0);
                    }
                }
                cursor.close();
            }
            return notificationId;
        } catch (IndexOutOfBoundsException ex) {
            cursor.close();
            ex.printStackTrace();
            return notificationId;
        } catch (NullPointerException nullEx) {
            cursor.close();
            nullEx.printStackTrace();
            return notificationId;
        }
    }


}
