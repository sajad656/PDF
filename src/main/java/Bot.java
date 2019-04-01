import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private final static String TOKEN = "";

    private final ConversionAdapter convertAssistant = new ConversionAdapter();

    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            if(update.getMessage().hasText()){
                String message =  update.getMessage().getText().toLowerCase();
                if(message.startsWith("/get")){
                    File pdf = convertAssistant.getPdf();
                    try {
                        SendDocument sendDocument = new SendDocument()
                                .setChatId(update.getMessage().getChatId())
                                .setDocument(pdf);

                        execute(sendDocument);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }else if(message.startsWith("/reset")){
                    convertAssistant.reset();
                }
            }else if(update.getMessage().hasPhoto()){
                List<PhotoSize> photo = update.getMessage().getPhoto();
                convertAssistant.add(photo.get(0).getFilePath());
            }
        }
    }

    public void onUpdatesReceived(List<Update> updates) {
        try {
            User me = getMe();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "Images2PdfBot";
    }

    public String getBotToken() {
        return TOKEN;
    }
}
