package ru.example.letterflow.domain.dto;

import java.util.ArrayList;
import java.util.Date;

public class MessageDto {
    private Long userId;
    private Long roomId;
    private Date createDate;
    private ArrayList<String> text;
}
