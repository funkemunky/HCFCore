package raton.meme.hcf.ymls;

import java.util.ArrayList;

import com.doctordark.utils.Config;

import raton.meme.hcf.HCF;

public class LangYML {
	
	public static void init(HCF plugin){
		Config config = new Config(HCF.getPlugin(), "lang");
		
		NO_PERM = config.getString("no_permission").replaceAll("&", "§");

		PLAYER_UNFOUND = config.getString("player_unfound").replaceAll("&", "§");

		PLAYER_UNFOUND = config.getString("player_unfound");

		PLAYER_ONLY = config.getString("player_only");

	}


	public static String NO_PERM = "§4You don't have permission to use this.";

	public static String PLAYER_UNFOUND = "§e{0} §4could not be found";

	public static String PLAYER_ONLY = "§cOnly players may use that!";

	public static String NOT_IN_FACTION = "§cYou are not in a faction!";

	/*
	 * {0} is the players name
	 */

	public static String ENABLED = "§aEnabled";

	public static String DISABLED = "§cDisabled";

	public static String ABLE = "§aAble";

	public static String UNABLE = "§cUnable";

	public static String INV_FULL = "§cYou must clear your inventory first!";

	public static String INVALID_NUMBER = "§e{0} is not a number!";

	/*
	 * Focus Messages
	 */

	public static String FOCUS_SUCCESS = "§5Your faction has now focused on {0}";

	public static String FOCUS_COLOR = "§e§l";

	/*
	 * Angle command messages
	 */
	public static String ANGLE_MSG = "§e{0} yaw§f, §e{1} pitch";

	/*
	 * Revive messages
	 * TODO: Make NOT_DEAD more professional eg. add a variable for rank.
	 * TODO: For example '&7(&6*&7) §6{0} &rused their &9{1} &rrank to revive the player &a{2} &rfrom their deathban.'
	 */

	public static String REVIVE_COOLDOWN = "§4You may not revive a player for another §e{0}§4.";

	public static String NOT_DEAD = "§e{0} is not deathbanned";

	public static String REVIVED = "§aYou have revived §e{0}§a!";

	public static String REVIVE_PLAYER = "§cYou revived {0}!";

	public static String REVIVE_BROADCAST = "§c{0} revived {1}!";

	/*
	 * End Portal Command
	 */

	public static String SELECT_END_PORTAL = "§cYou must select an end portal frame!";

	public static String LOCATION_SUCCESS = "§aSet location {0}";

	public static String FIRST_LOCATION_UNSET = "§cYou must set the first location!";

	public static String PORTAL_TOO_BIG = "§cYou cannot create a portal that big!";
	// public static String PORTAL_BIGGER_THAN_SUBBOTTEDS_DICK = "§cYou cannot create a portal over 12 inches!"

	public static String PORTAL_ELEVATION = "§cMake sure the portal is on the same elevation!";

	public static String PORTAL_SUCCESS = "§aCreated portal.";

	public static String PORTAL_WAND_GIVE = "§aSelect 2 points to make an end portal";

	/*
	 * Gopple Command
	 */

	public static String GAPPLE_COOLDOWN_NOT_ACTIVE = "§4You do not have a Gopple cooldown active.";

	public static String ACTIVE_COOLDOWN = "§eYour Gopple cooldown expires in {0}";

	/*
	 * Help Menu
	 */

	public static ArrayList<String> helpLines = new ArrayList<String>();

	public static ArrayList<String> getHelpLines(){
		helpLines.add("§aDefine /help in the configuration file!");	// Not sure how this will work but why not try it
		return helpLines;
	}


	/*
	 * Location Command
	 */

	public static String USER_LOCATION = "§e{0} §ais in the area of {1} at coordinates: {2}, {3}, {4}";

	/*
	 * Logout Command
	 */

	public static String LOGOUT_ACTIVE = "§aYour logout timer is already active!";

	public static String LOGOUT_START = "§aYour logout timer is now active!";

	/*
	 * PVPTimer Command
	 */

	public static String IMMUNITY_NOT_ACTIVE = "§cYour {0} timer is currently not active.";

	public static String IMMUNITY_DEACTIVATED = "§cYour {0} timer has now been deactivated!";

	public static String IMMUNITY_ACTIVE = "§aYour {0} timer is still active for another {1}";

	/*
	 * Regen Command
	 */

	public static String REGEN_NOT_IN_FACTION = LangYML.NOT_IN_FACTION;

	public static String FULL_DTR = "§aYour faction has full DTR!";

	public static String FROZEN_DTR = "§aYour faction is on DTR freeze for another {0}";

	public static String REGENERATING_DTR = "§aYour faction currently has {0} DTR and is regenerating at a rate of {1} every {2}. Estimated time until full DTR is {3}.";

	/*
	 * Server Time Command
	 */

	public static String SERVER_TIME = "§aThe server time is {0}";

	/*
	 * Spawn Cannon Command
	 */

	public static String CANNON_ONLY_OVERWORLD = "§aYou can only"; //TODO

	/*
	 * Toggle Capzone
	 */

	public static String TOGGLE_CAPZONE_MESSAGE = "§eYou are now {0} to see capzone entry messages";

	/*
	 * Found Diamonds
	 */

	public static String TOGGLED_FD_MSG = "§e{0} §ahas toggled §bFound Diamond Alerts";

	/*
	 * Toggle Lightning Strike
	 */

	public static String TOGGLE_LIGHTSTRIKE = "§eYou will now be {0} to see lightning strikes on a players death.";

	/*
	 * Scoreboard Toggle
	 */

	public static String TOGGLED_SB = "§eYou have now {0} your scoreboard.";

