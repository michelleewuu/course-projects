package tests;

import java.util.concurrent.atomic.LongAdder;

import chess.board.ArrayBoard;
import chess.board.ArrayMove;
import chess.bots.LazySearcher;
import chess.bots.TestAlphaBeta;
import chess.bots.TestJamboree;
import chess.bots.TestMinimax;
import chess.bots.TestParallelMinimax;
import chess.game.SimpleEvaluator;
import cse332.chess.interfaces.Move;
import cse332.chess.interfaces.Searcher;

public class Experiment {
	public static LongAdder NODE = new LongAdder();
	
    //public static final String STARTING_POSITION = "rnbqkb1r/pp2n1pp/4p3/2ppPp2/2B5/2N2N2/PPPP1PPP/R1BQK2R w KQkq d6";
    /*public static final String[] STARTING_POSITIONS = {"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -",
    	"rnbqkbnr/pp1pp1pp/8/2p2p2/4P3/2N2N2/PPPP1PPP/R1BQKB1R b KQkq e3", "rnbqkb1r/pp2n1pp/4p3/2ppPp2/2B5/2N2N2/PPPP1PPP/R1BQK2R w KQkq d6",
    	"r2qkb1r/pp1nn1pp/4p3/2ppPp2/8/2N2N2/PPPP1PPP/R1BQ1RK1 b kq -", "r2qkb1r/pp4pp/4p3/2ppnp2/8/2NP4/PPP2PPP/R1BQ1RK1 w kq -",
    	"r2qkb1r/pp5p/2n1p1p1/2pp1p2/5B2/2NP1Q2/PPP2PPP/R4RK1 b kq -", "r3k2r/pp5p/2n1p1p1/q1pp1p2/5B2/2bP1Q2/PPP2PPP/R4RK1 w kq -",
    	"2kr3r/pp5p/2n1p1p1/2pp1p2/5B2/2qP1QP1/P1P2P1P/R1R3K1 b - -", "2kr3r/p6p/2n3p1/2pppp2/1p6/2qPB1P1/P1P2PQP/R1R3K1 w - -",
    	"2kr2r1/p6p/2n5/2ppppp1/1p6/2qPB1P1/P1P2P1P/R1R3KQ b - -", "2kr2r1/p6p/2n5/2ppp3/1p6/2qPp3/P1P2P1P/R1R2K1Q w - -",
    	"2k2Qr1/p6p/2n5/3pp3/1pp5/2qPP3/P1P4P/R1R2K2 b - -", "2k3r1/p6p/8/3pp3/1ppnP3/2qP4/P1P1K2P/R1R5 w - -"};*/
	public static final String[] STARTING_POSITIONS = {"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -",
			"r3k2r/pp5p/2n1p1p1/q1pp1p2/5B2/2bP1Q2/PPP2PPP/R4RK1 w kq -", "2k3r1/p6p/8/3pp3/1ppnP3/2qP4/P1P1K2P/R1R5 w - -"};
   
    
    public static ArrayMove getBestMove(String fen, Searcher<ArrayMove, ArrayBoard> searcher, int depth, int cutoff) { 
        searcher.setDepth(depth);
        searcher.setCutoff(cutoff);
        searcher.setEvaluator(new SimpleEvaluator());

        return searcher.getBestMove(ArrayBoard.FACTORY.create().init(fen), 0, 0);
    }
    
    public static void printMove(String fen, Searcher<ArrayMove, ArrayBoard> searcher, int depth, int cutoff) {
        String botName = searcher.getClass().toString().split(" ")[1].replace("chess.bots.", "");
        System.out.println(botName + " returned " + getBestMove(fen, searcher, depth, cutoff));
    }
    public static void main(String[] args) {
        Searcher<ArrayMove, ArrayBoard> dumb = new TestParallelMinimax<>();
        double[] res = new double[3];
        for (int j = 0; j < 5; j++) {
	        for (int i = 0; i < STARTING_POSITIONS.length; i++) {
	        	NODE.reset();
	        	long start = System.currentTimeMillis();
	        	printMove(STARTING_POSITIONS[i], dumb, 5, 4);
	        	long finish = System.currentTimeMillis();
	        	//sum.add(NODE.sum());
	        	System.out.println(NODE.sum());
	        	//System.out.println((finish - start) * 1.0 / 1000);
	        	res[i] += (finish - start) * 1.0 / 1000;
	        }
        }
        //System.out.println("Early Board: " + res[0] / 5 );
        System.out.println("Middle Board: " + res[1] / 5 );
        //System.out.println("Late Board: " + res[2] / 5 );
    }
}
