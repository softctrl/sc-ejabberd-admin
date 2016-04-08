package br.com.softctrl.ejabberd.admin.xmlrpc;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import br.com.softctrl.ejabberd.admin.util.Constants;
import br.com.softctrl.ejabberd.admin.xmlrpc.SCParamBuilder.ISCAuthProcess;
import br.com.softctrl.utils.Objects;
import br.com.softctrl.utils.json.GsonUtils;

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
public class SCXmlRpcEjabberd extends XmlRpcClient {

    private final ISCAuthProcess mProcess;

    /**
     * 
     * @param url
     */
    public SCXmlRpcEjabberd(URL url) {
        this(null, null, null, url);
    }
    
    /**
     * 
     * @param user
     * @param host
     * @param password
     * @param url
     */
    public SCXmlRpcEjabberd(final String user, final String host, final String password, URL url) {
        final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(url);
        this.setConfig(config);
        if (Objects.nonNullOrEmpty(user) 
                && Objects.nonNullOrEmpty(user)
                && Objects.nonNullOrEmpty(user)) {
            mProcess = new ISCAuthProcess() {
                @Override
                public List<Object> auth(Hashtable<String, Object> params) {
                    List<Object> result = new ArrayList<Object>();
                    Hashtable<String, Object> auth = new Hashtable<String, Object>();
                    auth.put("user", user);
                    auth.put("server", host);
                    auth.put("password", password);
                    result.add(auth);
                    result.add(params);
                    return result;
                }
            };
        } else {
            mProcess = new ISCAuthProcess() {
                @Override
                public List<Object> auth(Hashtable<String, Object> params) {
                    List<Object> result = new ArrayList<Object>();
                    result.add(params);
                    return result;
                }
            };
        }
    }
    
    /**
     * 
     * @param methodName
     * @param paramBuilder
     * @return
     * @throws XmlRpcException
     */
    protected String execute(String methodName, SCParamBuilder paramBuilder) throws XmlRpcException {
        return GsonUtils.toJson(execute(methodName, paramBuilder.getParams()));
    }

    /**
     * Add an item to a user’s roster (supports ODBC).
     * 
     * @param localuser
     * @param localserver
     * @param user
     * @param server
     * @param nick
     * @param group
     * @param subs
     * @return {res,rescode}
     * @throws XmlRpcException
     */
    public String addRosterItem(String localuser, String localserver, String user, String server, String nick,
            String group, String subs) throws XmlRpcException {
        return execute(Constants.Api.ADD_ROSTERITEM,
                new SCParamBuilder().addParam("localuser", localuser)
                .addParam("localserver", localserver)
                .addParam("user", user)
                .addParam("server", server)
                .addParam("nick", nick)
                .addParam("group", group)
                .addParam("subs", subs));
    }
    
    /**
     * Store the database to backup file.
     * 
     * @param file
     * @return 
     * @throws XmlRpcException 
     */
    public String backup(String file) throws XmlRpcException{
        return execute(Constants.Api.BACKUP, new SCParamBuilder().addParam("file", file));
    }
    