	/*
	 * Lives & Deathban
	 * (Note: This took fucking two years)
	 */

	public static String NO_LIVES_DB = "§eThere are no lives in the database!";

	public static String EOTW_DB_JOIN = "§cYou are deathbanned for the rest of the map due to EOTW.";

	public static String DB_TIMER = "§cYou are still deathbanned for {0}: §e{1}";

	public static String LIFE_USED = "§aYou have used a life! You now have {0} lives.";

	public static String NO_LIVES = "§cYou have no life!";

	public static String DB_W_LIVES = "§cYou are still deathbanned with {0} remaining! You can use a life by joining within {1}";

	public static String DB_BYPASS = "§aYou would be deathban, but you have permission to bypass.";

	public static String LIFE_CHECK = "§e{0} currently has {1} lives";

	public static String NOT_DB = "§e{0} is not deathbanned";

	@Deprecated
	public static String DEATHBAN_REASON = "Insert Array here"; //TODO ArrayList

	public static String DB_LOCATION = "§eLocation: ({0}, {1}, {2})";

	public static String DB_REASON = "§eReason: [{0}]";

	public static String CLEAR_DB_MSG = "§eAll deathbans have been reset!";

	public static String LIVES_MUST_BE_POSITIVE = "§cThe amount of lives must be positive";

	public static String LIVES_GIFT_FAIL = "§cYou could not give {0} {1} live(s) because you only have {2}.";

	public static String LIVES_GIFT_SUCCESS = "§aYou gifted {0} with {1} live(s)";

	public static String LIVES_GIFT_SUCCESS_PLAYER = "§eYou gifted {0} with {1} live(s)";

	public static String LIVES_REVIVE_KITMAP = "§eYou cannot revive players during kitmap!";

	public static String LIVES_REVIVE_EOTW = "§eYou cannot revive players during kitmap!";

	public static String LIFE_USED_ON_OTHER = "§eYou have used a life to revive {0}";

	public static String LIVE_REVIVE_CONSOLE = "§eYou revived {0}";

	public static String LIVES_SET = "§e{0} now has {1} lives.";

	public static String DB_DURATION_ERROR = "§cInvalid Duration! Use format §o1h 2m";

	public static String DB_DURATION_SET = "§eDeathban time has been set to {0}!";

	public static String TOP_LIVES_TITLE = "§eTop {0} lives:";

	public static String TOP_LIVES_FORMAT = "§e{0} {1}: {2}"; //END OF DB

	/*
	 * Economy Messages
	 */

	public static String ECO_GIVE_ALL = "§e{0} gave all players ${1}";

	public static String ECO_BAL = "§eYour balance: {0}";

	public static String ECO_BAL_OTHER = "§e{0}'s balance: {1}";

	public static String ECO_ADDED = "§eAdded {0} to {1}'s balance. Their new balance is {2}";

	public static String ECO_TAKEN = "§eTaken {0} from {1}'s balance. Their new balance is {2}";

	public static String ECO_SET = "§eSet balance of {0} to {1}"; //this shit takes ages tho

	public static String ECO_NOT_POSITIVE = "§cYou must send money in positive amounts.";

	public static String ECO_SEND_FAIL = "§cYou tried to pay {0}, but you only have {1} in your account.";

	public static String ECO_SENT = "§aYou have sent {0} to {1}";

	public static String ECO_SENT_PLAYER = "§a{0} was a generous young man and sent you {1}!"; //xD

	/*
	 * Factions Messages
	 */

	public static String NOT_PLAYER_FAC = "§cThat faction could not be found or is not a player faction.";

	/*
	 * Events Messages
	 */

	public static String NO_EVENTS = "§cThere are no running events!";

	public static String EVENTS_CANCELLED = "§c{0} has cancelled the event {1}";

	public static String EVENT_CREATE_NAME_USED = "§cThere is already a event faction named {0}";

	public static String EVENT_CREATED = "§aCreated event faction {0} with event type {1}!";

	public static String EVENT_DELETE_NAME_UNUSED = "§cThere is no such event faction called {0}";

	public static String EVENT_DELETE_SUCCESS = "§aDeleted event faction {0}!";

	public static String EVENT_RENAME_SUCCESS = "§aRenamed event {0} to {1}";

	public static String NO_WORLDEDIT = "§cYou need WorldEdit installed to use this feature!";

	public static String WE_DL = "§cYou can download WorldEdit at §ehttps://dev.bukkit.org/projects/worldedit";

	public static String REQ_WE_SEL = "§cPlease make a WorldEdit selection!";

	public static String AREA_SMALL = "§cRegion selection must be {0} x {0}";

	public static String EVENT_AREA_SUCCESS = "§aUpdated the claim area for event {0}";

	public static String EVENT_STARTED = "§aStarted the event!";

	public static String EVENT_UPTIME = "§aEvent §e({0}) §ahas been live for §e{1}§a and started at §e{2}";

	/*
	 * Conquest Messages
	 */

	public static String CONQ_SETPOINTS_ERR = "§cMaximum points for Conquest is {0}";

	public static String CONQ_SETPOINTS_SUC = "§aSet points for {0} tp {1}";

	/*
	 * Crate Messages
	 */

	public static String INVALID_KEY_TYPE = "§cNo key type named {0}";

	public static String KEY_ONLY_POSITIVE = "§cYou can only give keys in positive quantities";

	public static String KEY_MAX_ALLOWED = "§cYou cannot give keys in quantities more than {0} at a time.";

	public static String KEY_SUCCESS = "§cGiven {0}x {1} key to {2}";

	public static String CRATE_CLOSED_DURING = "§ayou closed your crate reward inventory. Your items have been dropped!";
	
	//TODO: To be continued in later releases/when locale is finished.
}
