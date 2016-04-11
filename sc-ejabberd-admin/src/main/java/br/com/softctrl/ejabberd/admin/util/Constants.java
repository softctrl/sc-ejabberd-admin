package br.com.softctrl.ejabberd.admin.util;

/*
The MIT License (MIT)

Copyright (c) 2016 Carlos Timoshenko Rodrigues Lopes
http://www.0x09.com.br

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * Base on https://docs.ejabberd.im/admin/api/
 * 
 * @author carlostimoshenkorodrigueslopes@gmail.com
 */
public class Constants {

    /**
     * 
     * @author carlostimoshenkorodrigueslopes@gmail.com
     *
     */
    public static final class Api {

        public static final String ADD_ROSTERITEM = "add_rosteritem";

        public static final String BACKUP = "backup";
        
        public static final String BAN_ACCOUNT = "ban_account";
        
        public static final String CHANGE_PASSWORD = "change_password";
        
        public static final String CHANGE_ROOM_OPTION = "change_room_option";
        
        public static final String CHECK_ACCOUNT = "check_account";

        public static final String CHECK_PASSWORD = "check_password";

        public static final String CHECK_PASSWORD_HASH = "check_password_hash";

        public static final String COMPILE = "compile";

        public static final String CONNECTED_USERS = "connected_users";

        public static final String CONNECTED_USERS_INFO = "connected_users_info";

        public static final String CONNECTED_USERS_NUMBER = "connected_users_number";

        public static final String CONNECTED_USERS_VHOST = "connected_users_vhost";

        public static final String CONVERT_TO_SCRAM = "convert_to_scram";

        public static final String CREATE_ROOM = "create_room";

        public static final String CREATE_ROOMS_FILE = "create_rooms_file";

        public static final String DELETE_EXPIRED_MESSAGES = "delete_expired_messages";

        public static final String DELETE_OLD_MAM_MESSAGES = "delete_old_mam_messages";

        public static final String DELETE_OLD_MESSAGES = "delete_old_messages";

        public static final String DELETE_OLD_USERS = "delete_old_users";

        public static final String DELETE_OLD_USERS_VHOST = "delete_old_users_vhost";

        public static final String DELETE_ROSTER_ITEM = "delete_rosteritem";

        public static final String DESTROY_ROOM = "destroy_room";

        public static final String DESTROY_ROOMS_FILE = "destroy_rooms_file";

        public static final String DUMP = "dump";

        public static final String DUMP_TABLE = "dump_table";

        public static final String ECHO_INTEGER = "echo_integer";

        public static final String ECHO_INTEGER_LIST_STRING = "echo_integer_list_string";

        public static final String ECHO_INTEGER_STRING = "echo_integer_string";
        
        public static final String ECHO_ISATILS = "echo_isatils";

        public static final String ECHO_LIST_INTEGER = "echo_list_integer";

        public static final String ECHO_LIST_STRING = "echo_list_string";

        public static final String ECHO_STRING = "echo_string";

        public static final String EXPORT_TO_ODBC = "export2odbc";

        public static final String EXPORT_ODBC = "export_odbc";

        public static final String EXPORT_PIEFXIS = "export_piefxis";

        public static final String EXPORT_PIEFXIS_HOST = "export_piefxis_host";

        public static final String GEN_HTML_DOC_FOR_COMMANDS = "gen_html_doc_for_commands";

        public static final String GEN_MARKDOWN_DOC_FOR_COMMANDS = "gen_markdown_doc_for_commands";

        public static final String GET_COOKIE = "get_cookie";

        public static final String GET_LAST = "get_last";

        public static final String GET_LOG_LEVEL = "get_loglevel";

        public static final String GET_OFFLINE_COUNT = "get_offline_count";

        public static final String GET_ROOM_AFFILIATIONS = "get_room_affiliations";

        public static final String GET_ROOM_OCCUPANTS = "get_room_occupants";

        public static final String GET_ROOM_OCCUPANTS_NUMBER = "get_room_occupants_number";

        public static final String GET_ROOM_OPTIONS = "get_room_options";

        public static final String GET_ROSTER = "get_roster";

        public static final String GET_USER_ROOMS = "get_user_rooms";

        public static final String GET_VCARD = "get_vcard";

        public static final String GET_VCARD2 = "get_vcard2";

        public static final String GET_VCARD2_MULTI = "get_vcard2_multi";

        public static final String IMPORT_DIR = "import_dir";

        public static final String IMPORT_FILE = "import_file";

        public static final String IMPORT_PIEFXIS = "import_piefxis";

        public static final String IMPORT_PROSODY = "import_prosody";

        public static final String INCOMING_S2S_NUMBER = "incoming_s2s_number";

        public static final String INSTALL_FALLBACK = "install_fallback";

        public static final String JOIN_CLUSTER = "join_cluster";

        public static final String KICK_SESSION = "kick_session";

        public static final String KICK_USER = "kick_session";

        public static final String LEAVE_CLUSTER = "leave_cluster";

        public static final String LIST_CLUSTER = "list_cluster";

        public static final String LOAD = "load";

        public static final String MNESIA_CHANGE_NODENAME = "mnesia_change_nodename";

        public static final String MODULE_CHECK = "module_check";

        public static final String MODULE_INSTALL = "module_install";

        public static final String MODULE_UNINSTALL = "module_uninstall";

        public static final String MODULE_UPGRADE = "module_upgrade";

        public static final String MODULES_AVAILABLE = "modules_available";

        public static final String MODULES_INSTALLED = "modules_installed";

        public static final String MODULES_UPDATE_SPECS = "modules_update_specs";

        public static final String MUC_ONLINE_ROOMS = "muc_online_rooms";

        public static final String MUC_UNREGISTER_NICK = "muc_unregister_nick";

        public static final String NUM_ACTIVE_USERS = "num_active_users";

        public static final String NUM_RESOURCES = "num_resources";

        public static final String OUTGOING_S2S_NUMBER = "outgoing_s2s_number";

        public static final String POW = "pow";

        public static final String PRIVACY_SET = "privacy_set";

        public static final String PRIVATE_GET = "private_get";

        public static final String PRIVATE_SET = "private_set";

        public static final String PROCESS_ROSTERITEMS = "process_rosteritems";        
        

    }

}