    /**
     * Ban an account: kick sessions and set random password.
     * 
     * @param user User name to ban.
     * @param host Server name.
     * @param reason Reason for banning user.
     * @return {res,rescode} - Status code: 0 on success, 1 otherwise.
     * @throws XmlRpcException 
     */
    public String banAccount(String user, String host, String reason) throws XmlRpcException {
        return execute(Constants.Api.BAN_ACCOUNT, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host)
                .addParam("reason", reason));        
    }
    
    /**
     * Change the password of an account.
     * 
     * @param user User name.
     * @param host Server name.
     * @param newPass New password for user.
     * @return {res,rescode} Status code: 0 on success, 1 otherwise.
     * @throws XmlRpcException
     */
    public String changePassword(String user, String host, String newpass) throws XmlRpcException {
        return execute(Constants.Api.CHANGE_PASSWORD, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host)
                .addParam("newpass", newpass));        
    }
    
    /**
     * Change an option in a MUC room.
     * 
     * @param name
     * @param service
     * @param option
     * @param value
     * @return
     * @throws XmlRpcException
     */
    public String changeRoomOption(String name, String service, String option, String value) throws XmlRpcException {
        return execute(Constants.Api.CHANGE_ROOM_OPTION, new SCParamBuilder()
                .addParam("name", name)
                .addParam("service", service)
                .addParam("option", option)
                .addParam("value", value));  
    }
    
    /**
     * Check if an account exists or not.
     * 
     * @param name User name to check.
     * @param host Server to check
     * @return {res,rescode} Status code: 0 on success, 1 otherwise.
     * @throws XmlRpcException
     */
    public String checkAccount(String user, String host) throws XmlRpcException {
        return execute(Constants.Api.CHECK_ACCOUNT, new SCParamBuilder(this.mProcess)
                .addParam("user", user)
                .addParam("host", host));  
    }
    
    /**
     * Check if a password is correct.
     * 
     * @param user User name to check.
     * @param host Server to check.
     * @param password Password to check.
     * @return {res,rescode} Status code: 0 on success, 1 otherwise.
     * @throws XmlRpcException
     */
    public String checkPassword(String user, String host, String password) throws XmlRpcException {
        return execute(Constants.Api.CHECK_PASSWORD, new SCParamBuilder(this.mProcess)
                .addParam("user", user)
                .addParam("host", host)
                .addParam("password", password));  
    }
    
    /**
     * Check if the password hash is correct
     * Allowed hash methods: md5, sha.
     * 
     * @param user User name to check.
     * @param host Server to check.
     * @param passwordhash Password’s hash value. 
     * @param hashmethod Name of hash method.
     * @return {res,rescode} Status code: 0 on success, 1 otherwise.
     * @throws XmlRpcException
     */
    public String checkPasswordHash(String user, String host, String passwordhash, String hashmethod) throws XmlRpcException {
        return execute(Constants.Api.CHECK_PASSWORD_HASH, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host)
                .addParam("passwordhash", passwordhash)
                .addParam("hashmethod", hashmethod));  
    }
    
    /**
     * Recompile and reload Erlang source code file.
     * 
     * @param file Filename of erlang source file to compile.
     * @return {res,rescode} Status code: 0 on success, 1 otherwise.
     * @throws XmlRpcException
     */
    public String compile(String file) throws XmlRpcException {
        return execute(Constants.Api.COMPILE, new SCParamBuilder()
                .addParam("file", file));  
    }
    
    /**
     * List all established sessions.
     * 
     * @return {connected_users,{list,{sessions,string}}}.
     * @throws XmlRpcException
     */
    public String connectedUsers() throws XmlRpcException {
        return execute(Constants.Api.CONNECTED_USERS,
                new SCParamBuilder(this.mProcess));
    }
    
    /**
     * List all established sessions and their information.
     * 
     * @return {connected_users_info, {list, {sessions, {tuple, [{jid,string},
     *         {connection,string}, {ip,string}, {port,integer},
     *         {priority,integer}, {node,string}, {uptime,integer}]}}}}.
     * @throws XmlRpcException
     */
    public String connectedUsersInfo() throws XmlRpcException {
        return execute(Constants.Api.CONNECTED_USERS_INFO, new SCParamBuilder(this.mProcess));  
    }
    
    /**
     * Get the number of established sessions.
     * 
     * @return {num_sessions,integer}.
     * @throws XmlRpcException
     */
    public String connectedUsersNumber() throws XmlRpcException {
        return execute(Constants.Api.CONNECTED_USERS_NUMBER, new SCParamBuilder(this.mProcess));  
    }
    
    /**
     * Get the list of established sessions in a vhost.
     * 
     * @param vhost
     * @return {connected_users_vhost,{list,{sessions,string}}}.
     * @throws XmlRpcException
     */
    public String connectedUsersVhost(String vhost) throws XmlRpcException {
        return execute(Constants.Api.CONNECTED_USERS_VHOST, new SCParamBuilder(this.mProcess)
                .addParam("host", vhost));  
    }

    /**
     * Convert the passwords in ‘users’ ODBC table to SCRAM.
     * 
     * @param host
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String convertToScram(String host) throws XmlRpcException {
        return execute(Constants.Api.CONVERT_TO_SCRAM, new SCParamBuilder().addParam("host", host));  
    }
    
    /**
     * Convert the input file from Erlang to YAML format.
     * 
     * @param in
     * @param out
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String convertToYaml(String in, String out) throws XmlRpcException {
        return execute(Constants.Api.CONVERT_TO_SCRAM, new SCParamBuilder().addParam("in", in).addParam("out", out));
    }
    
    /**
     * Create a MUC room name@service in host.
     * 
     * @param name
     * @param service
     * @param host
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String createRoom(String name, String service, String host) throws XmlRpcException {
        return execute(Constants.Api.CREATE_ROOM, new SCParamBuilder()
                .addParam("name", name)
                .addParam("service", service)
                .addParam("host", host));
    }
    
    /**
     * Create the rooms indicated in file.
     * Provide one room JID per line. Rooms will be created after restart.
     * 
     * @param file
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String createRoomsFile(String file) throws XmlRpcException {
        return execute(Constants.Api.CREATE_ROOMS_FILE, new SCParamBuilder()
                .addParam("file", file));
    }
    
    /**
     * Delete expired offline messages from database.
     * 
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String deleteExpiredMessages() throws XmlRpcException {
        return execute(Constants.Api.DELETE_EXPIRED_MESSAGES, new SCParamBuilder());
    }

    /**
     * Delete MAM messages older than DAYS
     * 
     * @param type Valid message TYPEs: “chat”, “groupchat”, “all”.
     * @param days number of days.
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String deleteOldMamMessages(String type, Integer days) throws XmlRpcException {
        return execute(Constants.Api.DELETE_OLD_MAM_MESSAGES, new SCParamBuilder()
                .addParam("type", type)
                .addParam("days", days));
    }
    
    /**
     * Delete offline messages older than DAYS.
     * 
     * @param days
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String deleteOldMessages(Integer days) throws XmlRpcException {
        return execute(Constants.Api.DELETE_OLD_MESSAGES, new SCParamBuilder()
                .addParam("days", days));
    }
    
    /**
     * Delete users that didn’t log in last days, or that never logged.
     * 
     * @param days Last login age in days of accounts that should be removed.
     * @return {res,restuple} Result tuple.
     * @throws XmlRpcException
     */
    public String deleteOldUsers(Integer days) throws XmlRpcException {
        return execute(Constants.Api.DELETE_OLD_USERS, new SCParamBuilder()
                .addParam("days", days));
    }
    
    /**
     * Delete users that didn’t log in last days in vhost, or that never logged.
     * 
     * @param host Server name.
     * @param days Last login age in days of accounts that should be removed.
     * @return {res,restuple} Result tuple.
     * @throws XmlRpcException
     */
    public String deleteOldUsersVhost(String host, Integer days) throws XmlRpcException {
        return execute(Constants.Api.DELETE_OLD_USERS_VHOST, new SCParamBuilder()
                .addParam("host", host)
                .addParam("days", days));
    }
    
    /**
     * Delete an item from a user’s roster (supports ODBC).
     * 
     * @param localuser
     * @param localserver
     * @param user
     * @param server
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String deleteRosterItem(String localuser, String localserver, String user, String server) throws XmlRpcException {
        return execute(Constants.Api.DELETE_ROSTER_ITEM, new SCParamBuilder()
                .addParam("localuser", localuser)
                .addParam("localserver", localserver)
                .addParam("user", user)
                .addParam("server", server));
    }
    
    /**
     * Destroy a MUC room.
     * 
     * @param name
     * @param service
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String destroyRoom(String name, String service) throws XmlRpcException {
        return execute(Constants.Api.DESTROY_ROOM, new SCParamBuilder()
                .addParam("name", name)
                .addParam("service", service));
    }
    
    /**
     * Destroy the rooms indicated in file
     * Provide one room JID per line.
     * 
     * @param file
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String destroyRoomsFile(String file) throws XmlRpcException {
        return execute(Constants.Api.DESTROY_ROOMS_FILE, new SCParamBuilder()
                .addParam("file", file));
    }
    
    /**
     * Dump the database to text file.
     * 
     * @param file
     * @return {res,restuple}.
     * @throws XmlRpcException
     */
    public String dump(String file) throws XmlRpcException {
        return execute(Constants.Api.DUMP, new SCParamBuilder()
                .addParam("file", file));
    }
    
    /**
     * Dump a table to text file.
     * 
     * @param file
     * @param table
     * @return {res,restuple}.
     * @throws XmlRpcException
     */
    public String dumpTable(String file, String table) throws XmlRpcException {
        return execute(Constants.Api.DUMP_TABLE, new SCParamBuilder()
                .addParam("file", file)
                .addParam("table", table));
    }
    
    // Echo methods ------------------------------------------------------------
    
    /**
     * 
     * @param name
     * @param thislist
     * @return
     */
    private static final <T> Object[] getList(String name, T[] thislist){
        Object[] param = new Object[thislist.length];
        for(int index = 0; index < thislist.length; index++) {
            param[index] = new Object[]{name, thislist[index]};
        }
        return param;
        
    }
    
    /**
     * Echo Integer.
     * 
     * @param thisinteger
     * @return {thatinteger,integer}.
     * @throws XmlRpcException
     */
    public String echoInteger(Integer thisinteger) throws XmlRpcException {
        return execute(Constants.Api.ECHO_INTEGER, new SCParamBuilder()
                .addParam("thisinteger", thisinteger));
    }
    
    /**
     * Echo an integer and List of strings.
     * 
     * @param thisinteger
     * @param thislist
     * @return {thistuple,{tuple,[{thatinteger,integer}, {thatlist,{list,{thatstring,string}}}]}}.
     * @throws XmlRpcException
     */
    public String echoIntegerListString(Integer thisinteger, Object... thislist) throws XmlRpcException {
        return execute(Constants.Api.ECHO_INTEGER_LIST_STRING, new SCParamBuilder()
                .addParam("thisinteger", thisinteger)
                .addParam("thislist", thislist));
    }
    
    /**
     * Echo integer and string, in result as a tuple.
     * 
     * @param thisinteger
     * @param thisstring
     * @return {thistuple,{tuple,[{thisinteger,integer},{thisstring,string}]}}.
     * @throws XmlRpcException
     */
    public String echoIntegerString(Integer thisinteger, String thisstring) throws XmlRpcException {
        return execute(Constants.Api.ECHO_INTEGER_STRING, new SCParamBuilder()
                .addParam("thisinteger", thisinteger));
    }

    /**
     * Echo integer, string, atom and tuple of integer and list of strings.
     * 
     * @param thisinteger
     * @param thisstring
     * @param thisatom
     * @param thistuple
     *            {tuple,[{listlen,integer},
     *                    {thislist,{list,{contentstring, string}}}]
     *            }.
     * @return {results, {tuple, [{thatinteger,integer}, {thatstring,string},
     *         {thatatom,atom}, {thattuple, {tuple, [{listlen,integer},
     *         {thatlist,{list,{contentstring,string}}}]}}]}}.
     * @throws XmlRpcException
     */
    public String echoIsatils(Integer thisinteger, String thisstring, String thisatom, String... thislist)
            throws XmlRpcException {
        Object[][] thistuple = new Object[][]{{"listlen", thislist.length}, {"thislist", getList("contentstring", thislist)}};
        return this.execute(Constants.Api.ECHO_ISATILS, new SCParamBuilder()
                .addParam("thisinteger", thisinteger)
                .addParam("thisstring", thisstring)
                .addParam("thisatom", thisatom)
                .addParam("thistuple", thistuple));
    }

    /**
     * Echo List of integers.
     * 
     * @param thislist {list,{thisinteger,integer}}.
     * @return {thatlist,{list,{thatinteger,integer}}}.
     * @throws XmlRpcException
     */
    public String echoListInteger(Integer[] thislist)
            throws XmlRpcException {
        Object[] thislist2 = getList("thisinteger", thislist);
        return execute(Constants.Api.ECHO_LIST_INTEGER, new SCParamBuilder()
                .addParam("thislist", thislist2));
    }
    
    /**
     * Echo List of strings.
     * 
     * @param thislist {list,{thisstring,string}}.
     * @return {thatlist,{list,{thatstring,string}}}.
     * @throws XmlRpcException
     */
    public String echoListString(String[] thislist)
            throws XmlRpcException {
        Object[] thislist2 = getList("thisstring", thislist);
        return execute(Constants.Api.ECHO_LIST_STRING, new SCParamBuilder()
                .addParam("thislist", thislist2));
    }
    
    /**
     * Echo String.
     * 
     * @param thisstring
     * @return {thatstring,string}.
     * @throws XmlRpcException
     */
    public String echoString(String thisstring) throws XmlRpcException {
        return execute(Constants.Api.ECHO_STRING, new SCParamBuilder()
                .addParam("thisstring", thisstring));
    }
    
    /**
     * Export virtual host information from Mnesia tables to SQL files.
     * 
     * @param host
     * @param directory
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String exportToOdbc(String host, String directory) throws XmlRpcException {
        return execute(Constants.Api.EXPORT_TO_ODBC, new SCParamBuilder()
                .addParam("host", host)
                .addParam("directory", directory));
    }
    
    /**
     * Export all tables as SQL queries to a file.
     * 
     * @param host
     * @param file
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String exportOdbc(String host, String file) throws XmlRpcException {
        return execute(Constants.Api.EXPORT_ODBC, new SCParamBuilder()
                .addParam("host", host)
                .addParam("file", file));
    }
    
    /**
     * Export data of all users in the server to PIEFXIS files (XEP-0227).
     * 
     * @param dir
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String exportPiefxis(String dir) throws XmlRpcException {
        return execute(Constants.Api.EXPORT_PIEFXIS, new SCParamBuilder()
                .addParam("dir", dir));
    }
    
    /**
     * Export data of users in a host to PIEFXIS files (XEP-0227).
     * 
     * @param dir
     * @param host
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String exportPiefxisHost(String dir, String host) throws XmlRpcException {
        return execute(Constants.Api.EXPORT_PIEFXIS_HOST, new SCParamBuilder()
                .addParam("dir", dir)
                .addParam("host", host));
    }
    
    /**
     * Generates html documentation for ejabberd_commands.
     * 
     * @param file
     *            Path to file where generated documentation should be stored.
     * @param regex
     *            Regexp matching names of commands or modules that will be
     *            included inside generated document.
     * @param examples
     *            Comma separated list of languages (choosen from java, perl,
     *            xmlrpc, json)that will have example invocation include in
     *            markdown document.
     * @return {res,rescode} 0 if command failed, 1 when succedded.
     * @throws XmlRpcException
     */
    public String genHtmlDocForCommands(String file, String regex, String examples) throws XmlRpcException {
        return execute(Constants.Api.GEN_HTML_DOC_FOR_COMMANDS, new SCParamBuilder()
                .addParam("file", file)
                .addParam("regex", regex)
                .addParam("examples", examples));
    }
    
    /**
     * Generates markdown documentation for ejabberd_commands.
     * 
     * @param file
     *            Path to file where generated documentation should be stored.
     * @param regex
     *            Regexp matching names of commands or modules that will be
     *            included inside generated document.
     * @param examples
     *            Comma separated list of languages (choosen from java, perl,
     *            xmlrpc, json)that will have example invocation include in
     *            markdown document.
     * @return {res,rescode} 0 if command failed, 1 when succedded.
     * @throws XmlRpcException
     */
    public String genMarkdownDocForCommands(String file, String regex, String examples) throws XmlRpcException {
        return execute(Constants.Api.GEN_MARKDOWN_DOC_FOR_COMMANDS, new SCParamBuilder()
                .addParam("file", file)
                .addParam("regex", regex)
                .addParam("examples", examples));
    }
    
    /**
     * Get the Erlang cookie of this node.
     * 
     * @return {cookie,string} Erlang cookie used for authentication by ejabberd.
     * @throws XmlRpcException
     */
    public String getCookie() throws XmlRpcException {
        return execute(Constants.Api.GET_COOKIE, new SCParamBuilder(this.mProcess));
    }
    
    /**
     * Get last activity information (timestamp and status).
     * 
     * @param user
     * @param host
     * @return {last_activity,string}.
     * @throws XmlRpcException
     */
    public String getLast(String user, String host) throws XmlRpcException {
        return execute(Constants.Api.GET_LAST, new SCParamBuilder(this.mProcess)
                .addParam("user", user)
                .addParam("host", host));
    }
    
    /**
     * Get the current loglevel.
     * 
     * @return {leveltuple,{tuple,[{levelnumber,integer}, {levelatom,atom},
     *         {leveldesc,string}]}}.
     * @throws XmlRpcException
     */
    public String getLogLevel() throws XmlRpcException {
        return execute(Constants.Api.GET_LOG_LEVEL, new SCParamBuilder(this.mProcess));
    }
    
    /**
     * Get the number of unread offline messages.
     * 
     * @return {res,integer}.
     * @throws XmlRpcException
     */
    public String getOfflineCount() throws XmlRpcException {
        return execute(Constants.Api.GET_OFFLINE_COUNT, new SCParamBuilder(this.mProcess));
    }
    
    /**
     * Get the list of affiliations of a MUC room. 
     * 
     * @param name
     * @param service
     * @return {affiliations, {list, {affiliation, {tuple, [{username,string},
     *         {domain,string}, {affiliation,atom}, {reason,string}]}}}}.
     * @throws XmlRpcException
     */
    public String getRoomAffiliations(String name, String service) throws XmlRpcException {
        return execute(Constants.Api.GET_ROOM_AFFILIATIONS, new SCParamBuilder()
                .addParam("name", name)
                .addParam("service", service));
    }
    
    /**
     * Get the list of occupants of a MUC room.
     * @param name
     * @param service
     * @return {occupants,{list,{occupant,{tuple,[{jid,string}, {nick,string}, {role,string}]}}}}.
     * @throws XmlRpcException
     */
    public String getRoomOccupants(String name, String service) throws XmlRpcException {
        return execute(Constants.Api.GET_ROOM_OCCUPANTS, new SCParamBuilder()
                .addParam("name", name)
                .addParam("service", service));
    }
    
    /**
     * Get the number of occupants of a MUC room.
     * @param name
     * @param service
     * @return {occupants,integer}.
     * @throws XmlRpcException
     */
    public String getRoomOccupantsNumber(String name, String service) throws XmlRpcException {
        return execute(Constants.Api.GET_ROOM_OCCUPANTS_NUMBER, new SCParamBuilder()
                .addParam("name", name)
                .addParam("service", service));
    }

    /**
     * Get options from a MUC room.
     * @param name
     * @param service
     * @return {options,{list,{option,{tuple,[{name,string},{value,string}]}}}}.
     * @throws XmlRpcException
     */
    public String getRoomOptions(String name, String service) throws XmlRpcException {
        return execute(Constants.Api.GET_ROOM_OPTIONS, new SCParamBuilder()
                .addParam("name", name)
                .addParam("service", service));
    }
    
    /**
     * Get roster of a local user.
     * 
     * @return {contacts,{list,{contact,{tuple,[{jid,string}, {nick,string},
     *         {subscription,string}, {ask,string}, {group,string}]}}}}.
     * @throws XmlRpcException
     */
    public String getRoster() throws XmlRpcException {
        return execute(Constants.Api.GET_ROSTER, new SCParamBuilder(this.mProcess));
    }
    
    /**
     * Get the list of rooms where this user is occupant.
     * 
     * @param user
     * @param host
     * @return {rooms,{list,{room,string}}}.
     * @throws XmlRpcException
     */
    public String getUserRooms(String user, String host) throws XmlRpcException {
        return execute(Constants.Api.GET_USER_ROOMS, new SCParamBuilder(this.mProcess)
                .addParam("user", user)
                .addParam("host", host));
    }
    
    /**
     * Get content from a vCard field. Some vcard field names in get/set_vcard
     * are: FN - Full Name NICKNAME - Nickname BDAY - Birthday TITLE - Work:
     * Position ROLE - Work: Role Some vcard field names and subnames in
     * get/set_vcard2 are: N FAMILY - Family name N GIVEN - Given name N MIDDLE
     * - Middle name ADR CTRY - Address: Country ADR LOCALITY - Address: City
     * TEL HOME - Telephone: Home TEL CELL - Telephone: Cellphone TEL WORK -
     * Telephone: Work TEL VOICE - Telephone: Voice EMAIL USERID - E-Mail
     * Address ORG ORGNAME - Work: Company ORG ORGUNIT - Work: Department For a
     * full list of vCard fields check XEP-0054: vcard-temp at
     * http://www.xmpp.org/extensions/xep-0054.html
     * 
     * @param user
     * @param host
     * @param name
     * @return {content,string}.
     * @throws XmlRpcException
     */
    public String getVCard(String user, String host, String name) throws XmlRpcException {
        return execute(Constants.Api.GET_VCARD, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host)
                .addParam("name", name));
    }
    
    /**
     * Get content from a vCard field Some vcard field names and subnames in
     * get/set_vcard2 are: N FAMILY - Family name N GIVEN - Given name N MIDDLE
     * - Middle name ADR CTRY - Address: Country ADR LOCALITY - Address: City
     * TEL HOME - Telephone: Home TEL CELL - Telephone: Cellphone TEL WORK -
     * Telephone: Work TEL VOICE - Telephone: Voice EMAIL USERID - E-Mail
     * Address ORG ORGNAME - Work: Company ORG ORGUNIT - Work: Department Some
     * vcard field names in get/set_vcard are: FN - Full Name NICKNAME -
     * Nickname BDAY - Birthday TITLE - Work: Position ROLE - Work: Role For a
     * full list of vCard fields check XEP-0054: vcard-temp at
     * http://www.xmpp.org/extensions/xep-0054.html
     * 
     * @param user
     * @param host
     * @param name
     * @param subname
     * @return {content,string}.
     * @throws XmlRpcException
     */
    public String getVCard2(String user, String host, String name, String subname) throws XmlRpcException {
        return execute(Constants.Api.GET_VCARD2, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host)
                .addParam("name", name)
                .addParam("subname", subname));
    }
    
    /**
     * Get multiple contents from a vCard field Some vcard field names and
     * subnames in get/set_vcard2 are: N FAMILY - Family name N GIVEN - Given
     * name N MIDDLE - Middle name ADR CTRY - Address: Country ADR LOCALITY -
     * Address: City TEL HOME - Telephone: Home TEL CELL - Telephone: Cellphone
     * TEL WORK - Telephone: Work TEL VOICE - Telephone: Voice EMAIL USERID -
     * E-Mail Address ORG ORGNAME - Work: Company ORG ORGUNIT - Work: Department
     * 
     * Some vcard field names in get/set_vcard are: FN - Full Name NICKNAME -
     * Nickname BDAY - Birthday TITLE - Work: Position ROLE - Work: Role For a
     * full list of vCard fields check XEP-0054: vcard-temp at
     * http://www.xmpp.org/extensions/xep-0054.html.
     * 
     * @param user
     * @param host
     * @param name
     * @param subname
     * @return {contents,{list,{value,string}}}.
     * @throws XmlRpcException
     */
    public String getVCard2Multi(String user, String host, String name, String subname) throws XmlRpcException {
        return execute(Constants.Api.GET_VCARD2_MULTI, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host)
                .addParam("name", name)
                .addParam("subname", subname));
    }
    
    /**
     * Import users data from jabberd14 spool dir.
     * 
     * @param file
     * @return {res,restuple}.
     * @throws XmlRpcException
     */
    public String importDir(String file) throws XmlRpcException {
        return execute(Constants.Api.IMPORT_DIR, new SCParamBuilder()
                .addParam("file", file));
    }
    
    /**
     * Import user data from jabberd14 spool file.
     * 
     * @param file
     * @return {res,restuple}.
     * @throws XmlRpcException
     */
    public String importFile(String file) throws XmlRpcException {
        return execute(Constants.Api.IMPORT_FILE, new SCParamBuilder()
                .addParam("file", file));
    }
    
    /**
     * Import users data from a PIEFXIS file (XEP-0227).
     * 
     * @param file
     * @return {res,restuple}.
     * @throws XmlRpcException
     */
    public String importPiefxis(String file) throws XmlRpcException {
        return execute(Constants.Api.IMPORT_PIEFXIS, new SCParamBuilder()
                .addParam("file", file));
    }
    
    /**
     * Import data from Prosody.
     * 
     * @param dir
     * @return {res,restuple}.
     * @throws XmlRpcException
     */
    public String importProsody(String dir) throws XmlRpcException {
        return execute(Constants.Api.IMPORT_PROSODY, new SCParamBuilder()
                .addParam("dir", dir));
    }
    
    /**
     * Number of incoming s2s connections on the node.
     * 
     * @return {s2s_incoming,integer}.
     * @throws XmlRpcException
     */
    public String incomingS2sNumber() throws XmlRpcException {
        return execute(Constants.Api.INCOMING_S2S_NUMBER, new SCParamBuilder());
    }
    
    /**
     * Install the database from a fallback file.
     * 
     * @param file
     * @return {res,restuple}.
     * @throws XmlRpcException
     */
    public String installFallback(String file) throws XmlRpcException {
        return execute(Constants.Api.INSTALL_FALLBACK, new SCParamBuilder()
                .addParam("file", file));
    }
    
    /**
     * Join this node into the cluster handled by Node.
     * 
     * @param node
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String joinCluster(String node) throws XmlRpcException {
        return execute(Constants.Api.JOIN_CLUSTER, new SCParamBuilder()
                .addParam("node", node));
    }
    
    /**
     * Kick a user session.
     * 
     * @param user User name.
     * @param host Server name.
     * @param resource User’s resource.
     * @param reason Reason for closing session.
     * @return {res,rescode} Status code: 0 on success, 1 otherwise.
     * @throws XmlRpcException
     */
    public String kickSession(String user, String host, String resource, String reason) throws XmlRpcException {
        return execute(Constants.Api.KICK_SESSION, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host)
                .addParam("resource", resource)
                .addParam("reason", reason));
    }
    
    /**
     * Disconnect user’s active sessions.
     * 
     * @param user
     * @param host
     * @return {num_resources,integer}.
     * @throws XmlRpcException
     */
    public String kickUser(String user, String host) throws XmlRpcException {
        return execute(Constants.Api.KICK_USER, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host));
    }
    
    /**
     * Remove node handled by Node from the cluster.
     * 
     * @param node
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String leaveCluster(String node) throws XmlRpcException {
        return execute(Constants.Api.LEAVE_CLUSTER, new SCParamBuilder()
                .addParam("node", node));
    }
    
    /**
     * List nodes that are part of the cluster handled by Node.
     *  
     * @return {nodes,{list,{node,atom}}}.
     * @throws XmlRpcException
     */
    public String listCluster() throws XmlRpcException {
        return execute(Constants.Api.LIST_CLUSTER, new SCParamBuilder());
    }
    
    /**
     * Restore the database from text file.
     * 
     * @param file
     * @return {res,restuple}.
     * @throws XmlRpcException
     */
    public String load(String file) throws XmlRpcException {
        return execute(Constants.Api.LOAD, new SCParamBuilder()
                .addParam("file", file));
    }
    
    /**
     * Change the erlang node name in a backup file.
     * 
     * @param oldnodename
     * @param newnodename
     * @param oldbackup
     * @param newbackup
     * @return {res,restuple}.
     * @throws XmlRpcException
     */
    public String mnesiaChangeNodename(String oldnodename, String newnodename,
            String oldbackup, String newbackup) throws XmlRpcException {
        return execute(Constants.Api.MNESIA_CHANGE_NODENAME, new SCParamBuilder()
                .addParam("oldnodename", oldnodename)
                .addParam("newnodename", newnodename)
                .addParam("oldbackup", oldbackup)
                .addParam("newbackup", newbackup));
    }
    
    /**
     * ??
     * 
     * @param module
     * @return {res,integer}.
     * @throws XmlRpcException
     */
    public String moduleCheck(String module) throws XmlRpcException {
        return execute(Constants.Api.MODULE_CHECK, new SCParamBuilder()
                .addParam("module", module));
    }
    
    /**
     * ??
     * 
     * @param module
     * @return {res,integer}.
     * @throws XmlRpcException
     */
    public String moduleInstall(String module) throws XmlRpcException {
        return execute(Constants.Api.MODULE_INSTALL, new SCParamBuilder()
                .addParam("module", module));
    }
    
    /**
     * ??
     * 
     * @param module
     * @return {res,integer}.
     * @throws XmlRpcException
     */
    public String moduleUninstall(String module) throws XmlRpcException {
        return execute(Constants.Api.MODULE_UNINSTALL, new SCParamBuilder()
                .addParam("module", module));
    }
    
    /**
     * ??
     * 
     * @param module
     * @return {res,integer}.
     * @throws XmlRpcException
     */
    public String moduleUpgrade(String module) throws XmlRpcException {
        return execute(Constants.Api.MODULE_UPGRADE, new SCParamBuilder()
                .addParam("module", module));
    }
    
    /**
     * ??
     * 
     * @return {modules,{list,{module,{tuple,[{name,atom},{summary,string}]}}}}.
     * @throws XmlRpcException
     */
    public String modulesAvailable() throws XmlRpcException {
        return execute(Constants.Api.MODULES_AVAILABLE, new SCParamBuilder(this.mProcess));
    }
    
    /**
     * ??
     * 
     * @return {modules,{list,{module,{tuple,[{name,atom},{summary,string}]}}}}.
     * @throws XmlRpcException
     */
    public String modulesInstalled() throws XmlRpcException {
        return execute(Constants.Api.MODULES_INSTALLED, new SCParamBuilder(this.mProcess));
    }
    
    /**
     * ??
     * 
     * @return {res,integer}.
     * @throws XmlRpcException
     */
    public String modulesUpdate_specs() throws XmlRpcException {
        return execute(Constants.Api.MODULES_UPDATE_SPECS, new SCParamBuilder(this.mProcess));
    }
    
    /**
     * List existing rooms (‘global’ to get all vhosts).
     * 
     * @param host
     * @return {rooms,{list,{room,string}}}.
     * @throws XmlRpcException
     */
    public String mucOnlineRooms(String host) throws XmlRpcException {
        return execute(Constants.Api.MUC_ONLINE_ROOMS, new SCParamBuilder()
                .addParam("host", host));
    }
    
    /**
     * Unregister the nick in the MUC service.
     * 
     * @param nick
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String mucUnregisterNick(String nick) throws XmlRpcException {
        return execute(Constants.Api.MUC_UNREGISTER_NICK, new SCParamBuilder()
                .addParam("nick", nick));
    }
    
    
    /**
     * Get number of users active in the last days.
     * 
     * @param host Name of host to check.
     * @param days Number of days to calculate sum.
     * @return {users,integer} Number of users active on given server in last n days.
     * @throws XmlRpcException
     */
    public String numActiveUsers(String host, int days) throws XmlRpcException {
        return execute(Constants.Api.NUM_ACTIVE_USERS, new SCParamBuilder()
                .addParam("host", host)
                .addParam("days", days));
    }
    
    /**
     * Get the number of resources of a user.
     * 
     * @param user User name.
     * @param host Server name.
     * @return {resources,integer} Number of active resources for a user.
     * @throws XmlRpcException
     */
    public String numResources(String user, String host) throws XmlRpcException {
        return execute(Constants.Api.NUM_RESOURCES, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host));
    }
    
    /**
     * Number of outgoing s2s connections on the node.
     * 
     * @return {s2s_outgoing,integer}.
     * @throws XmlRpcException
     */
    public String outgoingS2sNumber() throws XmlRpcException {
        return execute(Constants.Api.OUTGOING_S2S_NUMBER, new SCParamBuilder());
    }
    
    /**
     * Return the power of base for exponent
     * This is an example command. The formula is: power = base ^ exponent.
     * 
     * @param base
     * @param expoent
     * @return {power,integer}.
     * @throws XmlRpcException
     */
    public String pow(int base, int expoent) throws XmlRpcException {
        return execute(Constants.Api.POW, new SCParamBuilder()
                .addParam("base", base)
                .addParam("expoent", expoent));
    }
    
    /**
     * Send a IQ set privacy stanza for a local account.
     * 
     * @param user
     * @param host
     * @param xmlquery
     * @return {res,rescode}.
     * @throws XmlRpcException
     */
    public String privacySet(String user, String host, String xmlquery) throws XmlRpcException {
        return execute(Constants.Api.PRIVACY_SET, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host)
                .addParam("xmlquery", xmlquery));
    }
    
    /**
     * Get some information from a user private storage.
     * 
     * @param user
     * @param host
     * @param element
     * @param ns
     * @return {res,string}.
     * @throws XmlRpcException
     */
    public String privateGet(String user, String host, String element, String ns) throws XmlRpcException {
        return execute(Constants.Api.PRIVATE_GET, new SCParamBuilder()
                .addParam("user", user)
                .addParam("host", host)
                .addParam("element", element)
                .addParam("ns", ns));
    }
    
    //TODO: Just a reminder to help me. next private_set

}
