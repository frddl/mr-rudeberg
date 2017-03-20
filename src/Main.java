
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {
	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi MrRudeberg = new TelegramBotsApi();
		
		try {
			MrRudeberg.registerBot(new Bot());
			System.out.println("Server is up now.");
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
	}
}
