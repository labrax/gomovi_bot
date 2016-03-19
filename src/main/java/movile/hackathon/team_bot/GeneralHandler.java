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
		String retorno = interactMessage(update.getMessage());
		System.out.println(retorno);

		SendMessage sendMessage = new SendMessage();
		// sendMessage.setText("huehuehue br? " +
		// update.getMessage().getText());
		sendMessage.setText(retorno);
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

	public String interactMessage(Message message) {
		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();

		if (message.getText() == "/cancelar")
			db.setState(user, chatId, "INICIAL");

		String state = db.getState(user, chatId);
		String substate = db.getSubState(user, chatId);

		switch (substate) {
		case "TEXTO":
			db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getText());
			break;
		case "CATEGORIA":
			db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getText());
			break;
		case "SUB-CATEGORIA":
			db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getText());
			break;
		case "SUMARIO":
			db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getText());
			break;
		case "DESCRICAO":
			db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getText());
			break;
		case "LOCALIZACAO":
			db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getLocation());
			break;
		default:
			break;
		}

		switch (state) {
		case "CADASTRAR":
			return processaCadastrar(message);
		case "BUSCAR":
			return processaBuscar(message);
		case "LISTAR":
			break;
		case "DELETAR":
			break;
		case "HISTORICO":
			break;
		case "AVALIAR":
			break;
		default:
		case "INICIAL":
			return processaInicial(message);
		}
		return "";
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
			}
		}

		return "";
	}

	String processaCadastrar(Message message) {
		return "";
	}

	String processaBuscar(Message message) {
		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();

		String substate = db.getInstance().getSubState(user, chatId);
		String new_substate = "";
		switch (substate) {
		case "CATEGORIA":
			new_substate = "SUB-CATEGORIA";
			break;
		case "SUB-CATEGORIA":
			new_substate = "LOCALIZACAO";
			break;
		case "LOCALIZACAO":
			String data = db.getInstance().getOptionsSelected(user, chatId);
			String[] splitted = data.split("#");
			System.out.println(splitted);
			return db.getInstance().getResultadosBusca(splitted[0], splitted[1], Float.parseFloat(splitted[2]),
					Float.parseFloat(splitted[3]));
		default:
			break;
		}

		return "";
	}

	String processaInicial(Message message) {
		String newState = "INICIAL";
		String newSubState = "";
		String return_message = "O quê deseja fazer?\n" + "/cadastrar cadastrar um serviço\n"
				+ "/buscar buscar um serviço\n" + "/listar listar os seus serviços oferecidos\n"
				+ "/deletar [id] delete um serviço seu oferecido\n"
				+ "/historico ver seu histórico de serviços selecionados\n"
				+ "/avaliar [usuario] [nota] avalie um usuário por um serviço oferecido\n"
				+ "/cancelar cancele um procedimento atual";

		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();

		String[] splitted = message.getText().split(" ");
		switch (splitted[0]) {
		case "/cadastrar":
			newState = "CADASTRAR";
			newSubState = "CATEGORIA";
			return_message = "Insira a categoria do serviço que deseja cadastrar: ";
			break;
		case "/buscar":
			newState = "BUSCAR";
			newSubState = "CATEGORIA";
			return_message = "Insira a categoria do serviço que deseja buscar: ";
			break;
		case "/listar":
			newState = "LISTAR";
			return_message = "Estes são os serviços que você tem: " + db.getServicosUsuario(user);
			break;
		case "/deletar":
			newState = "DELETAR";
			if (splitted.length > 1 && db.getInstance().deletarServico(user, Integer.parseInt(splitted[1])))
				return_message = "Serviço deletado!";
			else
				return_message = "Serviço com id " + splitted[1] + " não deletado. Você inseriu algo errado?\n"
						+ return_message;
			break;
		case "/historico":
			newState = "HISTORICO";
			if (splitted.length > 1)
				return_message = "Este é o seu histórico:\n" + db.getHistoricoUsuario(user);
			break;
		case "/avaliar":
			newState = "AVALIAR";
			if (splitted.length > 3
					&& db.getInstance().avaliar(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2])))
				return_message = "Obrigado por avaliar!";
			else
				return_message = "Ocorreu algum problema ao avaliar :(";
			break;
		default:
			newSubState = "";
			break;
		}

		db.getInstance().setState(user, chatId, newState);
		db.getInstance().setSubState(user, chatId, newSubState);
		db.getInstance().setOptionsSelected(user, chatId, "");

		return return_message;
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
