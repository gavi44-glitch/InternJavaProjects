package com.example.demo.utils;

public class StaticParameter {
    public final static String LDAP_PROPERTIES = "ldap.properties";
    public static final String CONFIG_FILE = "resourcesmsg.properties";
    public static final String PATH_DELIMITER = System.getProperty("file.separator");
    public static final String CONFIG_PATH = System.getProperty("catalina.base") + PATH_DELIMITER + "conf"
            + PATH_DELIMITER;
    public static final String URI_CLEARCACHE = "http://localhost:8082/DBAdapter/commonRest/refreshCache";

    public static final String ERROR_CODE = "ERROR_CODE";
    public static final String DESCRIPTION = "DESCRIPTION";

    public static final String USER_KEY = "USER_KEY";
    public static final String ID = "ID";
    public static final String MENU_KEY = "MENU_KEY";
    public static final String PARENT_MENU_KEY = "PARENT_MENU_KEY";
    public static final String USER_NAME = "USERNAME_KEY";
    public static final String FULL_NAME = "FULLNAME_KEY";
    public static final String ROLE_ID = "ROLE_ID";
    public static final String ROLE_NM = "ROLE_NM";

    public static final String VALUE_FOUND = "FOUND";
    public static final String VALUE_NOTFOUND = "NOT FOUND";

    public static final String FORWARD_SUCCESS = "success";
    public static final String FORWARD_FAILED = "failed";
    public static final String FORWARD_INPUT = "input";

    // command - command SP
    public static final String COMMAND_LOGIN = "Login";
    public static final String COMMAND_ADD = "ADD";
    public static final String COMMAND_ACTIVATE = "ACTIVATE";
    public static final String COMMAND_LOGOFF = "Logoff";
    public static final String COMMAND_UPDATE = "UPDATE";
    public static final String COMMAND_DELETE = "DELETE";
    public static final String COMMAND_CHANGEPASS = "CHANGEPASSWORD";
    public static final String COMMAND_TIMEOUTSESSION = "TIMEOUTSESSION";

    // Return value for hashmap
    public static final String RV_STATUS = "STATUS";
    public static final String RV_MESSAGE = "MESSAGE";

    public static final String FAILED_MESSAGE = "Failed";
    public static final String SUCCESS_CODE = "00";
    public static final String FAILED_CODE = "01";
    public static final String EXIST_CODE = "88";
    public static final String INACTIVE_CODE = "77";
    public static final String STILLLOGIN_CODE = "76";

    public static final String RESPONSE_CODE_SUCCESS = "00";
    public static final int RSP_CODE_SUCCESS = 0;
    public static final String RSP_DESC_SUCCESS = "Success";
    public static final String RSP_DESC_FAILED = "Failed";
    public static final String RESPONSE_CODE_INVALID_CONTACT = "01";
    public static final String RESPONSE_CODE_INVALID_TEMPLATE = "02";
    public static final String RESPONSE_CODE_DELIVERY_FAILED = "03";
    public static final String RESPONSE_CODE_GLOBAL_ERROR = "99";
    public static final String RESPONSE_DESC_GLOBAL_ERROR = "Server currently unable to process, please contact administrator";

    public static final String WORKFLOW_STATUS_WAITING_APPROVAL = "Waiting Approval";
    public static final String WORKFLOW_STATUS_APPROVED = "Approved";
    public static final String WORKFLOW_STATUS_REJECTED = "Rejected";
    public static final String WORKFLOW_STATUS_CANCELED = "Cancel";
    public static final String STATUS_CANCEL = "Cancel";
    public static final String STATUS_ACTIVE = "Active";


    public static final String PENDING_TASK_ACTION_INFO_ADD = "Add New";
    public static final String PENDING_TASK_ACTION_INFO_UPDATE = "Edit";
    public static final String PENDING_TASK_ACTION_INFO_DELETE = "Delete";
    public static final String PENDING_TASK_ACTION_INFO_CANCEL = "Cancel";

    public static final String SERVICE_NAME_DATASOUCE = "Data Source Maintanance";
    public static final String SERVICE_NAME_SERVICES = "Services Maintanance";

    public static final String PENDING_TASK_ACTION_URL_CONTEXT_ROOT = "/boadapter";
    public static final String PENDING_TASK_ACTION_URL_DATASOURCE = "/dataSourceDetailApproval";
    public static final String PENDING_TASK_ACTION_URL_SERVICES = "/serviceDetailApproval";

    public static final String TYPE_PROCESS = "QUERY";
    public static final String PENDING_TASK_CODE ="PENDING_TASK_CODE";

    public static final String ACTION_CREATE = "Create";
    public static final String ACTION_EDIT = "Edit";
    public static final String ACTION_DELETE = "Delete";
    public static final String ACTION_APPROVE = "Approve";
    public static final String ACTION_REJECT = "Reject";

    public static final String MENU_SERVICES = "SERVICES";
    public static final String MENU_APPROVAL = "APPROVAL";
    public static final String CLEAR_CACHE = "CLEAR CACHE";
    public static final String MENU_LOGIN = "LOGIN";

    /* Property Host */
    public static final String HOST_NAME = "HOST_NAME";
    public static final String IP_ADDRESS = "IP_ADDRESS";
}
