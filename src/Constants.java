public class Constants {
	
	class BotConfig {
		public static final String TOKEN = ""; //GET YOURS FROM @BotFather
		public static final String USERNAME = "rudebergBot";
	}
	
	class Replies {
		public static final String START = "Why don't you add me to any group to take care of?";
		public static final String WHAT = "Uhm.. I don't get it, %s.";
		public static final String KICKOUT = "%s, honey, I'm kicking you out for using offensive language \ud83d\ude14";
		public static final String WHYADMIN = "Why the hell I'm not able to kick admins? \ud83d\ude21";
		public static final String MAKEMEADMIN = "Okay, I need to be an administrator now.";
		public static final String WELCOME = "Welcome to the group. Don't use offensive words, otherwise I will kick you out. \ud83d\ude21";
	}
	
	class Commands {
		public static final String commandInitChar = "/";
		public static final String startCommand = commandInitChar + "start";
		
	}
	
	static class Data {
		final static String[] VULGAR = {"fuck", "asshole", "cocksucker", "nigga"};
	}
}
