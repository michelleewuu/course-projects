package chess.bots;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import chess.board.ArrayBoard;
import chess.game.SimpleEvaluator;
import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Move;

public class Jamboree2<M extends Move<M>, B extends Board<M, B>> extends AbstractSearcher<M, B> {
	private final ForkJoinPool POOL = new ForkJoinPool(28);
	private static final double PERCENTAGE_SEQUENTIAL = 0.5;
	private static final int DV_CUTOFF = 3;
	private static final int AGGR_PLY = 7;
	private static final int DFEN_PLY = 5;

	private class SortByValue implements Comparator<M>{
		B board;
		SimpleEvaluator e;
		
		public SortByValue(B board) {
			this.board = board;
			e = new SimpleEvaluator();
		}
		
		public int compare(M a, M b) {
			if((a.isCapture() && b.isCapture()) || (!a.isCapture() && !b.isCapture())) {
				return 0;
			}else if(a.isCapture()){
				return -1;
			}else {
				return 1;
			}
//			board.applyMove(a);
//			int aVal = e.eval((ArrayBoard) board);
//			board.undoMove();
//			board.applyMove(b);
//			int bVal = e.eval((ArrayBoard) board);
//			board.undoMove();
//			
//			return aVal - bVal;
//			
		}
	}
	
	public M getBestMove(B board, int myTime, int opTime) {
		
		if(myTime < 80000) {
			setDepth(6);
		} else if(myTime < 40000){
			setDepth(5);
		} else if(myTime > 120000){
			setDepth(7);
		}
		List<M> moves = board.generateMoves();
		moves.sort(new SortByValue(board));
		BestMove<M> move = POOL.invoke(new Jamboree(board, moves, ply + 1, (int) (moves.size() * PERCENTAGE_SEQUENTIAL), moves.size(),
				-evaluator.infty() + 1, evaluator.infty(), 1, null));
		System.out.println("Applied " + move.move +" with value " + move.value);
		System.out.println();
		System.out.println(board);
		return move.move;
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
				moves.sort(new SortByValue(board));
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
