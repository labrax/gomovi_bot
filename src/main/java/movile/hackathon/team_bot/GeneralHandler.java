package movile.hackathon.team_bot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class GeneralHandler extends TelegramLongPollingBot {
	// private static String TOKEN =
	// "198070718:AAG8BwYcnFF6_6MabfGpGXcHNo80IwVhvJs";
	// private static String USERNAME = "testbot";
	// danguilherme_bot:
	private static String TOKEN = "177468357:AAHnVizr1jRjhAQJnvEIbK94AS1yP_O9hvw";
	private static String USERNAME = "danguilherme_bot";

	private DatabaseConnMock db;

	private DatabaseConnMock getDb() {
		return DatabaseConnMock.getInstance();
	}

	public void onUpdateReceived(Update update) {
		db = getDb();

		System.out.println(update.getMessage().getFrom().getUserName() + ": " + update.getMessage().getText());
		SendMessage sendMessage = new SendMessage();
		sendMessage.setText("huehuehue br? " + update.getMessage().getText());
		sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
		try {
			sendMessage(sendMessage);

			interactMessage(update.getMessage());
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

	public void interactMessage(Message message) { // db
		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();

		if (message.getText() == "/cancelar")
			db.setState(user, chatId, "INICIAL");

		String state = db.getState(user, chatId);
		String substate = db.getSubState(user, chatId);

		// switch (substate) {
		// case "CATEGORIA":
		// db.setOptionsSelected(message.getFrom().getId(), message.getChatId(),
		// db.getOptionsSelected(message.getFrom().getId(), message.getChatId())
		// + "#" + message.getText());
		// break;
		// case "SUB-CATEGORIA":
		// db.setOptionsSelected(message.getFrom().getId(), message.getChatId(),
		// db.getOptionsSelected(message.getFrom().getId(), message.getChatId())
		// + "#" + message.getText());
		// break;
		// case "SUMARIO":
		// db.setOptionsSelected(message.getFrom().getId(), message.getChatId(),
		// db.getOptionsSelected(message.getFrom().getId(), message.getChatId())
		// + "#" + message.getText());
		// break;
		// case "DESCRICAO":
		// db.setOptionsSelected(message.getFrom().getId(), message.getChatId(),
		// db.getOptionsSelected(message.getFrom().getId(), message.getChatId())
		// + "#" + message.getText());
		// break;
		// case "LOCALIZACAO":
		// db.setOptionsSelected(message.getFrom().getId(), message.getChatId(),
		// db.getOptionsSelected(message.getFrom().getId(), message.getChatId())
		// + "#"
		// + message.getLocation());
		// break;
		// default:
		// break;
		// }

		if (state == null)
			state = "INICIAL";
		String command = getCommand(message);

		switch (state) {
		case "INICIAL":
			if (command.equals("/buscar"))
				processaBusca(message);
			break;
		case "BUSCAR":
			processaBusca(message);
		case "CADASTRAR":
			processaCadastrar(message);
			break;
		}
	}

	private String getCommand(Message message) {
		return message.getText().split(" ")[0];
	}

	private String getCommandArguments(Message message) {
		String text = message.getText();
		int index = text.indexOf(" ");
		if (index == -1)
			return "";
		text = text.substring(index, text.length()).trim();
		return text;
	}

	void processaCadastrar(Message message) {
		// switch (substate) {
		// case "CATEGORIA":
		// newSubState = "SUB-CATEGORIA";
		// break;
		// case "SUB-CATEGORIA":
		// newSubState = "SUMARIO";
		// break;
		// case "SUMARIO":
		// newSubState = "DESCRICAO";
		// break;
		// case "DESCRICAO":
		// newSubState = "LOCALIZACAO";
		// break;
		// default:
		// case "LOCALIZACAO":
		// newState = "INICIAL";
		// newSubState = "";
		// // TODO: ACABOU DE CADASTRAR, RETORNAR CONFIRMAÇÃO
		// break;
		// }
	}

	private String processaBusca(Message message) {
		int user = message.getFrom().getId();
		long chatId = message.getChatId();
		String state = db.getState(user, chatId);

		String text = getCommandArguments(message);
		if (!"".equals(text)) {
			// TODO: fazer busca pelo `text`
		} else {
			switch (state) {
			case "INICIAL":
				db.setSubState(user, chatId, "CATEGORIA");
				return "Qual categoria?";
			default:
				break;
			}
		}

		return "[default]";
	}

	private void sendMessage(long chatId, String message) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setText(message);
		sendMessage.setChatId(String.valueOf(chatId));

		try {
			sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
