package com.police.bot;

import com.police.configs.telegram.BotProperties;
import com.police.entities.Location;
import com.police.entities.PoliceStation;
import com.police.services.CallService;
import com.police.services.CrimeService;
import com.police.services.LocationService;
import com.police.services.PoliceStationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.logging.Logger;

@Component
public class NYPDTbot extends TelegramLongPollingBot {

    private PoliceStationInfoService policeStationInfoService;

    private CallService callService;

    private CrimeService crimeService;

    private LocationService locationService;

    private Logger logger = Logger.getLogger(getClass().getName());

    private BotProperties properties;

    @Autowired
    public NYPDTbot(DefaultBotOptions defaultBotOptions, BotProperties bp, CallService cls, CrimeService cs,
                    PoliceStationInfoService psis, LocationService ls) {
        super(defaultBotOptions);
        this.policeStationInfoService = psis;
        this.locationService = ls;
        this.properties = bp;
        this.crimeService = cs;
        this.callService = cls;
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String text = update.getMessage().getText();
            String answer = "";
            try {
                String[] parts = text.split(" ");
                if (parts.length == 1 && parts[0].equals("/site")){
                    answer = "\'Official\' NYPD site:\n" +
                            "http:/www.nypolicecw.com:7080";
                }
                else if (parts.length == 2 && parts[0].equals("/stationinfo")) {
                    if (Long.valueOf(parts[1]) <= 0 || Long.valueOf(parts[1]) > policeStationInfoService.getAll().size())
                        answer = "There's no Police Station with such id.";
                    else {
                        long id = policeStationInfoService.getPoliceStationById(Long.valueOf(parts[1])).getId();
                        String street = policeStationInfoService.getPoliceStationById(Long.valueOf(parts[1])).getStationLocation().getStreet();
                        long houseNumber = policeStationInfoService.getPoliceStationById(Long.valueOf(parts[1])).getStationLocation().getHouseNumber();
                        String openingTime = policeStationInfoService.getPoliceStationById(Long.valueOf(parts[1])).getOpeningTime().toLocalTime()
                                .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
                        String closingTime = policeStationInfoService.getPoliceStationById(Long.valueOf(parts[1])).getClosingTime().toLocalTime()
                                .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
                        answer = "There's an info about Police Station №" + id + ":\n"
                                + "Location: " + street + ", " + houseNumber + ".\n"
                                + "Opening time: " + openingTime + "\n"
                                + "Closing time: " + closingTime;
                    }
                }
                else if (parts.length >= 3 && parts[0].equals("/isplacesafe")) {
                    String street = "";
                    for (int i = 1; i < parts.length-1; i++)
                        street += parts[i] + " ";
                    long houseNumber = Long.valueOf(parts[parts.length-1]);;
                    Location location = locationService.getLocationByHouseAndStreet(houseNumber, street.trim());
                    if (location == null)
                        throw new NullPointerException();
                    int currentDanger = callService.getActiveCallByLocation(location).size();
                    int overallDanger = crimeService.getCrimesByLocation(location).size();
                    switch (currentDanger) {
                        case 0:
                            answer = "Right now, this place is probably safe.\n";
                            break;
                        case 1:
                            answer = "There is a good chance, that something bad is going on there, better stay away from this place.\n";
                            break;
                        case 2:
                            answer = "Something really bad is happening here right now, please, don't go to this location.\n";
                            break;
                        case 3:
                            answer = "This place is really dangerous right now, don't go anywhere near it.\n";
                            break;
                        default:
                            answer = "THERE IS AN EMERGENCY SITUATION RIGHT NOW";
                    }

                    switch (overallDanger) {
                        case 0:
                        case 1:
                        case 2:
                            answer += "Overall, this is really quite place.";
                            break;
                        case 3:
                        case 4:
                        case 5:
                            answer += "Usually this is a safe place, but better be careful.";
                            break;
                        case 6:
                        case 7:
                        case 8:
                            answer += "Generally, it is be a good advice not to go alone to there.";
                            break;
                        case 9:
                        case 10:
                        case 11:
                            answer += "This place is really dangerous usually, be extra careful.";
                            break;
                        default:
                            answer += "Based on crime statistics, NYPD strongly recommends that you don't go to this plsace.";
                    }
                }
            } catch (NullPointerException | NumberFormatException e) {
                answer = "This location does not exist";
            } finally {
                if (answer.equals(""))
                    answer = "Sorry, i can't recognize your command";
                long chat_id = update.getMessage().getChatId();
                SendMessage message = new SendMessage()
                        .setChatId(chat_id)
                        .setText(answer);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    //
                }
            }

        }

    }

    @Override
    public String getBotUsername() {
        return properties.getName();
    }
}
