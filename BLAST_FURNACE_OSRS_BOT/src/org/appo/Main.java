package org.appo;


import org.appo.impl.Banking;
import org.appo.impl.UsingBelt;
import org.appo.impl.WalkingToBelt;
import org.appo.impl.WalkingBack;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.InterfaceOptions;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.ChatMessageEvent;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.awt.*;


@ScriptMeta(developer = "appo", name = "BlastFurnace", desc = "Blast furnace steel bar bot")
public class Main extends TaskScript implements RenderListener {

    private static final Task[] TASKS = {new Banking(), new UsingBelt(), new WalkingToBelt(), new WalkingBack()};
    public static StopWatch timer;

    @Override
    public void onStart() {

        Log.fine("Started");
        submit(TASKS);

        timer = StopWatch.start();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g = renderEvent.getSource();
        g.drawString("My position: x=" + Players.getLocal().getX()+", y="+Players.getLocal().getY() + ", fl=" + Players.getLocal().getFloorLevel(), 30, 30); //x and y are destinations the size of a pixel on the canvas, canvas is 503x765
        g.drawString("walking case:  " + WalkingToBelt.caseNumber,30,50);
        g.drawString("Runtime: " + timer.toElapsedString(), 30, 70);
    }

}
