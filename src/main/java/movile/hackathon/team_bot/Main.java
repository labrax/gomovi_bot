package movile.hackathon.team_bot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

public class Main {

	public static void main(String[] args) {
		TelegramBotsApi botsapi = new TelegramBotsApi();
		try {
			/*botsapi.registerBot(new ConsumerHandler());
			botsapi.registerBot(new ProducerHandler());*/
			botsapi.registerBot(new GeneralHandler());
		}
		catch(TelegramApiException e) {
			e.printStackTrace();
		}

	}

}
