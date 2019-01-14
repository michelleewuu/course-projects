package chess.bots;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Move;

public class JamboreeSearcher<M extends Move<M>, B extends Board<M, B>> extends AbstractSearcher<M, B> {
	private final ForkJoinPool POOL = new ForkJoinPool();
	private static final double PERCENTAGE_SEQUENTIAL = 0.25;
	private static final int DV_CUTOFF = 3;
	
	
	public M getBestMove(B board, int myTime, int opTime) {
		
		List<M> moves = board.generateMoves();
		return POOL.invoke(new Jamboree(board, moves, ply + 1, (int) (moves.size() * PERCENTAGE_SEQUENTIAL), moves.size(),
				-evaluator.infty(), evaluator.infty(), 1, null)).move;
	}

	private class Jamboree extends RecursiveTask<BestMove<M>> {
		int alpha, beta, ply, lo, hi;
		B board;
		int flag;
		M move;
		List<M> moves;

		public Jamboree(B board, List<M> moves, int ply, int lo, int hi, int alpha, int beta, int flag, M move) {
			this.alpha = alpha;
			this.beta = beta;
			this.board = board;
			this.ply = ply - flag;
			this.flag = flag;
			this.move = move;
			this.hi = hi;
			this.lo = lo;
			this.moves = moves;
		}

		protected BestMove<M> compute() {
			BestMove<M> bestMove = new BestMove<M>(-evaluator.infty());
			
			if (flag == 1) {
				board = board.copy();
				if(move != null) {
					board.applyMove(move);
				}
				moves = board.generateMoves();
				if(moves.isEmpty()) {
		    			if(board.inCheck()) {
		    				bestMove.value = -evaluator.mate() - ply;
		    			}else {
		    				bestMove.value = -evaluator.stalemate();
		    			}
		    			return bestMove;
		    		}
				hi = moves.size();
				lo = (int) (PERCENTAGE_SEQUENTIAL * this.moves.size());
				
				if (ply <= cutoff) {
					return AlphaBetaSearcher.alphaBeta(evaluator, board, ply, alpha, beta);
				}
				
				for (int i = 0; i < PERCENTAGE_SEQUENTIAL * this.moves.size(); i++) {
					Jamboree task = new Jamboree(board, null, ply, lo, hi, -beta, -alpha, 1, this.moves.get(i));
					BestMove<M> curr = task.compute().negate();
					curr.move = moves.get(i);
					if (curr.value > bestMove.value) {
						bestMove = curr;
					}
					if (bestMove.value > alpha) {
						alpha = bestMove.value;
					}
					if (alpha >= beta) {
						return bestMove;
					}
				}
			}

			if (hi - lo <= DV_CUTOFF) {
				List<Jamboree> list = new ArrayList<Jamboree>();

				for (int i = lo; i < hi; i++) {
					Jamboree task = new Jamboree(board, null, ply, 0, 0, -beta, -alpha, 1, this.moves.get(i));
					list.add(task);
					task.fork();
				}

				for (int i = lo; i < hi; i++) {
					BestMove<M> curr = list.get(i - lo).join().negate();
					curr.move = this.moves.get(i);
					if (curr.value > bestMove.value) {
						bestMove = curr;
					}
					if (bestMove.value > alpha) {
						alpha = bestMove.value;
					}
					if (alpha >= beta) {
						return bestMove;
					}

				}
				return bestMove;

			} else {
				int mid = lo + (hi - lo) / 2;
				Jamboree left = new Jamboree(board, moves, ply, lo, mid, alpha, beta, 0, null);
				Jamboree right = new Jamboree(board, moves, ply, mid, hi, alpha, beta, 0, null);

				left.fork();
				BestMove<M> curr = right.compute();
				BestMove<M> leftResult = left.join();
				
				if (curr.value < leftResult.value) {
					curr = leftResult;
				}
				if (curr.value > bestMove.value) {
					bestMove = curr;
					alpha = curr.value;
				}
				if (alpha >= beta) {
					return bestMove;
				}
				return bestMove;
			}
		}
	}
}
