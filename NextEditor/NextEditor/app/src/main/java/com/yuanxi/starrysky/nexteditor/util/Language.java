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
            case "cs":
                return "text/x-csharp";
            case "m":
                return "text/x-objectivec";
            case "scala":
                return "text/x-scala";
            case "clj":
                return "clojure";
            case "COB":
                return "cobol";
            case "css":
                return "css";
            case "d":
                return "d";
            case "dart":
                return "dart";
            case "go":
                return "go";
            case "groovy":
                return "groovy";
            case "hs":
                return "haskell";
            case "hx":
                return "haxe";
            case "julia":
                return "julia";
            case "lua":
                return "lua";
            case "lisp":
                return "lisp";
            case "md":
                return "markdown";
            case "perl":
                return "perl";
            case "php":
                return "php";
            case "py":
                return "python";
            case "rb":
                return "ruby";
            case "rs":
                return "rust";
            case "sql":
                return "sql";
            case "swift":
                return "swift";
            case "xml":
                return "sql";
            case "yaml":
                return "yaml";
        }
        return "";
    }
}
