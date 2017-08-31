package rlbot.helper;

import rlbot.AgentOutput;
import rlbot.ObjInfo;

public abstract class MovementAction {
    public abstract boolean isDone(ObjInfo car);
    public abstract AgentOutput doMovement(ObjInfo car);
}