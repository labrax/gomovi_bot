package movile.hackathon.team_bot;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class GeneralHandler extends TelegramLongPollingBot {
	private static String TOKEN = Config.token;
	private static String USERNAME = Config.username;
	
	private DatabaseConnMock db;
	private DatabaseConnMock getDb() {
		return DatabaseConnMock.getInstance();
	}
	
	public void onUpdateReceived(Update update) {
		db = getDb();
		
		String retorno = interactMessage(update.getMessage());
		if(Config.DEBUG)
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
		
		String state = db.getState(user, chatId);
		String substate = db.getSubState(user, chatId);
		
		if(state == null)
			state = "";
		if(substate == null)
			substate = "";
		
		if(Config.DEBUG) {
			System.out.println(message.getFrom().getUserName() + ": " + message.getText());
			System.out.println("state: " + state + "; substate: " + substate);
		}
		
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
				if(message.getLocation() != null) {
					db.setOptionsSelected(user, chatId, "" + db.getOptionsSelected(user, chatId) + "#" + message.getLocation().getLatitude() + "#" + message.getLocation().getLongitude());
				}
				db.setOptionsSelected(user, chatId, "" + db.getOptionsSelected(user, chatId) + "#0#" + message.getText());
				break;
			default:
				break;
		}
		
		switch(state) {
			case "CADASTRAR":
				if(Config.DEBUG)
					System.out.println("Indo para cadastrar!");
				return processaCadastrar(message);
			case "BUSCAR":
				if(Config.DEBUG)
					System.out.println("Indo para buscar!");
				return processaBuscar(message);
			case "INICIAL":
			default:
				if(Config.DEBUG)
					System.out.println("Indo para inicial!");
				return processaInicial(message);
		}
	}
	
	String processaCadastrar(Message message) {
		int user = message.getFrom().getId();
		long chatId = message.getChatId();
		String substate = getDb().getSubState(user, chatId);
		
		String currentOptions = db.getOptionsSelected(user, chatId);
		
		switch (substate) {
		case "CATEGORIA":

			db.setOptionsSelected(user, chatId, "" + currentOptions + "#" + message.getLocation());
			break;

		default:
			break;
		}
		
		return "";
	}
	
	String processaBuscar(Message message) {
		Integer user = message.getFrom().getId();
		Long chatId = message.getChatId();
		
		String retorno = "";

		String substate = db.getSubState(user, chatId);
		String new_state = "BUSCAR";
		String new_substate = "";
		switch(substate) {
			case "CATEGORIA":
				new_substate = "SUB-CATEGORIA";
				retorno = "Insira a sub-categoria do serviço da busca:";
				break;
			case "SUB-CATEGORIA":
				new_substate = "LOCALIZACAO";
				retorno = "Nos envie a sua localização!";
				break;
			case "LOCALIZACAO":
				try {
					String data = db.getOptionsSelected(user, chatId);
					String[] splitted = data.split("#");
					
					retorno = "" + db.getResultadosBusca(splitted[0], splitted[1], Float.parseFloat(splitted[2]), Float.parseFloat(splitted[3]));
				}
				catch(Exception e) {
					retorno = "A busca falhou! :(";
					e.printStackTrace();
				}
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
+ "/detalhes [usuario] veja os detalhes de serviços oferecidos por um usuário\n"
+ "/avaliar [usuario] [nota] avalie um usuário por um serviço oferecido\n"
+ "/cancelar cancele um procedimento atual";
		
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
				return_message = "Estes são os serviços que você tem: " + getDb().getServicosUsuario(user);
				break;
			case "/deletar":
				if(splitted.length > 1 && db.deletarServico(user, Integer.parseInt(splitted[1])))
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
			case "/detalhes":
				String get_db = "";
				if(splitted.length > 1)
					get_db = db.getDetalhesUsuario(splitted[1]);
				if(get_db != "")
					return_message = "O usuário tem os seguintes serviços:\n" + get_db;
				else
					return_message = "O nome de usuário inserido é inválido! :(";
				break;
			case "/avaliar":
				if(splitted.length > 3 && db.avaliar(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]))) 
					return_message = "Obrigado por avaliar!";
				else
					return_message = "Ocorreu algum problema ao avaliar :(";
				break;
			case "/cancelar":
				db.setState(user, chatId, "INICIAL");
				db.setSubState(user, chatId, "");
				db.setOptionsSelected(user, chatId, "");
				break;
			default:
				newSubState = "";
				break;
		}
		
		db.setState(user, chatId, newState);
		db.setSubState(user, chatId, newSubState);
		db.setOptionsSelected(user, chatId, "");
		
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
}

/**
cadastrar - cadastrar um serviço
buscar - buscar um serviço
listar - listar os seus serviços oferecidos
deletar - delete um serviço seu oferecido (insira o número do indice na listagem)
historico - ver seu histórico de serviços selecionados
detalhes - veja os serviços oferecidos por um usuário (insira o nome dele)
avaliar - avalie um usuário por um serviço oferecido (insira como parâmetros o outro usuário e a nota)
cancelar - cancele um procedimento atual
 */
