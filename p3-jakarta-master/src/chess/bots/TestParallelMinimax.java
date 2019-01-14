package chess.bots;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Move;
import tests.Experiment;

public class TestParallelMinimax<M extends Move<M>, B extends Board<M, B>> extends
        AbstractSearcher<M, B> {
	private final ForkJoinPool POOL = new ForkJoinPool();
	private static final int DV_CUTOFF = 1;

    public M getBestMove(B board, int myTime, int opTime) {
		List<M> moves = board.generateMoves();
		return POOL.invoke(new SplitTask(board, moves, 0, moves.size(), ply, 0, null)).move;
    }
    
    private class SplitTask extends RecursiveTask<BestMove<M>>{
		List<M> moves;
		int lo,hi,ply;
		B board;
		int flag;
		M move;
		
		public SplitTask (B board, List<M> moves, int lo, int hi, int ply, int flag, M move){
			this.moves = moves;
			this.hi = hi;
			this.lo = lo;
			this.board = board;
			this.ply = ply - flag;
			this.flag = flag;
			this.move = move;
		}
		
		protected BestMove<M> compute() {
			if(flag == 1) {
				board = board.copy();
				Experiment.NODE.increment();
				board.applyMove(move);
				moves = board.generateMoves();
				if(moves.isEmpty()) {
					if(board.inCheck()) {
						return new BestMove(-evaluator.mate() - ply);
					}else {
						return new BestMove(-evaluator.stalemate());
					}
				}
				hi = moves.size();
			}
			if (ply <= cutoff) {
				return TestMinimax.minimax(evaluator, board, ply);
			}
			if(hi - lo <= DV_CUTOFF) {	
				SplitTask task1 = new SplitTask(board, null, 0, 0, ply , 1, moves.get(lo));
				BestMove<M> bestMove = task1.compute().negate();
				bestMove.move = moves.get(lo);
				
				
				List<SplitTask> list = new ArrayList<SplitTask>();
				for (int i = lo + 1; i < hi; i++) {			
					SplitTask task = new SplitTask(board, null, 0, 0, ply , 1, moves.get(i));
					list.add(task);
					task.fork(); //copy board inside child thread
				}
				for (int i = lo + 1; i < hi; i++) {			
					BestMove<M> curr = list.get(i - lo - 1).join().negate();
					curr.move = moves.get(i);
					if (curr.value > bestMove.value) {
						bestMove = curr;
					}			
				}
				return bestMove;
			}			
			
			
			int mid = lo + (hi - lo) / 2;			
			SplitTask left = new SplitTask(board, moves, lo, mid, ply, 0, null);
			SplitTask right = new SplitTask(board, moves, mid, hi, ply, 0, null);
			
			left.fork();
			BestMove<M> rightResult = right.compute();
			BestMove<M> leftResult = left.join();
			
			if (rightResult.value > leftResult.value) {
				return rightResult;
			} else {
				return leftResult;
			}
		}
	}
}