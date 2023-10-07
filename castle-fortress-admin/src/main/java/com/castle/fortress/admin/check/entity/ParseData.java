package com.castle.fortress.admin.check.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParseData {
    private String beforeText = "";
    private String text = "";
    private String afterTest = "";
    private int textSize;
    private int size = 0;
    private String rawText = "";


    private List<List<ParseData>> list = new ArrayList<>();

    private List<HistoryMetaData> similarList;
}
