package movile.hackathon.team_bot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class GeneralHandler extends TelegramLongPollingBot {
	private static String TOKEN = "198070718:AAG8BwYcnFF6_6MabfGpGXcHNo80IwVhvJs";
	private static String USERNAME = "testbot";
	
	private DatabaseConn db;
	
	public void onUpdateReceived(Update update) {
		db = DatabaseConn.getInstance();
		
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

	public void interactMessage(Message message) {
		String CURR_STATE = db.getState(message.getFrom().getId(), message.getChatId()); //pega do db
		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();
		
		if(message.getText() == "/cancelar")
			db.setState(user, chatId, "INICIAL");
		
		String state = db.getState(user, chatId);
		String substate = db.getSubState(user, chatId);
		
		switch(substate) {
			case "CATEGORIA":
				db.setOptionsSelected(message.getFrom().getId(), message.getChatId(), db.getOptionsSelected(message.getFrom().getId(), message.getChatId()) + "#" + message.getText() );
				break;
			case "SUB-CATEGORIA":
				db.setOptionsSelected(message.getFrom().getId(), message.getChatId(), db.getOptionsSelected(message.getFrom().getId(), message.getChatId()) + "#" + message.getText() );
				break;
			case "SUMARIO":
				db.setOptionsSelected(message.getFrom().getId(), message.getChatId(), db.getOptionsSelected(message.getFrom().getId(), message.getChatId()) + "#" + message.getText() );
				break;
			case "DESCRICAO":
				db.setOptionsSelected(message.getFrom().getId(), message.getChatId(), db.getOptionsSelected(message.getFrom().getId(), message.getChatId()) + "#" + message.getText() );
				break;
			case "LOCALIZACAO":
				db.setOptionsSelected(message.getFrom().getId(), message.getChatId(), db.getOptionsSelected(message.getFrom().getId(), message.getChatId()) + "#" + message.getLocation() );
				break;
			default:
				break;
		}
		
		String newState = state;
		String newSubState = substate;
		switch(state) {
			case "CADASTRAR":
				processaCadastrar(message);
				break;
			case "INICIAL":
				processaBusca(message);
				break;
		}
	}
	
	void processaCadastrar(Message message) {
		switch(substate) {
			case "CATEGORIA":
				newSubState = "SUB-CATEGORIA";
				break;
			case "SUB-CATEGORIA":
				newSubState = "SUMARIO";
				break;
			case "SUMARIO":
				newSubState = "DESCRICAO";
				break;
			case "DESCRICAO":
				newSubState = "LOCALIZACAO";
				break;
			default:
			case "LOCALIZACAO":
				newState = "INICIAL";
				newSubState = "";
				//TODO: ACABOU DE CADASTRAR, RETORNAR CONFIRMAÇÃO
				break;
		}
	}
	
	void processaBusca(Message message) {
		switch(message.getText().split(" ")[0]) {
			case "/cadastrar":
				break;
			case "/buscar":
				break;
			case "/listar":
				break;
			case "/deletar":
				break;
			case "/historico":
				break;
			case "/avaliar":
				break;
			default:
				newSubState = "";
				break;
		}
	}
}
