package chess.bots;

import java.util.*;
import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Evaluator;
import cse332.chess.interfaces.Move;

/**
 * This class should implement the minimax algorithm as described in the
 * assignment handouts.
 */
public class SimpleSearcher<M extends Move<M>, B extends Board<M, B>> extends
        AbstractSearcher<M, B> {
	
	
    public M getBestMove(B board, int myTime, int opTime) {
        /* Calculate the best move */
        return minimax(this.evaluator, board, ply).move;
    }

    static <M extends Move<M>, B extends Board<M, B>> BestMove<M> minimax(Evaluator<B> evaluator, B board, int depth) {
        //base case
    		if(depth == 0) {
    			return new BestMove(evaluator.eval(board));
    		}
    		
    		BestMove<M> bestMove = new BestMove(Integer.MIN_VALUE);
    		List<M> moves = board.generateMoves();
    		//recursive case of check
    		if(moves.isEmpty()) {
    			if(board.inCheck()) {
    				bestMove.value = -evaluator.mate() - depth;
    			}else {
    				bestMove.value = -evaluator.stalemate();
    			}
    			return bestMove;
    		}
    		
    		//recursive case of non-check
    		for(M move : moves) {
    			board.applyMove(move);
    			BestMove<M> curr = minimax(evaluator, board, depth-1).negate();
    			curr.move = move;
    			board.undoMove();
    			if(curr.value > bestMove.value) {
    				bestMove = curr;
    			}
    		}
    		return bestMove;
    }
}