package ru.example.letterflow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.*;
import ru.example.letterflow.repository.RoomRepo;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BotService {
    private final RoomService roomService;
    private final RoomRepo roomRepo;
    private final UserService userService;
    private final YouTubeService youTubeService;

    private final String availableCommands = "Доступные команды:\n" +
            "room create {Название комнаты} -- создание комнаты  {Название комнаты}\n" +
            "room create {Название комнаты} -c -- создание закрытой комнаты {Название комнаты}\n" +
            "room remove {Название комнаты} -- удалить комнату {Название комнаты}\n" +
            "room rename {Название комнаты} {Новое название} -- переименовать комнату из {Название комнаты} в {Новое название}\n" +
            "room connect {Название комнаты} -- войти в комнату  {Название комнаты}\n" +
            "room connect {Название комнаты} -l {login } -- добавить пользователя {login} в комнату {Название комнаты}\n" +
            "room disconnect {Название комнаты} -- выйти из комнаты {Название комнаты}\n" +
            "room disconnect {Название комнаты} -l {login} -- выгнать пользователя {login} из комнаты {Название комнаты}\n" +
            "user rename {login} -- изменить свое имя на {login}\n" +
            "user rename {login} {new_login} -- изменить пользователя {login} на {new_login}\n" +
            "user ban -l {login} -- выгоняет пользователя {login} из всех комнат\n" +
            "user moderator {login} -n -- назначить пользователя {login} модератором\n" +
            "user moderator {login} -d -- сделать модератора {login} обычным пользователем\n" +
            "yBot find -k {название канала} -- запросить ссылку на YouTube канал {название канала}\n" +
            "yBot find -v {название видео} -- запросить ссылку на YouTube видео {название видео}\n" +
            "yBot find -v {название видео} -w -- запросить количество просмотров на YouTube видео {название видео}\n" +
            "yBot find -v {название видео} -l -- запросить количество лайков на YouTube видео {название видео}\n" +
            "yBot help -- список доступных команд для взаимодействия\n" +
            "yBot channelInfo {название канала} -- выводится название канала и ссылки на последние 5 роликов\n" +
            "yBot videoCommentRandom {Название видео} - рандомно выбирается 1 комментарий под роликом и выводитсяЖ 1. login 2. сам комментарий\n" +
            "help -- выводит список доступных команд";

    public String getAvailableCommands() {
        return availableCommands;
    }

    @Autowired
    public BotService(RoomService roomService, RoomRepo roomRepo, UserService userService, YouTubeService youTubeService) {
        this.roomService = roomService;
        this.roomRepo = roomRepo;
        this.userService = userService;
        this.youTubeService = youTubeService;
    }

    public String roomCommand(User user, Map<String, String> mapCommand) throws InsufficientAccessRightsException, RoomAlreadyExistException, UserNotFoundException, ImpossibleActionException {
        String result = null;
        if(mapCommand.get("action").equals("create")){
            roomService.createRoom(user, mapCommand.get("roomName"), Boolean.valueOf(mapCommand.get("personal")));
            result = "Комната '" + mapCommand.get("roomName") + "' создана";
        }
        if(mapCommand.get("action").equals("remove")){
            roomService.deleteRoom(user, roomRepo.findByRoomName(mapCommand.get("roomName")));
            result = "Комната '" + mapCommand.get("roomName") + "' удалена";
        }
        if(mapCommand.get("action").equals("rename")) {
            roomService.renameRoom(user, roomRepo.findByRoomName(mapCommand.get("roomName")), mapCommand.get("newRoomName"));
            result = "Комната '" + mapCommand.get("roomName") + "' переименована на '" + mapCommand.get("newRoomName") +"'";
        }
        if(mapCommand.get("action").equals("connect")){
            if(Boolean.parseBoolean(mapCommand.get("login"))){
                userService.addUserInRoom(user.getUserId(), mapCommand.get("roomName"), mapCommand.get("login"));
                result = "Пользователь '" + mapCommand.get("login") + "' добавлен в комнату '" + mapCommand.get("roomName") + "'";
            } else{
                userService.addUserInRoom(user.getUserId(), mapCommand.get("roomName"), user.getLogin());
                result = "Вы зашли в комнату '" + mapCommand.get("roomName") + "'";
            }
        }
        if(mapCommand.get("action").equals("disconnect")){
            if(Boolean.parseBoolean(mapCommand.get("login"))){
                userService.deleteUserInRoom(user.getUserId(), mapCommand.get("roomName"), mapCommand.get("login"));
                result = "Пользователь '" + mapCommand.get("login") + "' удален из комнаты '" + mapCommand.get("roomName") + "'";
            } else{
                userService.deleteUserInRoom(user.getUserId(), mapCommand.get("roomName"), user.getLogin());
                result = "Вы вышли из комнаты '" + mapCommand.get("roomName") + "'";
            }
        }
        return result;
    }

    public String userCommand(User user, Map<String, String> mapCommand) throws UserNotFoundException, InsufficientAccessRightsException, UserAlreadyExistException {
        String result = null;
        if(mapCommand.get("action").equals("rename")){
            if(Boolean.parseBoolean(mapCommand.get("newLogin"))) {
                userService.editName(user.getUserId(), mapCommand.get("login"), mapCommand.get("newLogin"));
                result = "Пользователь '" + mapCommand.get("login") +"' переименован на '" + mapCommand.get("newLogin") + "'";
            }else{
                userService.editName(user.getUserId(), user.getLogin(), mapCommand.get("login"));
                result = "Ваш логин изменен на '" + mapCommand.get("login") +"'";
            }
        }
        if(mapCommand.get("action").equals("ban")){
            userService.cleanRooms(user, mapCommand.get("login"));
            userService.editRole(user.getUserId(), mapCommand.get("login"), "blocked");
            result = "Пользователь '" + mapCommand.get("login") +"' заблокирован и удален из всех комнат";
        }
        if(mapCommand.get("action").equals("moderator")){
            userService.editRole(user.getUserId(), mapCommand.get("login"), mapCommand.get("role"));
            String roleToAnswer = mapCommand.get("role").equals("moderator") ? "модератором" : "обычным пользователем";
            result = "Пользователь '" + mapCommand.get("login") + "' является " + roleToAnswer;
        }
        return result;
    }

    public String botCommand(User user, Map<String, String> mapCommand) throws GeneralSecurityException, IOException {
        String result = null;
        if(mapCommand.get("action").equals("find")) {
            if (Boolean.parseBoolean(mapCommand.get("channel"))) {
                result = "Ссылка на канал: https://www.youtube.com/channel/" + youTubeService.getChannelId(mapCommand.get("channel"));
            }
            if (Boolean.parseBoolean(mapCommand.get("video"))) {
                result = "Ссылка на видео: https://www.youtube.com/watch?v=" + youTubeService.getVideoId(mapCommand.get("video"));
                if (Boolean.parseBoolean(mapCommand.get("watchers"))) {
                    result += " Количество просмотров: " + youTubeService.getNumberWatchers(mapCommand.get("video")) + ".";
                }
                if (Boolean.parseBoolean(mapCommand.get("likes"))) {
                    result += " Количество лайков: " + youTubeService.getNumberLikes(mapCommand.get("video")) + ".";
                }
            }
        }
        if(mapCommand.get("action").equals("help")) {
            result = availableCommands;
        }
        if(mapCommand.get("action").equals("channelInfo")){
            List<String> listLinksVideos = youTubeService.getVideoFromChannel(mapCommand.get("channel"));
            result = "Канал: '" + listLinksVideos.get(0) + "'\n" +
                    "Ссылки на видео с канала: \n" +
                    "https://www.youtube.com/watch?v=" + listLinksVideos.get(1) + "\n" +
                    "https://www.youtube.com/watch?v=" + listLinksVideos.get(2) + "\n" +
                    "https://www.youtube.com/watch?v=" + listLinksVideos.get(3) + "\n" +
                    "https://www.youtube.com/watch?v=" + listLinksVideos.get(4) + "\n" +
                    "https://www.youtube.com/watch?v=" + listLinksVideos.get(5) + "\n";
        }
        if(mapCommand.get("action").equals("videoCommentRandom")){
            List<String> listRandomComment = youTubeService.findVideoCommentRanom(mapCommand.get("video"));
            result = "Комментарий пользователя '" + listRandomComment.get(0) + "': " + listRandomComment.get(1);
        }
        return result;
    }
}
