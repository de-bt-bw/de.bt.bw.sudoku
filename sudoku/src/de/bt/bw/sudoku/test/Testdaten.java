/**
 * 
 */
package de.bt.bw.sudoku.test;

/**
 * Rätsel und Lösungen zum Testen
 */
public class Testdaten {
	
	public static final int[][] raetsel_83_1 =
		{{0, 0, 0, 8, 0, 0, 7, 0, 5},
		 {0, 6, 0, 7, 1, 2, 9, 0, 0},
		 {0, 4, 0, 0, 0, 3, 0, 0, 1},
		 {0, 0, 0, 0, 3, 9, 6, 0, 0},
		 {3, 0, 0, 6, 0, 4, 0, 0, 7},
		 {0, 0, 4, 1, 7, 0, 0, 0, 0},
		 {2, 0, 0, 3, 0, 0, 0, 9, 0},
		 {0, 0, 3, 4, 6, 1, 0, 8, 0},
		 {4, 0, 6, 0, 0, 5, 0, 0, 0}
		}; // mittel, 5
	
	public static final int[][] loesung_83_1 =
		{{1, 3, 9, 8, 4, 6, 7, 2, 5},
		 {8, 6, 5, 7, 1, 2, 9, 3, 4},
		 {7, 4, 2, 5, 9, 3, 8, 6, 1},
		 {5, 1, 7, 2, 3, 9, 6, 4, 8},
		 {3, 9, 8, 6, 5, 4, 2, 1, 7},
		 {6, 2, 4, 1, 7, 8, 3, 5, 9},
		 {2, 5, 1, 3, 8, 7, 4, 9, 6},
		 {9, 7, 3, 4, 6, 1, 5, 8, 2},
		 {4, 8, 6, 9, 2, 5, 1, 7, 3}
		};
	
	public static final int[][] raetsel_FitImKopf =
		{{0, 0, 7, 6, 0, 1, 8, 0, 0},
		 {0, 2, 0, 0, 0, 0, 0, 1, 0},
		 {6, 0, 0, 0, 0, 0, 0, 0, 4},
		 {2, 0, 0, 1, 9, 3, 0, 0, 8},
		 {0, 0, 0, 7, 0, 4, 0, 0, 0},
		 {1, 0, 0, 5, 8, 6, 0, 0, 7},
		 {7, 0, 0, 0, 0, 0, 0, 0, 2},
		 {0, 3, 0, 0, 0, 0, 0, 9, 0},
		 {0, 0, 8, 2, 0, 9, 5, 0, 0}
		}; // schwer
	
	public static final int[][] loesung_FitImKopf =
		{{9, 4, 7, 6, 3, 1, 8, 2, 5},
		 {3, 2, 5, 8, 4, 7, 9, 1, 6},
		 {6, 8, 1, 9, 5, 2, 3, 7, 4},
		 {2, 7, 4, 1, 9, 3, 6, 5, 8},
		 {8, 5, 6, 7, 2, 4, 1, 3, 9},
		 {1, 9, 3, 5, 8, 6, 2, 4, 7},
		 {7, 6, 9, 3, 1, 5, 4, 8, 2},
		 {5, 3, 2, 4, 6, 8, 7, 9, 1},
		 {4, 1, 8, 2, 7, 9, 5, 6, 3}
		};
	
	public static final int[][] raetsel_Leer =
		{{0, 0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0, 0}
		}; // mehrdeutig
	
	/**
	 * Das leere Rätsel hat alle möglichen Sudoku-Lösungen.
	 * Dies ist die vermutete Lösung, wenn man das Rätsel 
	 * zeilenweise löst und stets den kleinsten möglichen
	 * Wert wählt. Dieser Ansatz erfordert Backtracking,
	 * d.h. ein Greedy-Algorithmus ohne Backtracking 
	 * führt nicht zu einer Lösung, sondern endet in 
	 * einer Sackgasse.
	 * 
	 */
	public static final int[][] loesung_Leer =
		{{1, 2, 3, 4, 5, 6, 7, 8, 9},
		 {4, 5, 6, 7, 8, 9, 1, 2, 3},
		 {7, 8, 9, 1, 2, 3, 4, 5, 6},
		 {2, 1, 4, 3, 6, 5, 8, 9, 7},
		 {3, 6, 5, 8, 9, 7, 2, 1, 4},
		 {8, 9, 7, 2, 1, 4, 3, 6, 5},
		 {5, 3, 1, 6, 4, 2, 9, 7, 8},
		 {6, 4, 2, 9, 7, 8, 5, 3, 1},
		 {9, 7, 8, 5, 3, 1, 6, 4, 2}
		};
	
