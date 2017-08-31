package rlbot;

import rlbot.helper.InterceptHelper;
import rlbot.helper.MovementHelper;
import rlbot.strategies.*;

public class Bot {
    public static enum Team {BLUE,ORANGE}
    
    private final Team team;
    private final StrategyController strat;
    private final MovementHelper movement;
    
    public Bot(Team team) {
        this.team = team;
        this.strat = new StrategyController(team);
        this.movement = new MovementHelper();
    }
    
    public AgentOutput getOutput(AgentInput input) {
        ObjInfo myCar, otherCar, ball;
        myCar = new ObjInfo(input.bluePosition, input.blueRotation, input.blueVelocity, input.blueBoost);
        otherCar = new ObjInfo(input.orangePosition, input.orangeRotation, input.orangeVelocity, input.orangeBoost);
        ball = new ObjInfo(input.ballPosition, null, input.ballVelocity, 0);
        
        InterceptHelper.blueIntercept = InterceptHelper.getIntercept(myCar, ball);
        InterceptHelper.orangeIntercept = InterceptHelper.getIntercept(otherCar, ball);
        
        if(team == Team.ORANGE) {
            ObjInfo temp = myCar;
            myCar = otherCar;
            otherCar = temp;
        }
        
        return strat.getBestStrategy(myCar, otherCar, ball, movement);
    }
    
    public String name() {
        return "Hexi's Bot";
    }
}
