package chess.bots;

import java.util.List;

import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Evaluator;
import cse332.chess.interfaces.Move;

public class AlphaBetaSearcher<M extends Move<M>, B extends Board<M, B>> extends AbstractSearcher<M, B> {
	
    public M getBestMove(B board, int myTime, int opTime) {
        /* Calculate the best move */
        return alphaBeta(this.evaluator, board, ply, -evaluator.infty(), evaluator.infty()).move;
    }

    static <M extends Move<M>, B extends Board<M, B>> BestMove<M> alphaBeta(Evaluator<B> evaluator, B board, int depth, int alpha, int beta) {
        //base case
    		if(depth == 0) {
    			return new BestMove(evaluator.eval(board));
    		}
    		BestMove<M> bestMove = new BestMove(-evaluator.infty());
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