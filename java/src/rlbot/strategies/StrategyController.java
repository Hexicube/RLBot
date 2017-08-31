package rlbot.strategies;

import rlbot.*;
import rlbot.Bot.Team;
import rlbot.helper.MovementHelper;

public class StrategyController {
    public static class StrategyOutput {
        public boolean raw = false;
        
        public final ObjInfo car, target;
        
        public StrategyOutput(ObjInfo car, ObjInfo target) {
            this.car = car;
            this.target = target;
        }
    }
    
    public static class StrategyOutputRaw extends StrategyOutput {
        public final AgentOutput out;
        
        public StrategyOutputRaw(AgentOutput out) {
            super(null, null);
            raw = true;
            this.out = out;
        }
    }
    
    private Strategy curStrat;
    private final Team team;
    
    public StrategyController(Team team) {
        this.team = team;
    }
    
    private static final Strategy[] stratList = new Strategy[]{
        new StrategyCloseDistance(),
        new StrategyGoalTend()
    };
    
    private static final Strategy blueOverride   = null,
                                  orangeOverride = null;
    
    public AgentOutput getBestStrategy(ObjInfo myCar, ObjInfo otherCar, ObjInfo ball, MovementHelper movement) {
        if(!movement.canRelease(myCar)) return movement.getMovement(myCar, null);
        
        if(team == Team.BLUE   &&   blueOverride != null) return movement.getMovement(myCar,   blueOverride.handleStrategy(myCar, otherCar, ball, team));
        if(team == Team.ORANGE && orangeOverride != null) return movement.getMovement(myCar, orangeOverride.handleStrategy(myCar, otherCar, ball, team));
        
        if(curStrat == null || curStrat.canRelease()) {
            double bestStratAmt = 0;
            Strategy bestStratObj = null;
            for(Strategy s : stratList) {
                double str = s.getStrategyStrength(myCar, otherCar, ball, team);
                if(bestStratObj == null || str > bestStratAmt) {
                    bestStratObj = s;
                    bestStratAmt = str;
                }
            }
            curStrat = bestStratObj;
        }
        return movement.getMovement(myCar, curStrat.handleStrategy(myCar, otherCar, ball, team));
    }
}