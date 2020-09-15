package doctor.dococean.com.doctorapp.utils;

import android.Manifest;
import android.hardware.camera2.params.StreamConfigurationMap;

/**
 * Created by nagendrasrivastava on 09/07/16.
 */
public class DocOceanConstants {

    public static final String USER_TYPE = "EXPERT";
    public static final int DEFAULT_ZOOM_VALUE = 15;
    public static final int DEFAULT_NOTIFICATION_ID = -1;
    public static final String SCHEDULED = "scheduled";

    public class IntentConstants {
        public static final String EXTRAS = "extras";
        public static final String FRAGMENT_NAME = "fragment_name";
        public static final String LOCATION = "location";
        public static final String PARCELABLE_DATA = "parcelable_data";
        public static final String SELECTED_ADDRESS = "selected_address";
        public static final String SELECTED_ADDRESS_ID = "selected_address_id";
        public static final String SIGN_UP_PIPELINE = "signup_pipeline";
        public static final String BOOKING_ID = "booking_id";
        public static final String NOTIFICATION_ID = "not_id";
        public static final String SCHEDULED_TAB = "scheduled_tab";
        public static final String SERVICE_TYPE = "service_type";
    }

    public class TagConstants {
        public static final String DOCTOR_TAG = "doctor";
        public static final String NURSE_TAG = "nurse";
        public static final String VACCINATION_TAG = "vaccine";
        public static final String PATHOLOGIST_TAG = "pathology";
    }

    public class  CrashlyticsConstants{
        public static final int SUCCESS = 3;
        public static final int FAILURE = 4;
        public static final int PERMISSION_DENIED = 5;

    }

    public interface REQUIRED_PERMISSION {
        String[] PERMISSION = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
        };
    }

    public interface RUNTIME_PERMISSION {
        int PERMISSION = 101;
    }

    public static final String PICK_UP = "pick_up";
    public static final String DROP = "drop";
    public static final String HOME_ADDRESS = "Set Home Address";
    public static final String WORK_ADDRESS = "Set Work Address";

    public static final String OTHER_PATIENT = "others";

    public class AppointmentStatus {
        public static final String CONFIRMED = "confirmed";
        public static final String SCHEDULED = "scheduled";
        public static final String COMPLETED = "completed";

    }

    public class SignupPipeLine {
        public static final String USER_INFO = "user_info";
        public static final String ADDRESS = "address";
        public static final String AVAILABILITY = "availability";
        public static final String COMPLETE = "complete";
    }

    public class SelectServicePlaceConstants {
        public static final String PATIENT_PLACE = "Patient place";
        public static final String EXPERT_PLACE = "Expert Place";
        public static final String BOTH = "Both";
    }


    public static class AppointmentConfirmationState {
        public static final String CONFIRM = "confirm";
        public static final String CANCEL = "cancel";
        public static final String EN_ROUTE = "en_route";
        public static final String COMPLETE = "complete";
        public static final String REJECT = "reject";
    }


    public static class ServiceType {
        public static final String ON_DEMAND = "on_demand";
        public static final String SCHEDULED = "scheduled";
    }

    /**
     * These constants are declared to compare the data received from server
     */
    public static class ServicePlace {
        public static final String PATIENT_PLACE = "PATIENT_PLACE";
        public static final String EXPERT_PLACE = "EXPERT_PLACE";
        public static final String ALL = "ALL";
    }
}


