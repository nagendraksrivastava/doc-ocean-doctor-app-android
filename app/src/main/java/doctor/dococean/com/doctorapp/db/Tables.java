package doctor.dococean.com.doctorapp.db;

import android.net.Uri;

/**
 * Created by nagendrasrivastava on 21/10/15.
 */
class Tables {

    private static final String AUTHORITY = "doctor.dococean.com.doctorapp";
    private static final String _ID = "_id";


    /**
     * Table name and column name declaration for expert user table
     * Content URi declaration for table
     */
    static final class ExpertUser {
        static final String TABLE_NAME = "user";
        static final String USER_NAME = "user_name";
        static final String USER_EMAIL = "email";
        static final String CONTACT_NO = "contact_no";
        static final String AUTH_KEY = "auth";
        static final String USER_IMAGE_URL = "usr_img_url";


        /**
         * Table which will be used to store key value pair, this table is replacement for shared preferences,
         */
        static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider.PARAMETER_NOTIFY +
                "=true");

        /**
         * URL which will notify if any changes happened into the database
         */
        public static final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" + AUTHORITY +
                "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider
                        .PARAMETER_NOTIFY +
                "=false");
    }


    static final class Profession {
        static final String TABLE_NAME = "profession";
        static final String PROFFESION_ID = "prof_id";
        static final String PROFESSION_NAME = "prof_name";


        /**
         * Table which will be used to store key value pair, this table is replacement for shared preferences,
         */
        static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider.PARAMETER_NOTIFY +
                "=true");

        /**
         * URL which will notify if any changes happened into the database
         */
        public static final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" + AUTHORITY +
                "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider
                        .PARAMETER_NOTIFY +
                "=false");
    }


    static final class Booking {
        static final String TABLE_NAME = "booking";
        static final String NOTIFICATION_ID = "noti_id";
        static final String BOOKING_ID = "booking_id";
        static final String BOOKING_DATA = "booking_data";


        /**
         * Table which will be used to store key value pair, this table is replacement for shared preferences,
         */
        static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider.PARAMETER_NOTIFY +
                "=true");

        /**
         * URL which will notify if any changes happened into the database
         */
        public static final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" + AUTHORITY +
                "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider
                        .PARAMETER_NOTIFY +
                "=false");
    }


    /**
     * Table which will be used to store key value pair, this table is replacement for shared preferences,
     */
    static final class Registry {
        static final String TABLENAME = "registry";
        static final String KEY = "key";
        static final String VALUE = "value";

        /**
         * URL which will notify if any changes happened into the database
         */
        static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
                TABLENAME +
                "?" +
                DocOceanContentProvider.PARAMETER_NOTIFY +
                "=true");

        /**
         * URl which can be used to communicate with the database but it will not notify for database changes automatically
         */
        public static final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" + AUTHORITY +
                "/" +
                TABLENAME +
                "?" +
                DocOceanContentProvider
                        .PARAMETER_NOTIFY +
                "=false");

    }


    static final String CREATE_REGISTRY_TABLE =
            " CREATE TABLE " + Registry.TABLENAME
                    + " ( "
                    + Registry.KEY
                    + " TEXT  PRIMARY KEY , "
                    + Registry.VALUE
                    + " TEXT );";


    static final String CREATE_NORMAL_USER_TABLE =
            " CREATE TABLE " + ExpertUser.TABLE_NAME
                    + " ( "
                    + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ExpertUser.USER_NAME
                    + " TEXT, "
                    + ExpertUser.USER_EMAIL
                    + " TEXT, "
                    + ExpertUser.CONTACT_NO
                    + " INTEGER, "
                    + ExpertUser.AUTH_KEY
                    + " TEXT UNIQUE, "
                    + ExpertUser.USER_IMAGE_URL
                    + " TEXT);";


    static final String CREATE_PROFESSION_TABLE =
            " CREATE TABLE " + Profession.TABLE_NAME
                    + " ( "
                    + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Profession.PROFFESION_ID
                    + " INTEGER, "
                    + Profession.PROFESSION_NAME
                    + " TEXT UNIQUE );";


    static final String CREATE_BOOKING_TABLE =
            " CREATE TABLE " + Booking.TABLE_NAME
                    + " ( "
                    + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Booking.NOTIFICATION_ID
                    + " INTEGER UNIQUE, "
                    + Booking.BOOKING_ID
                    + " TEXT UNIQUE, "
                    + Booking.BOOKING_DATA
                    + " TEXT );";


}
