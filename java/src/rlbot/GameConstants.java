package rlbot;

public class GameConstants {
    public static class Speeds {
        public static final double TURN_RATE = 1; //double-check this
        public static final double GRAVITY = -6.5;
        public static final double DODGE_BONUS_FORWARD = 10;
        public static final double DODGE_LOSS_MULT = 0.5;
        
        public static final double TOP_SPEED_BOOST  = 28.2;
        public static final double TOP_SPEED_NORMAL = 46;
        public static final double TOP_SPEED_BALL   = 56.5;
        
        public static final double BALL_AIR_FRIC_MULT = 0.97;
        public static final double BALL_GROUND_FRIC = -2.3;
        public static final double BALL_BOUNCINESS = 0.75;
    }
    
    public static class Sizes {
        public static final double HALF_FIELD_WIDTH  = 81.92;
        public static final double HALF_FIELD_LENGTH = 102.4;
        public static final double      FIELD_HEIGHT = 40; //double-check this
        
        public static final double HALF_GOAL_WIDTH  = HALF_FIELD_WIDTH / 4.5; //double-check this
        public static final double      GOAL_HEIGHT = HALF_GOAL_WIDTH * 0.8;  //double-check this
        
        public static final double CORNER_TAPER = 12.5; //double-check this
        public static final double EDGE_TAPER   = 3;    //double-check this
        
        public static final double BALL_RADIUS = 1.83;
    }
    
    public static class Locations {
        public static final Vector   BLUE_GOAL = new Vector(0, 0, 0);
        public static final Vector ORANGE_GOAL = new Vector(0, 0, 0);
        
        public static final double[][] LARGE_PADS = new double[][]{
            { 61, -82}, { 61, 0}, { 61, 82},
            {-61, -82}, {-61, 0}, {-61, 82}
        };
        
        public static final double[][] SMALL_PADS = new double[][]{
            //TODO
        };
    }
}