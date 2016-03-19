package movile.hackathon.team_bot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class GeneralHandler extends TelegramLongPollingBot {
	private static String TOKEN = "198070718:AAG8BwYcnFF6_6MabfGpGXcHNo80IwVhvJs";
	private static String USERNAME = "testbot";
	
	public void onUpdateReceived(Update update) {
		System.out.println(update.getMessage().getFrom().getUserName() + ": " + update.getMessage().getText());
		SendMessage sendMessage = new SendMessage();
		sendMessage.setText("huehuehue br? " + update.getMessage().getText());
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}

	public String getBotUsername() {
		return USERNAME;
	}

	public String getBotToken() {
		return TOKEN;
	}

}
