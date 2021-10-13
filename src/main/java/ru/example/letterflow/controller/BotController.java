package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.*;
import ru.example.letterflow.service.BotService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bot")
public class BotController {

    private final BotService botService;

    @Autowired
    public BotController(BotService botService) {
        this.botService = botService;
    }

    @GetMapping
    public ResponseEntity possibleActions(@RequestBody User user,
                                          @RequestBody String command) throws ImpossibleActionException, InsufficientAccessRightsException, RoomAlreadyExistException, UserNotFoundException, UserAlreadyExistException {
        Map<String, String> mapCommand = parseCommand(command);

        Map<String, String> mapService = new HashMap<>();
        mapService.put("room", botService.roomCommand(user, mapCommand));
        mapService.put("user", botService.userCommand(user, mapCommand));
        mapService.put("yBot", botService.botCommand(user, mapCommand));

        return ResponseEntity.ok(mapService.get(mapCommand.get("service")));
    }

    @PutMapping
    public Map<String, String> parseCommand(String command) throws ImpossibleActionException {
        ArrayList<String> listCommand = new ArrayList<>();
        Collections.addAll(listCommand, command.split(" "));
        Map<String, String> mapCommand = new HashMap();
        if(listCommand.size()==1 && listCommand.get(0).equals("help")){
            mapCommand.put("service", "yBot");
            mapCommand.put("action","help");
            return mapCommand;
        }
        try{
            mapCommand.put("service", listCommand.get(0));
            mapCommand.put("action", listCommand.get(1));

            if (mapCommand.get("service").equals("room") && listCommand.size() > 2) {
                mapCommand.put("roomName", listCommand.get(2));
                if(mapCommand.get("action").equals("rename")){
                    mapCommand.put("newRoomName", listCommand.get(3));
                }
                for (String com : listCommand) {
                    if (com.equals("-l")) {
                        mapCommand.put("login", listCommand.get(listCommand.indexOf(com) + 1));
                    }
                    if (com.equals("-c")) {
                        mapCommand.put("personal", "personal");
                    }
                }
            }
            if(mapCommand.get("service").equals("user")){
                mapCommand.put("login", listCommand.get(2));
                if(mapCommand.get("action").equals("rename")){
                    mapCommand.put("newLogin", listCommand.get(3));
                }
                for (String com : listCommand) {
                    if (com.equals("-n")) {
                        mapCommand.put("role", "moderator");
                    }
                    if (com.equals("-d")) {
                        mapCommand.put("role", "user");
                    }
                }
            }

            if(mapCommand.get("service").equals("yBot") && mapCommand.get("action").equals("find")){
                for (String com : listCommand) {
                    if (com.equals("-k")) {
                        mapCommand.put("channel", listCommand.get(listCommand.indexOf(com) + 1));
                    }
                    if (com.equals("-v")) {
                        mapCommand.put("video", listCommand.get(listCommand.indexOf(com) + 1));
                    }
                    if (com.equals("-w")) {
                        mapCommand.put("watchers", "watchers");
                    }
                    if (com.equals("-l")) {
                        mapCommand.put("likes", "likes");
                    }
                }
            }

        } catch (Exception e){
            throw new ImpossibleActionException("Вы ввели неверную команду");
        }
        return mapCommand;
    }
}
