package com.yuanxi.starrysky.nexteditor.util;

import java.io.File;

public class Language {

    private Language(){

    }

    public static String setLanguage(File file){
        String name = file.getName().substring(file.getName().lastIndexOf(".")+1);
        switch (name){
            case "java":
                return "text/x-java";
            case "c":
                return "text/x-csrc";
            case "py":
                return "python";
            case "md":
                return "markdown";
        }
        return "";
    }
}
