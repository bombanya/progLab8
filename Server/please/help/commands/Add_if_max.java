package please.help.commands;

import please.help.network.ChangeEvent;
import please.help.CollectionManager;
import please.help.Mode;
import please.help.organizationBuilding.ConsoleValidator;
import please.help.organizationBuilding.Organization;
import please.help.organizationBuilding.ScriptValidator;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Класс для комманды add_if_max.
 * Формат комманды: add_if_max
 */

public class Add_if_max extends Command{

    private static final long serialVersionUID = 20200916L;
    private Organization org;

    public Add_if_max(){
        commandName = "add_if_max";
    }

    private Add_if_max(Organization org){
        commandName = "add_if_max";
        this.org = org;
    }


    @Override
    public String execute(CollectionManager manager, String login, String password, String locale) {
        if (manager.collectionShell.collection.size() == 0
                || org.compareTo(Collections.max(manager.collectionShell.collection)) > 0) {
            if (manager.collectionShell.insertNewOrg(org, login)) {
                manager.collectionShell.registerEvent(org, ChangeEvent.Type.ADDED);
                return getStringFromResource("orgAdded", locale);
            }
            else return getStringFromResource("addError", locale);
        }
        else return getStringFromResource("smallAddError", locale);
    }

    @Override
    public Add_if_max validateCommand(LinkedList<String[]> data, CollectionManager manager) {
        if (data.size() == 0 || data.poll().length > 1) {
            System.out.println("Неверно введена комманда.");
            return null;
        }
        if (manager.getManagerMode() == Mode.CONSOLE) org = ConsoleValidator.buildFromConsole(null);
        else org = ScriptValidator.buildFromScript(null, data, manager);
        Add_if_max add_if_max = new Add_if_max(org);
        if (org != null) add_if_max.makeValid();
        return add_if_max;
    }
}
