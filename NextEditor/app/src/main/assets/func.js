function getValue(){
    Android.write(editor.getValue());
}

function setValue(){
    var value = new String(Android.read());
    editor.setValue(value);
}

function undo(){
    editor.undo();
}

function redo(){
    editor.redo();
}

function setLanguage(value){
    editor.setOption("mode",value);
}

function setTheme(value){
    editor.setOption("theme",value);
}

function readOnly(value){
    var flag = value ==="false" ? false : true;
    editor.setOption("readOnly",flag);
}

