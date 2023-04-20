package UserInterface.Windows;

import UserInterface.Buttons.PlayButton;
import UserInterface.Buttons.WindowButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelWindow extends ScrollableMenu {

    public LevelWindow(Menu menu){
        super(menu, "Decals\\default_menu.png");
        load();
    }

    @Override
    void loadButtons() {
        addButton(new WindowButton(this,menu, "BACK"));
        addButton(new WindowButton(this,menu, "CREATOR"));

        File levelsFile = new File("Levels");
        if(levelsFile.exists()){
            loadLevelButtons(levelsFile);
        }
    }

    private void loadLevelButtons(File levelsFile){
        ArrayList<File> levelList = new ArrayList<>();
        File[] fileArray = levelsFile.listFiles();
        if(fileArray != null){
            levelList.addAll(Arrays.asList(fileArray));
        }
        Collections.sort(levelList);
        for (File file : levelList) {
            String levelName = file.getName();
            Pattern pattern = Pattern.compile("^(Level)([1-9]+[0-9]*)(\\.)(txt)$");
            Matcher matcher = pattern.matcher(levelName);

            if (matcher.find()) {
                int levelNumber = Integer.parseInt(matcher.group(2));
                addButton(new PlayButton(this, null, levelNumber));
            }
        }
    }

    @Override
    void load() {
        loadFrame();
        loadPanel();
        loadScrollPane();
        loadTitle("LEVELS");
        loadButtons();
    }
}
