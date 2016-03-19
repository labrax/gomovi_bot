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

	public String interactMessage(Message message) {
		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();
		
		if(message.getText() == "/cancelar")
			db.setState(user, chatId, "INICIAL");
		
		String state = db.getState(user, chatId);
		String substate = db.getSubState(user, chatId);
		
		switch(substate) {
			case "TEXTO":
				db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getText() );
				break;
			case "CATEGORIA":
				db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getText() );
				break;
			case "SUB-CATEGORIA":
				db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getText() );
				break;
			case "SUMARIO":
				db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getText() );
				break;
			case "DESCRICAO":
				db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getText() );
				break;
			case "LOCALIZACAO":
				db.setOptionsSelected(user, chatId, db.getOptionsSelected(user, chatId) + "#" + message.getLocation() );
				break;
			default:
				break;
		}
		
		switch(state) {
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
	
	String processaCadastrar(Message message) {
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
	
	String processaBuscar(Message message) {
		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();
		
		String substate = db.getInstance().getSubState(user, chatId);
		String new_substate = "";
		switch(substate) {
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
				return db.getInstance().getResultadosBusca(splitted[0], splitted[1], Float.parseFloat(splitted[2]), Float.parseFloat(splitted[3]));
			default:
				break;
		}
		
		return "";
	}
	
	String processaInicial(Message message) {
		String newState = "INICIAL";
		String newSubState = "";
		String return_message = "O quê deseja fazer?";
		
		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();
		
		String [] splitted = message.getText().split(" ");
		switch(splitted[0]) {
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
				if(splitted.length > 1 && db.getInstance().deletarServico(user, Integer.parseInt(splitted[1])))
					return_message = "Serviço deletado!";
				else
					return_message = "Serviço com id " + splitted[1] + " não deletado. Você inseriu algo errado?\n" + return_message;
				break;
			case "/historico":
				newState = "HISTORICO";
				if(splitted.length > 1)
					return_message = "Este é o seu histórico:\n" + db.getHistoricoUsuario(user);
				break;
			case "/avaliar":
				newState = "AVALIAR";
				if(splitted.length > 3 && db.getInstance().avaliar(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]))) 
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
}
