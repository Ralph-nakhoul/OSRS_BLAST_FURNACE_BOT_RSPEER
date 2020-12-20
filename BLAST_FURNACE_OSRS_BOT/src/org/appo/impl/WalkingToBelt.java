package org.appo.impl;

import org.appo.Data;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class WalkingToBelt extends Task {

    private final Position case1[] = {new Position(1946, 4958, 0), new Position(1946, 4959, 0), new Position(1945, 4959),new Position(1945, 4958),new Position(1943, 4960, 0),new Position(1943, 4959, 0),new Position(1942, 4959, 0), new Position(1942, 4960, 0)};
    private final Position case2[] = {new Position(1941, 4960, 0),new Position(1941, 4961, 0),new Position(1941, 4959, 0),new Position(1940, 4959, 0),new Position(1940, 4960),new Position(1940, 4961, 0),new Position(1939, 4959, 0),new Position(1939, 4960, 0),new Position(1938, 4960, 0),new Position(1938, 4961, 0),new Position(1938, 4962, 0),new Position(1937, 4960, 0),new Position(1937, 4961, 0),new Position(1937, 4962, 0)};
    private final Position case3[] = {new Position(1937, 4963, 0),new Position(1938, 4963, 0),new Position(1938, 4964, 0),new Position(1937, 4964, 0),new Position(1937, 4965, 0),new Position(1938, 4965, 0),new Position(1938, 4966, 0)};
    private final Position case4[] = {new Position(1937, 4966, 0),new Position(1937, 4967, 0),new Position(1938, 4967, 0),new Position(1939, 4967, 0),new Position(1940, 4967, 0),new Position(1941, 4967, 0)};

    int randomNumber = Random.nextInt(10,110);
    public static int caseNumber;

    @Override
    public boolean validate() {
        if((Players.getLocal().distance(Data.BELT_TILE)>0)&&(Data.readyToWalkToBelt==true)&&(Data.inventoryReady())&&(!Players.getLocal().isMoving()))
            return true;

        return false;
    }

    @Override
    public int execute() {

        Log.fine("Walking to conveyor belt");

        WalkToBelt();

        return Random.nextInt(800, 1600);
    }

    //Pick a random position from array
    private Position pickFromArray(Position position[]){
        int randomNumber = Random.nextInt(0,position.length);
        Position returnValue = new Position(1);
        for(int i=0;i<position.length;i++){
            if(i==randomNumber){
                returnValue = position[i];
            }
        }
        return returnValue;
    }

    //Walk to belt
    private void WalkToBelt(){

        Time.sleep(300,600);

        if(randomNumber>=10&&randomNumber<20)
            caseNumber=1;
        else if(randomNumber>=30&&randomNumber<=50)
            caseNumber=2;
        else if(randomNumber>50&&randomNumber<75)
            caseNumber=3;
        else if(randomNumber>=75&&randomNumber<100)
            caseNumber=4;
        else
            caseNumber=5;


        switch (caseNumber){
            case 1:
                Position move1 = pickFromArray(case1);
                Movement.walkTo(move1);
                Time.sleep(Random.nextInt(600,900));
                if(randomNumber>=25)
                    caseNumber = 4;
                else if(randomNumber>=15&&randomNumber<25)
                    caseNumber=3;
                else if(randomNumber>11&&randomNumber<15)
                    caseNumber=2;
                else
                    caseNumber=5;


            case 2:
                Position move2 = pickFromArray(case2);
                Movement.walkTo(move2);
                Time.sleep(Random.nextInt(300,600));
                if(randomNumber>32&&randomNumber<45)
                    caseNumber = 3;
                else if(randomNumber>=46)
                    caseNumber=4;
                else
                    caseNumber=5;

            case 3:
                Position move3 = pickFromArray(case3);
                Movement.walkTo(move3);
                Time.sleep(Random.nextInt(600,900));
                if(randomNumber>90)
                    caseNumber=4;
                else caseNumber=5;

            case 4:
                Position move4 = pickFromArray(case4);
                Movement.walkTo(move4);
                Time.sleep(Random.nextInt(900,1500));
                caseNumber=5;
            case 5:
                Movement.walkTo(Data.BELT_TILE);
                Time.sleep(Random.nextInt(1000,2000));

                if(Players.getLocal().distance(Data.BELT_TILE)==0){
                    Data.beltLocationReady = true;
                    Data.readyToWalkToBelt=false;
                }

        }
    }

}
