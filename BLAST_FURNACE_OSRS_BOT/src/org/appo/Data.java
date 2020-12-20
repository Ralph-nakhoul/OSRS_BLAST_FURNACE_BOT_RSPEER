package org.appo;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.ui.Log;

public class Data {

    public static boolean beltLocationReady = false;
    public static boolean coalBagFull = true;
    public static boolean readyToWalkToBelt = true;
    public static boolean readyToWalkToBarDispenser = false;
    public static boolean readyToBank = false;

    public static final Position BANK_TILE = new Position(1948, 4957, 0);
    public static final Position BELT_TILE = new Position(1942, 4967, 0);

    public static Item[] coalBag = Inventory.getItems(item -> item.getName().contains("bag"));


    public static boolean inventoryReady(){
        Item[] ironOres = Inventory.getItems((item -> item.getName().contains("ore")));
        if(coalBagFull&&ironOres.length>=25){
            Log.fine("Inventory ready");
            return true;
        }
        return false;
    }

}
