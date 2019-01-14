package traffic;

import java.util.List;

import chess.bots.BestMove;
import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Evaluator;
import cse332.chess.interfaces.Move;

public class TrafficSearcher<M extends Move<M>, B extends Board<M, B>> extends
        AbstractSearcher<M, B> {

	 public M getBestMove(B board, int myTime, int opTime) {
	        /* Calculate the best move */
	        return alphaBeta(this.evaluator, board, ply, Integer.MIN_VALUE + 1, Integer.MAX_VALUE).move;
	    }

	    static <M extends Move<M>, B extends Board<M, B>> BestMove<M> alphaBeta(Evaluator<B> evaluator, B board, int depth, int alpha, int beta) {
	        //base case
	    		if(depth == 0) {
	    			return new BestMove(evaluator.eval(board));
	    		}
	    		
	    		BestMove<M> bestMove = new BestMove(Integer.MIN_VALUE);
	    		List<M> moves = board.generateMoves();
	    		//recursive case of check
	    		if(moves.isEmpty()) {
	    			return new BestMove(evaluator.eval(board));
	    		}
	    		
	    		//recursive case of non-check
	    		for(M move : moves) {
	    			board.applyMove(move);
	    			BestMove<M> curr = alphaBeta(evaluator, board, depth - 1, -beta, -alpha).negate();
	    			curr.move = move;
	    			board.undoMove();
	    			
	    			if(curr.value > bestMove.value) { // update value
	    				bestMove = curr;
	    			}
	    			if(bestMove.value > alpha) { // update lower bound
	    				alpha = bestMove.value;
	    			}
	    			if(alpha >= beta) { // pruning case
	    				return bestMove;
	    			}
	    		}
	    		return bestMove;
	    }
}