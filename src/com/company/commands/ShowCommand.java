package com.company.commands;

import com.company.Command;
import com.company.UI;
import com.company.security.PasswordStorage;
import com.company.structures.DataPassInterface;
import com.company.structures.Item;

/**
 * Created by jetbrains on 3/18/14.
 */
public class ShowCommand extends Command {

    private static final String description = "-show         show some record.    passman.jar -show <link>";

    public ShowCommand() {
        super(CommandType.SHOW, description);
    }

    @Override
    public void execute(DataPassInterface dpc, String[] args, PasswordStorage ps, UI ui) {

        if (args.length < 2) {
            System.out.println("Oh, poor! You should write more arguments!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        String link = args[1];

        Item pc = dpc.getPC(link, ps, ui);
        if (pc == null){
            System.out.println("Unfortunately I cannot remember such resources.");
            return;
        }

        sb.append("Link :\"").append(pc.getLink()).append("\" Login:\"").append(pc.getLogin()).append("\" Password:\"").append(pc.getPass()).append("\"");
        System.out.println(new String(sb));
    }
}
