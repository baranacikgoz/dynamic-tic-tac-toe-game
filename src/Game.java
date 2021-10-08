import java.util.Scanner;

public class Game {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome the tic-tac-toe.\nPlease provide n for n x n board: ");
		try {
			TicTacToe game = new TicTacToe(scanner.nextInt());
			game.playGame();
		} catch (Exception e) {
			System.out.println(e.getMessage() + ". Therefore game is starting with 3x3 size. ");
			TicTacToe game = new TicTacToe(3);
			game.playGame();
		}

	}
}

class TicTacToe {
	public static final byte X = 1, O = -1, EMPTY = 0;
	private byte board[][];
	private int size;
	private int totalEntries;// To end the game if there is no winner
	private byte currentPlayer; // Whose turn is it ?

	public TicTacToe(int size) throws IllegalArgumentException {
		if (size < 3) {
			throw new IllegalArgumentException("Board size can not be smaller than 3x3");
		} else {
			this.size = size;
			board = new byte[size][size];
			this.currentPlayer = X;
			totalEntries = 0;
		}
	}

	public void playGame() {
		GUI();
		Scanner s = new Scanner(System.in);
		while (totalEntries != size * size) {
			int i = 0;
			int j = 0;
			switch (currentPlayer) {
			case 1 -> System.out.println("X's turn.");
			case -1 -> System.out.println("O's turn.");
			}

			System.out.println("Please provide the x of the coordinate you've want to mark: ");
			i = s.nextInt();
			System.out.println("Please provide the y of the coordinate you've want to mark:  ");
			j = s.nextInt();
			try {
				putMark(i, j);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			GUI();
		}
		s.close();

	}

	public void putMark(int i, int j) throws IllegalArgumentException {// i and j stands for coordinate x,y directions
		if ((i < 0) || (i > size - 1) || (j < 0) || (j > size - 1)) {
			throw new IllegalArgumentException("Board limits are exceeded. Please try again: ");
		}
		if (board[i][j] != EMPTY) {
			throw new IllegalArgumentException("The position you've tried to mark is not empty. Please try again: ");
		}
		totalEntries++;
		if (totalEntries == size * size) {
			System.out.println("Tie : Nobody wins!");
			System.exit(1);
		}
		board[i][j] = currentPlayer;
		isWin(i, j, currentPlayer);
		currentPlayer *= -1;
		// GUI();
	}

	public void isWin(int i, int j, int player) {

		if (i == j) {// check if the point is in a diagonal
			int diagonal1Total = 0;
			for (int a = 0; a < board.length; a++) {
				diagonal1Total += board[a][a];
			}
			if (diagonal1Total == player * board.length) {
				declareWinner(player);
			}
		}

		if (i + j == board.length -1) { // check if the point is in the other diagonal
			int diagonal2Total = 0;
			int a = board.length - 1;
			int b = 0;
			while (a >= 0) {
				diagonal2Total += board[a][b];
				a--;
				b++;
			}
			if (diagonal2Total == player * board.length) {
				declareWinner(player);
			}
		}

		int rowTotal = 0;
		for (int a = 0; a < board.length; a++) {
			rowTotal += board[i][a];
		}
		if (rowTotal == player * board.length) {
			declareWinner(player);
		}
		
		int columnTotal = 0;
		for(int m=0; m < board.length; m++ ) {
			columnTotal += board[m][j];
		}
		if (columnTotal == player * board.length) {
			declareWinner(player);
		}
	}

	public void declareWinner(int player) {
		GUI();
		StringBuilder sb = new StringBuilder();
		switch (player) {
		case -1 -> sb.append("O");
		case 1 -> sb.append("X");
		}
		sb.append(" won.");
		System.out.println(sb.toString());
		System.exit(1);

	}

	public void GUI() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for (byte i = 0; i < size; i++) {
			for (byte j = 0; j < size; j++) {
				switch (board[i][j]) {
				case X:
					sb.append("X");
					break;
				case O:
					sb.append("O");
					break;
				case EMPTY:
					sb.append(" ");
					break;
				}
				if (j < size - 1)
					sb.append("|"); // column boundary
			}
			if (i < size - 1) {
				sb.append("\n");
				for (int n = 0; n < board.length; n++) {
					sb.append("--");
				}
				sb.append("\n");
			}
			; // row boundary
		}
		System.out.println(sb + "\n");
	}
}
