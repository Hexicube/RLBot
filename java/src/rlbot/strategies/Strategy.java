package rlbot.strategies;

import rlbot.*;
import rlbot.Bot.Team;
import rlbot.strategies.StrategyController.StrategyOutput;

public abstract class Strategy {
    public abstract double getStrategyStrength(ObjInfo myCar, ObjInfo otherCar, ObjInfo ball, Team team);
    public abstract StrategyOutput handleStrategy(ObjInfo myCar, ObjInfo otherCar, ObjInfo ball, Team team);
    public abstract boolean canRelease();
}