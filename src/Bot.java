import java.io.InvalidObjectException;
import java.util.Arrays;

import org.telegram.telegrambots.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		try {
			handleAll(update);
		} catch (InvalidObjectException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return Constants.BotConfig.USERNAME;
	}

	@Override
	public String getBotToken() {
		return Constants.BotConfig.TOKEN;
	}

	public void handleAll(Update update) throws InvalidObjectException {
		if (update.getMessage().getChat().isUserChat())
			handleCommands(update);
		else
			handleGroupMessages(update);
	}

	public void handleCommands(Update update) throws InvalidObjectException {
		Message message = update.getMessage();

		if (message != null && message.hasText()) {
			String messageText = message.getText();

			if (messageText.startsWith(Constants.Commands.startCommand)) {
				onStartCommand(message);
			} else {
				onWeirdCommand(message);
			}
		}
	}

	public void handleGroupMessages(Update update) throws InvalidObjectException {
		Message message = update.getMessage();

		if (message.getNewChatMember() != null){
			if (message.getNewChatMember().getUserName().equals(getBotUsername()))
				quickText(message, Constants.Replies.MAKEMEADMIN);
			else
				quickReply(message, Constants.Replies.WELCOME);
		} 
	
		if (message != null && message.hasText()) {
			String messageText = message.getText().toLowerCase();

			if (isOffensive(messageText)) {
				KickChatMember member = new KickChatMember();
				member.setChatId(message.getChatId());
				member.setUserId(message.getFrom().getId());

				try {
					kickMember(member);
					quickReply(message, String.format(Constants.Replies.KICKOUT, message.getFrom().getFirstName()));
				} catch (TelegramApiException e) {
					quickReply(message, Constants.Replies.WHYADMIN);
				}

			}
		}
	}

	public boolean isOffensive(String messageText) {
		String[] words = messageText.split("\\s+");
		for (int i = 0; i < words.length; i++)
			if (Arrays.asList(Constants.Data.VULGAR).contains(words[i].replaceAll("[^\\w]", "")))
					return true;
			
		return false;
		
	}

	public void onStartCommand(Message message) {
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setChatId(message.getChatId());
		sendMessageRequest.setText(String.format(Constants.Replies.START, message.getChat().getFirstName()));
		try {
			sendMessage(sendMessageRequest);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public void onWeirdCommand(Message message) {
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setChatId(message.getChatId());
		sendMessageRequest.setText(String.format(Constants.Replies.WHAT, message.getChat().getFirstName()));
		try {
			sendMessage(sendMessageRequest);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public void quickText(Message message, String text) {
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setText(text);
		sendMessageRequest.setChatId(message.getChatId());

		try {
			sendMessage(sendMessageRequest);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public void quickReply(Message message, String text) {
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setText(text);
		sendMessageRequest.setChatId(message.getChatId());
		sendMessageRequest.setReplyToMessageId(message.getMessageId());

		try {
			sendMessage(sendMessageRequest);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}