	public static final int[][] raetsel_83_51 =
		{{0, 0, 0, 5, 4, 0, 0, 0, 0},
		 {6, 0, 0, 0, 0, 0, 1, 9, 0},
		 {0, 0, 0, 0, 0, 6, 0, 2, 3},
		 {8, 0, 3, 9, 5, 0, 0, 0, 0},
		 {4, 9, 0, 0, 0, 0, 0, 8, 5},
		 {0, 0, 0, 0, 8, 7, 9, 0, 6},
		 {1, 3, 0, 6, 0, 0, 0, 0, 0},
		 {0, 4, 5, 0, 0, 0, 0, 0, 8},
		 {0, 0, 0, 0, 3, 8, 0, 0, 0}
		}; // schwer, 6
	
	public static final int[][] raetsel_Golden_1 =
		{{6, 0, 2, 7, 0, 1, 4, 5, 0},
		 {9, 5, 3, 2, 0, 0, 7, 8, 1},
		 {7, 4, 1, 0, 0, 0, 3, 6, 2},
		 {5, 0, 0, 0, 8, 2, 6, 0, 0},
		 {2, 6, 4, 0, 0, 0, 8, 3, 7},
		 {0, 0, 8, 6, 4, 0, 0, 0, 5},
		 {8, 9, 7, 0, 0, 0, 1, 2, 6},
		 {0, 2, 0, 0, 0, 6, 9, 4, 3},
		 {0, 3, 6, 1, 0, 9, 5, 0, 8}
		}; // leicht
	
	public static final int[][] raetsel_Golden_161 =
		{{0, 0, 2, 0, 1, 6, 3, 8, 7},
		 {0, 0, 0, 0, 0, 0, 0, 0, 9},
		 {0, 3, 0, 0, 0, 0, 6, 5, 0},
		 {0, 0, 1, 0, 8, 0, 0, 0, 0},
		 {2, 0, 9, 3, 6, 1, 4, 0, 8},
		 {0, 0, 0, 0, 2, 0, 1, 0, 0},
		 {0, 1, 7, 0, 0, 0, 0, 2, 0},
		 {5, 0, 0, 0, 0, 0, 0, 0, 0},
		 {9, 2, 4, 7, 5, 0, 8, 0, 0}
		}; // schwer
	
	public static final int[][] raetsel_Golden_309 =
		{{6, 0, 0, 0, 2, 0, 0, 0, 0},
		 {1, 0, 0, 0, 8, 6, 5, 4, 0},
		 {0, 0, 0, 7, 0, 1, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 7, 6, 0},
		 {2, 0, 8, 0, 0, 0, 1, 0, 4},
		 {0, 7, 4, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 9, 0, 5, 4, 0, 0},
		 {0, 2, 1, 6, 4, 0, 0, 0, 3},
		 {0, 0, 0, 0, 1, 0, 0, 0, 5}
		};

	public static final int[][] raetsel_Golden_311 = 
		{{6, 0, 0, 8, 0, 0, 0, 7, 0},
		 {0, 3, 0, 0, 0, 0, 0, 2, 0},
		 {9, 0, 0, 0, 2, 3, 0, 6, 0},
		 {1, 0, 0, 5, 0, 0, 0, 3, 0},
		 {0, 7, 3, 0, 8, 0, 9, 5, 0},
		 {0, 5, 0, 0, 0, 2, 0, 0, 1},
		 {0, 4, 0, 2, 1, 0, 0, 0, 5},
		 {0, 9, 0, 0, 0, 0, 0, 1, 0},
		 {0, 2, 0, 0, 0, 9, 0, 0, 7}
		};
	
	public static final int[][] raetsel_Golden_316 = 
		{{0, 0, 3, 0, 4, 0, 0, 0, 0},
		 {0, 7, 6, 0, 0, 3, 0, 0, 0},
		 {0, 0, 0, 0, 0, 7, 0, 0, 2},
		 {2, 0, 8, 7, 0, 0, 0, 0, 1},
		 {9, 0, 0, 0, 1, 0, 0, 0, 7},
		 {7, 0, 0, 0, 0, 5, 9, 0, 8},
		 {4, 0, 0, 5, 0, 0, 0, 0, 0},
		 {0, 0, 0, 4, 0, 0, 8, 6, 0},
		 {0, 0, 0, 0, 2, 0, 1, 0, 0}
		};
}
