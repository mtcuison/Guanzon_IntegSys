package org.rmj.g3appdriver.utils;

public interface WebApi {

    /**Main URL
     * WEB SERVER*/
    String URL_MAIN = "https://restgk.guanzongroup.com.ph/";

    /**GCARD MANAGEMENT*/
    String URL_GCARD = "gcard/";

    /**ACCOUNT SECURITY*/
    String URL_SECURITY = "security/";

    /**MS MANAGEMENT*/
    String URL_MS = "ms/";

    /**WEB API
     * FOR CHECKING APP VERSION
     * THIS API IS APPLICABLE FOR ALL APP
     * INTEGSYS, GUANZON_APP, TELECOM*/
    String URL_CHECK_APP_VERSION = URL_MAIN + URL_GCARD + URL_MS + "version_checker.php";

    /**FOR ADDING NEW GCARD NUMBER*/
    String URL_ADD_NEW_GCARD = URL_MAIN + URL_GCARD + URL_MS + "add_gcardnumber.php";

    /**FOR IMPORTING PROMO LINKS AND IMAGES*/
    String URL_IMPORT_PROMOLINK = URL_MAIN + URL_GCARD + URL_MS + "import_promo_link.php";

    /**FOR IMPORTING PLACE ORDERS*/
    String URL_IMPORT_PLACE_ORDER = URL_MAIN + URL_GCARD + URL_MS + "import_placed_orders.php";

    /**FOR IMPORTING REDEEMABLES*/
    String URL_IMPORT_REDEEM_ITEMS = URL_MAIN + URL_GCARD + URL_MS + "import_redeem_item.php";

    /**FOR IMPORTING TRANSACTIONS OFFLINE*/
    String URL_IMPORT_TRANSACTIONS_OFFLINE = URL_MAIN + URL_GCARD + URL_MS + "import_trans_offline.php";

    /**FOR IMPORTING TRANSACTIONS ONLINE*/
    String URL_IMPORT_TRANSACTIONS_ONLINE = URL_MAIN + URL_GCARD + URL_MS + "import_trans_online.php";

    /**FOR IMPORTING TRANSACTIONS PRE-ORDER*/
    String URL_IMPORT_TRANSACTIONS_PREORDER = URL_MAIN + URL_GCARD + URL_MS + "import_trans_preorder.php";

    /**FOR IMPORTING TRANSACTIONS REDEMPTION*/
    String URL_IMPORT_TRANSACTIONS_REDEMPTION = URL_MAIN + URL_GCARD + URL_MS + "import_trans_redemption.php";

    /**FOR IMPORTING MC REGISTRATION NOTICE*/
    String URL_IMPORT_MC_REGISTRATION = URL_MAIN + URL_GCARD + URL_MS + "import_registration.php";

    /**FOR REQUESTING MC SERVICE STATUS*/
    String URL_IMPORT_SERVICE = URL_MAIN + URL_GCARD + URL_MS + "import_service.php";

    /**FOR IMPORTING GUANZON MC AND MP BRANCHES*/
    String URL_IMPORT_BRANCH = URL_MAIN + URL_GCARD + URL_MS + "import_branch.php";

    /**FOR IMPORTING USER GCARD*/
    String URL_IMPORT_GCARD = URL_MAIN + URL_GCARD + URL_MS + "import_gcard.php";

    /**FOR REQUESTING AVAILABLE POINTS*/
    String URL_REQUEST_AVAIL_POINTS = URL_MAIN + URL_GCARD + URL_MS + "request_avl_points.php";

    /**FOR PLACING A PRE ORDER TRANSACTION*/
    String URL_PLACE_ODER = URL_MAIN + URL_GCARD + URL_MS + "place_order.php";

    /**FOR CANCELING PLACE ORDER*/
    String URL_CANCEL_ORDER = URL_MAIN + URL_GCARD + URL_MS + "cancel_order_item.php";

    /**FOR ACCOUNT SETTINGS CHANGING PASSWORD*/
    String URL_CHANGE_PASSWORD = URL_MAIN + URL_SECURITY + "/acctupdate.php";

    /**FOR ACCOUNT SETTINGS REQUEST LOGIN DEVICES*/
    String URL_REQUEST_DEVICES = URL_MAIN + URL_SECURITY + "/acctdevice.php";
}
