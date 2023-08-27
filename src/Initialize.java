import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;

public class Initialize extends StackPane {

    public static Square[][] squaresMatrix = new Square[8][8]; //store square references
    public static ArrayList<Piece> allPieces = new ArrayList<>(32);//store piece references
    public static ArrayList<Piece> twoKings = new ArrayList<>(2); //store two kings
    public static ArrayList<Piece> shortRooks = new ArrayList<>(2); // store two short rooks
    public static ArrayList<Piece> longRooks = new ArrayList<>(2); //store two long rooks
    public static String prevColor;

    //Initialize the chessBoard
    Initialize() {
        playStartSound();
        prevColor = "black"; //allows white to play

        //Drawing squares of chess board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square;
                if ((i+j) % 2 == 0) {
                    square = new Square(i,j,Color.WHITE);
                }else{
                    square = new Square(i,j,Color.GREY);
                }
                squaresMatrix[i][j] = square;
                getChildren().add(square);
            }
        }

        //setting up black pieces

        //setting up black pawns
        Pawn pawn1B = new Pawn(0,1,"black",this);
        getChildren().add(pawn1B);
        Pawn pawn2B = new Pawn(1,1,"black",this);
        getChildren().add(pawn2B);
        Pawn pawn3B = new Pawn(2,1,"black",this);
        getChildren().add(pawn3B);
        Pawn pawn4B = new Pawn(3,1,"black",this);
        getChildren().add(pawn4B);
        Pawn pawn5B = new Pawn(4,1,"black",this);
        getChildren().add(pawn5B);
        Pawn pawn6B = new Pawn(5,1,"black",this);
        getChildren().add(pawn6B);
        Pawn pawn7B = new Pawn(6,1,"black",this);
        getChildren().add(pawn7B);
        Pawn pawn8B = new Pawn(7,1,"black",this);
        getChildren().add(pawn8B);

        //setting up black rooks
        Rook rook1B = new Rook(0,0,"black",this);
        getChildren().add(rook1B);
        Rook rook2B = new Rook(7,0,"black",this);
        getChildren().add(rook2B);

        //setting up black knights
        Knight knight1B = new Knight(1, 0,"black",this);
        getChildren().add(knight1B);
        Knight knight2B = new Knight(6,0,"black",this);
        getChildren().add(knight2B);

        //setting up black bishops
        Bishop bishop1B = new Bishop(2,0,"black",this);
        getChildren().add(bishop1B);
        Bishop bishop2B = new Bishop(5,0,"black",this);
        getChildren().add(bishop2B);

        //setting up black queen and king
        Queen queen1B = new Queen(3,0,"black",this);
        getChildren().add(queen1B);
        King king1B = new King(4,0,"black",this);
        getChildren().add(king1B);


        //setting up white pawns
        Pawn pawn1W = new Pawn(0,6,"white",this);
        getChildren().add(pawn1W);
        Pawn pawn2W = new Pawn(1,6,"white",this);
        getChildren().add(pawn2W);
        Pawn pawn3W = new Pawn(2,6,"white",this);
        getChildren().add(pawn3W);
        Pawn pawn4W = new Pawn(3,6,"white",this);
        getChildren().add(pawn4W);
        Pawn pawn5W = new Pawn(4,6,"white",this);
        getChildren().add(pawn5W);
        Pawn pawn6W = new Pawn(5,6,"white",this);
        getChildren().add(pawn6W);
        Pawn pawn7W = new Pawn(6,6,"white",this);
        getChildren().add(pawn7W);
        Pawn pawn8W = new Pawn(7,6,"white",this);
        getChildren().add(pawn8W);

        //setting up white rooks
        Rook rook1W = new Rook(0,7,"white",this);
        getChildren().add(rook1W);
        Rook rook2W = new Rook(7,7,"white",this);
        getChildren().add(rook2W);

        //setting up black knights
        Knight knight1W = new Knight(1, 7,"white",this);
        getChildren().add(knight1W);
        Knight knight2W = new Knight(6,7,"white",this);
        getChildren().add(knight2W);

        //setting up black bishops
        Bishop bishop1W = new Bishop(2,7,"white",this);
        getChildren().add(bishop1W);
        Bishop bishop2W = new Bishop(5,7,"white",this);
        getChildren().add(bishop2W);

        //setting up black queen and king
        Queen queen1W = new Queen(3,7,"white",this);
        getChildren().add(queen1W);
        King king1W = new King(4,7,"white",this);
        getChildren().add(king1W);


        //store references for all pieces
        allPieces.add(pawn1W);
        allPieces.add(pawn2W);
        allPieces.add(pawn3W);
        allPieces.add(pawn4W);
        allPieces.add(pawn5W);
        allPieces.add(pawn6W);
        allPieces.add(pawn7W);
        allPieces.add(pawn8W);
        allPieces.add(rook1W);
        allPieces.add(rook2W);
        allPieces.add(knight1W);
        allPieces.add(knight2W);
        allPieces.add(bishop1W);
        allPieces.add(bishop2W);
        allPieces.add(queen1W);
        allPieces.add(king1W);//15

        allPieces.add(pawn1B);//16
        allPieces.add(pawn2B);
        allPieces.add(pawn3B);
        allPieces.add(pawn4B);
        allPieces.add(pawn5B);
        allPieces.add(pawn6B);
        allPieces.add(pawn7B);
        allPieces.add(pawn8B);
        allPieces.add(rook1B);
        allPieces.add(rook2B);
        allPieces.add(knight1B);
        allPieces.add(knight2B);
        allPieces.add(bishop1B);
        allPieces.add(bishop2B);
        allPieces.add(queen1B);
        allPieces.add(king1B); //31


        twoKings.add(king1W);
        twoKings.add(king1B);

        shortRooks.add(rook2W);
        shortRooks.add(rook2B);

        longRooks.add(rook1W);
        longRooks.add(rook1B);
    }
    public void remove(Piece piece){
        for(int i = 0; i<allPieces.size(); i++){
            if(piece == allPieces.get(i)){
                getChildren().remove(allPieces.get(i));
                allPieces.remove(i);
            }
        }
    }
    public void resetPiece(Piece piece){
        allPieces.add(piece);
        getChildren().add(piece);
    }
    private void playStartSound() {
        MediaPlayer mediaPlayer;
        String audioFilePath = "soundEffects/start.mp3";
        Media media = new Media(new File(audioFilePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

}
