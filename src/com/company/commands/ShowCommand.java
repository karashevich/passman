package com.company.commands;

import com.company.Command;
import com.company.structures.DataPassClass;
import com.company.structures.PassClass;

/**
 * Created by jetbrains on 3/18/14.
 */
public class ShowCommand extends Command {

    private static final String description = "-show         show some record.    passman.jar -show <link>";

    public ShowCommand() {
        super(CommandType.SHOW, description);
    }

    @Override
    public void execute(DataPassClass dpc, String[] args) {
        StringBuilder sb = new StringBuilder();
        String link = args[1];

        PassClass pc = dpc.getPC(link);


        sb.append("Link :\"").append(pc.getLink()).append("\" Login:\"").append(pc.getLogin()).append("\" Password:\"").append(pc.getPass()).append("\"");
        System.out.println(new String(sb));
    }
}
