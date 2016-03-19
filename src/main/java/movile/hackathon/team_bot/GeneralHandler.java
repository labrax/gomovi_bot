package movile.hackathon.team_bot;

import java.util.List;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class GeneralHandler extends TelegramLongPollingBot {
	private static String TOKEN = "177468357:AAHnVizr1jRjhAQJnvEIbK94AS1yP_O9hvw";
	private static String USERNAME = "testbot";
	
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
		//sendMessage.setText("huehuehue br? " + update.getMessage().getText());
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
		
		if(message.getText() == "/cancelar")
			db.setState(user, chatId, "INICIAL");
		
		String state = db.getState(user, chatId);
		String substate = db.getSubState(user, chatId);
		
		if(state == null)
			state = "";
		if(substate == null)
			substate = "";
		
		System.out.println("state: " + state + "; substate: " + substate);
		
		switch(substate) {
			case "TEXTO":
				db.setOptionsSelected(user, chatId, "" + db.getOptionsSelected(user, chatId) + "#" + message.getText());
				break;
			case "CATEGORIA":
				db.setOptionsSelected(user, chatId, "" + db.getOptionsSelected(user, chatId) + "#" + message.getText());
				break;
			case "SUB-CATEGORIA":
				db.setOptionsSelected(user, chatId, "" + db.getOptionsSelected(user, chatId) + "#" + message.getText());
				break;
			case "SUMARIO":
				db.setOptionsSelected(user, chatId, "" + db.getOptionsSelected(user, chatId) + "#" + message.getText());
				break;
			case "DESCRICAO":
				db.setOptionsSelected(user, chatId, "" + db.getOptionsSelected(user, chatId) + "#" + message.getText());
				break;
			case "LOCALIZACAO":
				db.setOptionsSelected(user, chatId, "" + db.getOptionsSelected(user, chatId) + "#" + message.getLocation());
				break;
			default:
				break;
		}
		
		System.out.println("Aqui!");
		
		switch(state) {
			case "CADASTRAR":
				System.out.println("Indo para cadastrar!");
				return processaCadastrar(message);
			case "BUSCAR":
				System.out.println("Indo para buscar!");
				return processaBuscar(message);
			case "INICIAL":
			default:
				System.out.println("Indo para inicial!");
				return processaInicial(message);
		}
	}
	
	String processaCadastrar(Message message) {
		int user = message.getFrom().getId();
		long chatId = message.getChatId();
		String substate = db.getSubState(user, chatId);
		
		return "";
	}
	
	String processaBuscar(Message message) {
		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();
		
		String retorno = "";
		
		db = getDb();
		String substate = db.getSubState(user, chatId);
		String new_state = "BUSCAR";
		String new_substate = "";
		switch(substate) {
			case "CATEGORIA":
				new_substate = "SUB-CATEGORIA";
				retorno = "Qual a sub-categoria do serviço que você esta buscando?";
				retorno += listToString(db.getSubCategorias("Saúde"));
				break;
			case "SUB-CATEGORIA":
				new_substate = "LOCALIZACAO";
				retorno = "Nos envie a sua localização!";
				break;
			case "LOCALIZACAO":
				String data = db.getInstance().getOptionsSelected(user, chatId);
				String[] splitted = data.split("#");
				System.out.println(splitted);
				retorno = db.getInstance().getResultadosBusca(splitted[0], splitted[1], Float.parseFloat(splitted[2]), Float.parseFloat(splitted[3]));
				new_state = "INICIAL";
				new_substate = "";
				break;
			default:
				new_state = "INICIAL";
				new_substate = "";
				retorno = "Oops, alguma coisa deu errado! :(";
				break;
		}
		db.setState(user, chatId, new_state);
		db.setSubState(user, chatId, new_substate);
		return retorno;
	}
	
	String processaInicial(Message message) {
		String newState = "INICIAL";
		String newSubState = "";
		String return_message = "O quê deseja fazer?\n" + 
"/cadastrar cadastrar um serviço\n"
+ "/buscar buscar um serviço\n"
+ "/listar listar os seus serviços oferecidos\n"
+ "/deletar [id] delete um serviço seu oferecido\n"
+ "/historico ver seu histórico de serviços selecionados\n"
+ "/avaliar [usuario] [nota] avalie um usuário por um serviço oferecido\n"
+ "/cancelar cancele um procedimento atual";
		
		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();
		
		String [] splitted = message.getText().split(" ");
		switch(splitted[0]) {
			case "/cadastrar":
				newState = "CADASTRAR";
				newSubState = "CATEGORIA";
				return_message = "Qual categoria deseja cadastrar?";
				return_message += listToString(db.getCategorias());
				break;
			case "/buscar":
				newState = "BUSCAR";
				newSubState = "CATEGORIA";
				return_message = "Insira a categoria do serviço que deseja buscar: ";
				return_message += listToString(db.getCategorias());
				break;
			case "/listar":
				return_message = "Estes são os serviços que você tem: " + getDb().getServicosUsuario(user);
				break;
			case "/deletar":
				if(splitted.length > 1 && getDb().deletarServico(user, Integer.parseInt(splitted[1])))
					return_message = "Serviço deletado!";
				else if(splitted.length > 1)
					return_message = "Serviço com id " + splitted[1] + " não deletado. Você inseriu algo errado?\n" + return_message;
				else
					return_message = "Por favor, insira o número do serviço (consulte em /listar no caso de dúvidas!\n" + return_message;
				break;
			case "/historico":
				if(splitted.length > 1)
					return_message = "Este é o seu histórico:\n" + getDb().getHistoricoUsuario(user);
				break;
			case "/avaliar":
				if(splitted.length > 3 && getDb().avaliar(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]))) 
					return_message = "Obrigado por avaliar!";
				else
					return_message = "Ocorreu algum problema ao avaliar :(";
				break;
			default:
				newSubState = "";
				break;
		}
		
		getDb().setState(user, chatId, newState);
		getDb().setSubState(user, chatId, newSubState);
		getDb().setOptionsSelected(user, chatId, "");
		
		return return_message;
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
	
	private String listToString(List<String> list) {
		return String.join(", ", list);
	}
}
