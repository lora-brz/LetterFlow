package ru.example.letterflow.controller;

import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.ImpossibleActionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//import ru.example.letterflow.service.BotService;

@RestController
@RequestMapping("/bot")
public class BotController {
    public static void main(String[] args) throws ImpossibleActionException {
        System.out.println(new BotController().parseCommand("help"));
    }

//    private final BotService botService;
//    private final String command;
//
//    @Autowired
//    public BotController(BotService botService, String command) {
//        this.botService = botService;
//        this.command = command;
//    }

    @GetMapping
    public void possibleActions(@RequestBody User user,
                                @RequestBody String command) throws ImpossibleActionException {
        Map<String, String> mapCommand = parseCommand(command);
        String service = mapCommand.get("service");
        String action = mapCommand.get("action");
        String roomName = mapCommand.get("roomName");
        String login = mapCommand.get("login");
        Boolean person = Boolean.valueOf(mapCommand.get("person"));
        Long milliseconds = Long.valueOf(mapCommand.get("milliseconds"));
        String role = mapCommand.get("role");
        String nameChannel = mapCommand.get("nameChannel");
        Boolean views = Boolean.valueOf(mapCommand.get("views"));
        Boolean likes = Boolean.valueOf(mapCommand.get("likes"));

        switch (service){
            case "room":
                roomCommand(action, roomName, person, login, milliseconds);
                break;
            case "user":
                userCommand(action, login, milliseconds, role);
                break;
            case "yBot":
                botCommand(action, nameChannel, views, likes);
                break;
        }
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
                for (String com : listCommand) {
                    if (com.equals("-l")) {
                        mapCommand.put("login", listCommand.get(listCommand.indexOf(com) + 1));
                    }
                    if (com.equals("-c")) {
                        mapCommand.put("person", "person");
                    }
                    if (com.equals("-m")) {
                        mapCommand.put("milliseconds", listCommand.get(listCommand.indexOf(com) + 1));
                    }
                }
            }
            if(mapCommand.get("service").equals("user")){
                mapCommand.put("login", listCommand.get(2));
                for (String com : listCommand) {
                    if (com.equals("-n")) {
                        mapCommand.put("role", "moderator");
                    }
                    if (com.equals("-d")) {
                        mapCommand.put("role", "user");
                    }
                    if (com.equals("-m")) {
                        mapCommand.put("milliseconds", listCommand.get(listCommand.indexOf(com) + 1));
                    }
                }
            }

            if(mapCommand.get("service").equals("yBot") && mapCommand.get("action").equals("find")){
                for (String com : listCommand) {
                    if (com.equals("-k")) {
                        mapCommand.put("nameChannel", listCommand.get(listCommand.indexOf(com) + 1));
                    }
                    if (com.equals("-v")) {
                        mapCommand.put("views", "views");
                    }
                    if (com.equals("-l")) {
                        mapCommand.put("likes", "likes");
                    }
                }
            }

        } catch (Exception e){
            throw new ImpossibleActionException("Ввели неверную команду");
        }
        return mapCommand;
    }

    public void roomCommand(String action, String roomName, Boolean person, String login, Long milliseconds){

    }

    public void userCommand(String action, String login, Long milliseconds, String role){

    }

    public void botCommand(String action, String nameChannel, Boolean views, Boolean likes){

    }

}
